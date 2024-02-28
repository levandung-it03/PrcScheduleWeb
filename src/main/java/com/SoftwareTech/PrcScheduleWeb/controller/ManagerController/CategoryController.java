package com.SoftwareTech.PrcScheduleWeb.controller.ManagerController;

import com.SoftwareTech.PrcScheduleWeb.config.StaticUtilMethods;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/manager/category/")
public class CategoryController {
    @Autowired
    private final StaticUtilMethods staticUtilMethods;

    @RequestMapping(value = "/teacher/add-teacher-account", method = GET)
    public ModelAndView getAddTeacherAccountPage(HttpServletRequest request) {
        return staticUtilMethods.customizeModelAndView(request, "add-account");
    }
}
