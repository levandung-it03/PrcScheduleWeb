package com.SoftwareTech.PrcScheduleWeb.service.ManagerService;

import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.DtoAddComputerRoom;
import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.DtoUpdateComputerRoom;
import com.SoftwareTech.PrcScheduleWeb.model.ComputerRoom;
import com.SoftwareTech.PrcScheduleWeb.repository.ComputerRoomRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ComputerRoomService {
    @Autowired
    private final ComputerRoomRepository computerRoomRepository;

    public String addComputerRoom(
        DtoAddComputerRoom computerRoomObject,
        RedirectAttributes redirectAttributes,
        HttpServletRequest request
    ) {
        final String standingUrl = request.getHeader("Referer");
        final String inpComputerRoom = String.format("2%s%s",
            computerRoomObject.getArea().trim().toUpperCase(),
            computerRoomObject.getRoomCode().toString()
        );
        final Optional<ComputerRoom> isExistingRoom = computerRoomRepository.findByComputerRoom(inpComputerRoom);

        if (isExistingRoom.isPresent()) {
            redirectAttributes.addFlashAttribute("computerRoomObject", computerRoomObject);
            return "redirect:" + standingUrl + "?errorMessage=eMv1at04";
        }
        else {
            try {
                computerRoomRepository.save(ComputerRoom.builder()
                    .computerRoom(inpComputerRoom)
                    .maxComputerQuantity(computerRoomObject.getMaxComputerQuantity())
                    .availableComputerQuantity(computerRoomObject.getMaxComputerQuantity())
                    .status(true)
                    .build());
            } catch (Exception e) { e.fillInStackTrace(); }
            return "redirect:" + standingUrl + "?succeedMessage=sMv1at01";
        }
    }

    public String updateComputerRoom(DtoUpdateComputerRoom computerRoomObject, HttpServletRequest request) {
        final String standingUrl = request.getHeader("Referer");
        final String updatedComputerRoom = request.getParameter("computerRoom");

        try {
            //--Check if updated ComputerRoom is existing, the query result is ignored.
            computerRoomRepository.findByComputerRoom(updatedComputerRoom).orElseThrow();

            computerRoomRepository.save(ComputerRoom.builder()
                .computerRoom(updatedComputerRoom)
                .maxComputerQuantity(computerRoomObject.getMaxComputerQuantity())
                .availableComputerQuantity(computerRoomObject.getAvailableComputerQuantity())
                .status(computerRoomObject.isStatus())
                .build()
            );

            return "redirect:/manager/category/computer-room/computer-room-list?succeedMessage=sMv1at03";
        } catch (Exception ignored) {
            return "redirect:" + standingUrl + "?computerRoom=" + updatedComputerRoom + "&errorMessage=eMv1at00";
        }
    }

    public String deleteComputerRoom(String computerRoom, HttpServletRequest request, HttpServletResponse response) {
        final String standingUrl = request.getHeader("Referer");
        final Optional<ComputerRoom> foundComputerRoom = computerRoomRepository.findByComputerRoom(computerRoom);

        if (foundComputerRoom.isEmpty())
            return "redirect:" + standingUrl + "?errorMessage=eMv1at05";

        try {
            computerRoomRepository.deleteById(computerRoom);
            return "redirect:" + standingUrl + "?succeedMessage=sMv1at02";
        } catch (Exception ignored) {
            return "redirect:" + standingUrl + "?errorMessage=eMv1at06";
        }
    }
}
