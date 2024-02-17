package com.SoftwareTech.PrcScheduleWeb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Major")
public class Major {
    @Id
    @Column(name = "major_id", length = 20, nullable = false)
    private String majorId;

    @Column(name = "major_name", nullable = false, unique = true)
    private String majorName;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "department_id", referencedColumnName = "department_id", nullable = false)
    @JsonIgnore
    private Department department;

    @Column(name = "status_enum", nullable = false, columnDefinition = "BIT(1) DEFAULT 1")
    private boolean status;
}
