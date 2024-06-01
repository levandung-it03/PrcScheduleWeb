package com.SoftwareTech.PrcScheduleWeb.repository;

import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.ResDtoTeacherAccountList;
import com.SoftwareTech.PrcScheduleWeb.model.Account;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByInstituteEmail(String instituteEmail);

    @Query("""
        SELECT new com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.ResDtoTeacherAccountList(
            a.accountId, a.instituteEmail, DATE(a.creatingTime), a.role, a.status, t.teacherId
        ) FROM Teacher t
        RIGHT JOIN t.account a
        WHERE a.role = 'TEACHER'
    """)
    List<ResDtoTeacherAccountList> findAllInSpecifiedPage(PageRequest pageRequest);

    @Query("SELECT a FROM Account a WHERE a.accountId = :accountId AND a.instituteEmail = :instituteEmail")
    Optional<Account> findByAccountIdAndInstituteEmail(
        @Param("accountId") Long accountId,
        @Param("instituteEmail") String instituteEmail
    );

    int countAllByStatus(boolean status);
    
    @Modifying
    @Query("""
        UPDATE Account a SET a.password = :#{#account.password}
        WHERE a.accountId = :#{#account.accountId}
        """)
    void changePassword(@Param("account") Account account);
}
