package com.SoftwareTech.PrcScheduleWeb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    name = "Semester",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "UK_Semester_01",
            columnNames = {"semester", "range_of_year"}
        )
    }
)
public class Semester {
    @Id
    @Column(name = "semester_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long semesterId;

    @Column(name = "semester", nullable = false)
    private Byte semester;

    @Column(name = "range_of_year", length = 20, nullable = false)
    private String rangeOfYear;

    @Column(name = "first_week", nullable = false)
    private Byte fistWeek;

    @Column(name = "total_week", nullable = false)
    private Byte totalWeek;

    @Column(name = "starting_date", nullable = false, columnDefinition = "DATE DEFAULT CURRENT_DATE")
    private Date startingDate;
}
