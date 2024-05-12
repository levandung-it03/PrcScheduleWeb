package com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto;

import com.SoftwareTech.PrcScheduleWeb.model.SectionClass;
import com.SoftwareTech.PrcScheduleWeb.model.Teacher;
import com.SoftwareTech.PrcScheduleWeb.model.enums.EntityInteractionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResDtoTeacherRequest {
    private Long requestId;
    private EntityInteractionStatus interactionStatus;
    private String requestMessageDetail;
    private String interactRequestReason;
    private Long pendingSubjectScheduleId;
    private SectionClass sectionClass;
    private Teacher teacher;
}
