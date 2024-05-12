package com.SoftwareTech.PrcScheduleWeb.service.ManagerService;

import com.SoftwareTech.PrcScheduleWeb.dto.TeacherServiceDto.ReqDtoTeacherInfo;
import com.SoftwareTech.PrcScheduleWeb.model.Department;
import com.SoftwareTech.PrcScheduleWeb.model.Teacher;
import com.SoftwareTech.PrcScheduleWeb.model.enums.Gender;
import com.SoftwareTech.PrcScheduleWeb.repository.DepartmentRepository;
import com.SoftwareTech.PrcScheduleWeb.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class M_TeacherService {
    @Autowired
    private final TeacherRepository teacherRepository;
    @Autowired
    private final DepartmentRepository departmentRepository;

    public void updateTeacher(ReqDtoTeacherInfo teacher) {
        Teacher oldTeacherInfo = teacherRepository
            .findByTeacherIdAndInstituteEmail(teacher.getTeacherId(), teacher.getInstituteEmail())
            .orElseThrow(() -> new NoSuchElementException("Teacher Id not found"));

        Department chosenDepartment = departmentRepository
            .findById(teacher.getDepartmentId())
            .orElseThrow(() -> new NoSuchElementException("Department Id not found"));

        teacherRepository.save(Teacher.builder()
            .teacherId(teacher.getTeacherId())
            .department(chosenDepartment)
            .lastName(teacher.getLastName())
            .firstName(teacher.getFirstName())
            .birthday(teacher.getBirthday())
        //--May throw IllegalArgumentException if Gender not found.
            .gender(Gender.valueOf(teacher.getGender()))
            .phone(teacher.getPhone())
            .status(true)
            .account(oldTeacherInfo.getAccount())
            .build()
        );
    }
}
