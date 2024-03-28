package com.SoftwareTech.PrcScheduleWeb.controller.ManagerController;

import com.SoftwareTech.PrcScheduleWeb.service.ManagerService.SubPageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/manager/sub-page")
public class SubPageController {
    @Autowired
    private final SubPageService subPageService;

    @RequestMapping(value = "/computer-room/update-computer-room", method = GET)
    public ModelAndView getUpdateComputerRoomPage(
        HttpServletRequest request,
        HttpServletResponse response,
        RedirectAttributes redirectAttributes
    ) throws IOException {
        final String redirectedUrl = "/manager/category/computer-room/computer-room-list";
        final String standingUrl = request.getHeader("Referer");

        try {
            ModelAndView modelAndView = subPageService.getUpdateComputerRoomPage(request);
            if (standingUrl.contains("page=")) {
                //--Get the page-number of previous list-page (serving turn-back list-page action).
                modelAndView.addObject("pageNumber", standingUrl.split("page=")[1]);
            } else if (standingUrl.contains("category")) {
                //--Previous list-page doesn't have page-number --> default-page = 1.
                modelAndView.addObject("pageNumber", 1);
            }

            return modelAndView;
        } catch (NullPointerException ignored) {
            response.sendRedirect(redirectedUrl);
        } catch (NoSuchElementException ignored) {
            redirectAttributes.addFlashAttribute("errorCode", "error_entity_01");
            response.sendRedirect(redirectedUrl);
        } catch (Exception ignored) {
            redirectAttributes.addFlashAttribute("errorCode", "error_systemApplication_01");
            response.sendRedirect(redirectedUrl);
        }
        return null;
    }

    @RequestMapping(value = "/teacher/update-teacher", method = GET)
    public ModelAndView getUpdateTeacherPage(
        HttpServletRequest request,
        HttpServletResponse response,
        RedirectAttributes redirectAttributes
    ) throws IOException {
        final String redirectedUrl = "/manager/category/teacher/teacher-list";
        final String standingUrl = request.getHeader("Referer");

        try {
            ModelAndView modelAndView = subPageService.getUpdateTeacherPage(request);
            if (standingUrl.contains("page=")) {
                //--Get the page-number of previous list-page (serving turn-back list-page action).
                modelAndView.addObject("pageNumber", standingUrl.split("page=")[1]);
            } else if (standingUrl.contains("category")) {
                //--Previous list-page doesn't have page-number --> default-page = 1.
                modelAndView.addObject("pageNumber", 1);
            }

            return modelAndView;
        } catch (NullPointerException ignored) {
            response.sendRedirect(redirectedUrl);
        } catch (NoSuchElementException ignored) {
            redirectAttributes.addFlashAttribute("errorCode", "error_entity_01");
            response.sendRedirect(redirectedUrl);
        } catch (Exception ignored) {
            redirectAttributes.addFlashAttribute("errorCode", "error_systemApplication_01");
            response.sendRedirect(redirectedUrl);
        }
        return null;
    }

    @RequestMapping(value = "/teacher/update-teacher-account", method = GET)
    public ModelAndView getUpdateTeacherAccountPage(
        HttpServletRequest request,
        HttpServletResponse response,
        RedirectAttributes redirectAttributes
    ) throws IOException {
        final String redirectedUrl = "/manager/category/teacher/teacher-account-list";
        final String standingUrl = request.getHeader("Referer");

        try {
            ModelAndView modelAndView = subPageService.getUpdateTeacherAccountPage(request);
            if (standingUrl.contains("page=")) {
                //--Get the page-number of previous list-page (serving turn-back list-page action).
                modelAndView.addObject("pageNumber", standingUrl.split("page=")[1]);
            } else if (standingUrl.contains("category")) {
                //--Previous list-page doesn't have page-number --> default-page = 1.
                modelAndView.addObject("pageNumber", 1);
            }

            return modelAndView;
        } catch (NullPointerException ignored) {
            response.sendRedirect(redirectedUrl);
        } catch (NoSuchElementException ignored) {
            redirectAttributes.addFlashAttribute("errorCode", "error_entity_01");
            response.sendRedirect(redirectedUrl);
        } catch (Exception ignored) {
            redirectAttributes.addFlashAttribute("errorCode", "error_systemApplication_01");
            response.sendRedirect(redirectedUrl);
        }
        return null;
    }

    @RequestMapping(value = "/practice-schedule/teacher-request-detail", method = GET)
    public ModelAndView getTeacherRequestDetailPage(
        HttpServletRequest request,
        HttpServletResponse response,
        RedirectAttributes redirectAttributes
    ) throws IOException {
        final String redirectedUrl = "/manager/category/teacher/teacher-request-list";

        try {
            return subPageService.getTeacherRequestDetailPage(request);
        } catch (NoSuchElementException ignored) {
            redirectAttributes.addFlashAttribute("errorCode", "error_entity_01");
            response.sendRedirect(redirectedUrl);
        } catch (Exception ignored) {
            redirectAttributes.addFlashAttribute("errorCode", "error_systemApplication_01");
            response.sendRedirect(redirectedUrl);
        }
        return null;
    }

    @RequestMapping(value = "/practice-schedule/add-practice-schedule", method = GET)
    public ModelAndView getAddPracticeSchedulePage(
        HttpServletRequest request,
        HttpServletResponse response,
        RedirectAttributes redirectAttributes,
        Model model
    ) throws IOException {
        final String redirectedUrl = "/manager/category/practice-schedule/teacher-request-list";

        try {
            return subPageService.getAddPracticeSchedulePage(request, model);
        } catch (NullPointerException ignored) {
            response.sendRedirect(redirectedUrl);
        } catch (NoSuchElementException ignored) {
            redirectAttributes.addFlashAttribute("errorCode", "error_entity_01");
            response.sendRedirect(redirectedUrl);
        } catch (Exception ignored) {
            redirectAttributes.addFlashAttribute("errorCode", "error_systemApplication_01");
            response.sendRedirect(redirectedUrl);
        }
        return null;
    }
}
