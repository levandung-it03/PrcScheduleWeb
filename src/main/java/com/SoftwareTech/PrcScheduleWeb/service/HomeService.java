package com.SoftwareTech.PrcScheduleWeb.service;

import com.SoftwareTech.PrcScheduleWeb.config.StaticUtilMethods;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

@Service
@RequiredArgsConstructor
public class HomeService {
    @Autowired
    private final StaticUtilMethods staticUtilMethods;

    public ModelAndView handleGettingHomeRequestFromBothRoles(HttpServletRequest request, Model model) {
        String loggedInRole = staticUtilMethods.isAValidAccessTokenInCookies(request);
        if (loggedInRole != null)
            return staticUtilMethods.customResponseModelView(model.asMap(), loggedInRole + "-home");
        else
            return staticUtilMethods.customResponseModelView(model.asMap(), "login");
    }
}
