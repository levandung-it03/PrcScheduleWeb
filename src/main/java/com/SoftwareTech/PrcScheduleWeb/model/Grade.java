package com.SoftwareTech.PrcScheduleWeb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Grade")
public class Grade {
    @Id
    @Column(name = "grade_id", length = 20, nullable = false)
    private String gradeId;

    @Column(name = "grade_name", nullable = false, unique = true)
    private String gradeName;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "major_id", referencedColumnName = "major_id", nullable = false)
    @JsonIgnore
    private Major major;

    @Column(name = "starting_year", nullable = false)
    private int startingYear;

    @Column(name = "ending_year", nullable = false)
    private int endingYear;

    @Column(name = "status_enum", nullable = false, columnDefinition = "BIT(1) DEFAULT 1")
    private boolean status;
}
