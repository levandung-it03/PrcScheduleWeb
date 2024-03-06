package com.SoftwareTech.PrcScheduleWeb.model;

import com.SoftwareTech.PrcScheduleWeb.model.enums.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Student")
public class Student {
    @Id
    @Column(name = "student_id", length = 20)
    private String studentId;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "grade_id", referencedColumnName = "grade_id", nullable = false)
    @JsonIgnore
    private Grade gradeId;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender_enum", length = 1, nullable = false)
    private Gender gender;

    @Column(name = "institute_email", nullable = false, unique = true)
    private String instituteEmail;
}
