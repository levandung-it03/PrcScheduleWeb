package com.SoftwareTech.PrcScheduleWeb.controller.ManagerController;

import com.SoftwareTech.PrcScheduleWeb.dto.AuthDto.DtoRegisterAccount;
import com.SoftwareTech.PrcScheduleWeb.service.ManagerService.ManageTeacherService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/service/v1/manager")
public class ManageTeacherController {
    @Autowired
    private final ManageTeacherService manageTeacherService;

    @RequestMapping(value = "/add-teacher-account", method = RequestMethod.POST)
    @ModelAttribute("registerObject")
    public void addTeacherAccount(
        DtoRegisterAccount registerObject,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws IOException {
        //--Redirect whether succeed or not.
        manageTeacherService.addTeacherAccount(registerObject, request, response);
    }

}
