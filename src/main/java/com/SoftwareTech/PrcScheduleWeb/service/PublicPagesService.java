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

    public ModelAndView renderLoginPage(
        HttpServletRequest request,
        HttpServletResponse response,
        Model model
    ) throws IOException {
        String loggedInRole = staticUtilMethods.isAValidAccessTokenInCookies(request);
        if (loggedInRole != null) {
            response.sendRedirect("redirect:/home");
            return null;
        } else {
            return staticUtilMethods.customResponseModelView(request, model.asMap(), "login");
        }
    }
}
