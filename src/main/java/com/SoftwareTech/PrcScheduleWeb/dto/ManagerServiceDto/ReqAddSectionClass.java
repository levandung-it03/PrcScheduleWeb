package com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto;

import com.SoftwareTech.PrcScheduleWeb.model.Grade;
import jakarta.persistence.Column;
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
public class ReqAddSectionClass {
    private long semesterId;

    @NotNull(message = "error_entity_03")
    private String gradeId;

    @NotNull(message = "error_entity_03")
    private String subjectId;

    @NotNull(message = "error_entity_03")
    @Min(value = 1, message = "error_entity_03")
    private Byte groupFromSubject;
}