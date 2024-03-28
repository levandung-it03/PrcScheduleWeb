package com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.DtoAsResponses;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DtoComputerRoomWithQuantity {
    private String roomId;
    private Integer maxQuantity;
}
