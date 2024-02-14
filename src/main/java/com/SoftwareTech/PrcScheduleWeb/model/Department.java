package com.SoftwareTech.PrcScheduleWeb.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Department")
public class Department {
    @Id
    @Column(name = "department_id", length = 20, nullable = false)
    private String departmentId;

    @Column(name = "department_name", length = 255, nullable = false, unique = true)
    private String departmentName;

    @Column(name = "status_enum", nullable = false, columnDefinition = "BIT(1) DEFAULT 1")
    private boolean status;

    @OneToMany(mappedBy = "department_id")
    private Teacher teacher;

    @OneToMany(mappedBy = "department_id")
    private Major major;
}
