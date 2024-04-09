package com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResDtoComputerRoomWithQuantity {
    private String roomId;
    private Integer maxQuantity;
}
