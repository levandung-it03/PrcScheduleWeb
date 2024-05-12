package com.SoftwareTech.PrcScheduleWeb.service.TeacherService;

import com.SoftwareTech.PrcScheduleWeb.config.StaticUtilMethods;
import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.ResDtoPracticeSchedule;
import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.ResDtoSubjectSchedule;
import com.SoftwareTech.PrcScheduleWeb.model.*;
import com.SoftwareTech.PrcScheduleWeb.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class T_CategoryService {
    @Autowired
    private final StaticUtilMethods staticUtilMethods;
    @Autowired
    private final TeacherRequestRepository teacherRequestRepository;
    @Autowired
    private final TeacherRepository teacherRepository;
    @Autowired
    private final SemesterRepository semesterRepository;
    @Autowired
    private final SubjectRegistrationRepository subjectRegistrationRepository;
    @Autowired
    private final SubjectScheduleRepository subjectScheduleRepository;
    @Autowired
    private final ClassroomRepository classroomRepository;

    public ModelAndView getTeacherRequestListPage(HttpServletRequest request, Model model) {
        ModelAndView modelAndView = staticUtilMethods
            .customResponsiveModelView(request, model, "teacher-request-list");
        modelAndView = staticUtilMethods.insertingHeaderDataOfModelView(request, modelAndView);
        PageRequest pageRequest = staticUtilMethods.getPageRequest(request);

        Account user = staticUtilMethods.getAccountInfoInCookie(request);
        Teacher teacher = teacherRepository
            .findByAccountAccountId(user.getAccountId())
            .orElseThrow(() -> new NoSuchElementException("Account Id is invalid"));

        modelAndView.addObject("role", "teacher");
        modelAndView.addObject("currentPage", pageRequest.getPageNumber() + 1);
        modelAndView.addObject("teacherRequestList", teacherRequestRepository
            .findAllTeacherRequestInSubjectScheduleByTeacherIdWithSpecifiedPage(teacher.getTeacherId(), pageRequest));

        return modelAndView;
    }

    public ModelAndView getAddTeacherRequestPage(HttpServletRequest request, Model model) throws SQLException {
        ModelAndView modelAndView = staticUtilMethods.customResponsiveModelView(request, model, "add-teacher-request");
        modelAndView = staticUtilMethods.insertingHeaderDataOfModelView(request, modelAndView);

        Account user = staticUtilMethods.getAccountInfoInCookie(request);
        Semester currentSemester = semesterRepository
            .findByCurrentDate(new Date(System.currentTimeMillis()))
            .orElseThrow(() -> new NoSuchElementException("There's no data of Semester in your DB"));
        Teacher teacher = teacherRepository
            .findByAccountAccountId(user.getAccountId())
            .orElseThrow(() -> new NoSuchElementException("Account Id is invalid"));
        List<SectionClass> sectionClassList = subjectScheduleRepository
            .findAllBySectionClassSemesterAndTeacher(currentSemester, teacher);
        modelAndView.addObject("sectionClassList", sectionClassList);
        modelAndView.addObject("currentSemester", currentSemester);
        modelAndView.addObject("action", "add");

        List<ResDtoSubjectSchedule> allSubjectSchedules = subjectScheduleRepository
            .findAllScheduleByTeacher(currentSemester.getSemesterId(), teacher.getTeacherId());

        //--Search all practiceSchedule and remove the available computer-rooms which are in this schedule.
        List<ResDtoPracticeSchedule> allPrcScheduleInSemester = subjectScheduleRepository
            .findAllPracticeScheduleInCurrentSemester(currentSemester.getSemesterId());

        List<String> computerRoomList = classroomRepository.findAll().stream().map(Classroom::getRoomId).toList();

        modelAndView.addObject("subjectScheduleList", allSubjectSchedules);
        modelAndView.addObject("allPrcScheduleInSemester", allPrcScheduleInSemester);
        modelAndView.addObject("computerRoomList", computerRoomList);
        return modelAndView;
    }
}
