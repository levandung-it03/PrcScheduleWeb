package com.SoftwareTech.PrcScheduleWeb.repository;

import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.DtoTeacherAccountList;
import com.SoftwareTech.PrcScheduleWeb.model.Account;
import com.SoftwareTech.PrcScheduleWeb.model.enums.Role;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByInstituteEmail(String instituteEmail);
    List<Account> findAllByRole(Role role);

    @Query("""
        SELECT new com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.DtoTeacherAccountList(
            a.accountId, a.instituteEmail, a.creatingTime, a.role, a.status, t.teacherId
        ) FROM Teacher t
        LEFT JOIN t.account a
        WHERE a.role = 'TEACHER'
    """)
    List<DtoTeacherAccountList> findAllInSpecifiedPage(PageRequest pageRequest);
}
