package com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto;

import com.SoftwareTech.PrcScheduleWeb.model.enums.Role;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResDtoTeacherAccountList {
    private Long accountId;
    private String instituteEmail;
    private LocalDateTime creatingTime;
    private Role role;
    private boolean status;
    private String teacherId;
}
