package com.SoftwareTech.PrcScheduleWeb.service.ManagerService;

import com.SoftwareTech.PrcScheduleWeb.config.StaticUtilMethods;
import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.*;
import com.SoftwareTech.PrcScheduleWeb.model.*;
import com.SoftwareTech.PrcScheduleWeb.model.enums.EntityInteractionStatus;
import com.SoftwareTech.PrcScheduleWeb.model.enums.Role;
import com.SoftwareTech.PrcScheduleWeb.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubPageService {
    @Autowired
    private final StaticUtilMethods staticUtilMethods;
    @Autowired
    private final TeacherRepository teacherRepository;
    @Autowired
    private final DepartmentRepository departmentRepository;
    @Autowired
    private final ClassroomRepository classroomRepository;
    @Autowired
    private final ComputerRoomDetailRepository computerRoomDetailRepository;
    @Autowired
    private final AccountRepository accountRepository;
    @Autowired
    private final SubjectScheduleRepository subjectScheduleRepository;
    @Autowired
    private final SubjectRegistrationRepository subjectRegistrationRepository;
    @Autowired
    private final TeacherRequestRepository teacherRequestRepository;
    @Autowired
    private final ManagerRepository managerRepository;

    public ModelAndView getUpdateComputerRoomPage(HttpServletRequest request) {
        final String roomId = request.getParameter("roomId");
        ModelAndView modelAndView = new ModelAndView("update-computer-room");

        //--Serve reload-page action on update-page (update-page doesn't have updated id on the URL bar).
        if (roomId == null)
            throw new NullPointerException("Computer Room Code is Null, so redirecting back to Computer Room List");

        Classroom computerRoom = classroomRepository
            .findById(roomId)
            .orElseThrow(() -> new NoSuchElementException("Computer Room not found"));
        ComputerRoomDetail computerRoomDetail = computerRoomDetailRepository
            .findByRoomId(roomId)
            .orElseThrow(() -> new NoSuchElementException("Computer Room not found"));

        modelAndView.addObject("roomObject", ResDtoComputerRoom.builder()
            .roomId(roomId)
            .maxQuantity(computerRoom.getMaxQuantity())
            .maxComputerQuantity(computerRoomDetail.getMaxComputerQuantity())
            .availableComputerQuantity(computerRoomDetail.getAvailableComputerQuantity())
            .status(computerRoom.isStatus())
            .build()
        );

        return modelAndView;
    }

    public ModelAndView getUpdateTeacherPage(HttpServletRequest request) {
        final String teacherId = request.getParameter("teacherId");
        ModelAndView modelAndView = new ModelAndView("update-teacher");

        //--Serve reload-page action on update-page (update-page doesn't have updated id on the URL bar).
        if (teacherId == null)
            throw new NullPointerException("Teacher Id is Null, so redirecting back to Teacher List");

        List<Department> departmentList = departmentRepository.findAll();
        Teacher updatedTeacher = teacherRepository.findById(teacherId)
            .orElseThrow(() -> new NoSuchElementException("Teacher Id not found"));

        if (updatedTeacher.getAccount().getRole().equals(Role.MANAGER))
            throw new AuthorizationServiceException("Can not update MANAGER info");

        ReqDtoUpdateTeacher teacher = ReqDtoUpdateTeacher.builder()
            .instituteEmail(updatedTeacher.getAccount().getInstituteEmail())
            .teacherId(updatedTeacher.getTeacherId())
            .lastName(updatedTeacher.getLastName())
            .firstName(updatedTeacher.getFirstName())
            .birthday(updatedTeacher.getBirthday())
            .gender(updatedTeacher.getGender().toString())
            .departmentId(updatedTeacher.getDepartment().getDepartmentId())
            .phone(updatedTeacher.getPhone())
            .build();

        modelAndView.addObject("teacher", teacher);
        modelAndView.addObject("departmentList", departmentList);

        return modelAndView;
    }

    public ModelAndView getUpdateTeacherAccountPage(HttpServletRequest request) {
        final String accountId = request.getParameter("accountId");
        ModelAndView modelAndView = new ModelAndView("update-teacher-account");

        //--Serve reload-page action on update-page (update-page doesn't have updated id on the URL bar).
        if (accountId == null)
            throw new NullPointerException("Account Id is Null, so redirecting back to Teacher Account List");

        Account account = accountRepository
            .findById(Long.parseLong(accountId))
            .orElseThrow(() -> new NoSuchElementException("Account Id not found"));

        if (account.getRole().equals(Role.MANAGER))
            throw new AuthorizationServiceException("Can not change data of MANAGER account");

        modelAndView.addObject("account", ReqDtoUpdateTeacherAccount.builder()
            .accountId(account.getAccountId())
            .instituteEmail(account.getInstituteEmail())
            .creatingTime(account.getCreatingTime())
            .status(account.isStatus())
            .build());
        return modelAndView;
    }

    public ModelAndView getTeacherRequestDetailPage(HttpServletRequest request, Model model) {
        //--May throw NumberFormatException
        final Long requestId = Long.parseLong(request.getParameter("requestId"));
        ModelAndView modelAndView = staticUtilMethods
            .customResponseModelView(request, model.asMap(), "teacher-request-detail");

        ResDtoTeacherRequest customTeacherRequest = subjectScheduleRepository
            .findTeacherRequestByRequestId(requestId)
            .orElseThrow(() -> new NoSuchElementException("Request Id not found"));

        List<SubjectSchedule> schedules = subjectScheduleRepository.findAllByTeacherRequestRequestId(requestId);
        List<Student> students = subjectRegistrationRepository.findAllStudentBySectionClassSectionClassId(
            customTeacherRequest.getSectionClass().getSectionClassId());

        modelAndView.addObject("customTeacherRequest", customTeacherRequest);
        modelAndView.addObject("practiceSchedules", schedules);
        modelAndView.addObject("students", students);
        return modelAndView;
    }

    public ModelAndView getAddPracticeSchedulePage(HttpServletRequest request, Model model) throws SQLException {
        final Long requestId = Long.parseLong(request.getParameter("requestId"));

        Optional<TeacherRequest> teacherRequest = teacherRequestRepository.findById(requestId);
        if (teacherRequest.isPresent()) {
            if (teacherRequest.orElseThrow().getInteractionStatus().equals(EntityInteractionStatus.CREATED))
                throw new SQLIntegrityConstraintViolationException("error_teacherRequest_01");

            if (teacherRequest.orElseThrow().getInteractionStatus().equals(EntityInteractionStatus.DENIED))
                throw new SQLIntegrityConstraintViolationException("error_teacherRequest_02");

            if (teacherRequest.orElseThrow().getInteractionStatus().equals(EntityInteractionStatus.CANCEL))
                throw new SQLIntegrityConstraintViolationException("error_teacherRequest_03");
        }

        ModelAndView modelAndView = staticUtilMethods
            .customResponseModelView(request, model.asMap(), "add-practice-schedule");
        ResDtoTeacherRequest customTeacherRequest = subjectScheduleRepository
            .findTeacherRequestByRequestId(requestId)
            .orElseThrow(() -> new NoSuchElementException("Request Id not found"));

        List<ResDtoSubjectSchedule> allSubjectSchedules = subjectScheduleRepository
            .findAllScheduleByTeacherRequest(
                customTeacherRequest.getSectionClass().getSemester().getSemesterId(),
                customTeacherRequest.getTeacher().getTeacherId(),
                customTeacherRequest.getSectionClass().getGrade().getGradeId()
            );
        //--Search all practiceSchedule and remove the available computer-rooms which are in this schedule.
            List<ResDtoPracticeSchedule> allPrcScheduleInSemester = subjectScheduleRepository
            .findAllPracticeScheduleInCurrentSemester(
                customTeacherRequest.getSectionClass().getSemester().getSemesterId()
            );
        //--To filter the computer-rooms which have the available quantity.
        Integer studentQuantityInCurrentSectionClass = subjectRegistrationRepository
            .countBySectionClassId(customTeacherRequest.getSectionClass().getSectionClassId());
        int studentQuantity = (studentQuantityInCurrentSectionClass == null) ? 0 : studentQuantityInCurrentSectionClass;

        List<String> computerRoomList = classroomRepository.findAllComputerRoomIdWithQuantity(studentQuantity);

        modelAndView.addObject("customTeacherRequest", customTeacherRequest);
        modelAndView.addObject("subjectScheduleList", allSubjectSchedules);
        modelAndView.addObject("allPrcScheduleInSemester", allPrcScheduleInSemester);
        modelAndView.addObject("computerRoomList", computerRoomList);
        return modelAndView;
    }

    public ModelAndView getUpdatePracticeSchedulePage(HttpServletRequest request, Model model) throws SQLException {
        final Long practiceScheduleId = Long.parseLong(request.getParameter("practiceScheduleId"));
        ModelAndView modelAndView = staticUtilMethods
            .customResponseModelView(request, model.asMap(), "add-practice-schedule");

        SubjectSchedule practiceSchedule = subjectScheduleRepository
            .findAllFieldsById(practiceScheduleId)
            .orElseThrow(() -> new NoSuchElementException("Subject Schedule Id not found"));

        ResDtoTeacherRequest customTeacherRequest = subjectScheduleRepository
            .findTeacherRequestByRequestId(practiceSchedule.getTeacherRequest().getRequestId())
            .orElseThrow(() -> new NoSuchElementException("Request Id not found"));

        List<ResDtoSubjectSchedule> allSubjectSchedules = subjectScheduleRepository
            .findAllScheduleByTeacherRequest(
                practiceSchedule.getSectionClass().getSemester().getSemesterId(),
                practiceSchedule.getTeacher().getTeacherId(),
                practiceSchedule.getSectionClass().getGrade().getGradeId()
            );

        List<ResDtoPracticeSchedule> allPrcScheduleInSemester = subjectScheduleRepository
            .findAllPracticeScheduleInCurrentSemester(
                practiceSchedule.getSectionClass().getSemester().getSemesterId()
            );
        //--To filter the computer-rooms which have the available quantity.
        Integer studentQuantityInCurrentSectionClass = subjectRegistrationRepository
            .countBySectionClassId(practiceSchedule.getSectionClass().getSectionClassId());
        int studentQuantity = (studentQuantityInCurrentSectionClass == null) ? 0 : studentQuantityInCurrentSectionClass;

        List<String> computerRoomList = classroomRepository.findAllComputerRoomIdWithQuantity(studentQuantity);

        modelAndView.addObject("customTeacherRequest", customTeacherRequest);
        modelAndView.addObject("subjectScheduleList", allSubjectSchedules);
        modelAndView.addObject("allPrcScheduleInSemester", allPrcScheduleInSemester);
        modelAndView.addObject("computerRoomList", computerRoomList);
        modelAndView.addObject("updatedPracticeSchedule", practiceSchedule);
        return modelAndView;
    }

    public ModelAndView getSetManagerInfoPage(HttpServletRequest request) {
        Account user = staticUtilMethods.getAccountInfoInCookie(request);
        if (managerRepository.existsByAccountAccountId(user.getAccountId()))
            throw new DuplicateKeyException("Manager Info is already existing!");

        ModelAndView modelAndView = new ModelAndView("add-person");

        modelAndView.addObject("roleName", "manager");
        modelAndView.addObject("actionType", "add");
        modelAndView.addObject("actionTail", "set-manager-info");
        return modelAndView;
    }
}
