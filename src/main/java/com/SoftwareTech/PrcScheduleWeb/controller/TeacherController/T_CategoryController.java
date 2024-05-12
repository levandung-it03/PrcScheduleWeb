package com.SoftwareTech.PrcScheduleWeb.controller.TeacherController;

import com.SoftwareTech.PrcScheduleWeb.service.TeacherService.T_CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/teacher/category")
public class T_CategoryController {
    @Autowired
    private final T_CategoryService categoryService;

    /*******************Practice_Schedule_Pages_on_Category*******************/
    @RequestMapping(value = "/practice-schedule/teacher-request-list", method = GET)
    public ModelAndView getTeacherRequestListPage(HttpServletRequest request, Model model) {
        return categoryService.getTeacherRequestListPage(request, model);
    }

    @RequestMapping(value = "/practice-schedule/add-teacher-request", method = GET)
    public ModelAndView getAddTeacherRequestPage(HttpServletRequest request, Model model) throws SQLException {
        return categoryService.getAddTeacherRequestPage(request, model);
    }

}
