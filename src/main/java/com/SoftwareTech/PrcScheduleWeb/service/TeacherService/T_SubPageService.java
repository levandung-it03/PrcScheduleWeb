package com.SoftwareTech.PrcScheduleWeb.service.TeacherService;

import com.SoftwareTech.PrcScheduleWeb.config.StaticUtilMethods;
import com.SoftwareTech.PrcScheduleWeb.dto.AuthDto.DtoChangePassword;
import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.ResDtoPracticeSchedule;
import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.ResDtoSubjectSchedule;
import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.ResDtoTeacherRequest;
import com.SoftwareTech.PrcScheduleWeb.model.*;
import com.SoftwareTech.PrcScheduleWeb.model.enums.EntityInteractionStatus;
import com.SoftwareTech.PrcScheduleWeb.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class T_SubPageService {
    @Autowired
    private final StaticUtilMethods staticUtilMethods;
    @Autowired
    private final TeacherRepository teacherRepository;
    @Autowired
    private final DepartmentRepository departmentRepository;
    @Autowired
    private final SubjectScheduleRepository subjectScheduleRepository;
    @Autowired
    private final SubjectRegistrationRepository subjectRegistrationRepository;
    @Autowired
    private final SemesterRepository semesterRepository;
    @Autowired
    private final ClassroomRepository classroomRepository;
    @Autowired
    private final TeacherRequestRepository teacherRequestRepository;

    public ModelAndView getSetTeacherInfoPage(HttpServletRequest request, Model model) {
        Account user = staticUtilMethods.getAccountInfoInCookie(request);
        if (teacherRepository.existsByAccountAccountId(user.getAccountId()))
            throw new DuplicateKeyException("Teacher Info is already existing!");

        ModelAndView modelAndView = staticUtilMethods.customResponsiveModelView(request, model, "add-person");

        modelAndView.addObject("departmentList", departmentRepository.findAll());
        modelAndView.addObject("instituteEmail", user.getInstituteEmail());
        modelAndView.addObject("roleName", "teacher");
        modelAndView.addObject("actionType", "add");
        modelAndView.addObject("actionTail", "teacher/set-teacher-info");
        return modelAndView;
    }

    public ModelAndView getUpdateTeacherInfoPage(HttpServletRequest request, Model model) {
        Account user = staticUtilMethods.getAccountInfoInCookie(request);
        ModelAndView modelAndView = staticUtilMethods.customResponsiveModelView(request, model, "add-person");
        modelAndView = staticUtilMethods.insertingHeaderDataOfModelView(request, modelAndView);

        //--We already have a "person" in header, so we don't need this line:
        //--modelAndView.addObject("person", person);
        modelAndView.addObject("departmentList", departmentRepository.findAll());
        modelAndView.addObject("roleName", "teacher");
        modelAndView.addObject("actionType", "update");
        modelAndView.addObject("actionTail", "teacher/update-teacher-info");
        return modelAndView;
    }


    public ModelAndView getChangePasswordPage(HttpServletRequest request, Model model) {
        ModelAndView modelAndView = staticUtilMethods.customResponsiveModelView(request, model, "change-password");
        modelAndView = staticUtilMethods.insertingHeaderDataOfModelView(request, modelAndView);

        DtoChangePassword changePasswordObj = (DtoChangePassword) model.asMap().get("changePasswordObj");
        if (changePasswordObj != null)
            modelAndView.addObject("changePasswordObj", model.asMap().get("changePasswordObj"));

        return modelAndView;
    }

    public ModelAndView getTeacherRequestDetailPage(HttpServletRequest request, Model model) {
        //--May throw NumberFormatException
        final Long requestId = Long.parseLong(request.getParameter("requestId"));
        ModelAndView modelAndView = staticUtilMethods
            .customResponsiveModelView(request, model, "teacher-request-detail");
        modelAndView = staticUtilMethods.insertingHeaderDataOfModelView(request, modelAndView);

        ResDtoTeacherRequest customTeacherRequest = subjectScheduleRepository
            .findTeacherRequestByRequestId(requestId)
            .orElseThrow(() -> new NoSuchElementException("Request Id not found"));

        List<SubjectSchedule> schedules = subjectScheduleRepository.findAllByTeacherRequestRequestId(requestId);
        List<Student> students = subjectRegistrationRepository.findAllStudentBySectionClassSectionClassId(
            customTeacherRequest.getSectionClass().getSectionClassId());

        modelAndView.addObject("customTeacherRequest", customTeacherRequest);
        modelAndView.addObject("practiceSchedules", schedules);
        modelAndView.addObject("students", students);
        modelAndView.addObject("role", "teacher");
        return modelAndView;
    }

    public ModelAndView getUpdateTeacherRequestPage(HttpServletRequest request, Model model) throws SQLException {
        //--May throw NumberFormatException
        final Long requestId = Long.parseLong(request.getParameter("requestId"));
        TeacherRequest teacherRequest = teacherRequestRepository
            .findById(requestId)
            .orElseThrow(() -> new NoSuchElementException("Teacher Request Id not found"));
        //--Can not update a Teacher Request which is not 'PENDING'
        if (!teacherRequest.getInteractionStatus().equals(EntityInteractionStatus.PENDING))
            throw new SQLIntegrityConstraintViolationException("error_teacherRequest_06");

        ModelAndView modelAndView = staticUtilMethods
            .customResponsiveModelView(request, model, "add-teacher-request");
        modelAndView = staticUtilMethods.insertingHeaderDataOfModelView(request, modelAndView);

        Account user = staticUtilMethods.getAccountInfoInCookie(request);
        Semester currentSemester = semesterRepository
            .findByCurrentDate(new Date(System.currentTimeMillis()))
            .orElseThrow(() -> new NoSuchElementException("There's no data of Semester in your DB"));
        SubjectSchedule updatedSubjectSchedule = subjectScheduleRepository
            .findAllByTeacherRequestRequestId(requestId)
            .getFirst();
        //--This request doesn't belong to current Semester.
        if (!currentSemester.getSemesterId().equals(updatedSubjectSchedule.getSectionClass().getSemester().getSemesterId()))
            throw new SQLIntegrityConstraintViolationException("error_teacherRequest_07");

        Teacher teacher = teacherRepository
            .findByAccountAccountId(user.getAccountId())
            .orElseThrow(() -> new NoSuchElementException("Account Id is invalid"));

        List<SectionClass> sectionClassList = subjectScheduleRepository
            .findAllBySectionClassSemesterAndTeacher(currentSemester, teacher);
        modelAndView.addObject("sectionClass", subjectScheduleRepository
            .findAllByTeacherRequestRequestId(requestId).getFirst().getSectionClass());
        modelAndView.addObject("teacherRequest", teacherRequest);
        modelAndView.addObject("sectionClassList", sectionClassList);
        modelAndView.addObject("currentSemester", currentSemester);
        modelAndView.addObject("action", "update");

        //--Data for rendering subject-schedule-table.

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
