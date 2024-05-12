package com.SoftwareTech.PrcScheduleWeb.controller.ManagerController;
import com.SoftwareTech.PrcScheduleWeb.service.ManagerService.M_CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/manager/category")
public class M_CategoryController {
    @Autowired
    private final M_CategoryService categoryService;

    /*******************Practice_Schedule_Pages_on_Category*******************/
    @RequestMapping(value = "/practice-schedule/teacher-request-list", method = GET)
    public ModelAndView getTeacherRequestListPage(HttpServletRequest request, Model model) {
        return categoryService.getTeacherRequestListPage(request, model);
    }

    /*******************Computer_Room_Pages_on_Category*******************/
    @RequestMapping(value = "/computer-room/add-computer-room", method = GET)
    public ModelAndView getAddComputerRoomPage(HttpServletRequest request, Model model) {
        return categoryService.getAddComputerRoomPage(request, model);
    }

    @RequestMapping(value = "/computer-room/computer-room-list", method = GET)
    public ModelAndView getComputerRoomListPage(HttpServletRequest request, Model model) {
        return categoryService.getComputerRoomListPage(request, model);
    }

    /*******************Teacher_Pages_on_Category*******************/
    @RequestMapping(value = "/teacher/add-teacher-account", method = GET)
    public ModelAndView getAddTeacherAccountPage(HttpServletRequest request, Model model) {
        return categoryService.getAddTeacherAccountPage(request, model);
    }

    @RequestMapping(value = "/teacher/teacher-list", method = GET)
    public ModelAndView getDefaultTeacherListPage(HttpServletRequest request, Model model) {
        return categoryService.getTeacherListPage(request, model);
    }

    @RequestMapping(value = "/teacher/teacher-account-list", method = GET)
    public ModelAndView getTeacherAccountListPage(HttpServletRequest request, Model model) {
        return categoryService.getTeacherAccountListPage(request, model);
    }

    /*******************Extra_Features_On_Category*******************/
    @RequestMapping(value = "/extra-features/extra-features-page", method = GET)
    public ModelAndView getExtraFeaturePage(HttpServletRequest request, Model model) {
        return categoryService.getExtraFeaturePage(request, model);
    }
}
