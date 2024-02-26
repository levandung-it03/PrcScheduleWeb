package com.SoftwareTech.PrcScheduleWeb.dto;

import lombok.Builder;

@Builder
public record DtoAuthentication(String instituteEmail, String password) {
}
