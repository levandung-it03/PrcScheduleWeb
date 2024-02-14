package com.SoftwareTech.PrcScheduleWeb.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Subject")
public class Subject {
    @Id
    @Column(name = "subject_id", length = 20, nullable = false)
    private String subjectId;

    @Column(name = "name", length = 255, nullable = false)
    private String subjectName;

    @Column(name = "credits_number", nullable = false)
    private byte creditsNumber;

    @Column(name = "status", nullable = false, columnDefinition = "BIT(1) DEFAULT 1")
    private boolean status;

    @OneToMany(mappedBy = "subject_id")
    private SubjectDetail subjectDetail;
}
