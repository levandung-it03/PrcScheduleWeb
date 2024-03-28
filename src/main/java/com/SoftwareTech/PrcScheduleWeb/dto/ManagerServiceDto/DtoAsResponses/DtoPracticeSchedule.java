package com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.DtoAsResponses;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DtoPracticeSchedule {
    private Byte day;
    private Byte startingWeek;
    private Byte totalWeek;
    private Byte startingPeriod;
    private Byte lastPeriod;
    private String roomId;
}
