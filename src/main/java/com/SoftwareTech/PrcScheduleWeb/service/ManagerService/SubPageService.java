package com.SoftwareTech.PrcScheduleWeb.service.ManagerService;

import com.SoftwareTech.PrcScheduleWeb.config.StaticUtilMethods;
import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.DtoComputerRoom;
import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.DtoUpdateTeacher;
import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.DtoUpdateTeacherAccount;
import com.SoftwareTech.PrcScheduleWeb.model.Account;
import com.SoftwareTech.PrcScheduleWeb.model.ComputerRoomDetail;
import com.SoftwareTech.PrcScheduleWeb.model.Department;
import com.SoftwareTech.PrcScheduleWeb.model.Teacher;
import com.SoftwareTech.PrcScheduleWeb.model.enums.Role;
import com.SoftwareTech.PrcScheduleWeb.repository.AccountRepository;
import com.SoftwareTech.PrcScheduleWeb.repository.ComputerRoomDetailRepository;
import com.SoftwareTech.PrcScheduleWeb.repository.DepartmentRepository;
import com.SoftwareTech.PrcScheduleWeb.repository.TeacherRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

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
    private final ComputerRoomDetailRepository computerRoomDetailRepository;
    @Autowired
    private final AccountRepository accountRepository;

    public ModelAndView getUpdateComputerRoomPage(HttpServletRequest request) {
        final String roomId = request.getParameter("roomId");
        ModelAndView modelAndView = staticUtilMethods.customResponseModelView(request, "update-computer-room");

        if (roomId == null)
            throw new NullPointerException("Computer Room Code is Null, so redirecting back to Computer Room List");

        ComputerRoomDetail computerRoomDetail = computerRoomDetailRepository
            .findByRoomId(roomId)
            .orElseThrow(() -> new NoSuchElementException("Computer Room not found"));

        modelAndView.addObject("roomObject", DtoComputerRoom.builder()
            .roomId(roomId)
            .maxComputerQuantity(computerRoomDetail.getMaxComputerQuantity())
            .availableComputerQuantity(computerRoomDetail.getAvailableComputerQuantity())
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
}
