package com.SoftwareTech.PrcScheduleWeb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    name = "Subject_Detail",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "UK_Subject_Detail_01",
            columnNames = {"group_from_subject", "grade_id", "subject_id"}
        )
    }
)
@Check(constraints = "available_quantity <= max_quantity")
public class SubjectDetail {
    @Id
    @Column(name = "subject_detail_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subjectDetailId;

    @Column(name = "group_from_subject", nullable = false)
    private Byte groupFromSubject;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "grade_id", referencedColumnName = "grade_id", nullable = false)
    @JsonIgnore
    private Grade grade;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "subject_id", referencedColumnName = "subject_id", nullable = false)
    @JsonIgnore
    private Subject subject;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "semester_id", referencedColumnName = "semester_id", nullable = false)
    @JsonIgnore
    private Semester semester;

    @Column(name = "group_from_grade", nullable = false)
    private Byte groupFromGrade;

    @Column(name = "max_quantity", nullable = false)
    private Integer maxQuantity;

    @Column(name = "available_quantity", nullable = false)
    private Integer availableQuantity;
}