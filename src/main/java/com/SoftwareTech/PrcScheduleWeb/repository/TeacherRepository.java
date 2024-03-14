package com.SoftwareTech.PrcScheduleWeb.repository;
import com.SoftwareTech.PrcScheduleWeb.model.Teacher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, String> {
    @Query("SELECT t FROM Teacher t")
    List<Teacher> findAllInSpecifiedPage(PageRequest pageRequest);

    @Query("SELECT t FROM Teacher t WHERE t.teacherId = :teacherId AND t.account.instituteEmail = :instituteEmail")
    Optional<Teacher> findByTeacherIdAndInstituteEmail(
        @Param("teacherId") String teacherId,
        @Param("instituteEmail") String instituteEmail
    );

    @Modifying
    @Query("DELETE FROM Teacher t WHERE t.account.accountId = :accountId")
    void deleteByAccountId(@Param("accountId") Long accountId)
        throws SQLIntegrityConstraintViolationException;
}
