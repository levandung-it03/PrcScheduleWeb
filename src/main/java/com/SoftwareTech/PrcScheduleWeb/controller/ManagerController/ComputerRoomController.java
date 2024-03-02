package com.SoftwareTech.PrcScheduleWeb.controller.ManagerController;

import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.DtoComputerRoom;
import com.SoftwareTech.PrcScheduleWeb.service.ManagerService.ComputerRoomService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "${url.post.manager.prefix.v1}")
public class ComputerRoomController {
    @Autowired
    private ComputerRoomService computerRoomService;

    @RequestMapping(value = "/add-computer-room")
    public String addComputerRoom(
        @ModelAttribute("computerRoomObject") DtoComputerRoom computerRoomObject,
        RedirectAttributes redirectAttributes,
        HttpServletRequest request
    ) {
        final String standingUrl = request.getHeader("Referer");
        final HashMap<String, String> addComputerRoomResult = computerRoomService.addComputerRoom(computerRoomObject);

        if (addComputerRoomResult.get("status").equals("error")) {
            redirectAttributes.addFlashAttribute("computerRoomObject", computerRoomObject);
            return "redirect:" + standingUrl + "?errorMessage=" + addComputerRoomResult.get("code");
        }
        else {
            return "redirect:" + standingUrl + "?succeedMessage=" + addComputerRoomResult.get("code");
        }
    }
}
