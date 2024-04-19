package com.SoftwareTech.PrcScheduleWeb.controller.ManagerController;

import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.ReqAddGrade;
import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.ReqAddStudent;
import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.ReqAddSubject;
import com.SoftwareTech.PrcScheduleWeb.service.ManagerService.PostExtraFeaturesService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import jakarta.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Set;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "${url.post.manager.prefix.v1}")
public class PostExtraFeaturesController {
    @Autowired
    private final Validator hibernateValidator;
    @Autowired
    private final PostExtraFeaturesService postExtraFeaturesService;

    /**Author: Le Van Dung**/
    @RequestMapping(value = "/add-subject", method = POST)
    public String addSubject(
        @ModelAttribute("subjectObject") ReqAddSubject subjectObject,
        HttpServletRequest request,
        RedirectAttributes redirectAttributes
    ) {
        final String standingUrl = request.getHeader("Referer");
        Set<ConstraintViolation<ReqAddSubject>> violations = hibernateValidator.validate(subjectObject);
        if (!violations.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorCode", violations.iterator().next().getMessage());
            redirectAttributes.addFlashAttribute("subjectObject", subjectObject);
            return "redirect:" + standingUrl;
        }

        try {
            postExtraFeaturesService.addSubject(subjectObject);
            redirectAttributes.addFlashAttribute("succeedCode", "succeed_add_01");
        } catch (DuplicateKeyException ignored) {
            redirectAttributes.addFlashAttribute("subjectObject", subjectObject);
            redirectAttributes.addFlashAttribute("errorCode", "error_subject_01");
        } catch (Exception ignored) {
            redirectAttributes.addFlashAttribute("subjectObject", subjectObject);
            redirectAttributes.addFlashAttribute("errorCode", "error_systemApplication_01");
        }
        return "redirect:" + standingUrl;
    }
    /*----------------------*/

    /**Author: Nguyen Quang Linh**/
    @RequestMapping(value = "/add-student", method = POST)
    public String addStudent(
            @ModelAttribute("studentObject") ReqAddStudent studentObject,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
    ) {
        final String standingUrl = request.getHeader("Referer");
        Set<ConstraintViolation<ReqAddStudent>> violations = hibernateValidator.validate(studentObject);
        if (!violations.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorCode", violations.iterator().next().getMessage());
            redirectAttributes.addFlashAttribute("studentObject", studentObject);
            return "redirect:" + standingUrl;
        }

        try {
            postExtraFeaturesService.addStudent(studentObject);
            redirectAttributes.addFlashAttribute("succeedCode", "succeed_add_01");
        } catch (DuplicateKeyException ignored) {
            redirectAttributes.addFlashAttribute("studentObject", studentObject);
            redirectAttributes.addFlashAttribute("errorCode", "error_subject_01");
        } catch (Exception ignored) {
            redirectAttributes.addFlashAttribute("studentObject", studentObject);
            redirectAttributes.addFlashAttribute("errorCode", "error_systemApplication_01");
        }
        return "redirect:" + standingUrl;
    }
    @RequestMapping(value = "/add-grade", method = POST)
    public String addGrade(
            @ModelAttribute("gradeObject") ReqAddGrade gradeObject,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
    ) {
        final String standingUrl = request.getHeader("Referer");
        Set<ConstraintViolation<ReqAddGrade>> violations = hibernateValidator.validate(gradeObject);
        if (!violations.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorCode", violations.iterator().next().getMessage());
            redirectAttributes.addFlashAttribute("studentObject", gradeObject);
            return "redirect:" + standingUrl;
        }

        try {
            postExtraFeaturesService.addGrade(gradeObject);
            redirectAttributes.addFlashAttribute("succeedCode", "succeed_add_01");
        } catch (DuplicateKeyException ignored) {
            redirectAttributes.addFlashAttribute("studentObject", gradeObject);
            redirectAttributes.addFlashAttribute("errorCode", "error_subject_01");
        } catch (Exception ignored) {
            redirectAttributes.addFlashAttribute("studentObject", gradeObject);
            redirectAttributes.addFlashAttribute("errorCode", "error_systemApplication_01");
        }
        return "redirect:" + standingUrl;
    }
    /*----------------------*/
}
