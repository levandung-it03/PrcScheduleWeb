package com.SoftwareTech.PrcScheduleWeb.model;

import com.SoftwareTech.PrcScheduleWeb.model.enums.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Teacher")
public class Teacher {
    @Id
    @Column(name = "teacher_id", length = 20)
    private String teacherId;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "department_id", referencedColumnName = "department_id", nullable = false)
    @JsonIgnore
    private Department department;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "birthday", nullable = false)
    private Date birthday;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender_enum", length = 4, nullable = false)
    private Gender gender;

    @Column(name = "phone_number", length = 20, nullable = false, unique = true)
    private String phone;

    @Column(name = "status_enum", nullable = false, columnDefinition = "BIT DEFAULT 1")
    private boolean status;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "account_id", referencedColumnName = "account_id", nullable = false)
    @JsonIgnore
    private Account account;
}
