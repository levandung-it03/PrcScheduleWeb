package com.SoftwareTech.PrcScheduleWeb.config;

import com.SoftwareTech.PrcScheduleWeb.model.Account;
import com.SoftwareTech.PrcScheduleWeb.model.Department;
import com.SoftwareTech.PrcScheduleWeb.model.enums.Role;
import com.SoftwareTech.PrcScheduleWeb.repository.AccountRepository;
import com.SoftwareTech.PrcScheduleWeb.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitialDataLoader implements CommandLineRunner {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public void run(String... args) throws Exception {
        if (accountRepository.findByRole(Role.MANAGER).isEmpty()) {
            Account managerAccount = Account.builder()
                .status(true)
                .creatingTime(new Timestamp(System.currentTimeMillis()))
                .instituteEmail("manager0@ptithcm.edu.vn")
                .password("$2a$12$dzSEHUe6lixG0EkEzrcQfuV18XLaZpvDvF9apXe9.9PigXDgGw9p.")
                .role(Role.MANAGER)
                .build();
            Account savedManagerAccount = accountRepository.save(managerAccount);
            System.out.println("Manager saved with Email: " + savedManagerAccount.getInstituteEmail());
        }
        if (departmentRepository.count() == 0) {
            List<Department> departments = List.of(
                Department.builder().departmentId("CNTT02").status(true).build(),
                Department.builder().departmentId("CNTT02").status(true).build(),
                Department.builder().departmentId("CNTT02").status(true).build()
            );
        }
    }
}