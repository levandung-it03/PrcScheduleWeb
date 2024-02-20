package com.SoftwareTech.PrcScheduleWeb.auth;

import lombok.*;

@Data
@AllArgsConstructor
@Builder
public class AuthenticationResponse {
    private String token;
}
