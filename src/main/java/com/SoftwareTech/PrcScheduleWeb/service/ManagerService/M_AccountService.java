package com.SoftwareTech.PrcScheduleWeb.service.ManagerService;

import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.ReqDtoAddTeacherAccount;
import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.ReqDtoUpdateTeacherAccount;
import com.SoftwareTech.PrcScheduleWeb.model.Account;
import com.SoftwareTech.PrcScheduleWeb.model.enums.Role;
import com.SoftwareTech.PrcScheduleWeb.repository.AccountRepository;
import com.SoftwareTech.PrcScheduleWeb.repository.ManagerRepository;
import com.SoftwareTech.PrcScheduleWeb.repository.TeacherRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class M_AccountService {
    @Autowired
    private final AccountRepository accountRepository;
    @Autowired
    private final PasswordEncoder getPasswordEncoder;
    @Autowired
    private final TeacherRepository teacherRepository;

    public void addTeacherAccount(ReqDtoAddTeacherAccount newAccountObject) {
        final String email = newAccountObject.getInstituteEmail().trim();
        final String password = newAccountObject.getPassword().trim();

        if (!password.equals(newAccountObject.getRetypePassword().trim()))
            throw new IllegalArgumentException("Password not correct");

        if (accountRepository.findByInstituteEmail(email).isPresent())
            throw new DuplicateKeyException("Email is already existed");

        accountRepository.save(Account.builder()
            .instituteEmail(email)
            .password(getPasswordEncoder.encode(password))
            .role(Role.TEACHER)
            .creatingTime(LocalDateTime.now())
            .status(true)
            .build());
    }

    public void updateTeacherAccount(HttpServletRequest request, ReqDtoUpdateTeacherAccount account) {
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
        //--May throw NumberFormatException
        Long accountId = Long.parseLong(accountIdPathParam);

        if (!accountRepository.existsById(accountId))
            throw new NoSuchElementException("Account Id not found!");

        teacherRepository.deleteByAccountId(accountId);
        accountRepository.deleteById(accountId);
    }
}
