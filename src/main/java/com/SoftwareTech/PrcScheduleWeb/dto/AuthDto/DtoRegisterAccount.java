package com.SoftwareTech.PrcScheduleWeb.dto.AuthDto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoRegisterAccount {
    @NotEmpty(message = "error_account_01")
    @Pattern(regexp = "^[^@\\s]+[.\\w]*@(ptithcm\\.edu\\.vn|ptit\\.edu\\.vn|student\\.ptithcm\\.edu\\.vn)$",
        message = "error_account_01")
    private String instituteEmail;

    @NotEmpty(message = "error_account_03")
    @Length(min = 8, message = "error_account_03")
    private String password;

    @NotEmpty(message = "error_account_03")
    @Length(min = 8, message = "error_account_03")
    private String retypePassword;
}
