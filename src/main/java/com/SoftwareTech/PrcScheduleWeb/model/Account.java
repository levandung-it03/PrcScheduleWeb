package com.SoftwareTech.PrcScheduleWeb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "object_id", length = 20, nullable = false)
    private String objectId;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Column(name = "creating_time", nullable = false, columnDefinition = "DATETIME DEFAULT (CURRENT_TIMESTAMP())")
    private Timestamp creatingTime;

    @OneToOne
    @JoinColumn(name = "object_id", referencedColumnName = "teacher_id")
    private Teacher teacher;

    @OneToOne
    @JoinColumn(name = "object_id", referencedColumnName = "student_id")
    private Student student;

    @OneToOne
    @JoinColumn(name = "object_id", referencedColumnName = "manager_id")
    private Manager manager;

}
