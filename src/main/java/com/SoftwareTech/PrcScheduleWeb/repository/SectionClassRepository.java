package com.SoftwareTech.PrcScheduleWeb.repository;

import com.SoftwareTech.PrcScheduleWeb.model.SectionClass;
import com.SoftwareTech.PrcScheduleWeb.model.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SectionClassRepository extends JpaRepository<SectionClass, Long> {
    @Query("select s from SectionClass s where s.semester.semesterId =:semesterId AND s.grade.gradeId =:gradeId AND s.subject.subjectId=:subjectId")
    Optional <SectionClass> findByGradeAndSemesterAndSubject(
        @Param("semesterId") long semesterId,
        @Param("gradeId") String gradeId,
        @Param("subjectId") String subjectId
    );
}
