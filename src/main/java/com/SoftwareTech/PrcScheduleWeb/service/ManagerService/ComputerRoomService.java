package com.SoftwareTech.PrcScheduleWeb.service.ManagerService;

import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.DtoAddComputerRoom;
import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.DtoUpdateComputerRoom;
import com.SoftwareTech.PrcScheduleWeb.model.Classroom;
import com.SoftwareTech.PrcScheduleWeb.model.ComputerRoomDetail;
import com.SoftwareTech.PrcScheduleWeb.model.enums.RoomType;
import com.SoftwareTech.PrcScheduleWeb.repository.ClassroomRepository;
import com.SoftwareTech.PrcScheduleWeb.repository.ComputerRoomDetailRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ComputerRoomService {
    @Autowired
    private final ClassroomRepository classroomRepository;
    @Autowired
    private final ComputerRoomDetailRepository computerRoomDetailRepository;

    public String addComputerRoom(
        DtoAddComputerRoom roomObject,
        RedirectAttributes redirectAttributes,
        HttpServletRequest request
    ) {
        final String standingUrl = request.getHeader("Referer");
        final String inpComputerRoom = String.format("2%s%s",
            roomObject.getArea().trim().toUpperCase(),
            roomObject.getRoomCode().toString()
        );
        final Optional<Classroom> isExistingRoom = classroomRepository.findById(inpComputerRoom);

        if (isExistingRoom.isPresent()) {
            redirectAttributes.addFlashAttribute("roomObject", roomObject);
            return "redirect:" + standingUrl + "?errorMessage=eMv1at04";
        } else {
            try {
                Classroom practiceRoom = Classroom.builder()
                    .roomId(inpComputerRoom)
                    .roomType(RoomType.PRC)
                    .status(true)
                    .build();

                classroomRepository.save(practiceRoom);
                computerRoomDetailRepository.save(ComputerRoomDetail.builder()
                    .classroom(practiceRoom)
                    .maxComputerQuantity(roomObject.getMaxComputerQuantity())
                    .availableComputerQuantity(roomObject.getMaxComputerQuantity())
                    .build());
            } catch (Exception e) {
                e.fillInStackTrace();
            }
            return "redirect:" + standingUrl + "?succeedMessage=sMv1at01";
        }
    }

    public String updateComputerRoom(DtoUpdateComputerRoom roomObject, HttpServletRequest request) {
        final String standingUrl = request.getHeader("Referer");
        final String updatedComputerRoom = request.getParameter("roomId");

        try {
            Classroom practiceRoom = classroomRepository.findById(updatedComputerRoom).orElseThrow();
            ComputerRoomDetail computerRoomDetail = computerRoomDetailRepository.findByRoomId(updatedComputerRoom)
                .orElseThrow();

            //--Update data inside Classroom(RoomType.PRC).
            practiceRoom.setStatus(roomObject.isStatus());

            //--Update data inside ComputerRoomDetail.
            computerRoomDetail.setClassroom(practiceRoom);
            computerRoomDetail.setMaxComputerQuantity(computerRoomDetail.getMaxComputerQuantity());
            computerRoomDetail.setAvailableComputerQuantity(computerRoomDetail.getAvailableComputerQuantity());

            //--Update data into Database.
            classroomRepository.save(practiceRoom);
            computerRoomDetailRepository.save(computerRoomDetail);

            return "redirect:/manager/category/computer-room/computer-room-list?succeedMessage=sMv1at03";
        } catch (Exception ignored) {
            return "redirect:" + standingUrl + "?roomId=" + updatedComputerRoom + "&errorMessage=eMv1at00";
        }
    }

    public String deleteComputerRoom(String roomId, HttpServletRequest request, HttpServletResponse response) {
        final String standingUrl = request.getHeader("Referer");
        final Optional<Classroom> foundComputerRoom = classroomRepository.findById(roomId);

        if (foundComputerRoom.isEmpty())
            return "redirect:" + standingUrl + "?errorMessage=eMv1at05";

        try {
            //--Get the linked ComputerRoomDetail.
            ComputerRoomDetail computerRoomDetail = computerRoomDetailRepository.findByRoomId(roomId).orElseThrow();


            //--Delete both ComputerRoomDetail and Classroom.
            computerRoomDetailRepository.deleteById(computerRoomDetail.getComputerRoomDetailId());
            classroomRepository.deleteById(foundComputerRoom.get().getRoomId());

            return "redirect:" + standingUrl + "?succeedMessage=sMv1at02";
        } catch (Exception ignored) {
            return "redirect:" + standingUrl + "?errorMessage=eMv1at06";
        }
    }
}
