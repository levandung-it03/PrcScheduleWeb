package com.SoftwareTech.PrcScheduleWeb.repository;

import com.SoftwareTech.PrcScheduleWeb.model.ComputerRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComputerRoomRepository extends JpaRepository<ComputerRoom, String> {
    Optional<ComputerRoom> findByComputerRoom(String computerRoom);
}
