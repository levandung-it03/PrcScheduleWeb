package com.SoftwareTech.PrcScheduleWeb.auth;

import lombok.Builder;

@Builder
public record AuthenticationRequest(String instituteEmail, String password) {
}
