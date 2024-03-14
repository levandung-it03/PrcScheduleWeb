package com.SoftwareTech.PrcScheduleWeb.service.ManagerService;

import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.DtoAddComputerRoom;
import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.DtoUpdateComputerRoom;
import com.SoftwareTech.PrcScheduleWeb.model.Classroom;
import com.SoftwareTech.PrcScheduleWeb.model.ComputerRoomDetail;
import com.SoftwareTech.PrcScheduleWeb.model.enums.RoomType;
import com.SoftwareTech.PrcScheduleWeb.repository.ClassroomRepository;
import com.SoftwareTech.PrcScheduleWeb.repository.ComputerRoomDetailRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ComputerRoomService {
    @Autowired
    private final ClassroomRepository classroomRepository;
    @Autowired
    private final ComputerRoomDetailRepository computerRoomDetailRepository;

    @Transactional(rollbackOn = {Exception.class})
    public String addComputerRoomAndGetStandingUrlWithMessage(DtoAddComputerRoom roomObject, String standingUrl) {
        final String area = roomObject.getArea().trim().toUpperCase();

        if (!Pattern.compile("^[A-Z]$").matcher(area).matches()
            || roomObject.getRoomCode() == null
            || roomObject.getMaxComputerQuantity() == null
            || roomObject.getRoomCode() <= 0
            || roomObject.getRoomCode() >= 100
            || roomObject.getMaxComputerQuantity() <= 0
            || roomObject.getMaxComputerQuantity() >= 1000
        ) {
            throw new IllegalStateException("Invalid input data");
        }

        final String inpComputerRoom = String.format("2%s%s", area, roomObject.getRoomCode());

        //--Query result will be ignored because it belongs to validate.
        if (classroomRepository.findById(inpComputerRoom).isPresent())
            throw new DuplicateKeyException("Computer Room is already existed");

        //--Preparing added data.
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
        return "redirect:" + standingUrl + "?succeedMessage=sMv1at01";
    }

    public String updateComputerRoomAndGetRedirect(DtoUpdateComputerRoom roomInp, String roomCode, String redirectedUrl) {
        Classroom practiceRoom = classroomRepository
            .findById(roomCode)
            .orElseThrow(() -> new NoSuchElementException("Computer Room not found"));
        ComputerRoomDetail computerRoomDetail = computerRoomDetailRepository
            .findByRoomId(roomCode)
            .orElseThrow(() -> new NoSuchElementException("Computer Room Detail not found in this Computer Room"));

        //--Update data inside Classroom(RoomType.PRC).
        practiceRoom.setStatus(roomInp.isStatus());

        //--Update data inside ComputerRoomDetail.
        computerRoomDetail.setClassroom(practiceRoom);
        computerRoomDetail.setMaxComputerQuantity(computerRoomDetail.getMaxComputerQuantity());
        computerRoomDetail.setAvailableComputerQuantity(computerRoomDetail.getAvailableComputerQuantity());

        //--Update data into Database.
        classroomRepository.save(practiceRoom);
        computerRoomDetailRepository.save(computerRoomDetail);

        return "redirect:" + redirectedUrl + "?succeedMessage=sMv1at03";
    }

    @Transactional(rollbackOn = {Exception.class})
    public String deleteComputerRoomAndGetRedirect(String roomId, String standingUrl) {
        final Classroom foundComputerRoom = classroomRepository.findById(roomId).orElseThrow();
        final ComputerRoomDetail computerRoomDetail = computerRoomDetailRepository.findByRoomId(roomId).orElseThrow();

        //--Delete both ComputerRoomDetail and Classroom.
        computerRoomDetailRepository.deleteById(computerRoomDetail.getComputerRoomDetailId());
        classroomRepository.deleteById(foundComputerRoom.getRoomId());

        return "redirect:" + standingUrl + "?succeedMessage=sMv1at02";
    }
}
