package com.SoftwareTech.PrcScheduleWeb.controller;

import com.SoftwareTech.PrcScheduleWeb.auth.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

    @RequestMapping(value = "/teacher/home", method = RequestMethod.GET)
    public String getTeacherHome() {
        return "teacher-home";
    }

    @RequestMapping(value = "/manager/home", method = RequestMethod.GET)
    public String getManagerHome() {
            return "manager-home";
    }

}
