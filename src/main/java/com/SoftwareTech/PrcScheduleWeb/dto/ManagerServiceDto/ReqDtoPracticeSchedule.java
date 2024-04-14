package com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReqDtoPracticeSchedule {
    @NotNull(message = "error_entity_01")
    private String teacherId;

    @NotNull(message = "error_entity_01")
    private Long requestId;

    @NotNull(message = "error_entity_01")
    private Long sectionClassId;

    @NotBlank(message = "error_schedule_01")
    private String practiceScheduleListAsString;
}
