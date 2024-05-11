package com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReqDtoUpdatePracticeSchedule extends ReqDtoPracticeSchedule{
    @NotNull(message = "error_entity_01")
    private Long updatedPracticeScheduleId;
}
