package com.SoftwareTech.PrcScheduleWeb.auth;

import lombok.Builder;

@Builder
public record DtoRegisterRequest(String instituteEmail, String password) {
}
