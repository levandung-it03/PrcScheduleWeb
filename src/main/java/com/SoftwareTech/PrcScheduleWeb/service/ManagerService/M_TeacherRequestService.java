package com.SoftwareTech.PrcScheduleWeb.service.ManagerService;

import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.ReqDtoInteractTeacherRequest;
import com.SoftwareTech.PrcScheduleWeb.model.SectionClass;
import com.SoftwareTech.PrcScheduleWeb.model.Semester;
import com.SoftwareTech.PrcScheduleWeb.model.SubjectSchedule;
import com.SoftwareTech.PrcScheduleWeb.model.TeacherRequest;
import com.SoftwareTech.PrcScheduleWeb.model.enums.EntityInteractionStatus;
import com.SoftwareTech.PrcScheduleWeb.repository.SemesterRepository;
import com.SoftwareTech.PrcScheduleWeb.repository.SubjectScheduleRepository;
import com.SoftwareTech.PrcScheduleWeb.repository.TeacherRequestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class M_TeacherRequestService {
    @Autowired
    private final TeacherRequestRepository teacherRequestRepository;
    @Autowired
    private final SubjectScheduleRepository subjectScheduleRepository;
    @Autowired
    private final SemesterRepository semesterRepository;

    @Transactional(rollbackOn = {Exception.class})
    public void denyingTeacherRequest(ReqDtoInteractTeacherRequest requestInteraction) throws SQLIntegrityConstraintViolationException {
        TeacherRequest deinedTeacherRequest = teacherRequestRepository
            .findById(requestInteraction.getRequestId())
            .orElseThrow(() -> new NoSuchElementException("Teacher Request Id not found!"));

        //--Just the PENDING Request can be denied, the others can not be
        if (!deinedTeacherRequest.getInteractionStatus().equals(EntityInteractionStatus.PENDING))
            throw new SQLIntegrityConstraintViolationException("error_teacherRequest_04");

        Semester currentSemester = semesterRepository
            .findByCurrentDate(new Date(System.currentTimeMillis()))
            .orElseThrow(() -> new NoSuchElementException("Semester Id not found"));
        SubjectSchedule deniedSubjectSchedule = subjectScheduleRepository
            .findAllByTeacherRequestRequestId(deinedTeacherRequest.getRequestId())
            .getFirst();
        //--If this request doesn't belong to current Semester
        if (!currentSemester.getSemesterId().equals(deniedSubjectSchedule.getSectionClass().getSemester().getSemesterId()))
            throw new SQLIntegrityConstraintViolationException("error_teacherRequest_07");

        deinedTeacherRequest.setInteractionStatus(EntityInteractionStatus.DENIED);
        deinedTeacherRequest.setInteractRequestReason(requestInteraction.getInteractionReason());
        deinedTeacherRequest.setUpdatingTime(LocalDateTime.now());
        teacherRequestRepository.updateByRequestId(deinedTeacherRequest);
    }
}
