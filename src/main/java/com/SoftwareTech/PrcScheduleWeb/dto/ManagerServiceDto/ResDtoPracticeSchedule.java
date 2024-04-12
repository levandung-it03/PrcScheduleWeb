package com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResDtoPracticeSchedule {
    private Long subjectScheduleId;
    private Byte day;
    private Byte startingWeek;
    private Byte totalWeek;
    private Byte startingPeriod;
    private Byte lastPeriod;
    private String roomId;
}
