package com.SoftwareTech.PrcScheduleWeb.service.TeacherService;

import com.SoftwareTech.PrcScheduleWeb.dto.TeacherServiceDto.ReqDtoTeacherInfo;
import com.SoftwareTech.PrcScheduleWeb.model.Teacher;
import com.SoftwareTech.PrcScheduleWeb.model.enums.Gender;
import com.SoftwareTech.PrcScheduleWeb.repository.AccountRepository;
import com.SoftwareTech.PrcScheduleWeb.repository.DepartmentRepository;
import com.SoftwareTech.PrcScheduleWeb.repository.TeacherRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class T_TeacherService {
    @Autowired
    private final TeacherRepository teacherRepository;
    @Autowired
    private final AccountRepository accountRepository;
    @Autowired
    private final DepartmentRepository departmentRepository;
    
    public void setTeacherInfoForWhiteInfoAccount(ReqDtoTeacherInfo teacherInfo) {
        if (teacherRepository.existsById(teacherInfo.getTeacherId()))
            throw new DuplicateKeyException("Teacher Is is already existing!");

        teacherRepository.save(Teacher.builder()
            .teacherId(teacherInfo.getTeacherId())
            .firstName(teacherInfo.getFirstName())
            .lastName(teacherInfo.getLastName())
            .birthday(teacherInfo.getBirthday())
            .phone(teacherInfo.getPhone())
            .gender(Gender.valueOf(teacherInfo.getGender()))
            .status(true)
            .account(accountRepository
                .findByInstituteEmail(teacherInfo.getInstituteEmail())
                .orElseThrow(() -> new NoSuchElementException("Account Email not found!"))
            )
            .department(departmentRepository
                .findById(teacherInfo.getDepartmentId())
                .orElseThrow(() -> new NoSuchElementException("Department Id is invalid"))
            )
            .build());
    }


    @Transactional(rollbackOn = {Exception.class})
    public void updateTeacherInfo(ReqDtoTeacherInfo teacherInfo) {
        if (!teacherRepository.existsById(teacherInfo.getTeacherId()))
            throw new NoSuchElementException("Teacher Id not found!");

        if (!teacherRepository.existsByAccountInstituteEmail(teacherInfo.getInstituteEmail()))
            throw new NoSuchElementException("Teacher Email not found");

        teacherRepository.updateById(Teacher.builder()
            .teacherId(teacherInfo.getTeacherId())
            .firstName(teacherInfo.getFirstName())
            .lastName(teacherInfo.getLastName())
            .birthday(teacherInfo.getBirthday())
            .phone(teacherInfo.getPhone())
            .gender(Gender.valueOf(teacherInfo.getGender()))
            .department(departmentRepository
                .findById(teacherInfo.getDepartmentId())
                .orElseThrow(() -> new NoSuchElementException("Department Id is invalid"))
            )
            .build());
    }
}
