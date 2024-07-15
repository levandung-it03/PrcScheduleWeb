package com.SoftwareTech.PrcScheduleWeb.model;

import com.SoftwareTech.PrcScheduleWeb.model.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Account")
public class Account implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "institute_email", nullable = false, unique = true)
    private String instituteEmail;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "creating_time", nullable = false, columnDefinition = "DATETIME DEFAULT (CURRENT_TIMESTAMP())")
    private LocalDateTime creatingTime;
    @Enumerated(EnumType.STRING)
    @Column(name = "role_enum", nullable = false)
    private Role role;

    @Column(name = "status_enum", nullable = false, columnDefinition = "BIT DEFAULT 1")
    private boolean status;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return instituteEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
