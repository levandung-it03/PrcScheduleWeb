package com.SoftwareTech.PrcScheduleWeb.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RegisterRequest {
    private String instituteEmail;
    private String password;
}
