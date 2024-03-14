package com.SoftwareTech.PrcScheduleWeb.service.ManagerService;

import com.SoftwareTech.PrcScheduleWeb.dto.AuthDto.DtoRegisterAccount;
import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.DtoUpdateTeacherAccount;
import com.SoftwareTech.PrcScheduleWeb.model.Account;
import com.SoftwareTech.PrcScheduleWeb.model.enums.Role;
import com.SoftwareTech.PrcScheduleWeb.repository.AccountRepository;
import com.SoftwareTech.PrcScheduleWeb.repository.TeacherRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AccountService {
    @Autowired
    private final AccountRepository accountRepository;
    @Autowired
    private final PasswordEncoder getPasswordEncoder;
    @Autowired
    private final TeacherRepository teacherRepository;

    public String addTeacherAccountAndGetRedirect(DtoRegisterAccount registerObject, HttpServletRequest request) {
        final String standingUrl = request.getHeader("Referer");
        final String email = registerObject.getInstituteEmail().trim();
        final String password = registerObject.getPassword().trim();

        if ((password.length() < 8) || !password.equals(registerObject.getRetypePassword().trim()))
            throw new IllegalStateException("Password not correct");

        if (!Pattern
            .compile("^[^@\\s]+[.\\w]*@(ptithcm\\.edu\\.vn|ptit\\.edu\\.vn|student\\.ptithcm\\.edu\\.vn)$")
            .matcher(email).matches())
            throw new IllegalStateException("Invalid Email format");

        if (accountRepository.findByInstituteEmail(email).isPresent())
            throw new DuplicateKeyException("Email is already existed");

        accountRepository.save(Account.builder()
            .instituteEmail(email)
            .password(getPasswordEncoder.encode(password))
            .role(Role.TEACHER)
            .creatingTime(new Timestamp(System.currentTimeMillis()))
            .status(true)
            .build());

        return "redirect:" + standingUrl + "?succeedMessage=sMv1at01";
    }

    @Transactional(rollbackOn = {Exception.class})
    public String deleteTeacherAccountAndGetRedirect(String accountIdPathParam, String standingUrl)
        throws SQLIntegrityConstraintViolationException, NumberFormatException {
        Long accountId = Long.parseLong(accountIdPathParam);

        teacherRepository.deleteByAccountId(accountId);
        accountRepository.deleteById(accountId);

        return "redirect:" + standingUrl + "?succeedMessage=sMv1at02";
    }

    public String updateTeacherAccountAndGetRedirect(HttpServletRequest request, DtoUpdateTeacherAccount account) {
        final Long accountId = Long.parseLong(request.getParameter("accountId"));
        final Account updatedAccount = accountRepository
            .findByAccountIdAndInstituteEmail(accountId, account.getInstituteEmail())
            .orElseThrow(() -> new NoSuchElementException("Account Id and Institute Email pair not found"));

        //--Update new data inside old object.
        updatedAccount.setStatus(account.isStatus());

        //--Save new data into Database.
        accountRepository.save(updatedAccount);

        return "redirect:/manager/category/teacher/teacher-account-list?succeedMessage=sMv1at03";
    }
}
