package com.SoftwareTech.PrcScheduleWeb.controller;

import com.SoftwareTech.PrcScheduleWeb.service.HomeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class HomeController {
    @Autowired
    private final HomeService homeService;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView getHome(HttpServletRequest request) {
        return homeService.handleGettingHomeRequestFromBothRoles(request);
    }

}
