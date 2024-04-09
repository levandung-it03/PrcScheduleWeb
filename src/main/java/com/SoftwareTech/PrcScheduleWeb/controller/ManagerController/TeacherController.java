package com.SoftwareTech.PrcScheduleWeb.controller.ManagerController;

import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.ReqDtoUpdateTeacher;
import com.SoftwareTech.PrcScheduleWeb.service.ManagerService.TeacherService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.NoSuchElementException;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "${url.post.manager.prefix.v1}")
public class TeacherController {
    @Autowired
    private final TeacherService teacherService;

    @RequestMapping(value = "/update-teacher", method = POST)
    public String updateTeacherInfo(
        @Valid @ModelAttribute("teacher") ReqDtoUpdateTeacher teacher,
        HttpServletRequest request,
        RedirectAttributes redirectAttributes,
        BindingResult bindingResult
    ) {
        String page = (request.getParameter("pageNumber") == null) ? "1" : request.getParameter("pageNumber");
        final String redirectedUrl = "/manager/category/teacher/teacher-list?page=" + page;
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorCode", bindingResult.getFieldErrors().getFirst());
            return "redirect:" + redirectedUrl;
        }

        try {
            teacherService.updateTeacher(teacher);
            redirectAttributes.addFlashAttribute("succeedCode", "succeed_update_01");
        } catch (IllegalArgumentException | NoSuchElementException ignored) {
            redirectAttributes.addFlashAttribute("errorCode", "error_entity_01");
        } catch (Exception ignored) {
//            redirectAttributes.addFlashAttribute("errorCode", "error_systemApplication_01");
        }
        return "redirect:" + redirectedUrl;
    }
}
