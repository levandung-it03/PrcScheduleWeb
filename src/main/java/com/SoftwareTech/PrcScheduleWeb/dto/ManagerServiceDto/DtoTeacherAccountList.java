package com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto;

import com.SoftwareTech.PrcScheduleWeb.model.enums.Role;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DtoTeacherAccountList {
    private Long accountId;
    private String instituteEmail;
    private Timestamp creatingTime;
    private Role role;
    private boolean status;
    private String teacherId;
}
