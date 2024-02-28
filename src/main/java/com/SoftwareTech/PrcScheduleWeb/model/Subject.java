package com.SoftwareTech.PrcScheduleWeb.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    @Column(name = "name", nullable = false)
    private String subjectName;

    @Column(name = "credits_number", nullable = false)
    private Byte creditsNumber;

    @Column(name = "status_enum", nullable = false, columnDefinition = "TINYINT DEFAULT 1")
    private boolean status;
}
