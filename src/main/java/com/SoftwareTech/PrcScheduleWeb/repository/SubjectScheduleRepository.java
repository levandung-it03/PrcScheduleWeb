package com.SoftwareTech.PrcScheduleWeb.repository;

import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.DtoPracticeSchedule;
import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.DtoSubjectSchedule;
import com.SoftwareTech.PrcScheduleWeb.model.SubjectSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

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
        SELECT DISTINCT new com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.DtoSubjectSchedule(
            s.sectionClass.subject.subjectName, s.day, s.startingWeek, s.totalWeek, s.startingPeriod, s.lastPeriod,
            s.classroom.roomId
        ) FROM SubjectSchedule s
        WHERE (s.sectionClass.semester.semesterId = :semesterId AND s.teacher.teacherId = :teacherId)
        OR  (s.sectionClass.semester.semesterId = :semesterId AND s.sectionClass.grade.gradeId = :gradeId)
    """)
    List<DtoSubjectSchedule> findAllScheduleByTeacherRequest(
        @Param("semesterId") Long semesterId,
        @Param("teacherId") String teacherId,
        @Param("gradeId") String gradeId
    ) throws SQLException;

    @Query("""
        SELECT new com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.DtoPracticeSchedule(
            s.day, s.startingWeek, s.totalWeek, s.startingPeriod, s.lastPeriod, s.classroom.roomId
        ) FROM SubjectSchedule s
        WHERE s.sectionClass.semester.semesterId = :semesterId AND s.classroom.Id = 'PRC'
        """)
    List<DtoPracticeSchedule> findAllPracticeScheduleInCurrentSemester(@Param("semesterId") Long semesterId);
}
