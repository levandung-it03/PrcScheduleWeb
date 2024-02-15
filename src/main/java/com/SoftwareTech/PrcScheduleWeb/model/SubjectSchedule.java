package com.SoftwareTech.PrcScheduleWeb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Subject_Schedule")
public class SubjectSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "subject_schedule_id")
    private Long subjectScheduleId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "subject_detail_id", referencedColumnName = "subject_detail_id", nullable = false)
    @JsonIgnore
    private SubjectDetail subjectDetail;

    @Column(name = "day", nullable = false)
    private byte day;

    @Column(name = "starting_week", nullable = false)
    private byte startingWeek;

    @Column(name = "total_week", nullable = false)
    private byte totalWeek;

    @Column(name = "starting_period", nullable = false)
    private byte startingPeriod;

    @Column(name = "last_period", nullable = false)
    private byte lastPeriod;

    @Column(name = "classroom", length = 10, nullable = false)
    private String classroom;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "teacher_id", referencedColumnName = "teacher_id", nullable = false)
    @JsonIgnore
    private Teacher teacher;

    @Column(name = "status_enum", nullable = false, columnDefinition = "BIT(1) DEFAULT 1")
    private boolean status;

    public String getDayAsString() {
        HashMap<Byte, String> dayDictionary = new HashMap<>();
        dayDictionary.put((byte)2, "Mo.Monday");
        dayDictionary.put((byte)3, "Tu.Tuesday");
        dayDictionary.put((byte)4, "We.Wednesday");
        dayDictionary.put((byte)5, "Th.Thursday");
        dayDictionary.put((byte)6, "Fr.Friday");
        dayDictionary.put((byte)7, "Sa.Saturday");
        dayDictionary.put((byte)8, "Su.Sunday");
        return dayDictionary.get(this.getDay());
    }
}
