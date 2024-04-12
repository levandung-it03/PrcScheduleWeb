package com.SoftwareTech.PrcScheduleWeb.model;

import com.SoftwareTech.PrcScheduleWeb.model.enums.EntityInteractionStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Teacher_Request")
public class TeacherRequest {
    @Id
    @Column(name = "request_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

    @Column(name = "request_message_detail", length = 100000, updatable = false)
    private String requestMessageDetail;

    @Column(name = "interacting_request_reason", length = 100000)
    private String interactRequestReason;

    @Enumerated(EnumType.STRING)
    @Column(name = "interaction_status_enum", length = 7, nullable = false)
    private EntityInteractionStatus interactionStatus;

    @Column(name = "updating_time", nullable = false, columnDefinition = "DATETIME DEFAULT (CURRENT_TIMESTAMP())")
    private LocalDateTime updatingTime;
}
