package com.SoftwareTech.PrcScheduleWeb.model;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "Teacher")
public class Teacher {
    @Id
    @Column(name = "teacher_id", length = 20)
    private String teacherId;

    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "department_id")
    private Grade departmentId;

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
