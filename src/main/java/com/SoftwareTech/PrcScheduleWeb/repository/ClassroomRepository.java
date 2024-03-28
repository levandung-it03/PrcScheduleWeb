package com.SoftwareTech.PrcScheduleWeb.repository;

import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.DtoAsResponses.DtoComputerRoom;
import com.SoftwareTech.PrcScheduleWeb.model.Classroom;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, String> {

    @Query("""
            SELECT new com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.DtoComputerRoom(
                cl.roomId, cl.maxQuantity, co.maxComputerQuantity, co.availableComputerQuantity, cl.status
            ) FROM ComputerRoomDetail co
            INNER JOIN co.classroom cl
        """)
    List<DtoComputerRoom> findAllInSpecifiedPage(PageRequest pageRequest);

    @Query("""
        SELECT cl.roomId FROM Classroom cl
        WHERE cl.status = True AND cl.roomType = com.SoftwareTech.PrcScheduleWeb.model.enums.RoomType.PRC
            AND cl.maxQuantity >= :studentQuantity
        """)
    List<String> findAllComputerRoomIdWithQuantity(@Param("studentQuantity") int studentQuantity);
}
