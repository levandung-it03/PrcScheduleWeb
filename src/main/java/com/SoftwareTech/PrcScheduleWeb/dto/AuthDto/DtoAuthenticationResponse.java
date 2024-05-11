package com.SoftwareTech.PrcScheduleWeb.dto.AuthDto;

import com.SoftwareTech.PrcScheduleWeb.model.enums.Role;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoAuthenticationResponse {
    private String token;
    private Role role;

}
