package com.SoftwareTech.PrcScheduleWeb.auth;

import com.SoftwareTech.PrcScheduleWeb.dto.DtoAuthentication;
import com.SoftwareTech.PrcScheduleWeb.model.Account;
import com.SoftwareTech.PrcScheduleWeb.model.enums.Role;
import com.SoftwareTech.PrcScheduleWeb.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Base64;

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

    public DtoAuthenticationResponse register(DtoRegisterRequest request) {
        Account account = Account.builder()
            .instituteEmail(request.instituteEmail())
            .password(passwordEncoder.encode(request.password()))
            .creatingTime(Timestamp.valueOf(LocalDateTime.now()))
            .role(Role.TEACHER)
            .build();
        var jwtToken = jwtService.generateToken(account);
        accountRepository.save(account);
        return DtoAuthenticationResponse.builder()
            .token(jwtToken)
            .role(Role.TEACHER)
            .build();
    }

    public DtoAuthenticationResponse authenticate(DtoAuthentication request) {
        //--Configure an AuthenticateToken by InputAccount.
        UsernamePasswordAuthenticationToken authenticateToken = new UsernamePasswordAuthenticationToken(
            request.instituteEmail(),
            request.password()
        );
        //--Authenticate InputAccount with AuthenticationManager.
        //--Use the configured AuthenticationProvider for authentication.
        authenticationManager.authenticate(authenticateToken);
        Account account = accountRepository.findByInstituteEmail(request.instituteEmail())
            .orElseThrow();
        byte[] jwtTokenAsBytes = jwtService.generateToken(account).getBytes();
        return DtoAuthenticationResponse.builder()
            .encodedToken(Base64.getEncoder().encodeToString(jwtTokenAsBytes))
            .role(account.getRole())
            .build();
    }
}
