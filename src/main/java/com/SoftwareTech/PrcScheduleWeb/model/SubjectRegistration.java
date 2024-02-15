package com.SoftwareTech.PrcScheduleWeb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    name = "subject_registration",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "UK_Subject_Registration_01",
            columnNames = {"student_id", "subject_detail_id"}
        )
    }
)
public class SubjectRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "subject_registration_id")
    private Long subjectRegistrationId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id", referencedColumnName = "student_id", nullable = false)
    @JsonIgnore
    private Student student;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "subject_detail_id", referencedColumnName = "subject_detail_id", nullable = false)
    @JsonIgnore
    private SubjectDetail subjectDetail;

    @Column(name = "creating_date", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime creatingDate;
}
