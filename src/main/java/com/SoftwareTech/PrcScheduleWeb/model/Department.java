package com.SoftwareTech.PrcScheduleWeb.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Department")
public class Department {
    @Id
    @Column(name = "department_id", length = 20, nullable = false)
    private String departmentId;

    @Column(name = "department_name", nullable = false)
    private String departmentName;
}
