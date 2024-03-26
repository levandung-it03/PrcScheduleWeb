package com.SoftwareTech.PrcScheduleWeb.service.ManagerService;

import com.SoftwareTech.PrcScheduleWeb.config.StaticUtilMethods;
import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.*;
import com.SoftwareTech.PrcScheduleWeb.model.*;
import com.SoftwareTech.PrcScheduleWeb.model.enums.Role;
import com.SoftwareTech.PrcScheduleWeb.model.enums.RoomType;
import com.SoftwareTech.PrcScheduleWeb.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

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
    private final TeacherRequestRepository teacherRequestRepository;
    @Autowired
    private final SubjectScheduleRepository subjectScheduleRepository;
    @Autowired
    private final SubjectRegistrationRepository subjectRegistrationRepository;

    public ModelAndView getUpdateComputerRoomPage(HttpServletRequest request) {
        final String roomId = request.getParameter("roomId");
        ModelAndView modelAndView = staticUtilMethods.customResponseModelView(request, "update-computer-room");

        if (roomId == null)
            throw new NullPointerException("Computer Room Code is Null, so redirecting back to Computer Room List");

        Classroom computerRoom = classroomRepository
            .findById(roomId)
            .orElseThrow(() -> new NoSuchElementException("Computer Room not found"));
        ComputerRoomDetail computerRoomDetail = computerRoomDetailRepository
            .findByRoomId(roomId)
            .orElseThrow(() -> new NoSuchElementException("Computer Room not found"));

        modelAndView.addObject("roomObject", DtoComputerRoom.builder()
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
        ModelAndView modelAndView = staticUtilMethods.customResponseModelView(request, "update-teacher");

        if (teacherId == null)
            throw new NullPointerException("Teacher Id is Null, so redirecting back to Teacher List");

        List<Department> departmentList = departmentRepository.findAll();
        Teacher updatedTeacher = teacherRepository.findById(teacherId)
            .orElseThrow(() -> new NoSuchElementException("Teacher Id not found"));

        if (updatedTeacher.getAccount().getRole().equals(Role.MANAGER))
            throw new AuthorizationServiceException("Can not update MANAGER info");

        DtoUpdateTeacher teacher = DtoUpdateTeacher.builder()
            .instituteEmail(updatedTeacher.getAccount().getInstituteEmail())
            .teacherId(updatedTeacher.getTeacherId())
            .lastName(updatedTeacher.getLastName())
            .firstName(updatedTeacher.getFirstName())
            .birthday(updatedTeacher.getBirthday())
            .gender(updatedTeacher.getGender())
            .departmentId(updatedTeacher.getDepartment().getDepartmentId())
            .phone(updatedTeacher.getPhone())
            .build();

        modelAndView.addObject("teacher", teacher);
        modelAndView.addObject("departmentList", departmentList);

        return modelAndView;
    }

    public ModelAndView getUpdateTeacherAccountPage(HttpServletRequest request) {
        final String accountId = request.getParameter("accountId");
        ModelAndView modelAndView = staticUtilMethods.customResponseModelView(request, "update-teacher-account");

        if (accountId == null)
            throw new NullPointerException("Account Id is Null, so redirecting back to Teacher Account List");

        Account account = accountRepository
            .findById(Long.parseLong(accountId))
            .orElseThrow(() -> new NoSuchElementException("Account Id not found"));

        if (account.getRole().equals(Role.MANAGER))
            throw new AuthorizationServiceException("Can not change data of MANAGER account");

        modelAndView.addObject("account", DtoUpdateTeacherAccount.builder()
            .accountId(account.getAccountId())
            .instituteEmail(account.getInstituteEmail())
            .creatingTime(account.getCreatingTime())
            .status(account.isStatus())
            .build());
        return modelAndView;
    }

    public ModelAndView getTeacherRequestDetailPage(HttpServletRequest request) {
        final String requestId = request.getParameter("requestId");
        ModelAndView modelAndView = new ModelAndView("teacher-request-detail");

        if (requestId == null)
            throw new NullPointerException("Request Id is Null, so redirecting back to Teacher Request List");

        TeacherRequest teacherRequest = teacherRequestRepository
            .findById(Long.parseLong(requestId))
            .orElseThrow(() -> new NoSuchElementException("Request Id not found"));

        modelAndView.addObject("teacherRequest", teacherRequest);
        return modelAndView;
    }

    public ModelAndView getAddPracticeSchedulePage(HttpServletRequest request) throws SQLException {
        final String requestId = request.getParameter("requestId");
        ModelAndView modelAndView = staticUtilMethods.customResponseModelView(request, "add-practice-schedule");

        if (requestId == null)
            throw new NullPointerException("Request Id is Null, so redirecting back to Teacher Request List");

        TeacherRequest teacherRequest = teacherRequestRepository
            .findById(Long.parseLong(requestId))
            .orElseThrow(() -> new NoSuchElementException("Request Id not found"));

        List<DtoSubjectSchedule> allSubjectSchedules = subjectScheduleRepository
            .findAllScheduleByTeacherRequest(
                teacherRequest.getSectionClass().getSemester().getSemesterId(),
                teacherRequest.getTeacher().getTeacherId(),
                teacherRequest.getSectionClass().getGrade().getGradeId()
            );

        List<DtoPracticeSchedule> allPrcScheduleInSemester = subjectScheduleRepository
            .findAllPracticeScheduleInCurrentSemester(
                teacherRequest.getSectionClass().getSemester().getSemesterId()
            );

        Integer studentQuantityInCurrentSectionClass = subjectRegistrationRepository
            .countBySectionClassId(teacherRequest.getSectionClass().getSectionClassId());
        int studentQuantity = (studentQuantityInCurrentSectionClass == null) ? 0 : studentQuantityInCurrentSectionClass;

        List<String> computerRoomList = classroomRepository
            .findAllComputerRoomIdWithQuantity(studentQuantity);

        modelAndView.addObject("teacherRequest", teacherRequest);
        modelAndView.addObject("subjectScheduleList", allSubjectSchedules);
        modelAndView.addObject("allPrcScheduleInSemester", allPrcScheduleInSemester);
        modelAndView.addObject("computerRoomList", computerRoomList);
        modelAndView.addObject("studentQuantity", studentQuantity);
        return modelAndView;
    }
}
