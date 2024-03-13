package com.SoftwareTech.PrcScheduleWeb.service;

import com.SoftwareTech.PrcScheduleWeb.config.StaticUtilMethods;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Service
@RequiredArgsConstructor
public class PublicPagesService {
    @Autowired
    private final StaticUtilMethods staticUtilMethods;

    public ModelAndView renderLoginPage(HttpServletRequest request) {
        String loggedInRole = staticUtilMethods.isAValidAccessTokenInCookies(request);
        if (loggedInRole != null)
            return staticUtilMethods.customResponseModelView(request, loggedInRole + "-home");
        else
            return staticUtilMethods.customResponseModelView(request, "login");
    }
}
