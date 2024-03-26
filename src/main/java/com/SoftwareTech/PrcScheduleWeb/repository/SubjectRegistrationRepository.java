package com.SoftwareTech.PrcScheduleWeb.repository;

import com.SoftwareTech.PrcScheduleWeb.model.SubjectRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRegistrationRepository extends JpaRepository<SubjectRegistration, Long> {
    @Query("""
        SELECT COUNT(s.subjectRegistrationId) FROM SubjectRegistration s
        WHERE s.sectionClass.sectionClassId = :sectionClassId
        GROUP BY s.sectionClass.sectionClassId
        """)
    Integer countBySectionClassId(@Param("sectionClassId") Long sectionClassId);
}
