package com.SoftwareTech.PrcScheduleWeb.service.ManagerService;

import com.SoftwareTech.PrcScheduleWeb.dto.AuthDto.DtoRegisterAccount;
import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.DtoAsRequests.DtoUpdateTeacherAccount;
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
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AccountService {
    @Autowired
    private final AccountRepository accountRepository;
    @Autowired
    private final PasswordEncoder getPasswordEncoder;
    @Autowired
    private final TeacherRepository teacherRepository;

    public void addTeacherAccount(DtoRegisterAccount registerObject) {
        final String email = registerObject.getInstituteEmail().trim();
        final String password = registerObject.getPassword().trim();

        if (!password.equals(registerObject.getRetypePassword().trim()))
            throw new IllegalStateException("Password not correct");

        if (accountRepository.findByInstituteEmail(email).isPresent())
            throw new DuplicateKeyException("Email is already existed");

        accountRepository.save(Account.builder()
            .instituteEmail(email)
            .password(getPasswordEncoder.encode(password))
            .role(Role.TEACHER)
            .creatingTime(new Timestamp(System.currentTimeMillis()))
            .status(true)
            .build());
    }

    public void updateTeacherAccount(HttpServletRequest request, DtoUpdateTeacherAccount account) {
        final Long accountId = Long.parseLong(request.getParameter("accountId"));
        final Account updatedAccount = accountRepository
            .findByAccountIdAndInstituteEmail(accountId, account.getInstituteEmail())
            .orElseThrow(() -> new NoSuchElementException("Account Id and Institute Email pair not found"));

        //--Update new data inside old object.
        updatedAccount.setStatus(account.isStatus());

        //--Save new data into Database.
        accountRepository.save(updatedAccount);
    }

    @Transactional(rollbackOn = {Exception.class})
    public void deleteTeacherAccount(String accountIdPathParam) {
        Long accountId = Long.parseLong(accountIdPathParam);
        /*--Ignore_result--*/accountRepository
            .findById(accountId)
            .orElseThrow(() -> new NoSuchElementException("Account Id not found!"));

        teacherRepository.deleteByAccountId(accountId);
        accountRepository.deleteById(accountId);
    }
}
