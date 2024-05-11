package com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResDtoComputerRoomWithQuantity {
    private String roomId;
    private Integer maxQuantity;
}
