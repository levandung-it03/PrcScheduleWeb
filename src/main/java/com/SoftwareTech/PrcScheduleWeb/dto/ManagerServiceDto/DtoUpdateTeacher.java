package com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto;

import com.SoftwareTech.PrcScheduleWeb.model.enums.Gender;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DtoUpdateTeacher {
    private String teacherId;
    private String instituteEmail;
    private String lastName;
    private String firstName;
    private Date birthday;
    private Gender gender;
    private String departmentId;
    private String phone;
}
