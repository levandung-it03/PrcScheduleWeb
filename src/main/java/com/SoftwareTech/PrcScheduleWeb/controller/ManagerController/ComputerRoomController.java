package com.SoftwareTech.PrcScheduleWeb.controller.ManagerController;

import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.DtoAsRequests.DtoAddComputerRoom;
import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.DtoAsRequests.DtoUpdateComputerRoom;
import com.SoftwareTech.PrcScheduleWeb.service.ManagerService.ComputerRoomService;
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
public class ComputerRoomController {
    @Autowired
    private final ComputerRoomService computerRoomService;

    @RequestMapping(value = "/add-computer-room", method = POST)
    public String addComputerRoom(
        @Valid @ModelAttribute("roomObject") DtoAddComputerRoom roomObject,
        HttpServletRequest request,
        RedirectAttributes redirectAttributes,
        BindingResult bindingResult
    ) {
        final String standingUrl = request.getHeader("Referer");
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorCode", bindingResult.getFieldErrors().getFirst());
            return "redirect:" + standingUrl;
        }

        try {
            computerRoomService.addComputerRoom(roomObject);
            redirectAttributes.addFlashAttribute("succeedCode", "succeed_add_01");
        } catch (DuplicateKeyException ignored) {
            redirectAttributes.addFlashAttribute("roomObject", roomObject);
            redirectAttributes.addFlashAttribute("errorCode", "error_computerRoom_02");
        } catch (Exception ignored) {
            redirectAttributes.addFlashAttribute("roomObject", roomObject);
            redirectAttributes.addFlashAttribute("errorCode", "error_systemApplication_01");
        }
        return "redirect:" + standingUrl;
    }

    @RequestMapping(value = "/update-computer-room", method = POST)
    public String updateComputerRoom(
        @Valid @ModelAttribute("roomObject") DtoUpdateComputerRoom roomObject,
        HttpServletRequest request,
        RedirectAttributes redirectAttributes,
        BindingResult bindingResult
    ) {
        String page = (request.getParameter("pageNumber") == null) ? "1" : request.getParameter("pageNumber");
        final String redirectedUrl = "/manager/category/computer-room/computer-room-list?page=" + page;
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorCode", bindingResult.getFieldErrors().getFirst());
            return "redirect:" + redirectedUrl;
        }

        try {
            computerRoomService.updateComputerRoom(roomObject, request);
            redirectAttributes.addFlashAttribute("succeedCode", "succeed_update_01");
        } catch (IllegalStateException ignored) {
            redirectAttributes.addFlashAttribute("errorCode", "error_entity_03");
        } catch (NoSuchElementException e) {
            redirectAttributes.addFlashAttribute("errorCode", "error_entity_01");
        } catch (Exception ignored) {
            redirectAttributes.addFlashAttribute("errorCode", "error_systemApplication_01");
        }
        return "redirect:" + redirectedUrl;
    }

    @RequestMapping(value = "/computer-room-list-active-btn", method = POST)
    public String deleteComputerRoom(
        @ModelAttribute("deleteBtn") String roomId,
        HttpServletRequest request,
        RedirectAttributes redirectAttributes
    ) {
        try {
            computerRoomService.deleteComputerRoom(roomId);
            redirectAttributes.addFlashAttribute("succeedCode", "succeed_delete_01");
        } catch (NoSuchElementException ignored) {
            redirectAttributes.addFlashAttribute("errorCode", "error_entity_01");
        } catch (Exception ignored) {
            redirectAttributes.addFlashAttribute("errorCode", "error_entity_02");
        }
        return "redirect:" + request.getHeader("Referer");
    }
}
