package com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoUpdateComputerRoom {
    private Integer maxComputerQuantity;
    private boolean status;
    private Integer availableComputerQuantity;
}
