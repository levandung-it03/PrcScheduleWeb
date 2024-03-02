package com.SoftwareTech.PrcScheduleWeb.controller.ManagerController;

import com.SoftwareTech.PrcScheduleWeb.config.StaticUtilMethods;
import com.SoftwareTech.PrcScheduleWeb.dto.AuthDto.DtoRegisterAccount;
import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.DtoComputerRoom;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "${url.get.manager.prefix}")
public class CategoryController {
    @Autowired
    private final StaticUtilMethods staticUtilMethods;

    @RequestMapping(value = "/teacher/add-teacher-account", method = GET)
    public ModelAndView getAddTeacherAccountPage(HttpServletRequest request, Model model) {
        ModelAndView modelAndView = staticUtilMethods.customizeResponsiveModelAndView(request, "add-account");
        DtoRegisterAccount registerObject = (DtoRegisterAccount) model.asMap().get("registerObject");

        if (registerObject != null)
            modelAndView.addObject(registerObject);
        return modelAndView;
    }

    @RequestMapping(value = "/computer-room/add-computer-room", method = GET)
    public ModelAndView getAddComputerRoomPage(HttpServletRequest request, Model model) {
        ModelAndView modelAndView = staticUtilMethods.customizeResponsiveModelAndView(request, "add-computer-room");
        DtoComputerRoom computerRoomObject = (DtoComputerRoom) model.asMap().get("computerRoomObject");

        if (computerRoomObject != null)
            modelAndView.addObject(computerRoomObject);
        return modelAndView;
    }

}
