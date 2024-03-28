package com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.DtoAsRequests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DtoUpdateTeacherAccount {
    @NotNull(message = "error_entity_01")
    private Long accountId;

    @NotEmpty(message = "error_account_01")
    @Pattern(regexp = "^[^@\\s]+[.\\w]*@(ptithcm\\.edu\\.vn|ptit\\.edu\\.vn|student\\.ptithcm\\.edu\\.vn)$",
        message = "error_account_01")
    private String instituteEmail;

    @NotNull(message = "error_entity_03")
    private Timestamp creatingTime;

    @NotNull(message = "error_entity_03")
    private boolean status;
}
