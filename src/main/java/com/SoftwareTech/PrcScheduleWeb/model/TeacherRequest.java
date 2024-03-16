package com.SoftwareTech.PrcScheduleWeb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @ManyToOne
    @JoinColumn(name = "section_class_id", referencedColumnName = "section_class_id", nullable = false)
    @JsonIgnore
    private SectionClass sectionClass;

    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "teacher_id", nullable = false)
    @JsonIgnore
    private Teacher teacher;

    @Column(name = "request_message_detail", length = 100000)
    private String requestMessageDetail;

    @Column(name = "was_created", nullable = false, columnDefinition = "BIT DEFAULT 0")
    private boolean wasCreated;
}
