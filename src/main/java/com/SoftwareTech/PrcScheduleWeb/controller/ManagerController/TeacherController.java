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
        final String standingUrl = request.getHeader("Referer");

        try {
            return teacherService.updateTeacherAndGetRedirect(teacher);
        } catch (IllegalStateException ignored) {
            return String.format("redirect:%s?teacherId=%s&errorMessage=eMv1at09", standingUrl, teacher.getTeacherId());
        } catch (Exception ignored) {
            return "redirect:/manager/category/teacher/teacher-list?errorMessage=eMv1at00";
        }
    }
}
