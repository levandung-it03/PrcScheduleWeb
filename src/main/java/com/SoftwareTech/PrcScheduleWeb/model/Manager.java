package com.SoftwareTech.PrcScheduleWeb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "Manager")
public class Manager {
    @Id
    @Column(name = "manager_id", length = 20)
    private String managerId;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "first_name", nullable = false)
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

    @Column(name = "institute_email", nullable = false, unique = true)
    private String instituteEmail;

    @Column(name = "status_enum", nullable = false, columnDefinition = "BIT(1) DEFAULT 1")
    private boolean status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "account_id", nullable = false)
    @JsonIgnore
    private Account account;
}
