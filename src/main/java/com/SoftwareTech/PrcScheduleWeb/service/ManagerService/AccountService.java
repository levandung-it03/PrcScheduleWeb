package com.SoftwareTech.PrcScheduleWeb.service.ManagerService;

import com.SoftwareTech.PrcScheduleWeb.dto.AuthDto.DtoRegisterAccount;
import com.SoftwareTech.PrcScheduleWeb.model.Account;
import com.SoftwareTech.PrcScheduleWeb.model.enums.Role;
import com.SoftwareTech.PrcScheduleWeb.repository.AccountRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AccountService {
    @Autowired
    private final AccountRepository accountRepository;
    @Autowired
    private final PasswordEncoder getPasswordEncoder;

    public void addTeacherAccount(
        DtoRegisterAccount registerObject,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws IOException {
        final String url = "/manager/category/teacher/add-teacher-account";
        final String email = registerObject.instituteEmail().trim();
        final String password = registerObject.password().trim();
        final String retypePassword = registerObject.retypePassword().trim();
        final boolean isInvalidPassword = (password.length() < 8) || !password.equals(retypePassword);
        final boolean isInvalidUsername = !Pattern
            .compile("^[^@\\s]+[.\\w]*@(ptithcm\\.edu\\.vn|ptit\\.edu\\.vn|student\\.ptithcm\\.edu\\.vn)$")
            .matcher(email).matches();
        final boolean isExistingEmail = accountRepository.findByInstituteEmail(email).isPresent();

        if (isInvalidPassword) {
            request.setAttribute("registerObject", registerObject);
            response.sendRedirect(url + "?errorMessage=eMv1at01");
        }
        else if (isInvalidUsername) {
            request.setAttribute("registerObject", registerObject);
            response.sendRedirect(url + "?errorMessage=eMv1at02");
        }
        else if (isExistingEmail) {
            request.setAttribute("registerObject", registerObject);
            response.sendRedirect(url + "?errorMessage=eMv1at03");
        }
        else {
            Account account = Account.builder()
                .instituteEmail(email)
                .password(getPasswordEncoder.encode(password))
                .role(Role.TEACHER)
                .creatingTime(new Timestamp(System.currentTimeMillis()))
                .status(true)
                .build();
            accountRepository.save(account);
            response.sendRedirect(url + "?succeedMessage=sMv1at01");
        }
    }
}
