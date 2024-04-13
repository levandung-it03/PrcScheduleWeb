package com.SoftwareTech.PrcScheduleWeb.repository;

import com.SoftwareTech.PrcScheduleWeb.model.SectionClass;
import com.SoftwareTech.PrcScheduleWeb.model.Student;
import com.SoftwareTech.PrcScheduleWeb.model.SubjectRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRegistrationRepository extends JpaRepository<SubjectRegistration, Long> {
    @Query("""
        SELECT COUNT(s.subjectRegistrationId) FROM SubjectRegistration s
        WHERE s.sectionClass.sectionClassId = :sectionClassId
        GROUP BY s.sectionClass.sectionClassId
        """)
    Integer countBySectionClassId(@Param("sectionClassId") Long sectionClassId);

    @Query("SELECT s.student FROM SubjectRegistration s WHERE s.sectionClass.sectionClassId = :sectionClassId")
    List<Student> findAllStudentBySectionClassSectionClassId(@Param("sectionClassId") Long sectionClassId);
}
