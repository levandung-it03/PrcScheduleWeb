package com.SoftwareTech.PrcScheduleWeb.service.ManagerService;

import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.DtoUpdateTeacher;
import com.SoftwareTech.PrcScheduleWeb.model.Department;
import com.SoftwareTech.PrcScheduleWeb.model.Teacher;
import com.SoftwareTech.PrcScheduleWeb.repository.DepartmentRepository;
import com.SoftwareTech.PrcScheduleWeb.repository.TeacherRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class TeacherService {
    @Autowired
    private final TeacherRepository teacherRepository;
    @Autowired
    private final DepartmentRepository departmentRepository;

    public void updateTeacherAndGetRedirect(DtoUpdateTeacher teacher) {
        Teacher oldTeacherInfo = teacherRepository
            .findByTeacherIdAndInstituteEmail(teacher.getTeacherId(), teacher.getInstituteEmail())
            .orElseThrow(() -> new NoSuchElementException("Teacher Id not found"));

        Department chosenDepartment = departmentRepository.findById(teacher.getDepartmentId()).orElseThrow();

        if (!Pattern.compile("^[A-Za-zÀ-ỹ]{1,50}( [A-Za-zÀ-ỹ]{1,50})*$").matcher(teacher.getLastName()).matches())
            throw new IllegalStateException("Invalid Last-name format");

        if (!Pattern.compile("^[A-Za-zÀ-ỹ]{1,50}$").matcher(teacher.getFirstName()).matches())
            throw new IllegalStateException("Invalid First-name format");

        if (!Pattern.compile("^[0-9]{4,12}$").matcher(teacher.getPhone()).matches())
            throw new IllegalStateException("Invalid Phone-number format");

        teacherRepository.save(Teacher.builder()
            .teacherId(teacher.getTeacherId())
            .department(chosenDepartment)
            .lastName(teacher.getLastName())
            .firstName(teacher.getFirstName())
            .birthday(teacher.getBirthday())
            .gender(teacher.getGender())
            .phone(teacher.getPhone())
            .status(true)
            .account(oldTeacherInfo.getAccount())
            .build()
        );
    }
}
