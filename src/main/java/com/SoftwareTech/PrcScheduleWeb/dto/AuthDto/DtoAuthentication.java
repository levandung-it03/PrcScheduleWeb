package com.SoftwareTech.PrcScheduleWeb.dto.AuthDto;

import lombok.Builder;

@Builder
public record DtoAuthentication(String instituteEmail, String password) {
}
