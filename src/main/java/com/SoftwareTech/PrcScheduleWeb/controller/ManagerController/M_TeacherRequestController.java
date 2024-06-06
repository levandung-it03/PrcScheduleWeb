package com.SoftwareTech.PrcScheduleWeb.controller.ManagerController;

import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.ReqDtoInteractTeacherRequest;
import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.ReqDtoManagerInfo;
import com.SoftwareTech.PrcScheduleWeb.service.ManagerService.M_TeacherRequestService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(path = "${url.post.manager.prefix.v1}")
public class M_TeacherRequestController {
    @Autowired
    private final M_TeacherRequestService teacherRequestService;
    @Autowired
    private final Validator hibernateValidator;

    @RequestMapping(value = "/deny-teacher-request", method = POST)
    public String denyingTeacherRequest(
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
            teacherRequestService.denyingTeacherRequest(requestInteraction);
            redirectAttributes.addFlashAttribute("succeedCode", "succeed_update_01");
        } catch (NumberFormatException | NoSuchElementException e) {
            redirectAttributes.addFlashAttribute("errorCode", "error_entity_01");
        } catch (SQLIntegrityConstraintViolationException e) {
            redirectAttributes.addFlashAttribute("errorCode", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorCode", "error_systemApplication_01");
        }
        return "redirect:" + standingUrl;
    }
}
