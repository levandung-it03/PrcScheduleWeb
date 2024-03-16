package com.SoftwareTech.PrcScheduleWeb.repository;

import com.SoftwareTech.PrcScheduleWeb.model.TeacherRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRequestRepository extends JpaRepository<TeacherRequest, Long> {

    @Query("SELECT r FROM TeacherRequest r")
    List<TeacherRequest> findAllInSpecifiedPage(PageRequest pageRequest);
}
