package com.SoftwareTech.PrcScheduleWeb.dto.AuthDto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoRegisterAccount {
    private String instituteEmail;
    private String password;
    private String retypePassword;
}
