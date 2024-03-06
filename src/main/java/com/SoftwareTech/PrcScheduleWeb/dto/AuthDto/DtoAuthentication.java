package com.SoftwareTech.PrcScheduleWeb.dto.AuthDto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoAuthentication {
    private String instituteEmail;
    private String password;
}
