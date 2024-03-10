package com.SoftwareTech.PrcScheduleWeb.repository;

import com.SoftwareTech.PrcScheduleWeb.model.ComputerRoom;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComputerRoomRepository extends JpaRepository<ComputerRoom, String> {
    Optional<ComputerRoom> findByComputerRoom(String computerRoom);

    @Query("SELECT r FROM ComputerRoom r")
    List<ComputerRoom> findAllInSpecifiedPage(PageRequest pageRequest);
}
