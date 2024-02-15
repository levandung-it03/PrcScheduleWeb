package com.SoftwareTech.PrcScheduleWeb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "creating_time", nullable = false, columnDefinition = "DATETIME DEFAULT (CURRENT_TIMESTAMP())")
    private Timestamp creatingTime;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "object_id", referencedColumnName = "teacher_id", nullable = false)
    @JsonIgnore
    private Teacher teacher;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "object_id", referencedColumnName = "student_id", nullable = false)
    @JsonIgnore
    private Student student;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "object_id", referencedColumnName = "manager_id", nullable = false)
    @JsonIgnore
    private Manager manager;
}
