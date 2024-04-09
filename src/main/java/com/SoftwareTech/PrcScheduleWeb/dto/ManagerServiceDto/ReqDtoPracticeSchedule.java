package com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReqDtoPracticeSchedule {
    @NotNull(message = "error_systemApplication_01")
    private String teacherId;

    @NotNull(message = "error_systemApplication_01")
    private Long requestId;

    @NotNull(message = "error_systemApplication_01")
    private Long sectionClassId;

    @NotEmpty(message = "error_entity_03")
    private String practiceScheduleListAsString;
}
