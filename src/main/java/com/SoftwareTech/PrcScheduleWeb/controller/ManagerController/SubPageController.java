package com.SoftwareTech.PrcScheduleWeb.controller.ManagerController;

import com.SoftwareTech.PrcScheduleWeb.service.ManagerService.SubPageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import org.springframework.web.servlet.ModelAndView;

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
        HttpServletResponse response
    ) throws IOException {
        final String standingUrl = request.getHeader("Referer");

        try {
            return subPageService.getUpdateComputerRoomPage(request);
        } catch (NullPointerException ignored) {
            response.sendRedirect("/manager/category/computer-room/computer-room-list");
        } catch (NoSuchElementException ignored) {
            response.sendRedirect(standingUrl + "?errorMessage=eMv1at05");
        } catch (Exception ignored) {
            response.sendRedirect(standingUrl + "?errorMessage=eMv1at00");
        }
        return null;
    }

    @RequestMapping(value = "/teacher/update-teacher", method = GET)
    public ModelAndView getUpdateTeacherPage(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws IOException {
        final String standingUrl = request.getHeader("Referer");

        try {
            return subPageService.getUpdateTeacherPage(request);
        } catch (NullPointerException ignored) {
            response.sendRedirect("/manager/category/teacher/teacher-list");
        } catch (NoSuchElementException ignored) {
            response.sendRedirect(standingUrl + "?errorMessage=eMv1at07");
        } catch (Exception ignored) {
            response.sendRedirect(standingUrl + "?errorMessage=eMv1at00");
        }
        return null;
    }

    @RequestMapping(value = "/teacher/update-teacher-account", method = GET)
    public ModelAndView getUpdateTeacherAccountPage(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws IOException {
        final String redirectedUrl = "/manager/category/teacher/teacher-account-list";
        try {
            return subPageService.getUpdateTeacherAccountPage(request);
        } catch (NullPointerException ignored) {
            response.sendRedirect("/manager/category/teacher/teacher-account-list");
        } catch (NoSuchElementException ignored) {
            response.sendRedirect(redirectedUrl + "?errorMessage=eMv1at08");
        } catch (Exception ignored) {
            response.sendRedirect(redirectedUrl + "?errorMessage=eMv1at00");
        }
        return null;
    }
}
