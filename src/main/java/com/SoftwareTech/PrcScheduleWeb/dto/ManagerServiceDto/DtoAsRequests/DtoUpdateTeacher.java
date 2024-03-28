package com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.DtoAsRequests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DtoUpdateTeacher {
    @NotEmpty(message = "error_entity_01")
    private String teacherId;

    @NotEmpty(message = "error_account_01")
    @Pattern(regexp = "^[^@\\s]+[.\\w]*@(ptithcm\\.edu\\.vn|ptit\\.edu\\.vn|student\\.ptithcm\\.edu\\.vn)$",
        message = "error_account_01")
    private String instituteEmail;

    @NotEmpty(message = "error_entity_03")
    @Pattern(regexp = "^[A-Za-zÀ-ỹ]{1,50}( [A-Za-zÀ-ỹ]{1,50})*$", message = "error_account_01")
    private String lastName;

    @NotEmpty(message = "error_entity_03")
    @Pattern(regexp = "^[A-Za-zÀ-ỹ]{1,50}$", message = "error_account_01")
    private String firstName;

    @NotNull(message = "error_entity_03")
    @Past(message = "error_entity_03")
    private Date birthday;

    @NotNull(message = "error_entity_03")
    private String gender;

    @NotEmpty(message = "error_entity_03")
    private String departmentId;

    @NotEmpty(message = "error_entity_03")
    @Pattern(regexp = "^[0-9]{4,12}$", message = "error_account_01")
    private String phone;
}
