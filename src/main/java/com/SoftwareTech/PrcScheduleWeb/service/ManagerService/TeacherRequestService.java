package com.SoftwareTech.PrcScheduleWeb.service.ManagerService;

import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.ReqDtoInteractTeacherRequest;
import com.SoftwareTech.PrcScheduleWeb.model.SubjectSchedule;
import com.SoftwareTech.PrcScheduleWeb.model.TeacherRequest;
import com.SoftwareTech.PrcScheduleWeb.model.enums.EntityInteractionStatus;
import com.SoftwareTech.PrcScheduleWeb.repository.SubjectScheduleRepository;
import com.SoftwareTech.PrcScheduleWeb.repository.TeacherRequestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TeacherRequestService {
    @Autowired
    private final TeacherRequestRepository teacherRequestRepository;
    @Autowired
    private final SubjectScheduleRepository subjectScheduleRepository;

    @Transactional(rollbackOn = {Exception.class})
    public void denyTeacherRequest(ReqDtoInteractTeacherRequest requestInteraction) throws SQLIntegrityConstraintViolationException {
        TeacherRequest deinedTeacherRequest = teacherRequestRepository
            .findById(requestInteraction.getRequestId())
            .orElseThrow(() -> new NoSuchElementException("Teacher Request Id not found!"));

        if (!deinedTeacherRequest.getInteractionStatus().equals(EntityInteractionStatus.PENDING))
            throw new SQLIntegrityConstraintViolationException("Just the PENDING Request can be denied, the others can not be");

        deinedTeacherRequest.setInteractionStatus(EntityInteractionStatus.DENIED);
        deinedTeacherRequest.setInteractRequestReason(requestInteraction.getInteractionReason());
        deinedTeacherRequest.setUpdatingTime(LocalDateTime.now());
        teacherRequestRepository.updateByRequestId(deinedTeacherRequest);
    }
}
