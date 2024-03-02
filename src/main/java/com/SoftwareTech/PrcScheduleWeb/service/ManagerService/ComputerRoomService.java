package com.SoftwareTech.PrcScheduleWeb.service.ManagerService;

import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.DtoComputerRoom;
import com.SoftwareTech.PrcScheduleWeb.model.ComputerRoom;
import com.SoftwareTech.PrcScheduleWeb.repository.ComputerRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ComputerRoomService {
    @Autowired
    private final ComputerRoomRepository computerRoomRepository;

    public HashMap<String, String> addComputerRoom(DtoComputerRoom computerRoom) {
        final HashMap<String, String> result = new HashMap<>();
        final String inpComputerRoom = String.format("2%s%s",
            computerRoom.getArea().trim().toUpperCase(),
            computerRoom.getRoomCode().toString()
        );
        final Optional<ComputerRoom> isExistingRoom = computerRoomRepository.findByComputerRoom(inpComputerRoom);

        if (isExistingRoom.isPresent()) {
            result.put("status", "error");
            result.put("code", "eMv1at04");
        }
        else {
            try {
                computerRoomRepository.save(ComputerRoom.builder()
                    .computerRoom(inpComputerRoom)
                    .maxComputerQuantity(computerRoom.getMaxComputerQuantity())
                    .availableComputerQuantity(computerRoom.getMaxComputerQuantity())
                    .isRented(false)
                    .status(true)
                    .build());
            } catch (Exception e) { e.fillInStackTrace(); }

            result.put("status", "succeed");
            result.put("code", "sMv1at02");
        }
        return result;
    }

}
