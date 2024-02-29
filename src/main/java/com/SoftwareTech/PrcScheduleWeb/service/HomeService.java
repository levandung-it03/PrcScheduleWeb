package com.SoftwareTech.PrcScheduleWeb.service;

import com.SoftwareTech.PrcScheduleWeb.config.StaticUtilMethods;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class HomeService {
    @Autowired
    private final StaticUtilMethods staticUtilMethods;

    public ModelAndView handleGetHomeRequestFromBothRoles(HttpServletRequest request) {
        String loggedInRole = staticUtilMethods.isAValidAccessTokenInCookies(request);
        if (loggedInRole != null)
            return staticUtilMethods.customizeResponsiveModelAndView(request, loggedInRole + "-home");
        else
            return staticUtilMethods.customizeResponsiveModelAndView(request, "login");
    }
}
