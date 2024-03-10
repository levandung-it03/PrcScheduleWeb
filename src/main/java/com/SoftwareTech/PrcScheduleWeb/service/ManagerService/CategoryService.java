package com.SoftwareTech.PrcScheduleWeb.service.ManagerService;

import com.SoftwareTech.PrcScheduleWeb.config.StaticUtilMethods;
import com.SoftwareTech.PrcScheduleWeb.dto.AuthDto.DtoRegisterAccount;
import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.DtoAddComputerRoom;
import com.SoftwareTech.PrcScheduleWeb.model.enums.Role;
import com.SoftwareTech.PrcScheduleWeb.repository.AccountRepository;
import com.SoftwareTech.PrcScheduleWeb.repository.ComputerRoomRepository;
import com.SoftwareTech.PrcScheduleWeb.repository.TeacherRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.awt.print.Pageable;

@Service
@RequiredArgsConstructor
public class CategoryService {
    @Autowired
    private final StaticUtilMethods staticUtilMethods;
    @Autowired
    private final ComputerRoomRepository computerRoomRepository;
    @Autowired
    private final TeacherRepository teacherRepository;
    @Autowired
    private final AccountRepository accountRepository;

    public ModelAndView getAddTeacherAccountPage(HttpServletRequest request, Model model) {
        ModelAndView modelAndView = staticUtilMethods.customizeResponsiveModelAndView(request, "add-account");
        DtoRegisterAccount registerObject = (DtoRegisterAccount) model.asMap().get("registerObject");

        if (registerObject != null)
            modelAndView.addObject(registerObject);
        return modelAndView;
    }

    public ModelAndView getAddComputerRoomPage(HttpServletRequest request, Model model) {
        ModelAndView modelAndView = staticUtilMethods.customizeResponsiveModelAndView(request, "add-computer-room");

        //--Refill data form after an error occurs.
        DtoAddComputerRoom computerRoomObject = (DtoAddComputerRoom) model.asMap().get("computerRoomObject");

        if (computerRoomObject != null)
            modelAndView.addObject(computerRoomObject);
        return modelAndView;
    }

    public ModelAndView getComputerRoomListPage(HttpServletRequest request) {
        ModelAndView modelAndView = staticUtilMethods.customizeResponsiveModelAndView(request, "computer-room-list");
        modelAndView.addObject("computerRoomList", computerRoomRepository.findAll());

        return modelAndView;
    }

    public ModelAndView getTeacherListPage(HttpServletRequest request) {
        ModelAndView modelAndView = staticUtilMethods.customizeResponsiveModelAndView(request, "teacher-list");
        modelAndView.addObject("teacherList", teacherRepository.findAll());

        return modelAndView;
    }

    public ModelAndView getDefaultTeacherAccountListPage(HttpServletRequest request) {
        ModelAndView modelAndView = staticUtilMethods.customizeResponsiveModelAndView(request, "teacher-account-list");

        int requestPageParam = (request.getParameter("page") == null) ? 1 : Integer.parseInt(request.getParameter("page"));
        int requestPage = Math.max(requestPageParam, 1);
        modelAndView.addObject("currentPage", requestPage);
        modelAndView.addObject("accountList",
            accountRepository.findAllByExistingTeacherIds(PageRequest.of(requestPage - 1, 10)));

        return modelAndView;
    }
}
