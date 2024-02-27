package com.SoftwareTech.PrcScheduleWeb.model;

import jakarta.persistence.*;
import lombok.*;

 java.util.List;

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

    @Column(name = "department_name", nullable = false, unique = true)
    private String departmentName;

    @Column(name = "status_enum", nullable = false, columnDefinition = "BIT(1) DEFAULT 1")
    private boolean status;
}
