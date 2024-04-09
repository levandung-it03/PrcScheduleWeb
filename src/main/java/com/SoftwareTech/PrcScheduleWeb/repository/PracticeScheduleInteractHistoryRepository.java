package com.SoftwareTech.PrcScheduleWeb.repository;

import com.SoftwareTech.PrcScheduleWeb.model.PracticeScheduleInteractionHistory;
import com.SoftwareTech.PrcScheduleWeb.model.SubjectSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PracticeScheduleInteractHistoryRepository extends JpaRepository<PracticeScheduleInteractionHistory, Long> {
    @Query("""
        SELECT p.subjectSchedule FROM PracticeScheduleInteractionHistory p
        WHERE p.teacherRequest.requestId = :requestId
    """)
    List<SubjectSchedule> findAllSubjectScheduleByTeacherRequestId(@Param("requestId") String requestId);
}
