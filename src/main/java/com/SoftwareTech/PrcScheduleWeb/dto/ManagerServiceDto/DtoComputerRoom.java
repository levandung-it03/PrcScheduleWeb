package com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DtoComputerRoom {
    private String roomId;
    private Integer maxComputerQuantity;
    private Integer availableComputerQuantity;
    private boolean status;
}
