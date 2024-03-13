package com.SoftwareTech.PrcScheduleWeb.repository;

import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.DtoComputerRoom;
import com.SoftwareTech.PrcScheduleWeb.model.Classroom;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, String> {

    @Query("""
            SELECT new com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.DtoComputerRoom(
                cl.roomId, co.maxComputerQuantity, co.availableComputerQuantity, cl.status
            ) FROM ComputerRoomDetail co
            INNER JOIN co.classroom cl
        """)
    List<DtoComputerRoom> findAllInSpecifiedPage(PageRequest pageRequest);


}
