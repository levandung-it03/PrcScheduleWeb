package com.SoftwareTech.PrcScheduleWeb.dto.AuthDto;

import com.SoftwareTech.PrcScheduleWeb.model.enums.Role;
import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DtoAuthenticationResponse {
    private String token;
    private Role role;

}
