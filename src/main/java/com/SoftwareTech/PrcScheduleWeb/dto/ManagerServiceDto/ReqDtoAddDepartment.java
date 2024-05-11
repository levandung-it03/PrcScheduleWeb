package com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReqDtoAddDepartment {

    @Size(max = 20, message = "error_entity_03")
    @NotNull(message = "error_entity_03")
    private String departmentId;

    @NotNull(message = "error_entity_03")
    private String departmentName;
}