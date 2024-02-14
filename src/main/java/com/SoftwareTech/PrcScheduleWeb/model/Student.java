package com.SoftwareTech.PrcScheduleWeb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Student")
public class Student {
    @Id
    @Column(name = "student_id", length = 20)
    private String studentId;

    @ManyToOne
    @JoinColumn(name = "grade_id", referencedColumnName = "grade_id")
    private Grade gradeId;

    @Column(name = "last_name", length = 255, nullable = false)
    private String lastName;

    @Column(name = "first_name", length = 255, nullable = false)
    private String firstName;

    @Column(name = "birthday", nullable = false)
    private Date birthday;

    // Gender=B(Boy), G(Girl).
    @Column(name = "gender_enum", length = 1, nullable = false)
    private String gender;

    @Column(name = "identifier", length = 20, nullable = false, unique = true)
    private String identity;

    @Column(name = "phone_number", length = 20, nullable = false, unique = true)
    private String phone;

    @Column(name = "institute_email", length = 255, nullable = false, unique = true)
    private String instituteEmail;

    @Column(name = "status_enum", nullable = false, columnDefinition = "BIT(1) DEFAULT 1")
    private boolean status;

    @OneToOne(mappedBy = "object_id")
    private Account account;
}
