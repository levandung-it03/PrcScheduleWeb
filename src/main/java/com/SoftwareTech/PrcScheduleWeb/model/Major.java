package com.SoftwareTech.PrcScheduleWeb.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Major")
public class Major {
    @Id
    @Column(name = "major_id", length = 20, nullable = false)
    private String majorId;

    @Column(name = "major_name", length = 255, nullable = false, unique = true)
    private String majorName;

    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "department_id")
    private Department departmentId;

    @Column(name = "status_enum", nullable = false, columnDefinition = "BIT(1) DEFAULT 1")
    private boolean status;

    @OneToMany(mappedBy = "major_id")
    private Grade grade;
}
