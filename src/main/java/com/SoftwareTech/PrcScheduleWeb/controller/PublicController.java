package com.SoftwareTech.PrcScheduleWeb.controller;

import com.SoftwareTech.PrcScheduleWeb.service.ManagerService.M_PublicPagesService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "/public")
@RequiredArgsConstructor
public class PublicController {
    @Autowired
    private final M_PublicPagesService publicPagesService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView getLoginView(HttpServletRequest request, Model model) {
        return publicPagesService.renderLoginPage(request, model);
    }

    @RequestMapping(value = "/forgot-password-page", method = RequestMethod.GET)
    public ModelAndView getForgotPasswordView(HttpServletRequest request, Model model) {
        return publicPagesService.getForgotPasswordView(request, model);
    }

}
