package com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto;

import com.SoftwareTech.PrcScheduleWeb.model.Department;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReqDtoAddGrade {
    @NotBlank(message = "error_entity_01")
    private String gradeId;

    @NotNull(message = "error_entity_01")
    private Department department;
}
