package com.SoftwareTech.PrcScheduleWeb.controller.ManagerController;

import com.SoftwareTech.PrcScheduleWeb.dto.TeacherServiceDto.ReqDtoTeacherInfo;
import com.SoftwareTech.PrcScheduleWeb.service.ManagerService.M_TeacherService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.NoSuchElementException;
import java.util.Set;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "${url.post.manager.prefix.v1}")
public class M_TeacherController {
    @Autowired
    private final M_TeacherService teacherService;
    @Autowired
    private final Validator hibernateValidator;

    @RequestMapping(value = "/update-teacher", method = POST)
    public String updateTeacherInfo(
        @ModelAttribute("teacher") ReqDtoTeacherInfo teacher,
        RedirectAttributes redirectAttributes,
        HttpServletRequest request
    ) {
        String page = (request.getParameter("pageNumber") == null) ? "1" : request.getParameter("pageNumber");
        final String redirectedUrl = "/manager/category/teacher/teacher-list?page=" + page;
        Set<ConstraintViolation<ReqDtoTeacherInfo>> violations = hibernateValidator.validate(teacher);
        if (!violations.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorCode", violations.iterator().next().getMessage());
            return "redirect:" + redirectedUrl;
        }

        try {
            teacherService.updateTeacher(teacher);
            redirectAttributes.addFlashAttribute("succeedCode", "succeed_update_01");
        } catch (IllegalArgumentException | NoSuchElementException ignored) {
            redirectAttributes.addFlashAttribute("errorCode", "error_entity_01");
        } catch (Exception ignored) {
            redirectAttributes.addFlashAttribute("errorCode", "error_systemApplication_01");
        }
        return "redirect:" + redirectedUrl;
    }
}