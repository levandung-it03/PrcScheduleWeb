package com.SoftwareTech.PrcScheduleWeb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashMap;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Subject_Schedule")
public class SubjectSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_schedule_id")
    private Long subjectScheduleId;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "section_class_id", referencedColumnName = "section_class_id", nullable = false)
    @JsonIgnore
    private SectionClass sectionClass;

    @Column(name = "day", nullable = false)
    private Byte day;

    @Column(name = "starting_week", nullable = false)
    private Byte startingWeek;

    @Column(name = "total_week", nullable = false)
    private Byte totalWeek;

    @Column(name = "starting_period", nullable = false)
    private Byte startingPeriod;

    @Column(name = "last_period", nullable = false)
    private Byte lastPeriod;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", referencedColumnName = "room_id", nullable = false)
    private Classroom classroom;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", referencedColumnName = "teacher_id", nullable = false)
    @JsonIgnore
    private Teacher teacher;

    @Column(name = "status_enum", nullable = false, columnDefinition = "BIT DEFAULT 1")
    private boolean status;

    public boolean canBeCombined(SubjectSchedule laterSchedule) {
        return (this.getDay().equals(laterSchedule.getDay())
            &&  this.getStartingPeriod().equals(laterSchedule.getStartingPeriod())
            &&  this.getLastPeriod().equals(laterSchedule.getLastPeriod())
            &&  this.getClassroom().getRoomId().equals(laterSchedule.getClassroom().getRoomId())
            &&  this.getTeacher().getTeacherId().equals(laterSchedule.getTeacher().getTeacherId())
            &&  this.getStartingWeek() + this.getTotalWeek() == laterSchedule.getStartingWeek()
        );
    }
}
