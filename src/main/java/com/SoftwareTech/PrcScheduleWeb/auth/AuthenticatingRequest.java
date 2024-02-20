package com.SoftwareTech.PrcScheduleWeb.auth;

import lombok.Builder;

@Builder
public record AuthenticatingRequest(String instituteEmail, String password) {
}
