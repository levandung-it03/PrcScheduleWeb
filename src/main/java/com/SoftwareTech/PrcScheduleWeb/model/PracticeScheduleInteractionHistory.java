package com.SoftwareTech.PrcScheduleWeb.model;

import com.SoftwareTech.PrcScheduleWeb.model.enums.DBInteraction;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Practice_Schedule_Interaction_History")
public class PracticeScheduleInteractionHistory {
    @Id
    @Column(name = "history_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyId;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id", referencedColumnName = "request_id", nullable = false)
    @JsonIgnore
    private TeacherRequest teacherRequest;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_schedule_id", referencedColumnName = "subject_schedule_id")
    @JsonIgnore
    private SubjectSchedule subjectSchedule;

    @Column(name = "creating_time", nullable = false, columnDefinition = "DATETIME DEFAULT (CURRENT_TIMESTAMP())")
    private LocalDateTime creatingTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "interact_type_enum", nullable = false)
    private DBInteraction interactType;

    @Column(name = "message")
    private String message;
}
