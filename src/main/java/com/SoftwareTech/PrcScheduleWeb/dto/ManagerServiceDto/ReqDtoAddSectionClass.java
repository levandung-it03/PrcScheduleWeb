package com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReqDtoAddSectionClass {
    private long semesterId;

    @NotNull(message = "error_entity_03")
    private String gradeId;

    @NotNull(message = "error_entity_03")
    private String subjectId;

    @NotNull(message = "error_entity_03")
    @Min(value = 1, message = "error_entity_03")
    private Byte groupFromSubject;
}