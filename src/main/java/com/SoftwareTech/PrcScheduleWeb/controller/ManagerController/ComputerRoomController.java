package com.SoftwareTech.PrcScheduleWeb.controller.ManagerController;

import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.DtoAddComputerRoom;
import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.DtoUpdateComputerRoom;
import com.SoftwareTech.PrcScheduleWeb.service.ManagerService.ComputerRoomService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "${url.post.manager.prefix.v1}")
public class ComputerRoomController {
    @Autowired
    private ComputerRoomService computerRoomService;

    @RequestMapping(value = "/add-computer-room", method = POST)
    public String addComputerRoom(
        @ModelAttribute("computerRoomObject") DtoAddComputerRoom computerRoomObject,
        RedirectAttributes redirectAttributes,
        HttpServletRequest request
    ) {
        return computerRoomService.addComputerRoom(computerRoomObject, redirectAttributes, request);
    }

    @RequestMapping(value = "/update-computer-room", method = POST)
    public String updateComputerRoom(
        @ModelAttribute("computerRoomObject") DtoUpdateComputerRoom computerRoomObject,
        HttpServletRequest request
    ) {
        return computerRoomService.updateComputerRoom(computerRoomObject, request);
    }

    @RequestMapping(value = "/computer-room-list-active-btn", method = POST)
    public String deleteComputerRoom(
        @ModelAttribute("deleteBtn") String computerRoom,
        HttpServletRequest request,
        HttpServletResponse response) {
        return computerRoomService.deleteComputerRoom(computerRoom, request, response);
    }
}
