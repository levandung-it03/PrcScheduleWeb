package com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DtoAddComputerRoom {
    private String area;
    private Integer roomCode;
    private Integer maxComputerQuantity;
}
