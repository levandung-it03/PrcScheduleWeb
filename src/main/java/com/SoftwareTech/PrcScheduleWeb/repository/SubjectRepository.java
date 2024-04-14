package com.SoftwareTech.PrcScheduleWeb.repository;

import com.SoftwareTech.PrcScheduleWeb.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, String> {

    @Query("SELECT s FROM Subject s WHERE s.subjectId = :id OR s.subjectName = :subjectName")
    Optional<Subject> findByIdOrSubjectName(@Param("id") String id, @Param("subjectName") String subjectName);
}
