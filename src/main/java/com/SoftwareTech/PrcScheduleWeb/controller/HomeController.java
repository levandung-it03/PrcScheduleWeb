package com.SoftwareTech.PrcScheduleWeb.controller;

import com.SoftwareTech.PrcScheduleWeb.service.ManagerService.M_HomeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class HomeController {
    @Autowired
    private final M_HomeService homeService;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView getHomePageOfBothRoles(HttpServletRequest request, Model model) {
        return homeService.handleGettingHomeRequestFromBothRoles(request, model);
    }
}
