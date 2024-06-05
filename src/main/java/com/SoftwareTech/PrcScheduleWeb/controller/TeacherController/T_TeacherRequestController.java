package com.SoftwareTech.PrcScheduleWeb.controller.TeacherController;

import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.ReqDtoInteractTeacherRequest;
import com.SoftwareTech.PrcScheduleWeb.dto.TeacherServiceDto.ReqDtoTeacherRequest;
import com.SoftwareTech.PrcScheduleWeb.service.TeacherService.T_TeacherRequestService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "${url.post.teacher.prefix.v1}")
public class T_TeacherRequestController {
    @Autowired
    private final T_TeacherRequestService teacherRequestService;
    @Autowired
    private final Validator hibernateValidator;

    @RequestMapping(value = "/add-teacher-request", method = POST)
    public String addTeacherRequest(
        @ModelAttribute("teacherRequest") ReqDtoTeacherRequest teacherRequest,
        HttpServletRequest request,
        RedirectAttributes redirectAttributes
    ) {
        final String standingUrl = request.getHeader("Referer");
        Set<ConstraintViolation<ReqDtoTeacherRequest>> violations = hibernateValidator.validate(teacherRequest);
        if (!violations.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorCode", violations.iterator().next().getMessage());
            return "redirect:" + standingUrl;
        }

        try {
            teacherRequestService.addTeacherRequest(request, teacherRequest);
            redirectAttributes.addFlashAttribute("succeedCode", "succeed_add_01");
        } catch (DuplicateKeyException ignored) {
            redirectAttributes.addFlashAttribute("errorCode", "error_teacherRequest_05");
        } catch (Exception ignored) {
            redirectAttributes.addFlashAttribute("errorCode", "error_systemApplication_01");
        }
        return "redirect:" + standingUrl;
    }

    @RequestMapping(value = "/update-teacher-request", method = POST)
    public String updateTeacherRequest(
        @ModelAttribute("teacherRequest") ReqDtoTeacherRequest teacherRequest,
        HttpServletRequest request,
        RedirectAttributes redirectAttributes
    ) {
        final String standingUrl = request.getHeader("Referer");
        Set<ConstraintViolation<ReqDtoTeacherRequest>> violations = hibernateValidator.validate(teacherRequest);
        if (!violations.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorCode", violations.iterator().next().getMessage());
            return "redirect:" + standingUrl;
        }

        try {
            teacherRequestService.updateTeacherRequest(request, teacherRequest);
            redirectAttributes.addFlashAttribute("succeedCode", "succeed_update_01");
        } catch (NumberFormatException | NoSuchElementException ignored) {
            redirectAttributes.addFlashAttribute("errorCode", "error_entity_01");
        } catch (DuplicateKeyException ignored) {
            redirectAttributes.addFlashAttribute("errorCode", "error_teacherRequest_05");
        } catch (Exception ignored) {
            redirectAttributes.addFlashAttribute("errorCode", "error_systemApplication_01");
        }
        return "redirect:/teacher/category/practice-schedule/teacher-request-list";
    }

    @RequestMapping(value = "/cancel-teacher-request", method = POST)
    public String cancelTeacherRequest(
        @ModelAttribute("requestInteraction") ReqDtoInteractTeacherRequest requestInteraction,
        HttpServletRequest request,
        RedirectAttributes redirectAttributes
    ) {
        final String standingUrl = request.getHeader("Referer");
        Set<ConstraintViolation<ReqDtoInteractTeacherRequest>> violations = hibernateValidator.validate(requestInteraction);
        if (!violations.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorCode", violations.iterator().next().getMessage());
            return "redirect:" + standingUrl;
        }

        try {
            teacherRequestService.cancelTeacherRequest(requestInteraction);
            redirectAttributes.addFlashAttribute("succeedCode", "succeed_update_01");
        } catch (NoSuchElementException ignored) {
            redirectAttributes.addFlashAttribute("errorCode", "error_entity_01");
        } catch (DuplicateKeyException ignored) {
            redirectAttributes.addFlashAttribute("errorCode", "error_teacherRequest_05");
        } catch (SQLIntegrityConstraintViolationException ignored) {
            redirectAttributes.addFlashAttribute("errorCode", "error_teacherRequest_07");
        } catch (Exception ignored) {
            redirectAttributes.addFlashAttribute("errorCode", "error_systemApplication_01");
        }
        return "redirect:" + standingUrl;
    }
}
