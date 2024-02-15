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
@Table(name = "String_Id_Status_Updating_History")
public class StringIdStatusUpdatingHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "status_history_id")
    private Long statusHistoryId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "updated_id", referencedColumnName = "manager_id", nullable = false)
    @JsonIgnore
    private Manager manager;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "updated_id", referencedColumnName = "teacher_id", nullable = false)
    @JsonIgnore
    private Teacher teacher;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "updated_id", referencedColumnName = "student_id", nullable = false)
    @JsonIgnore
    private Student student;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "updated_id", referencedColumnName = "grade_id", nullable = false)
    @JsonIgnore
    private Grade grade;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "updated_id", referencedColumnName = "major_id", nullable = false)
    @JsonIgnore
    private Major major;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "updated_id", referencedColumnName = "department_id", nullable = false)
    @JsonIgnore
    private Department department;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "updated_id", referencedColumnName = "subject_id", nullable = false)
    @JsonIgnore
    private Subject subject;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "updated_id", referencedColumnName = "computer_room", nullable = false)
    @JsonIgnore
    private ComputerRoom computerRoom;

    @Column(name = "new_status_enum", nullable = false)
    private byte newStatusEnum;

    @Column(name = "updating_date", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updatingDate;
}
