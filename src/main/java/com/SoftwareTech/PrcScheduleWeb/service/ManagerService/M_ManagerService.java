package com.SoftwareTech.PrcScheduleWeb.service.ManagerService;

import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.ReqDtoManagerInfo;
import com.SoftwareTech.PrcScheduleWeb.model.Manager;
import com.SoftwareTech.PrcScheduleWeb.model.enums.Gender;
import com.SoftwareTech.PrcScheduleWeb.repository.AccountRepository;
import com.SoftwareTech.PrcScheduleWeb.repository.ManagerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class M_ManagerService {
    @Autowired
    private final ManagerRepository managerRepository;
    @Autowired
    private final AccountRepository accountRepository;

    public void setManagerInfoForWhiteInfoAccount(ReqDtoManagerInfo managerInfo) {
        if (managerRepository.existsById(managerInfo.getManagerId()))
            throw new DuplicateKeyException("Manager Is is already existing!");

        managerRepository.save(Manager.builder()
            .managerId(managerInfo.getManagerId())
            .firstName(managerInfo.getFirstName())
            .lastName(managerInfo.getLastName())
            .birthday(managerInfo.getBirthday())
            .phone(managerInfo.getPhone())
            .gender(Gender.valueOf(managerInfo.getGender()))
            .status(true)
            .account(accountRepository
                .findByInstituteEmail(managerInfo.getInstituteEmail())
                .orElseThrow(() -> new NoSuchElementException("Account Email not found!"))
            )
            .build());
    }

    @Transactional(rollbackOn = {Exception.class})
    public void updateManagerInfo(ReqDtoManagerInfo managerInfo) {
        if (!managerRepository.existsById(managerInfo.getManagerId()))
            throw new NoSuchElementException("Manager Id not found!");

        if (!managerRepository.existsByAccountInstituteEmail(managerInfo.getInstituteEmail()))
            throw new NoSuchElementException("Manager Email not found");

        managerRepository.updateById(Manager.builder()
            .managerId(managerInfo.getManagerId())
            .firstName(managerInfo.getFirstName())
            .lastName(managerInfo.getLastName())
            .birthday(managerInfo.getBirthday())
            .phone(managerInfo.getPhone())
            .gender(Gender.valueOf(managerInfo.getGender()))
            .build());
    }
}
