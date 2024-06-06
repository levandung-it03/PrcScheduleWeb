package com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto;

import jakarta.validation.constraints.Min;
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
public class ReqDtoInteractTeacherRequest {
    @NotNull(message = "error_entity_03")
    @Min(value = 0, message = "error_entity_03")
    private Long requestId;

    private String interactionReason;
}
