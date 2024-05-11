package com.SoftwareTech.PrcScheduleWeb.service;

import com.SoftwareTech.PrcScheduleWeb.config.StaticUtilMethods;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PublicPagesService {
    @Autowired
    private final StaticUtilMethods staticUtilMethods;

    public ModelAndView renderLoginPage(HttpServletRequest request, Model model) {
        if (staticUtilMethods.getAccountInfoInCookie(request) != null) {
            return new ModelAndView("redirect:/home");
        } else {
            return staticUtilMethods.customResponsiveModelView(request, model, "login");
        }
    }

    public ModelAndView getForgotPasswordView(HttpServletRequest request, Model model) {
        if (staticUtilMethods.getAccountInfoInCookie(request) != null) {
            return new ModelAndView("redirect:/home");
        } else {
            return staticUtilMethods.customResponsiveModelView(request, model, "forgot-password-page");
        }
    }
}
