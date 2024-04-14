package com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReqDtoUpdateTeacherAccount {
    @NotNull(message = "error_entity_01")
    private Long accountId;

    @NotBlank(message = "error_entity_03")
    @Pattern(regexp = "^[^@\\s]+[.\\w]*@(ptithcm\\.edu\\.vn|ptit\\.edu\\.vn|student\\.ptithcm\\.edu\\.vn)$",
        message = "error_entity_03")
    private String instituteEmail;

    @NotNull(message = "error_entity_03")
    private LocalDateTime creatingTime;

    @NotNull(message = "error_entity_03")
    private boolean status;
}
