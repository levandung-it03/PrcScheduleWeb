package com.SoftwareTech.PrcScheduleWeb.controller.ManagerController;

import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.DtoUpdateTeacher;
import com.SoftwareTech.PrcScheduleWeb.service.ManagerService.TeacherService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "${url.post.manager.prefix.v1}")
public class TeacherController {
    @Autowired
    private final TeacherService teacherService;

    @RequestMapping(value = "/update-teacher", method = POST)
    public String updateTeacherInfo(
        @ModelAttribute("teacher") DtoUpdateTeacher teacher,
        HttpServletRequest request
    ) {
        String page = (request.getParameter("pageNumber") == null) ? "1" : request.getParameter("pageNumber");
        final String redirectedUrl = "/manager/category/teacher/teacher-list";

        try {
            teacherService.updateTeacherAndGetRedirect(teacher);
            return "redirect:" + redirectedUrl + "?page=" + page + "&succeedMessage=sMv1at03";
        } catch (IllegalStateException ignored) {
            return "redirect:" + redirectedUrl + "?page=" + page + "&errorMessage=eMv1at09";
        } catch (Exception ignored) {
            return "redirect:" + redirectedUrl + "?page=" + page + "&errorMessage=eMv1at00";
        }
    }
}
