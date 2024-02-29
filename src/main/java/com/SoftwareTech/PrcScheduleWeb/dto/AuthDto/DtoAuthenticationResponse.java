package com.SoftwareTech.PrcScheduleWeb.dto.AuthDto;

import com.SoftwareTech.PrcScheduleWeb.model.enums.Role;
import lombok.*;

@Builder
public record DtoAuthenticationResponse(String token, Role role) {
}
