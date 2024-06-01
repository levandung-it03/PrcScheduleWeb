package com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto;

import com.SoftwareTech.PrcScheduleWeb.model.enums.Role;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResDtoTeacherAccountList {
    private Long accountId;
    private String instituteEmail;
    private Date creatingTime;
    private Role role;
    private boolean status;
    private String teacherId;
}
