package com.SoftwareTech.PrcScheduleWeb.controller.ManagerController;

import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.DtoAddComputerRoom;
import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.DtoUpdateComputerRoom;
import com.SoftwareTech.PrcScheduleWeb.service.ManagerService.ComputerRoomService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "${url.post.manager.prefix.v1}")
public class ComputerRoomController {
    @Autowired
    private final ComputerRoomService computerRoomService;

    @RequestMapping(value = "/add-computer-room", method = POST)
    public String addComputerRoom(
        @ModelAttribute("roomObject") DtoAddComputerRoom roomObject,
        RedirectAttributes redirectAttributes,
        HttpServletRequest request
    ) {
        final String standingUrl = request.getHeader("Referer");

        try {
            return computerRoomService.addComputerRoomAndGetStandingUrlWithMessage(roomObject, standingUrl);
        } catch (IllegalStateException ignored) {
            redirectAttributes.addFlashAttribute("roomObject", roomObject);
            return "redirect:" + standingUrl + "?errorMessage=eMv1at09";
        } catch (DuplicateKeyException ignored) {
            redirectAttributes.addFlashAttribute("roomObject", roomObject);
            return "redirect:" + standingUrl + "?errorMessage=eMv1at04";
        } catch (Exception ignored) {
            redirectAttributes.addFlashAttribute("roomObject", roomObject);
            return "redirect:" + standingUrl + "?errorMessage=eMv1at00";
        }
    }

    @RequestMapping(value = "/update-computer-room", method = POST)
    public String updateComputerRoom(
        @ModelAttribute("roomObject") DtoUpdateComputerRoom roomObject,
        HttpServletRequest request
    ) {
        final String redirectedUrl = "/manager/category/computer-room/computer-room-list";
        final String roomCode = request.getParameter("roomId");

        try {
            return computerRoomService.updateComputerRoomAndGetRedirect(roomObject, roomCode, redirectedUrl);
        } catch (NoSuchElementException e) {
            return "redirect:" + redirectedUrl + "?errorMessage=eMv1at05";
        } catch (Exception ignored) {
            return "redirect:" + redirectedUrl + "?errorMessage=eMv1at00";
        }
    }

    @RequestMapping(value = "/computer-room-list-active-btn", method = POST)
    public String deleteComputerRoom(
        @ModelAttribute("deleteBtn") String roomId,
        HttpServletRequest request
    ) {
        final String standingUrl = request.getHeader("Referer");

        try {
            return computerRoomService.deleteComputerRoomAndGetRedirect(roomId, standingUrl);
        } catch (NoSuchElementException ignored) {
            return "redirect:" + standingUrl + "?errorMessage=eMv1at05";
        } catch (Exception ignored) {
            return "redirect:" + standingUrl + "?errorMessage=eMv1at06";
        }
    }
}
