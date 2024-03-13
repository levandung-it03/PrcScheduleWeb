package com.SoftwareTech.PrcScheduleWeb.repository;
import com.SoftwareTech.PrcScheduleWeb.model.Teacher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, String> {
    @Query("SELECT t FROM Teacher t")
    List<Teacher> findAllInSpecifiedPage(PageRequest pageRequest);
}
