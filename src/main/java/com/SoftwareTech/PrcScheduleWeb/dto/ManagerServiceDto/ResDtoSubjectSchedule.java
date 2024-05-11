package com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResDtoSubjectSchedule {
    private Long subjectScheduleId;
    private String subjectName;
    private Byte day;
    private Byte startingWeek;
    private Byte totalWeek;
    private Byte startingPeriod;
    private Byte lastPeriod;
    private String roomId;
}
