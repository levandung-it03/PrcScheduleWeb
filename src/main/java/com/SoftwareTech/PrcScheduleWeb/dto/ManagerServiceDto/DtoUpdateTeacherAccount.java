package com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DtoUpdateTeacherAccount {
    private Long accountId;
    private String instituteEmail;
    private Timestamp creatingTime;
    private boolean status;
}
