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
import org.springframework.ui.Model;

import java.io.IOException;

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
        return subPageService.getUpdateComputerRoomPage(request, response);
    }


}
