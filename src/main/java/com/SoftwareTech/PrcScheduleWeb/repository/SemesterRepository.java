package com.SoftwareTech.PrcScheduleWeb.repository;

import com.SoftwareTech.PrcScheduleWeb.model.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.Optional;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, Long> {
    @Query("SELECT s FROM Semester s WHERE (s.semester = :semester AND s.rangeOfYear = :rangeOfYear)")
    Optional<Semester> findBySemesterAndRangeOfYear(@Param("semester") Byte semester, @Param("rangeOfYear") String rangeOfYear);

    @Query("SELECT s FROM Semester s WHERE :currentDate >= s.startingDate ORDER BY s.startingDate DESC LIMIT 1")
    Optional<Semester> findByCurrentDate(@Param("currentDate") Date nowSqlDate);
}
