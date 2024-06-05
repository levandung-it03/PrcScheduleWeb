package com.SoftwareTech.PrcScheduleWeb.controller.TeacherController;

import com.SoftwareTech.PrcScheduleWeb.service.TeacherService.T_SubPageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.NoSuchElementException;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/teacher/sub-page")
public class T_SubPageController {
    private final T_SubPageService subPageService;

    @RequestMapping(value = "/teacher/update-account/change-password")
    public ModelAndView getChangePasswordPage(HttpServletRequest request, Model model) {
        return subPageService.getChangePasswordPage(request, model);
    }

    @RequestMapping(value = "/teacher/set-teacher-info")
    public ModelAndView getSetTeacherInfoPage(HttpServletRequest request, Model model) {
        try {
            return subPageService.getSetTeacherInfoPage(request, model);
        } catch (DuplicateKeyException ignored) {
            //--Teacher info is already existing so redirecting to HomePage.
            return new ModelAndView("redirect:/home");
        } catch (Exception ignored) {
            request.getSession().setAttribute("errorCode", "error_systemApplication_01");
        }
        return new ModelAndView("redirect:" + request.getHeader("Referer"));
    }

    @RequestMapping(value = "/teacher/update-teacher-info")
    public ModelAndView getUpdateTeacherInfoPage(HttpServletRequest request, Model model) {
        try {
            return subPageService.getUpdateTeacherInfoPage(request, model);
        } catch (NoSuchElementException ignored) {
            request.getSession().setAttribute("errorCode", "error_entity_01");
        } catch (Exception ignored) {
            request.getSession().setAttribute("errorCode", "error_systemApplication_01");
        }
        return new ModelAndView("redirect:/home");
    }

    @RequestMapping(value = "/practice-schedule/teacher-request-detail", method = GET)
    public ModelAndView getTeacherRequestDetailPage(HttpServletRequest request, Model model) {
        try {
            return subPageService.getTeacherRequestDetailPage(request, model);
        } catch (NoSuchElementException ignored) {
            request.getSession().setAttribute("errorCode", "error_entity_01");
        } catch (Exception ignored) {
            request.getSession().setAttribute("errorCode", "error_systemApplication_01");
        }
        return new ModelAndView("redirect:/manager/category/teacher/teacher-request-list");
    }

    @RequestMapping(value = "/practice-schedule/update-teacher-request", method = GET)
    public ModelAndView getUpdateTeacherRequestPage(HttpServletRequest request, Model model) {
        final String standingUrl = request.getHeader("Referer");
        try {
            return subPageService.getUpdateTeacherRequestPage(request, model);
        } catch (NumberFormatException | NullPointerException ignored) {
            //--This exception may throw when user reloads our page, so do nothing.
        } catch (NoSuchElementException ignored) {
            request.getSession().setAttribute("errorCode", "error_entity_01");
        } catch (SQLIntegrityConstraintViolationException e) {
            request.getSession().setAttribute("errorCode", e.getMessage());
        } catch (Exception ignored) {
            request.getSession().setAttribute("errorCode", "error_systemApplication_01");
        }
        return new ModelAndView("redirect:" + standingUrl);
    }
}
