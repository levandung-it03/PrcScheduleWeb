package com.SoftwareTech.PrcScheduleWeb.controller.ManagerController;

import com.SoftwareTech.PrcScheduleWeb.dto.AuthDto.DtoRegisterAccount;
import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.DtoUpdateTeacherAccount;
import com.SoftwareTech.PrcScheduleWeb.service.ManagerService.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequiredArgsConstructor
@RequestMapping(path = "${url.post.manager.prefix.v1}")
public class AccountController {
    @Autowired
    private final AccountService accountService;

    @RequestMapping(value = "/add-teacher-account", method = POST)
    public String addTeacherAccount(
        @ModelAttribute("registerObject") DtoRegisterAccount registerObject,
        RedirectAttributes redirectAttributes,
        HttpServletRequest request
    ) {
        final String standingUrl = request.getHeader("Referer");

        try {
            return accountService.addTeacherAccountAndGetRedirect(registerObject, request);
        } catch (IllegalArgumentException ignored) {
            redirectAttributes.addFlashAttribute("registerObject", registerObject);
            return "redirect:" + standingUrl + "?errorMessage=eMv1at09";
        } catch (DuplicateKeyException ignored) {
            redirectAttributes.addFlashAttribute("registerObject", registerObject);
            return "redirect:" + standingUrl + "?errorMessage=eMv1at03";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("registerObject", registerObject);
            return "redirect:" + standingUrl + "?errorMessage=eMv1at00";
        }
    }

    @RequestMapping(value = "/update-teacher-account", method = POST)
    public String updateTeacherAccount(
        @ModelAttribute("account") DtoUpdateTeacherAccount account,
        HttpServletRequest request
    ) {
        final String redirectedUrl = "/manager/category/teacher/teacher-account-list";

        try {
            return accountService.updateTeacherAccountAndGetRedirect(request, account);
        } catch (NullPointerException | NumberFormatException e) {
            return "redirect:" + redirectedUrl + "?errorMessage=eMv1at08";
        } catch (Exception e) {
            return "redirect:" + redirectedUrl + "?errorMessage=eMv1at00";
        }
    }

    @RequestMapping(value = "/teacher-account-list-active-btn", method = POST)
    public String deleteTeacherAccount(
        @ModelAttribute("deleteBtn") String accountId,
        HttpServletRequest request
    ) {
        final String standingUrl = request.getHeader("Referer");

        try {
            return accountService.deleteTeacherAccountAndGetRedirect(accountId, standingUrl);
        } catch (NullPointerException | NumberFormatException e) {
            return "redirect:" + standingUrl + "?errorMessage=eMv1at08";
        } catch (Exception e) {
            return "redirect:" + standingUrl + "?errorMessage=eMv1at06";
        }
    }
}
