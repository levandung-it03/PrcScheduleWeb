package com.SoftwareTech.PrcScheduleWeb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;

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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int subjectDetailId;

    @Column(name = "group_from_subject", nullable = false)
    private byte groupFromSubject;

    @ManyToOne
    @JoinColumn(name = "grade_id", referencedColumnName = "grade_id")
    private Grade grade;

    @ManyToOne
    @JoinColumn(name = "subject_id", referencedColumnName = "subject_id")
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "semester_id", referencedColumnName = "semester_id")
    private Semester semester;

    @Column(name = "group_from_grade", nullable = false)
    private byte groupFromGrade;

    @Column(name = "max_quantity", nullable = false)
    private int maxQuantity;

    @Column(name = "available_quantity", nullable = false)
    private int availableQuantity;


}
