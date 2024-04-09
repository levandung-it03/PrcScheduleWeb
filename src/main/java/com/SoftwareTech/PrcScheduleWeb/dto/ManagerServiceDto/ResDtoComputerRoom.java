package com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto;

import lombok.*;

@Getter
@Setter
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
