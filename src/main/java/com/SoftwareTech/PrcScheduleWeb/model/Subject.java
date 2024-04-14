package com.SoftwareTech.PrcScheduleWeb.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Subject")
public class Subject {
    @Id
    @Column(name = "subject_id", length = 20, nullable = false)
    private String subjectId;

    @Column(name = "subject_name", unique = true, nullable = false)
    private String subjectName;

    @Column(name = "credits_number", nullable = false)
    private Byte creditsNumber;

    @Column(name = "status_enum", nullable = false, columnDefinition = "BIT DEFAULT 1")
    private boolean status;
}
