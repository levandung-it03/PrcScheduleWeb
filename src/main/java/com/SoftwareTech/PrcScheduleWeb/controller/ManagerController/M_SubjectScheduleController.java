package com.SoftwareTech.PrcScheduleWeb.controller.ManagerController;

import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.ReqDtoPracticeSchedule;
import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.ReqDtoUpdatePracticeSchedule;
import com.SoftwareTech.PrcScheduleWeb.service.ManagerService.M_SubjectScheduleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.NoSuchElementException;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "${url.post.manager.prefix.v1}")
public class M_SubjectScheduleController {
    @Autowired
    private final M_SubjectScheduleService subjectScheduleService;
    @Autowired
    private final Logger logger;
    @Autowired
    private final Validator hibernateValidator;

    @RequestMapping(value = "/add-practice-schedule", method = POST)
    public String addPracticeSchedule(
        @ModelAttribute("practiceScheduleObj") ReqDtoPracticeSchedule practiceScheduleObj,
        HttpServletRequest request,
        RedirectAttributes redirectAttributes
    ) {
        final String standingUrl = request.getHeader("Referer") + "?requestId=" + practiceScheduleObj.getRequestId();
        Set<ConstraintViolation<ReqDtoPracticeSchedule>> violations = hibernateValidator.validate(practiceScheduleObj);
        if (!violations.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorCode", violations.iterator().next().getMessage());
            return "redirect:" + standingUrl;
        }

        try {
            subjectScheduleService.addPracticeSchedule(practiceScheduleObj);
            redirectAttributes.addFlashAttribute("succeedCode", "succeed_add_01");
            return "redirect:/manager/category/practice-schedule/teacher-request-list";
        } catch (NoSuchElementException e) {
            redirectAttributes.addFlashAttribute("errorCode", "error_entity_01");
            logger.info(e.toString());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorCode", "error_systemApplication_01");
            logger.info(e.toString());
        }
        return "redirect:" + standingUrl;
    }

    @RequestMapping(value = "/update-practice-schedule", method = POST)
    public String updatePracticeSchedule(
        @ModelAttribute("practiceScheduleObj") ReqDtoUpdatePracticeSchedule practiceScheduleObj,
        HttpServletRequest request,
        RedirectAttributes redirectAttributes
    ) {
        final String standingUrl = request.getHeader("Referer") + "?practiceScheduleId="+ practiceScheduleObj
            .getUpdatedPracticeScheduleId();
        Set<ConstraintViolation<ReqDtoUpdatePracticeSchedule>> violations = hibernateValidator.validate(practiceScheduleObj);
        if (!violations.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorCode", violations.iterator().next().getMessage());
            return "redirect:" + standingUrl;
        }

        try {
            subjectScheduleService.updatePracticeSchedule(practiceScheduleObj);
            redirectAttributes.addFlashAttribute("succeedCode", "succeed_update_01");
            return "redirect:/manager/sub-page/practice-schedule/teacher-request-detail?requestId="
                + practiceScheduleObj.getRequestId();
        } catch (NoSuchElementException e) {
            redirectAttributes.addFlashAttribute("errorCode", "error_entity_01");
            logger.info(e.toString());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorCode", "error_systemApplication_01");
            logger.info(e.toString());
        }
        return "redirect:" + standingUrl;
    }

    @RequestMapping(value = "/delete-practice-schedule", method = POST)
    public String deletePracticeSchedule(
        @ModelAttribute("deleteBtn") String practiceScheduleId,
        HttpServletRequest request,
        RedirectAttributes redirectAttributes
    ) {
        try {
            subjectScheduleService.deletePracticeSchedule(practiceScheduleId);
            redirectAttributes.addFlashAttribute("succeedCode", "succeed_delete_01");
        } catch (NumberFormatException | NoSuchElementException e) {
            logger.info(e.toString());
            redirectAttributes.addFlashAttribute("errorCode", "error_entity_01");
        } catch (SQLIntegrityConstraintViolationException e) {
            logger.info(e.toString());
            redirectAttributes.addFlashAttribute("errorCode", e.getMessage());
        } catch (Exception e) {
            logger.info(e.toString());
            redirectAttributes.addFlashAttribute("errorCode", "error_systemApplication_01");
        }
        return "redirect:" + request.getHeader("Referer");
    }
}
