package com.SoftwareTech.PrcScheduleWeb.dto.AuthDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DtoChangePassword {
    @NotBlank(message = "error_account_01")
    @Pattern(regexp = "^[^@\\s]+[.\\w]*@(ptithcm\\.edu\\.vn|ptit\\.edu\\.vn|student\\.ptithcm\\.edu\\.vn)$",
        message = "error_account_01")
    private String instituteEmail;

    @NotBlank(message = "error_account_03")
    @Length(min = 8, message = "error_account_03")
    private String password;

    @NotBlank(message = "error_account_03")
    @Length(min = 8, message = "error_account_03")
    private String retypePassword;

    @NotBlank(message = "error_account_03")
    @Length(min = 6, message = "error_account_03")
    private String otpCode = "______";
}