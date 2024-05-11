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
public class ReqDtoAddSubject {
    @NotBlank(message = "error_entity_03")
    private String subjectId;

    @NotBlank(message = "error_entity_03")
    @Pattern(regexp = "^[A-Za-zÀ-ỹ]{1,50}( [A-Za-zÀ-ỹ]{1,50})*$", message = "error_systemApplication_01")
    private String subjectName;

    @NotNull(message = "error_entity_03")
    @Min(value = 1, message = "error_entity_03")
    @Max(value = 20, message = "error_entity_03")
    private Byte creditsNumber;


}
