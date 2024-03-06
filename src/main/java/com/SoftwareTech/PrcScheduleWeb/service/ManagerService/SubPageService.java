package com.SoftwareTech.PrcScheduleWeb.service.ManagerService;

import com.SoftwareTech.PrcScheduleWeb.config.StaticUtilMethods;
import com.SoftwareTech.PrcScheduleWeb.model.ComputerRoom;
import com.SoftwareTech.PrcScheduleWeb.repository.ComputerRoomRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubPageService {
    @Autowired
    private final ComputerRoomRepository computerRoomRepository;
    @Autowired
    private final StaticUtilMethods staticUtilMethods;

    public ModelAndView getUpdateComputerRoomPage(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws IOException {
        final String computerRoom = request.getParameter("computerRoom");
        ModelAndView modelAndView = staticUtilMethods
            .customizeResponsiveModelAndView(request, "update-computer-room");
        Optional<ComputerRoom> computerRoomObject = computerRoomRepository.findByComputerRoom(computerRoom);

        if (computerRoomObject.isEmpty()) {
            response.sendRedirect("/manager/category/computer-room/computer-room-list");
            return null;
        }
        else {
            modelAndView.addObject("computerRoomObject", computerRoomObject.get());
            return modelAndView;
        }
    }
}
