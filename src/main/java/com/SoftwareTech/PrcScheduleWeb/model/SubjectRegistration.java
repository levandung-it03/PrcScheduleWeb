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
    name = "subject_registration",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "UK_Subject_Registration_01",
            columnNames = {"student_id", "section_class_id"}
        )
    }
)
public class SubjectRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_registration_id")
    private Long subjectRegistrationId;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "student_id", referencedColumnName = "student_id", nullable = false)
    @JsonIgnore
    private Student student;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "section_class_id", referencedColumnName = "section_class_id", nullable = false)
    @JsonIgnore
    private SectionClass sectionClass;
}
