package com.SoftwareTech.PrcScheduleWeb.model;

import com.SoftwareTech.PrcScheduleWeb.model.enums.RoomType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Classroom")
public class Classroom {
    @Id
    @Column(name = "room_id", length = 10)
    private String roomId;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_type_enum", length = 4, nullable = false)
    private RoomType roomType;

    @Column(name = "max_quantity", nullable = false)
    private Integer maxQuantity;

    @Column(name = "status_enum", nullable = false, columnDefinition = "BIT DEFAULT 1")
    private boolean status;
}
