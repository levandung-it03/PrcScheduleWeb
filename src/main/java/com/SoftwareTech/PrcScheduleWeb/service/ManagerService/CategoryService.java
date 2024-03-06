package com.SoftwareTech.PrcScheduleWeb.service.ManagerService;

import com.SoftwareTech.PrcScheduleWeb.config.StaticUtilMethods;
import com.SoftwareTech.PrcScheduleWeb.dto.AuthDto.DtoRegisterAccount;
import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.DtoAddComputerRoom;
import com.SoftwareTech.PrcScheduleWeb.repository.ComputerRoomRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

@Service
@RequiredArgsConstructor
public class CategoryService {
    @Autowired
    private final StaticUtilMethods staticUtilMethods;
    @Autowired
    private final ComputerRoomRepository computerRoomRepository;

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

    public ModelAndView getComputerRoomListPage(HttpServletRequest request, Model model) {
        ModelAndView modelAndView = staticUtilMethods.customizeResponsiveModelAndView(request, "computer-room-list");
        modelAndView.addObject("computerRoomList", computerRoomRepository.findAll());

        return modelAndView;
    }
}
