package com.SoftwareTech.PrcScheduleWeb.repository;

import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.ResDtoPracticeSchedule;
import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.ResDtoSubjectSchedule;
import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.ResDtoTeacherRequest;
import com.SoftwareTech.PrcScheduleWeb.model.SectionClass;
import com.SoftwareTech.PrcScheduleWeb.model.Semester;
import com.SoftwareTech.PrcScheduleWeb.model.SubjectSchedule;
import com.SoftwareTech.PrcScheduleWeb.model.Teacher;
import com.SoftwareTech.PrcScheduleWeb.model.enums.EntityInteractionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectScheduleRepository extends JpaRepository<SubjectSchedule, Long> {
    /**
     * 1. Get all SubjectSchedule which is being tough by TeacherRequest.teacherId in current semester:<br>
     *    - Param:  t = teacherRequest.teacherId,
     *              s = teacherRequest.sectionClassId --> sectionClass.semesterId
     *<br>
     *    - Query:  SubjectSchedule INNER JOIN SectionClass(sectionClassId, semesterId) AS SC
     *              WHERE (SC.semesterId == s) AND (teacherId == t)
     *<br>
     *<br>
     * 2. Get all SubjectSchedule of this Grade in current semester:<br>
     *    - Explain: in my research from a subject-register-table, every theory-schedules from the different groups of
     *    a section-class is similar to each other. They just have the different practice-schedules.
     *    - Param:  gra = teacherRequest.sectionClassId --> sectionClass.gradeId,
     *              s = teacherRequest.sectionClassId --> sectionClass.semesterId
     *<br>
     *    - Query:  SubjectSchedule INNER JOIN SectionClass(sectionClassId, gradeId, groupFromSubject, semesterId) AS SC
     *              WHERE (SC.semesterId == s) AND (SC.gradeId == gra)
     *<br>
     *<br>
     * 3. Comparison:<br>
     * - In several documents said that when we query by the Relationship_Annotations, we make the code more readable,<br>
     * maintainable and avoid auto-creating JOIN query.
     */
    @Query("""
        SELECT DISTINCT new com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.ResDtoSubjectSchedule(
            s.subjectScheduleId, s.sectionClass.subject.subjectName, s.day, s.startingWeek, s.totalWeek,
            s.startingPeriod, s.lastPeriod, s.classroom.roomId
        ) FROM SubjectSchedule s
        WHERE s.day IS NOT NULL AND s.startingWeek IS NOT NULL AND s.totalWeek IS NOT NULL AND s.startingPeriod IS NOT NULL
        AND s.lastPeriod IS NOT NULL AND s.classroom IS NOT NULL
        AND (
            (s.sectionClass.semester.semesterId = :semesterId AND s.teacher.teacherId = :teacherId)
            OR  (s.sectionClass.semester.semesterId = :semesterId AND s.sectionClass.grade.gradeId = :gradeId)
        )
    """)
    List<ResDtoSubjectSchedule> findAllScheduleByTeacherRequest(
        @Param("semesterId") Long semesterId,
        @Param("teacherId") String teacherId,
        @Param("gradeId") String gradeId
    ) throws SQLException;

    @Query("""
        SELECT DISTINCT new com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.ResDtoSubjectSchedule(
            s.subjectScheduleId, s.sectionClass.subject.subjectName, s.day, s.startingWeek, s.totalWeek,
            s.startingPeriod, s.lastPeriod, s.classroom.roomId
        ) FROM SubjectSchedule s
        WHERE s.day IS NOT NULL
            AND s.startingWeek IS NOT NULL
            AND s.totalWeek IS NOT NULL
            AND s.startingPeriod IS NOT NULL
            AND s.lastPeriod IS NOT NULL
            AND s.classroom IS NOT NULL
            AND s.sectionClass.semester.semesterId = :semesterId
            AND s.teacher.teacherId = :teacherId
    """)
    List<ResDtoSubjectSchedule> findAllScheduleByTeacher(
        @Param("semesterId") Long semesterId,
        @Param("teacherId") String teacherId
    ) throws SQLException;

    @Query("""
        SELECT new com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.ResDtoPracticeSchedule(
            s.subjectScheduleId, s.day, s.startingWeek, s.totalWeek, s.startingPeriod, s.lastPeriod, s.classroom.roomId
        ) FROM SubjectSchedule s
        WHERE s.sectionClass.semester.semesterId = :semesterId AND s.classroom.roomType = 'PRC'
        """)
    List<ResDtoPracticeSchedule> findAllPracticeScheduleInCurrentSemester(@Param("semesterId") Long semesterId);

    @Query("SELECT s FROM SubjectSchedule s WHERE subjectScheduleId = :subjectScheduleId")
    Optional<SubjectSchedule> findAllFieldsById(@Param("subjectScheduleId") Long subjectScheduleId);

    @Query("""
        SELECT s FROM SubjectSchedule s
        WHERE (s.teacherRequest.requestId = :requestId) AND (NOT s.subjectScheduleId = :subjectScheduleId)
    """)
    List<SubjectSchedule> findAllByTeacherRequestRequestIdToUpdate(
        @Param("subjectScheduleId") Long subjectScheduleId,
        @Param("requestId") Long requestId
    );

    @Query("""
        SELECT new com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.ResDtoTeacherRequest(
            s.teacherRequest.requestId, s.teacherRequest.interactionStatus,
            s.teacherRequest.requestMessageDetail, s.teacherRequest.interactRequestReason,
            s.subjectScheduleId, s.sectionClass, s.teacher
        ) FROM SubjectSchedule s WHERE s.teacherRequest.requestId = :requestId
        GROUP BY s.teacherRequest.requestId
    """)
    Optional<ResDtoTeacherRequest> findTeacherRequestByRequestId(Long requestId);

    void deleteAllByTeacherRequestRequestId(Long requestId);

    List<SubjectSchedule> findAllByTeacherRequestRequestId(Long requestId);

    @Modifying
    @Query("""
        DELETE FROM SubjectSchedule s WHERE (s.teacherRequest.requestId = :requestId)
            AND (s.day IS NULL OR s.startingWeek IS NULL OR s.totalWeek IS NULL OR s.startingPeriod IS NULL
                OR s.lastPeriod IS NULL OR s.classroom IS NULL)
        """)
    void deleteScheduleByPendingRequestId(Long requestId);

    int countByTeacherRequestRequestId(Long requestId);
    @Query("""
        SELECT DISTINCT ss.sectionClass FROM SubjectSchedule ss
        WHERE ss.sectionClass.semester = :currentSemester AND ss.teacher = :teacher
    """)
    List<SectionClass> findAllBySectionClassSemesterAndTeacher(
        @Param("currentSemester") Semester currentSemester,
        @Param("teacher") Teacher teacher
    );

    @Query("""
        SELECT COUNT(ss) FROM SubjectSchedule ss
        WHERE ss.sectionClass.sectionClassId = :sectionClassId AND ss.teacherRequest.interactionStatus = :interactionStatus
    """)
    int existsByIdSectionClassIdAndRequestStatus(
        @Param("sectionClassId") Long sectionClassId,
        @Param("interactionStatus")EntityInteractionStatus interactionStatus
    );


    @Modifying
    @Query("""
       UPDATE SubjectSchedule s SET s.sectionClass = :#{#subjectSchedule.sectionClass}
       WHERE s.teacherRequest = :#{#subjectSchedule.teacherRequest}
    """)
    void updateByTeacherRequestAndSectionClass(@Param("subjectSchedule") SubjectSchedule sectionClassIdIsInvalid);

}
