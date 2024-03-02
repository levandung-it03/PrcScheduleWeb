package com.SoftwareTech.PrcScheduleWeb.service.ManagerService;

import com.SoftwareTech.PrcScheduleWeb.dto.AuthDto.DtoRegisterAccount;
import com.SoftwareTech.PrcScheduleWeb.model.Account;
import com.SoftwareTech.PrcScheduleWeb.model.enums.Role;
import com.SoftwareTech.PrcScheduleWeb.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AccountService {
    @Autowired
    private final AccountRepository accountRepository;
    @Autowired
    private final PasswordEncoder getPasswordEncoder;

    public HashMap<String, String> addTeacherAccount(DtoRegisterAccount registerObject) {
        final HashMap<String, String> result = new HashMap<>();
        final String email = registerObject.getInstituteEmail().trim();
        final String password = registerObject.getPassword().trim();
        final String retypePassword = registerObject.getRetypePassword().trim();
        final boolean isInvalidPassword = (password.length() < 8) || !password.equals(retypePassword);
        final boolean isInvalidUsername = !Pattern
            .compile("^[^@\\s]+[.\\w]*@(ptithcm\\.edu\\.vn|ptit\\.edu\\.vn|student\\.ptithcm\\.edu\\.vn)$")
            .matcher(email).matches();
        final boolean isExistingEmail = accountRepository.findByInstituteEmail(email).isPresent();

        if (isInvalidPassword) {
            result.put("status", "error");
            result.put("code", "eMv1at01");
        }
        else if (isInvalidUsername) {
            result.put("status", "error");
            result.put("code", "eMv1at02");
        }
        else if (isExistingEmail) {
            result.put("status", "error");
            result.put("code", "eMv1at03");
        }
        else {
            try {
                accountRepository.save(Account.builder()
                    .instituteEmail(email)
                    .password(getPasswordEncoder.encode(password))
                    .role(Role.TEACHER)
                    .creatingTime(new Timestamp(System.currentTimeMillis()))
                    .status(true)
                    .build());
            } catch (Exception e) { e.fillInStackTrace(); }

            result.put("status", "succeed");
            result.put("code", "sMv1at01");
        }
        return result;
    }
}
