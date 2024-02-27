package com.SoftwareTech.PrcScheduleWeb.auth;

import com.SoftwareTech.PrcScheduleWeb.model.enums.Role;
import lombok.*;

@Builder
public record DtoAuthenticationResponse(String encodedToken, Role role) {
}
