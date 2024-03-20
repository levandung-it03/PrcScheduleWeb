package com.SoftwareTech.PrcScheduleWeb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
    name = "Section_Class",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "UK_Section_Class_01",
            columnNames = {"group_from_subject", "grade_id", "subject_id"}
        )
    }
)
public class SectionClass {
    @Id
    @Column(name = "section_class_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sectionClassId;

    @Column(name = "group_from_subject", nullable = false)
    private Byte groupFromSubject;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "grade_id", referencedColumnName = "grade_id", nullable = false)
    @JsonIgnore
    private Grade grade;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", referencedColumnName = "subject_id", nullable = false)
    @JsonIgnore
    private Subject subject;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "semester_id", referencedColumnName = "semester_id", nullable = false)
    @JsonIgnore
    private Semester semester;
}