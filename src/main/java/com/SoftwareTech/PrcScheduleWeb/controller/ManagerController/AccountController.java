package com.SoftwareTech.PrcScheduleWeb.controller.ManagerController;

import com.SoftwareTech.PrcScheduleWeb.dto.AuthDto.DtoRegisterAccount;
import com.SoftwareTech.PrcScheduleWeb.service.ManagerService.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "${url.post.manager.prefix.v1}")
public class AccountController {
    @Autowired
    private final AccountService accountService;

    @RequestMapping(value = "/add-teacher-account", method = RequestMethod.POST)
    public String addTeacherAccount(
        @ModelAttribute("registerObject") DtoRegisterAccount registerObject,
        RedirectAttributes redirectAttributes,
        HttpServletRequest request
    ) {
        return accountService.addTeacherAccount(registerObject, redirectAttributes, request);
    }
}
