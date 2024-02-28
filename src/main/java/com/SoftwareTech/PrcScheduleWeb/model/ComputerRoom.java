package com.SoftwareTech.PrcScheduleWeb.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Check;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "computer_room")
@Check(constraints = "max_computer_quantity >= available_computer_quantity")
public class ComputerRoom {
    @Id
    @Column(name = "computer_room", length = 10)
    private String computerRoom;

    @Column(name = "max_computer_quantity", nullable = false)
    private Integer maxComputerQuantity;

    @Column(name = "available_computer_quantity", nullable = false)
    private Integer availableComputerQuantity;

    @Column(name = "is_rented", nullable = false)
    private Byte isRented;

    @Column(name = "status_enum", nullable = false, columnDefinition = "TINYINT DEFAULT 1")
    private boolean status;
}
