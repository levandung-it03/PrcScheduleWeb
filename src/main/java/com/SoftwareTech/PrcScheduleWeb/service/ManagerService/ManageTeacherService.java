package com.SoftwareTech.PrcScheduleWeb.service.ManagerService;

import com.SoftwareTech.PrcScheduleWeb.dto.DtoRegisterAccount;
import com.SoftwareTech.PrcScheduleWeb.model.Account;
import com.SoftwareTech.PrcScheduleWeb.model.enums.Role;
import com.SoftwareTech.PrcScheduleWeb.repository.AccountRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ManageTeacherService {
    @Autowired
    private final AccountRepository accountRepository;
    @Autowired
    private final PasswordEncoder getPasswordEncoder;

    public void addTeacherAccount(
        DtoRegisterAccount registerObject,
        HttpServletResponse response
    ) throws IOException {
        final String email = registerObject.instituteEmail().trim();
        final String password = registerObject.password().trim();
        final String retypePassword = registerObject.retypePassword().trim();
        final boolean isInvalidPassword = (password.length() < 8) || !password.equals(retypePassword);
        final boolean isInvalidUsername = Pattern
            .matches("^[^@\\s]+@(ptithcm\\.edu\\.vn|ptit\\.edu\\.vn|student\\.ptithcm\\.edu\\.vn)$", email);

        if (isInvalidPassword) {
            response.sendRedirect("/manager/category/add-teacher-account?errorMessage=eMv1at01");
        }
        else if (isInvalidUsername) {
            response.sendRedirect("/manager/category/add-teacher-account?errorMessage=eMv1at02");
        }
        else {
            Account account = Account.builder()
                .instituteEmail(email)
                .password(getPasswordEncoder.encode(password))
                .role(Role.TEACHER)
                .creatingTime(new Timestamp(System.currentTimeMillis()))
                .build();
            accountRepository.save(account);
            response.sendRedirect("/manager/category/add-teacher-account?succeedMessage=sMv1at01");
        }
    }
}
