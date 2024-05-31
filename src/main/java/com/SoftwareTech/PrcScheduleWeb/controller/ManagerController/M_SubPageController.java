package com.SoftwareTech.PrcScheduleWeb.controller.ManagerController;

import com.SoftwareTech.PrcScheduleWeb.service.ManagerService.M_SubPageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/manager/sub-page")
public class M_SubPageController {
    @Autowired
    private final M_SubPageService subPageService;

    @RequestMapping(value = "/manager/set-manager-info")
    public ModelAndView getSetManagerInfoPage(HttpServletRequest request, Model model) {
        try {
            return subPageService.getSetManagerInfoPage(request, model);
        } catch (DuplicateKeyException ignored) {
            //--Manager info is already existing so redirecting to HomePage.
            return new ModelAndView("redirect:/home");
        } catch (Exception ignored) {
            request.getSession().setAttribute("errorCode", "error_systemApplication_01");
        }
        return new ModelAndView("redirect:" + request.getHeader("Referer"));
    }

    @RequestMapping(value = "/manager/update-manager-info")
    public ModelAndView getUpdateManagerInfoPage(HttpServletRequest request, Model model) {
        try {
            return subPageService.getUpdateManagerInfoPage(request, model);
        } catch (NoSuchElementException ignored) {
            request.getSession().setAttribute("errorCode", "error_entity_01");
        } catch (Exception ignored) {
            request.getSession().setAttribute("errorCode", "error_systemApplication_01");
        }
        return new ModelAndView("redirect:/home");
    }

    @RequestMapping(value = "/manager/show-info")
    public ModelAndView getPersonInfoPage(HttpServletRequest request, Model model) {
        return subPageService.getPersonInfoPage(request, model);
    }

    @RequestMapping(value = "/manager/update-account/change-password")
    public ModelAndView getChangePasswordPage(HttpServletRequest request, Model model) {
        return subPageService.getChangePasswordPage(request, model);
    }

    @RequestMapping(value = "/computer-room/update-computer-room", method = GET)
    public ModelAndView getUpdateComputerRoomPage(HttpServletRequest request, Model model) {
        final String standingUrl = request.getHeader("Referer");

        try {
            ModelAndView modelAndView = subPageService.getUpdateComputerRoomPage(request, model);
            if (standingUrl.contains("page=")) {
                //--Get the page-number of previous list-page (serving turn-back list-page action).
                modelAndView.addObject("pageNumber", standingUrl.split("page=")[1]);
            } else if (standingUrl.contains("category")) {
                //--Previous list-page doesn't have page-number --> default-page = 1.
                modelAndView.addObject("pageNumber", 1);
            }

            return modelAndView;
        } catch (NullPointerException ignored) {
            //--This exception may throw when user reloads our page, so do nothing.
        } catch (NoSuchElementException ignored) {
            request.getSession().setAttribute("errorCode", "error_entity_01");
        } catch (Exception ignored) {
            request.getSession().setAttribute("errorCode", "error_systemApplication_01");
        }
        return new ModelAndView("redirect:/manager/category/computer-room/computer-room-list");
    }

    @RequestMapping(value = "/teacher/update-teacher", method = GET)
    public ModelAndView getUpdateTeacherPage(HttpServletRequest request, Model model) {
        final String standingUrl = request.getHeader("Referer");

        try {
            ModelAndView modelAndView = subPageService.getUpdateTeacherPage(request, model);
            if (standingUrl.contains("page=")) {
                //--Get the page-number of previous list-page (serving turn-back list-page action).
                modelAndView.addObject("pageNumber", standingUrl.split("page=")[1]);
            } else if (standingUrl.contains("category")) {
                //--Previous list-page doesn't have page-number --> default-page = 1.
                modelAndView.addObject("pageNumber", 1);
            }

            return modelAndView;
        } catch (NullPointerException ignored) {
            //--This exception may throw when user reloads our page, so do nothing.
        } catch (NoSuchElementException ignored) {
            request.getSession().setAttribute("errorCode", "error_entity_01");
        } catch (Exception ignored) {
            request.getSession().setAttribute("errorCode", "error_systemApplication_01");
        }
        return new ModelAndView("redirect:/manager/category/teacher/teacher-list");
    }

    @RequestMapping(value = "/teacher/update-teacher-account", method = GET)
    public ModelAndView getUpdateTeacherAccountPage(HttpServletRequest request, Model model) {
        final String standingUrl = request.getHeader("Referer");

        try {
            ModelAndView modelAndView = subPageService.getUpdateTeacherAccountPage(request, model);
            if (standingUrl.contains("page=")) {
                //--Get the page-number of previous list-page (serving turn-back list-page action).
                modelAndView.addObject("pageNumber", standingUrl.split("page=")[1]);
            } else if (standingUrl.contains("category")) {
                //--Previous list-page doesn't have page-number --> default-page = 1.
                modelAndView.addObject("pageNumber", 1);
            }

            return modelAndView;
        } catch (NullPointerException ignored) {
            //--This exception may throw when user reloads our page, so do nothing.
        } catch (NoSuchElementException ignored) {
            request.getSession().setAttribute("errorCode", "error_entity_01");
        } catch (Exception ignored) {
            request.getSession().setAttribute("errorCode", "error_systemApplication_01");
        }
        return new ModelAndView("redirect:/manager/category/teacher/teacher-account-list");
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
        return new ModelAndView("redirect:/manager/category/practice-schedule/teacher-request-list");
    }

    @RequestMapping(value = "/practice-schedule/add-practice-schedule", method = GET)
    public ModelAndView getAddPracticeSchedulePage(HttpServletRequest request, Model model) {
        try {
            return subPageService.getAddPracticeSchedulePage(request, model);
        } catch (NumberFormatException | NullPointerException ignored) {
            //--This exception may throw when user reloads our page, so do nothing.
        } catch (NoSuchElementException ignored) {
            request.getSession().setAttribute("errorCode", "error_entity_01");
        } catch (SQLIntegrityConstraintViolationException e) {
            request.getSession().setAttribute("errorCode", e.getMessage());
        } catch (Exception ignored) {
            request.getSession().setAttribute("errorCode", "error_systemApplication_01");
        }
        return new ModelAndView("redirect:/manager/category/practice-schedule/teacher-request-list");
    }

    @RequestMapping(value = "/practice-schedule/update-practice-schedule", method = GET)
    public ModelAndView getUpdatePracticeSchedulePage(HttpServletRequest request, Model model) {
        try {
            return subPageService.getUpdatePracticeSchedulePage(request, model);
        } catch (NumberFormatException | NullPointerException ignored) {
            //--This exception may throw when user reloads our page, so do nothing.
        } catch (NoSuchElementException ignored) {
            request.getSession().setAttribute("errorCode", "error_entity_01");
        } catch (SQLIntegrityConstraintViolationException ignored) {
            request.getSession().setAttribute("errorCode", "error_schedule_03");
        } catch (Exception ignored) {
            request.getSession().setAttribute("errorCode", "error_systemApplication_01");
        }
        return new ModelAndView("redirect:/manager/category/practice-schedule/teacher-request-list");
    }
}
