package com.SoftwareTech.PrcScheduleWeb.controller.ManagerController;

import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.ReqDtoAddTeacherAccount;
import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.ReqDtoUpdateTeacherAccount;
import com.SoftwareTech.PrcScheduleWeb.service.ManagerService.M_AccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.NoSuchElementException;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "${url.post.manager.prefix.v1}")
public class M_AccountController {
    @Autowired
    private final M_AccountService accountService;
    @Autowired
    private final Validator hibernateValidator;

    @RequestMapping(value = "/add-teacher-account", method = POST)
    public String addTeacherAccount(
        @ModelAttribute("newAccountObject") ReqDtoAddTeacherAccount newAccountObject,
        RedirectAttributes redirectAttributes,
        HttpServletRequest request
    ) {
        final String standingUrl = request.getHeader("Referer");
        Set<ConstraintViolation<ReqDtoAddTeacherAccount>> violations = hibernateValidator.validate(newAccountObject);
        if (!violations.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorCode", violations.iterator().next().getMessage());
            return "redirect:" + standingUrl;
        }

        try {
            accountService.addTeacherAccount(newAccountObject);
            redirectAttributes.addFlashAttribute("succeedCode", "succeed_add_01");
        } catch (IllegalArgumentException ignored) {
            redirectAttributes.addFlashAttribute("newAccountObject", newAccountObject);
            redirectAttributes.addFlashAttribute("errorCode", "error_entity_03");
        } catch (DuplicateKeyException ignored) {
            redirectAttributes.addFlashAttribute("newAccountObject", newAccountObject);
            redirectAttributes.addFlashAttribute("errorCode", "error_account_02");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("newAccountObject", newAccountObject);
            redirectAttributes.addFlashAttribute("errorCode", "error_systemApplication_01");
        }
        return "redirect:" + standingUrl;
    }

    @RequestMapping(value = "/update-teacher-account", method = POST)
    public String updateTeacherAccount(
        @ModelAttribute("account") ReqDtoUpdateTeacherAccount account,
        HttpServletRequest request,
        RedirectAttributes redirectAttributes
    ) {
        String page = (request.getParameter("pageNumber") == null) ? "1" : request.getParameter("pageNumber");
        final String redirectedUrl = "/manager/category/teacher/teacher-account-list?page=" + page;
        Set<ConstraintViolation<ReqDtoUpdateTeacherAccount>> violations = hibernateValidator.validate(account);
        if (!violations.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorCode", violations.iterator().next().getMessage());
            return "redirect:" + redirectedUrl;
        }

        try {
            accountService.updateTeacherAccount(request, account);
            redirectAttributes.addFlashAttribute("succeedCode", "succeed_update_01");
        } catch (NoSuchElementException e) {
            redirectAttributes.addFlashAttribute("errorCode", "error_entity_01");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorCode", "error_systemApplication_01");
        }
        return "redirect:" + redirectedUrl;
    }

    @RequestMapping(value = "/teacher-account-list-active-btn", method = POST)
    public String deleteTeacherAccount(
        @ModelAttribute("deleteBtn") String accountId,
        HttpServletRequest request,
        RedirectAttributes redirectAttributes
    ) {
        try {
            accountService.deleteTeacherAccount(accountId);
            redirectAttributes.addFlashAttribute("succeedCode", "succeed_delete_01");
        } catch (NumberFormatException | NoSuchElementException e) {
            redirectAttributes.addFlashAttribute("errorCode", "error_entity_01");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorCode", "error_entity_02");
        }
        return "redirect:" + request.getHeader("Referer");
    }
}
