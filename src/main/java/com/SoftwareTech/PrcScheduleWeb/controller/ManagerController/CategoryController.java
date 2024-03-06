package com.SoftwareTech.PrcScheduleWeb.controller.ManagerController;
import com.SoftwareTech.PrcScheduleWeb.service.ManagerService.CategoryService;
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
public class CategoryController {
    @Autowired
    private final CategoryService categoryService;

    @RequestMapping(value = "/teacher/add-teacher-account", method = GET)
    public ModelAndView getAddTeacherAccountPage(HttpServletRequest request, Model model) {
        return categoryService.getAddTeacherAccountPage(request, model);
    }

    @RequestMapping(value = "/computer-room/add-computer-room", method = GET)
    public ModelAndView getAddComputerRoomPage(HttpServletRequest request, Model model) {
        return categoryService.getAddComputerRoomPage(request, model);
    }

    @RequestMapping(value = "/computer-room/computer-room-list", method = GET)
    public ModelAndView getComputerRoomListPage(HttpServletRequest request, Model model) {
        return categoryService.getComputerRoomListPage(request, model);
    }

}
