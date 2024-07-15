package com.SoftwareTech.PrcScheduleWeb.service.AuthService;

import com.SoftwareTech.PrcScheduleWeb.config.EmailService;
import com.SoftwareTech.PrcScheduleWeb.config.StaticUtilMethods;
import com.SoftwareTech.PrcScheduleWeb.dto.AuthDto.DtoAuthenticationResponse;
import com.SoftwareTech.PrcScheduleWeb.dto.AuthDto.DtoAuthentication;
import com.SoftwareTech.PrcScheduleWeb.dto.AuthDto.DtoChangePassword;
import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.ReqDtoAddTeacherAccount;
import com.SoftwareTech.PrcScheduleWeb.model.Account;
import com.SoftwareTech.PrcScheduleWeb.model.enums.Role;
import com.SoftwareTech.PrcScheduleWeb.repository.AccountRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    @Autowired
    private final AccountRepository accountRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final StaticUtilMethods staticUtilMethods;
    @Autowired
    private final EmailService emailService;

    public DtoAuthenticationResponse register(ReqDtoAddTeacherAccount request) {
        Account account = Account.builder()
            .instituteEmail(request.getInstituteEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .creatingTime(LocalDateTime.now())
            .role(Role.MANAGER)
            .status(true)
            .build();
        var jwtToken = jwtService.generateToken(account);
        accountRepository.save(account);
        return DtoAuthenticationResponse.builder()
            .token(jwtToken)
            .role(Role.MANAGER)
            .build();
    }

    public DtoAuthenticationResponse authenticate(DtoAuthentication authObject) {
        //--Configure an AuthenticateToken by InputAccount.
        UsernamePasswordAuthenticationToken authenticateToken = new UsernamePasswordAuthenticationToken(
            authObject.getInstituteEmail(),
            authObject.getPassword()
        );
        //--Authenticate InputAccount with AuthenticationManager.
        //--Use the configured AuthenticationProvider for authentication.
        authenticationManager.authenticate(authenticateToken);
        Account account = accountRepository
            .findByInstituteEmail(authObject.getInstituteEmail())
            .orElseThrow(() -> new UsernameNotFoundException("Institute Email as Username not found"));

        var jwtToken = jwtService.generateToken(account);
        return DtoAuthenticationResponse.builder()
            .token(jwtToken)
            .role(account.getRole())
            .build();
    }

    public Cookie customizeAccessTokenToServeCookie(String jwtToken) {
        byte[] jwtTokenAsBytes = jwtToken.getBytes();
        String encodedJwtToken = Base64.getEncoder().encodeToString(jwtTokenAsBytes);

        Cookie accessTokenCookie = new Cookie("AccessToken", encodedJwtToken);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setSecure(true);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(2*60*60 - 1);
        return accessTokenCookie;
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        jwtService.clearAllTokenCookies(request, response);
    }

    @Transactional(rollbackOn = {Exception.class})
    public void changePassword(
        HttpServletRequest request,
        HttpServletResponse response,
        DtoChangePassword changePasswordObj
    ) {
        if (!changePasswordObj.getRetypePassword().equals(changePasswordObj.getPassword()))
            throw new IllegalArgumentException("Retype Password does not correct");

        Account user = staticUtilMethods.getAccountInfoInCookie(request);

        //--Confirming that the changed account's password belongs to the current login account.
        Account accountInDB = accountRepository
            .findByInstituteEmail(changePasswordObj.getInstituteEmail())
            .orElseThrow(() -> new NoSuchElementException("Institute Email not found!"));

        String otpCodeInSession = (String) request.getSession().getAttribute("OTP-" + user.getInstituteEmail());
        if (!otpCodeInSession.equals(changePasswordObj.getOtpCode()))
            throw new NoSuchElementException("Otp code does not correct");

        accountInDB.setPassword(passwordEncoder.encode(changePasswordObj.getPassword()));
        accountRepository.changePassword(accountInDB);
        request.getSession().removeAttribute("OTP-" + changePasswordObj.getInstituteEmail());
        //--Logout after changing password successfully
        jwtService.clearAllTokenCookies(request, response);
    }

    public void sendingOtpCode(HttpServletRequest request, String instituteEmail) {
        Account user = staticUtilMethods.getAccountInfoInCookie(request);
        if (!user.getInstituteEmail().equals(instituteEmail))
            throw new NoSuchElementException("Email is invalid");

        //--Remove the previous OTP code in session if it's existing.
        request.getSession().removeAttribute("OTP-" + instituteEmail);

        String otpCode = staticUtilMethods.generateOTP(6);
        String otpMailMessage = String.format("""
            <div>
                <p style="font-size: 18px">Không chia sẻ thông tin tài khoản phía dưới cho ai khác.
                    Vui lòng bảo mật tốt mã OTP của bạn!</p>
                <h2>Tài khoản: <b>%s</b></h2>
                <h2>OTP: <b>%s</b></h2>
            </div>
        """, user.getInstituteEmail(), otpCode);
        emailService.sendSimpleEmail(
            instituteEmail,
            "Mã OTP Cho Ứng Dụng Phân Công Lịch Thực Hành",
            otpMailMessage
        );
        //--Save into session for the next actions.
        request.getSession().setAttribute("OTP-" + instituteEmail, otpCode);
    }

    @Transactional(rollbackOn = {Exception.class})
    public void sendingOtpCodeForNewPassword(HttpServletRequest request) {
        String instituteEmail = request.getParameter("instituteEmail");
        if (instituteEmail.isEmpty())   throw new NoSuchElementException("Email is invalid");
        Account account = accountRepository
            .findByInstituteEmail(instituteEmail)
            .orElseThrow(() -> new NoSuchElementException("Email is invalid"));

        String otpCode = staticUtilMethods.generateOTP(8);
        String otpMailMessage = String.format("""
            <div>
                <p style="font-size: 18px">Không chia sẻ thông tin tài khoản phía dưới cho ai khác.
                    Vui lòng bảo mật tốt mật khẩu mới của bạn!</p>
                <h2>Tài khoản: <b>%s</b></h2>
                <h2>Mật khẩu: <b>%s</b></h2>
            </div>
        """, instituteEmail, otpCode);
        emailService.sendSimpleEmail(
            instituteEmail,
            "Mã OTP Cho Ứng Dụng Phân Công Lịch Thực Hành",
            otpMailMessage
        );
        account.setPassword(passwordEncoder.encode(otpCode));
        accountRepository.changePassword(account);
    }
}
