package com.SoftwareTech.PrcScheduleWeb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Practice_Schedule_Creation")
public class PracticeScheduleCreation {
    @Id
    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_schedule_id", referencedColumnName = "subject_schedule_id")
    @JsonIgnore
    private SubjectSchedule subjectSchedule;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "request_id", referencedColumnName = "request_id")
    @JsonIgnore
    private TeacherRequest teacherRequest;
}
