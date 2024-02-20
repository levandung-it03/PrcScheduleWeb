package com.SoftwareTech.PrcScheduleWeb.auth;

import com.SoftwareTech.PrcScheduleWeb.config.JwtService;
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

@Service
@RequiredArgsConstructor
public class AuthenticatingService {
    @Autowired
    private final AccountRepository accountRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        Account account = Account.builder()
            .instituteEmail(request.getInstituteEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .creatingTime(Timestamp.valueOf(LocalDateTime.now()))
            .role(Role.TEACHER)
            .build();
        accountRepository.save(account);
        var jwtToken = jwtService.generateToken(account);
        return AuthenticationResponse.builder()
            .token(jwtToken)
            .build();
    }

    public AuthenticationResponse authenticate(AuthenticatingRequest request) {
        //--Let the Authentication Process for AuthenticationManager.
        UsernamePasswordAuthenticationToken userPassAuth = new UsernamePasswordAuthenticationToken(
            request.instituteEmail(),
            request.password()
        );
        //--Separate code to put Debug.
        authenticationManager.authenticate(userPassAuth);
        Account account = accountRepository.findByInstituteEmail(request.instituteEmail())
            .orElseThrow();
        String jwtToken = jwtService.generateToken(account);
        return AuthenticationResponse.builder()
            .token(jwtToken)
            .build();
    }
}
