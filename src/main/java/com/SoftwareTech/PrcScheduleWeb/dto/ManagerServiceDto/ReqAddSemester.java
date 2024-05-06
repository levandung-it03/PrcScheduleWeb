package com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReqAddSemester {
    @NotNull(message = "error_entity_03")
    @Min(value = 1, message = "error_entity_03")
    @Max(value = 3, message = "error_entity_03")
    private Byte semester;

    private String rangeOfYear;

    @NotNull(message = "error_entity_03")
    @Min(value = 1, message = "error_entity_03")
    @Max(value = 100, message = "error_entity_03")
    private Byte firstWeek;

    @NotNull(message = "error_entity_03")
    @Min(value = 1, message = "error_entity_03")
    @Max(value = 50, message = "error_entity_03")
    private Byte totalWeek;
}