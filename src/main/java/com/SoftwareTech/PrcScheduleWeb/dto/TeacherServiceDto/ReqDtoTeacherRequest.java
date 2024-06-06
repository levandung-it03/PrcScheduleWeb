package com.SoftwareTech.PrcScheduleWeb.dto.TeacherServiceDto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReqDtoTeacherRequest {
    @NotNull(message = "error_entity_03")
    private Long sectionClassId;
    @NotEmpty(message = "error_entity_03")
    private String requestMessageDetail = "";
}
