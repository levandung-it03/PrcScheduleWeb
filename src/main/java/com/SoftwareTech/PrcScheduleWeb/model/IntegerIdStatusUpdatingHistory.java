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
@Table(name = "Integer_Id_Status_Updating_History")
public class IntegerIdStatusUpdatingHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "status_history_id")
    private Long statusHistoryId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "updated_id", referencedColumnName = "semester_id", nullable = false)
    @JsonIgnore
    private Semester semester;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "updated_id", referencedColumnName = "subject_detail_id", nullable = false)
    @JsonIgnore
    private SubjectDetail subjectDetail;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "updated_id", referencedColumnName = "subject_schedule_id", nullable = false)
    @JsonIgnore
    private SubjectSchedule subjectSchedule;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "updated_id", referencedColumnName = "practice_schedule_id", nullable = false)
    @JsonIgnore
    private PracticeSchedule practiceSchedule;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "updated_id", referencedColumnName = "subject_registration_id", nullable = false)
    @JsonIgnore
    private SubjectRegistration subjectRegistration;

    @Column(name = "new_status_enum", nullable = false)
    private byte newStatusEnum;

    @Column(name = "updating_date", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updatingDate;
}
