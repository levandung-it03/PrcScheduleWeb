package com.SoftwareTech.PrcScheduleWeb.controller.ManagerController;

import com.SoftwareTech.PrcScheduleWeb.dto.AuthDto.DtoRegisterAccount;
import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.ReqDtoUpdateTeacherAccount;
import com.SoftwareTech.PrcScheduleWeb.service.ManagerService.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "${url.post.manager.prefix.v1}")
public class AccountController {
    @Autowired
    private final AccountService accountService;

    @RequestMapping(value = "/add-teacher-account", method = POST)
    public String addTeacherAccount(
        @Valid @ModelAttribute("registerObject") DtoRegisterAccount registerObject,
        RedirectAttributes redirectAttributes,
        HttpServletRequest request,
        BindingResult bindingResult
    ) {
        final String standingUrl = request.getHeader("Referer");
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("registerObject", registerObject);
            redirectAttributes.addFlashAttribute("errorCode", bindingResult.getFieldErrors().getFirst());
            return "redirect:" + standingUrl;
        }

        try {
            accountService.addTeacherAccount(registerObject);
            redirectAttributes.addFlashAttribute("succeedCode", "succeed_add_01");
        } catch (IllegalArgumentException ignored) {
            redirectAttributes.addFlashAttribute("registerObject", registerObject);
            redirectAttributes.addFlashAttribute("errorCode", "error_entity_03");
        } catch (DuplicateKeyException ignored) {
            redirectAttributes.addFlashAttribute("registerObject", registerObject);
            redirectAttributes.addFlashAttribute("errorCode", "error_account_02");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("registerObject", registerObject);
            redirectAttributes.addFlashAttribute("errorCode", "error_systemApplication_01");
        }
        return "redirect:" + standingUrl;
    }

    @RequestMapping(value = "/update-teacher-account", method = POST)
    public String updateTeacherAccount(
        @Valid @ModelAttribute("account") ReqDtoUpdateTeacherAccount account,
        HttpServletRequest request,
        RedirectAttributes redirectAttributes,
        BindingResult bindingResult
    ) {
        String page = (request.getParameter("pageNumber") == null) ? "1" : request.getParameter("pageNumber");
        final String redirectedUrl = "/manager/category/teacher/teacher-account-list";
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorCode", bindingResult.getFieldErrors().getFirst());
            return "redirect:" + redirectedUrl + "?page=" + page;
        }

        try {
            accountService.updateTeacherAccount(request, account);
            redirectAttributes.addFlashAttribute("succeedCode", "succeed_update_01");
        } catch (NoSuchElementException e) {
            redirectAttributes.addFlashAttribute("errorCode", "error_entity_01");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorCode", "error_systemApplication_01");
        }
        return "redirect:" + redirectedUrl + "?page=" + page;
    }

    @RequestMapping(value = "/teacher-account-list-active-btn", method = POST)
    public String deleteTeacherAccount(
        @Valid @ModelAttribute("deleteBtn") String accountId,
        HttpServletRequest request,
        RedirectAttributes redirectAttributes
    ) {
        try {
            accountService.deleteTeacherAccount(accountId);
            redirectAttributes.addFlashAttribute("succeedCode", "succeed_delete_01");
        } catch (NoSuchElementException e) {
            redirectAttributes.addFlashAttribute("errorCode", "error_entity_01");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorCode", "error_entity_02");
        }
        return "redirect:" + request.getHeader("Referer");
    }
}
