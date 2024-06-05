package com.SoftwareTech.PrcScheduleWeb.service.TeacherService;

import com.SoftwareTech.PrcScheduleWeb.config.StaticUtilMethods;
import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.ReqDtoInteractTeacherRequest;
import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.ResDtoTeacherRequest;
import com.SoftwareTech.PrcScheduleWeb.dto.TeacherServiceDto.ReqDtoTeacherRequest;
import com.SoftwareTech.PrcScheduleWeb.model.*;
import com.SoftwareTech.PrcScheduleWeb.model.enums.EntityInteractionStatus;
import com.SoftwareTech.PrcScheduleWeb.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class T_TeacherRequestService {
    @Autowired
    private final StaticUtilMethods staticUtilMethods;
    @Autowired
    private final TeacherRepository teacherRepository;
    @Autowired
    private final TeacherRequestRepository teacherRequestRepository;
    @Autowired
    private final SubjectScheduleRepository subjectScheduleRepository;
    @Autowired
    private final SectionClassRepository sectionClassRepository;
    @Autowired
    private final SemesterRepository semesterRepository;

    @Transactional(rollbackOn = {Exception.class})
    public void addTeacherRequest(HttpServletRequest request, ReqDtoTeacherRequest teacherRequest) {
        Account user = staticUtilMethods.getAccountInfoInCookie(request);
        Teacher teacher = teacherRepository
            .findByAccountAccountId(user.getAccountId())
            .orElseThrow(() -> new NoSuchElementException("Account Id is invalid"));

        if (subjectScheduleRepository.existsByIdSectionClassIdAndRequestStatus(
            teacherRequest.getSectionClassId(), EntityInteractionStatus.PENDING) >= 1)
            throw new DuplicateKeyException("There's a Pending Teacher Request existing");

        TeacherRequest savedTeacherRequest = TeacherRequest.builder()
            .interactRequestReason(null)
            .interactionStatus(EntityInteractionStatus.PENDING)
            .requestMessageDetail(teacherRequest.getRequestMessageDetail())
            .updatingTime(LocalDateTime.now())
            .build();
        teacherRequestRepository.save(savedTeacherRequest);
        subjectScheduleRepository.save(SubjectSchedule.builder()
            .day(null)
            .lastPeriod(null)
            .startingPeriod(null)
            .startingWeek(null)
            .totalWeek(null)
            .classroom(null)
            .sectionClass(
                sectionClassRepository
                    .findById(teacherRequest.getSectionClassId())
                    .orElseThrow(() -> new NoSuchElementException("Section Class Id is invalid"))
            )
            .teacher(teacher)
            .teacherRequest(savedTeacherRequest)
            .build()
        );
    }

    @Transactional(rollbackOn = {Exception.class})
    public void updateTeacherRequest(HttpServletRequest request, ReqDtoTeacherRequest teacherRequest) {
        final Long requestId = Long.parseLong(request.getParameter("requestId"));
        if (subjectScheduleRepository.existsByIdSectionClassIdAndRequestStatus(
            teacherRequest.getSectionClassId(), EntityInteractionStatus.PENDING) < 1)
            throw new DuplicateKeyException("There's a no Pending Teacher Request existing to be updated");

        if (!teacherRequestRepository.existsById(requestId))
            throw new NoSuchElementException("Teacher Request Id is invalid");

        TeacherRequest savedTeacherRequest = TeacherRequest.builder()
            .requestId(requestId)
            .interactRequestReason(null)
            .requestMessageDetail(teacherRequest.getRequestMessageDetail())
            .updatingTime(LocalDateTime.now())
            .build();
        teacherRequestRepository.update(savedTeacherRequest);
        subjectScheduleRepository.updateByTeacherRequestAndSectionClass(SubjectSchedule.builder()
            .sectionClass(
                sectionClassRepository
                    .findById(teacherRequest.getSectionClassId())
                    .orElseThrow(() -> new NoSuchElementException("Section Class Id is invalid"))
            )
            .teacherRequest(savedTeacherRequest)
            .build()
        );
    }

    @Transactional(rollbackOn = {Exception.class})
    public void cancelTeacherRequest(ReqDtoInteractTeacherRequest requestInteraction
    ) throws SQLIntegrityConstraintViolationException {
        if (!teacherRequestRepository.existsById(requestInteraction.getRequestId()))
            throw new NoSuchElementException("Teacher Request Id is invalid");

        ResDtoTeacherRequest dtoTeacherRequest = subjectScheduleRepository
            .findTeacherRequestByRequestId(requestInteraction.getRequestId())
            .orElseThrow(() -> new NoSuchElementException("Teacher Request Id not found"));

        if (subjectScheduleRepository.existsByIdSectionClassIdAndRequestStatus(
            dtoTeacherRequest.getSectionClass().getSectionClassId(), EntityInteractionStatus.CREATED) >= 1)
            throw new DuplicateKeyException("There's a Created Teacher Request existing");

        Semester currentSemester = semesterRepository
            .findByCurrentDate(new Date(System.currentTimeMillis()))
            .orElseThrow(() -> new NoSuchElementException("Semester Id not found"));

        if (!currentSemester.getSemesterId().equals(dtoTeacherRequest.getSectionClass().getSemester().getSemesterId()))
            throw new SQLIntegrityConstraintViolationException("This request doesn't belong to current Semester");

        TeacherRequest savedTeacherRequest = TeacherRequest.builder()
            .requestId(requestInteraction.getRequestId())
            .interactionStatus(EntityInteractionStatus.CANCEL)
            .interactRequestReason(requestInteraction.getInteractionReason())
            .updatingTime(LocalDateTime.now())
            .build();
        teacherRequestRepository.cancelTeacherRequest(savedTeacherRequest);
    }
}
