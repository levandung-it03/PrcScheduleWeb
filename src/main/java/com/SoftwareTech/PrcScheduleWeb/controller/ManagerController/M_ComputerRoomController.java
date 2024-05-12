package com.SoftwareTech.PrcScheduleWeb.controller.ManagerController;

import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.ReqDtoAddComputerRoom;
import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.ReqDtoUpdateComputerRoom;
import com.SoftwareTech.PrcScheduleWeb.service.ManagerService.M_ComputerRoomService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import jakarta.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.NoSuchElementException;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "${url.post.manager.prefix.v1}")
public class M_ComputerRoomController {
    @Autowired
    private final M_ComputerRoomService computerRoomService;
    @Autowired
    private final Validator hibernateValidator;

    @RequestMapping(value = "/add-computer-room", method = POST)
    public String addComputerRoom(
        @ModelAttribute("roomObject") ReqDtoAddComputerRoom roomObject,
        HttpServletRequest request,
        RedirectAttributes redirectAttributes
    ) {
        final String standingUrl = request.getHeader("Referer");
        Set<ConstraintViolation<ReqDtoAddComputerRoom>> violations = hibernateValidator.validate(roomObject);
        if (!violations.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorCode", violations.iterator().next().getMessage());
            return "redirect:" + standingUrl;
        }

        try {
            computerRoomService.addComputerRoom(roomObject);
            redirectAttributes.addFlashAttribute("succeedCode", "succeed_add_01");
        } catch (DuplicateKeyException e) {
            redirectAttributes.addFlashAttribute("roomObject", roomObject);
            redirectAttributes.addFlashAttribute("errorCode", e.getMessage());
        } catch (Exception ignored) {
            redirectAttributes.addFlashAttribute("roomObject", roomObject);
            redirectAttributes.addFlashAttribute("errorCode", "error_systemApplication_01");
        }
        return "redirect:" + standingUrl;
    }

    @RequestMapping(value = "/update-computer-room", method = POST)
    public String updateComputerRoom(
        @ModelAttribute("roomObject") ReqDtoUpdateComputerRoom roomObject,
        HttpServletRequest request,
        RedirectAttributes redirectAttributes
    ) {
        String page = (request.getParameter("pageNumber") == null) ? "1" : request.getParameter("pageNumber");
        final String redirectedUrl = "/manager/category/computer-room/computer-room-list?page=" + page;
        Set<ConstraintViolation<ReqDtoUpdateComputerRoom>> violations = hibernateValidator.validate(roomObject);
        if (!violations.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorCode", violations.iterator().next().getMessage());
            return "redirect:" + redirectedUrl;
        }

        try {
            computerRoomService.updateComputerRoom(roomObject, request);
            redirectAttributes.addFlashAttribute("succeedCode", "succeed_update_01");
        } catch (IllegalArgumentException ignored) {
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
