package com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResDtoComputerRoom {
    private String roomId;
    private Integer maxQuantity;
    private Integer maxComputerQuantity;
    private Integer availableComputerQuantity;
    private boolean status;
}
