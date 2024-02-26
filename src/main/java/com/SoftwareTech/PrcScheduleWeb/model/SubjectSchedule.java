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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "subject_detail_id", referencedColumnName = "subject_detail_id", nullable = false)
    @JsonIgnore
    private SubjectDetail subjectDetail;

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

    @Column(name = "classroom", length = 10, nullable = false)
    private String classroom;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "teacher_id", referencedColumnName = "teacher_id", nullable = false)
    @JsonIgnore
    private Teacher teacher;

    @Column(name = "status_enum", nullable = false, columnDefinition = "BIT(1) DEFAULT 1")
    private boolean status;

    public String[] getDayAsString() {
        HashMap<Byte, String[]> dayDictionary = new HashMap<>();
        dayDictionary.put((byte)2, new String[] {"Mo", "Monday"});
        dayDictionary.put((byte)3, new String[] {"Tu", "Tuesday"});
        dayDictionary.put((byte)4, new String[] {"We", "Wednesday"});
        dayDictionary.put((byte)5, new String[] {"Th", "Thursday"});
        dayDictionary.put((byte)6, new String[] {"Fr", "Friday"});
        dayDictionary.put((byte)7, new String[] {"Sa", "Saturday"});
        dayDictionary.put((byte)8, new String[] {"Su", "Sunday"});
        return dayDictionary.get(this.getDay());
    }
}