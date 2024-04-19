package com.SoftwareTech.PrcScheduleWeb.repository;

import com.SoftwareTech.PrcScheduleWeb.model.Grade;
import com.SoftwareTech.PrcScheduleWeb.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GradeRepository extends JpaRepository<Grade, String> {
    @Query("SELECT s FROM Grade s WHERE s.gradeId = :id")
    Optional<Student> findByGradeId(@Param("id") String id);
}
