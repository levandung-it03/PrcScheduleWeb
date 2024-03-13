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

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class TeacherService {
    @Autowired
    private final TeacherRepository teacherRepository;
    @Autowired
    private final DepartmentRepository departmentRepository;

    public String updateTeacher(DtoUpdateTeacher teacher, HttpServletRequest request) {
        final String standingUrl = request.getHeader("Referer");
        final String updatedTeacherId = request.getParameter("teacherId");

        try {
            //--Check if updated TeacherId is existing.
            Teacher oldTeacherInfo = teacherRepository.findById(updatedTeacherId).orElseThrow();

            Department choosedDepartment = departmentRepository.findById(teacher.getDepartmentId()).orElseThrow();

            //--Find the invalid last-name.
            if (!Pattern.compile("^[A-Za-zÀ-ỹ]{1,50}$").matcher(teacher.getLastName()).matches())
                throw new IllegalStateException();

            //--Find the invalid first-name.
            if (!Pattern.compile("^[A-Za-zÀ-ỹ]{1,50}( [A-Za-zÀ-ỹ]{1,50})*$").matcher(teacher.getFirstName()).matches())
                throw new IllegalStateException();

            //--Find the invalid phone number.
            if (!Pattern.compile("^[0-9]{4,12}$").matcher(teacher.getPhone()).matches())
                throw new IllegalStateException();

            teacherRepository.save(Teacher.builder()
                .teacherId(updatedTeacherId)
                .department(choosedDepartment)
                .lastName(teacher.getLastName())
                .firstName(teacher.getFirstName())
                .birthday(teacher.getBirthday())
                .gender(teacher.getGender())
                .phone(teacher.getPhone())
                .status(true)
                .account(oldTeacherInfo.getAccount())
                .build()
            );

            return "redirect:/manager/category/teacher/teacher-list?succeedMessage=sMv1at03";
        } catch (Exception ignored) {
            return "redirect:" + standingUrl + "?teacherId=" + updatedTeacherId + "&errorMessage=eMv1at00";
        }
    }
}
