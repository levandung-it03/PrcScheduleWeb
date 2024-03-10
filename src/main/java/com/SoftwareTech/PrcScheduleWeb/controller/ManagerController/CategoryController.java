package com.SoftwareTech.PrcScheduleWeb.controller.ManagerController;
import com.SoftwareTech.PrcScheduleWeb.service.ManagerService.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/manager/category")
public class CategoryController {
    @Autowired
    private final CategoryService categoryService;

    /*******************Computer_Room_Pages_on_Category*******************/
    @RequestMapping(value = "/computer-room/add-computer-room", method = GET)
    public ModelAndView getAddComputerRoomPage(HttpServletRequest request, Model model) {
        return categoryService.getAddComputerRoomPage(request, model);
    }

    @RequestMapping(value = "/computer-room/computer-room-list", method = GET)
    public ModelAndView getComputerRoomListPage(HttpServletRequest request) {
        return categoryService.getComputerRoomListPage(request);
    }

    /*******************Teacher_Pages_on_Category*******************/
    @RequestMapping(value = "/teacher/add-teacher-account", method = GET)
    public ModelAndView getAddTeacherAccountPage(HttpServletRequest request, Model model) {
        return categoryService.getAddTeacherAccountPage(request, model);
    }

    @RequestMapping(value = "/teacher/teacher-list", method = GET)
    public ModelAndView getDefaultTeacherListPage(HttpServletRequest request) {
        return categoryService.getTeacherListPage(request);
    }

    @RequestMapping(value = "/teacher/teacher-account-list", method = GET)
    public ModelAndView getDefaultTeacherAccountListPage(HttpServletRequest request) {
        return categoryService.getDefaultTeacherAccountListPage(request);
    }

//    @RequestMapping(value = "/teacher/teacher-list", method = POST)
//    public ModelAndView getTeacherListPage(@PathVariable("pageBtn") Integer page, HttpServletRequest request) {
//        return categoryService.getTeacherListPage(request, page);
//    }

}
