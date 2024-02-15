package com.SoftwareTech.PrcScheduleWeb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "computer_room")
@Check(constraints = "max_computer_quantity >= available_computer_quantity")
public class ComputerRoom {
    @Id
    @Column(name = "computer_room", length = 10)
    private String computerRoom;

    @Column(name = "max_computer_quantity", nullable = false)
    private int maxComputerQuantity;

    @Column(name = "available_computer_quantity", nullable = false)
    private int availableComputerQuantity;

    @Column(name = "is_rented", nullable = false)
    private byte isRented;

    @Column(name = "status_enum", nullable = false)
    private byte status;
}
