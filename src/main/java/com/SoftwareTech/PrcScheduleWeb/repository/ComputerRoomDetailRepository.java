package com.SoftwareTech.PrcScheduleWeb.repository;

import com.SoftwareTech.PrcScheduleWeb.model.ComputerRoomDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComputerRoomDetailRepository extends JpaRepository<ComputerRoomDetail, Long> {

    @Query("SELECT co FROM ComputerRoomDetail co WHERE co.classroom.roomId = :roomId")
    Optional<ComputerRoomDetail> findByRoomId(@Param("roomId") String roomId);
}
