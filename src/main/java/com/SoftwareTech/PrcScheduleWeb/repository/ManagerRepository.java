package com.SoftwareTech.PrcScheduleWeb.repository;

import com.SoftwareTech.PrcScheduleWeb.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, String> {
    Optional<Manager> findByAccountAccountId(Long accountId);

    boolean existsByAccountAccountId(Long accountId);
    boolean existsByAccountInstituteEmail(String instituteEmail);

    @Modifying
    @Query("""
       UPDATE Manager m SET
            m.firstName = :#{#manager.firstName},
            m.lastName = :#{#manager.lastName},
            m.birthday = :#{#manager.birthday},
            m.phone = :#{#manager.phone},
            m.gender = :#{#manager.gender}
        WHERE m.managerId = :#{#manager.managerId}
    """)
    void updateById(@Param("manager") Manager manager);
}
