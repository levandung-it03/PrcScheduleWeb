package com.SoftwareTech.PrcScheduleWeb.service.ManagerService;

import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.ReqDtoAddComputerRoom;
import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.ReqDtoUpdateComputerRoom;
import com.SoftwareTech.PrcScheduleWeb.model.Classroom;
import com.SoftwareTech.PrcScheduleWeb.model.ComputerRoomDetail;
import com.SoftwareTech.PrcScheduleWeb.model.enums.RoomType;
import com.SoftwareTech.PrcScheduleWeb.repository.ClassroomRepository;
import com.SoftwareTech.PrcScheduleWeb.repository.ComputerRoomDetailRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class M_ComputerRoomService {
    @Autowired
    private final ClassroomRepository classroomRepository;
    @Autowired
    private final ComputerRoomDetailRepository computerRoomDetailRepository;

    @Transactional(rollbackOn = {Exception.class})
    public void addComputerRoom(ReqDtoAddComputerRoom roomObject) {
        final String area = roomObject.getArea().trim().toUpperCase();
        final String inpComputerRoom = String.format("2%s%s", area, roomObject.getRoomCode());

        //--Query result will be ignored because it belongs to validate.
        Optional<Classroom> classroom = classroomRepository.findById(inpComputerRoom);
        if (classroom.isPresent()) {
            if (classroom.get().getRoomType().equals(RoomType.NORM)) {
                throw new DuplicateKeyException("error_computerRoom_01");
            }
            else {
                throw new DuplicateKeyException("error_computerRoom_02");
            }
        }

        //--Preparing added data.
        Classroom practiceRoom = Classroom.builder()
            .roomId(inpComputerRoom)
            .roomType(RoomType.PRC)
            .maxQuantity(roomObject.getMaxQuantity())
            .status(true)
            .build();

        classroomRepository.save(practiceRoom);
        computerRoomDetailRepository.save(ComputerRoomDetail.builder()
            .classroom(practiceRoom)
            .maxComputerQuantity(roomObject.getMaxComputerQuantity())
            .availableComputerQuantity(roomObject.getMaxComputerQuantity())
            .build());
    }

    @Transactional(rollbackOn = {Exception.class})
    public void updateComputerRoom(ReqDtoUpdateComputerRoom roomInp, HttpServletRequest request) {
        if (roomInp.getAvailableComputerQuantity() > roomInp.getMaxComputerQuantity())
            throw new IllegalArgumentException("Available quantity must be least than maximum quantity.");

        final String roomCode = request.getParameter("roomId");
        Classroom practiceRoom = classroomRepository
            .findById(roomCode)
            .orElseThrow(() -> new NoSuchElementException("Computer Room not found"));
        ComputerRoomDetail computerRoomDetail = computerRoomDetailRepository
            .findByRoomId(roomCode)
            .orElseThrow(() -> new NoSuchElementException("Computer Room Detail not found in this Computer Room"));

        //--Update data inside Classroom(RoomType.PRC).
        practiceRoom.setMaxQuantity(roomInp.getMaxQuantity());
        practiceRoom.setStatus(roomInp.isStatus());

        //--Update data inside ComputerRoomDetail.
        computerRoomDetail.setClassroom(practiceRoom);
        computerRoomDetail.setMaxComputerQuantity(computerRoomDetail.getMaxComputerQuantity());
        computerRoomDetail.setAvailableComputerQuantity(computerRoomDetail.getAvailableComputerQuantity());

        //--Update data into Database.
        classroomRepository.save(practiceRoom);
        computerRoomDetailRepository.save(computerRoomDetail);
    }

    @Transactional(rollbackOn = {Exception.class})
    public void deleteComputerRoom(String roomId) {
        if (!classroomRepository.existsById(roomId))
            throw new NoSuchElementException("Classroom not found!");

        final ComputerRoomDetail computerRoomDetail = computerRoomDetailRepository
            .findByRoomId(roomId)
            .orElseThrow(() -> new NoSuchElementException("Computer Room Detail Id of Classroom not found!"));

        //--Delete both ComputerRoomDetail and Classroom.
        computerRoomDetailRepository.deleteById(computerRoomDetail.getComputerRoomDetailId());
        classroomRepository.deleteById(roomId);
    }
}
