package com.SoftwareTech.PrcScheduleWeb.controller.TeacherController;

import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.ReqDtoManagerInfo;
import com.SoftwareTech.PrcScheduleWeb.dto.TeacherServiceDto.ReqDtoTeacherInfo;
import com.SoftwareTech.PrcScheduleWeb.service.TeacherService.T_TeacherService;
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

import java.util.NoSuchElementException;
import java.util.Set;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "${url.post.teacher.prefix.v1}")
public class T_TeacherController {
    @Autowired
    private final Validator hibernateValidator;
    @Autowired
    private final T_TeacherService teacherService;

    @RequestMapping(value = "/set-teacher-info", method = POST)
    public String setTeacherInfoForWhiteInfoAccount(
        @ModelAttribute("person") ReqDtoTeacherInfo teacherInfo,
        RedirectAttributes redirectAttributes,
        HttpServletRequest request
    ) {
        final String standingUrl = request.getHeader("Referer");
        Set<ConstraintViolation<ReqDtoTeacherInfo>> violations = hibernateValidator.validate(teacherInfo);
        if (!violations.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorCode", violations.iterator().next().getMessage());
            return "redirect:" + standingUrl;
        }

        try {
            teacherService.setTeacherInfoForWhiteInfoAccount(teacherInfo);
            return "redirect:/home";
        } catch (DuplicateKeyException e) {
            redirectAttributes.addFlashAttribute("errorCode", "error_teacher_01");
        } catch (NoSuchElementException e) {
            redirectAttributes.addFlashAttribute("errorCode", "error_account_01");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorCode", "error_systemApplication_01");
        }
        return "redirect:" + standingUrl;
    }

    @RequestMapping(value = "/update-teacher-info", method = POST)
    public String updateTeacherInfoForWhiteInfoAccount(
        @ModelAttribute("person") ReqDtoTeacherInfo teacherInfo,
        RedirectAttributes redirectAttributes,
        HttpServletRequest request
    ) {
        final String standingUrl = request.getHeader("Referer");
        Set<ConstraintViolation<ReqDtoTeacherInfo>> violations = hibernateValidator.validate(teacherInfo);
        if (!violations.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorCode", violations.iterator().next().getMessage());
            return "redirect:" + standingUrl;
        }

        try {
            teacherService.updateTeacherInfo(teacherInfo);
            return "redirect:/home";
        } catch (NoSuchElementException e) {
            redirectAttributes.addFlashAttribute("errorCode", "error_entity_03");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorCode", "error_systemApplication_01");
        }
        return "redirect:" + standingUrl;
    }
}
