package com.SoftwareTech.PrcScheduleWeb.dto;

import lombok.Builder;

@Builder
public record DtoRegisterAccount(String instituteEmail, String password, String retypePassword) {
}
