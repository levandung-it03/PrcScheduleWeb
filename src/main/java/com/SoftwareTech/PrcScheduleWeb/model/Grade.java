package com.SoftwareTech.PrcScheduleWeb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Grade")
public class Grade {
    @Id
    @Column(name = "grade_id", length = 20, nullable = false)
    private String gradeId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "department_id", referencedColumnName = "department_id", nullable = false)
    @JsonIgnore
    private Department departmentId;

    @Column(name = "status_enum", nullable = false, columnDefinition = "BIT DEFAULT 1")
    private boolean status;
}
