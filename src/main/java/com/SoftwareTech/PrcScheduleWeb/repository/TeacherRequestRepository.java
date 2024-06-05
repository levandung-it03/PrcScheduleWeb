package com.SoftwareTech.PrcScheduleWeb.repository;

import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.ResDtoTeacherRequest;
import com.SoftwareTech.PrcScheduleWeb.model.Semester;
import com.SoftwareTech.PrcScheduleWeb.model.TeacherRequest;
import com.SoftwareTech.PrcScheduleWeb.model.enums.EntityInteractionStatus;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRequestRepository extends JpaRepository<TeacherRequest, Long> {
    @Query("""
        SELECT new com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.ResDtoTeacherRequest(
            s.teacherRequest.requestId, s.teacherRequest.interactionStatus,
            s.teacherRequest.requestMessageDetail, s.teacherRequest.interactRequestReason,
            s.subjectScheduleId, s.sectionClass, s.teacher
        ) FROM SubjectSchedule s WHERE s.teacherRequest IS NOT NULL
        GROUP BY s.teacherRequest.requestId
        ORDER BY s.sectionClass.semester.startingDate DESC, s.teacherRequest.interactionStatus ASC,
            s.teacherRequest.updatingTime DESC
    """)
    List<ResDtoTeacherRequest> findAllTeacherRequestInSubjectScheduleWithSpecifiedPage(PageRequest pageRequest);

    @Query("""
        SELECT new com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.ResDtoTeacherRequest(
            s.teacherRequest.requestId, s.teacherRequest.interactionStatus,
            s.teacherRequest.requestMessageDetail, s.teacherRequest.interactRequestReason,
            s.subjectScheduleId, s.sectionClass, s.teacher
        ) FROM SubjectSchedule s WHERE s.teacherRequest IS NOT NULL AND s.teacher.teacherId = :teacherId
        GROUP BY s.teacherRequest.requestId
        ORDER BY s.sectionClass.semester.startingDate DESC, s.teacherRequest.interactionStatus ASC,
            s.teacherRequest.updatingTime DESC
    """)
    List<ResDtoTeacherRequest> findAllTeacherRequestInSubjectScheduleByTeacherIdWithSpecifiedPage(
        @Param("teacherId") String teacherId,
        PageRequest pageRequest
    );


    @Query("""
        SELECT new com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.ResDtoTeacherRequest(
            s.teacherRequest.requestId, s.teacherRequest.interactionStatus,
            s.teacherRequest.requestMessageDetail, s.teacherRequest.interactRequestReason,
            s.subjectScheduleId, s.sectionClass, s.teacher
        ) FROM SubjectSchedule s
        WHERE s.teacherRequest IS NOT NULL AND s.teacherRequest.interactionStatus = :interactionStatus
            AND s.sectionClass.semester = :currentSemester
        GROUP BY s.teacherRequest.requestId
        ORDER BY s.teacherRequest.updatingTime DESC
    """)
    List<ResDtoTeacherRequest> findAllTeacherRequestInSubjectScheduleByInteractionStatusInCurrentSemester(
        @Param("interactionStatus") EntityInteractionStatus interactionStatus,
        @Param("currentSemester") Semester currentSemester
    );

    @Modifying
    @Query("""
        UPDATE TeacherRequest t
        SET t.interactRequestReason = :#{#teacherRequest.interactRequestReason},
            t.interactionStatus = :#{#teacherRequest.interactionStatus},
            t.updatingTime = :#{#teacherRequest.updatingTime}
        WHERE t.requestId = :#{#teacherRequest.requestId}
    """)
    void updateByRequestId(@Param("teacherRequest") TeacherRequest teacherRequest);

    int countAllByInteractionStatus(EntityInteractionStatus interactionStatus);

    @Modifying
    @Query("""
        UPDATE TeacherRequest t
        SET t.requestMessageDetail = :#{#teacherRequest.requestMessageDetail},
            t.updatingTime = :#{#teacherRequest.updatingTime}
        WHERE t.requestId = :#{#teacherRequest.requestId}
    """)
    void update(@Param("teacherRequest") TeacherRequest savedTeacherRequest);

    @Modifying
    @Query("""
        UPDATE TeacherRequest t
        SET t.interactRequestReason = :#{#teacherRequest.interactRequestReason},
            t.interactionStatus = :#{#teacherRequest.interactionStatus},
            t.updatingTime = :#{#teacherRequest.updatingTime}
        WHERE t.requestId = :#{#teacherRequest.requestId}
    """)
    void cancelTeacherRequest(@Param("teacherRequest") TeacherRequest savedTeacherRequest);
}
