package com.SoftwareTech.PrcScheduleWeb.repository;

import com.SoftwareTech.PrcScheduleWeb.model.Department;
import com.SoftwareTech.PrcScheduleWeb.model.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, String> {
    @Query("SELECT d FROM Department d WHERE (d.departmentId =:departmentId OR d.departmentName =:departmentName)")
    Optional<Department> findByDepartmentIdAndDepartmentName(@Param("departmentId") String departmentId, @Param("departmentName") String departmentName);

}
