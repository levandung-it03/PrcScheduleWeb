package com.SoftwareTech.PrcScheduleWeb.service.ManagerService;

import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.ReqDtoPracticeSchedule;
import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.ReqDtoUpdatePracticeSchedule;
import com.SoftwareTech.PrcScheduleWeb.model.*;
import com.SoftwareTech.PrcScheduleWeb.model.enums.EntityInteractionStatus;
import com.SoftwareTech.PrcScheduleWeb.model.enums.RoomType;
import com.SoftwareTech.PrcScheduleWeb.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class M_SubjectScheduleService {
    @Autowired
    private final SectionClassRepository sectionClassRepository;
    @Autowired
    private final TeacherRepository teacherRepository;
    @Autowired
    private final TeacherRequestRepository teacherRequestRepository;
    @Autowired
    private final ClassroomRepository classroomRepository;
    @Autowired
    private final SubjectScheduleRepository subjectScheduleRepository;
    @Autowired
    private final SemesterRepository semesterRepository;

    @Transactional(rollbackOn = {Exception.class})
    public void addPracticeSchedule(ReqDtoPracticeSchedule practiceScheduleObj) {
        //--Prepare data to add into practice-schedule.
        SectionClass sectionClass = sectionClassRepository
            .findById(practiceScheduleObj.getSectionClassId())
            .orElseThrow(() -> new NoSuchElementException("Request Id is invalid"));
        Teacher teacher = teacherRepository
            .findById(practiceScheduleObj.getTeacherId())
            .orElseThrow(() -> new NoSuchElementException("Teacher Id is invalid"));
        TeacherRequest teacherRequest = teacherRequestRepository
            .findById(practiceScheduleObj.getRequestId())
            .orElseThrow(() -> new NoSuchElementException("Request Id is invalid!"));

        //--Prepare empty results-set to handle adding practice-schedule.
        HashMap<String, Classroom> classroomObjects = new HashMap<>();
        ArrayList<HashMap<String, String>> extractedPlainData = new ArrayList<>();

        //--Split the plain-data, which is separated by ", " string.
        String[] practiceScheduleRows = practiceScheduleObj.getPracticeScheduleListAsString().split(", ");
        //--Loop through the split plain-data result. Example: ["week:10_day:2_period:3_roomId:2B11", ...].
        for (String row : practiceScheduleRows) {
            HashMap<String, String> currentDataFields = new HashMap<>();
            String[] tempSplitDataField;
            //--Extract data as: ["field:data", ...].
            for (String plainDataField : row.split("_")) {
                tempSplitDataField = plainDataField.split(":");
                currentDataFields.put(tempSplitDataField[0], tempSplitDataField[1]);
            }
            //--Store it into a temp List.
            extractedPlainData.add(currentDataFields);
            //--Store the corresponding (if it doesn't exist yet) Classroom as roomId.
            if (classroomObjects.get(currentDataFields.get("roomId")) == null) {
                classroomObjects.put(currentDataFields.get("roomId"), classroomRepository
                    .findById(currentDataFields.get("roomId"))
                    .orElseThrow(() -> new NoSuchElementException("Room Id is invalid"))
                );
            }
        }
        //--Sorting by week to make the combination return the right result.
        extractedPlainData.sort((a, b) -> {
            if (!a.get("week").equals(b.get("week")))
                return Integer.parseInt(a.get("week")) - Integer.parseInt(b.get("week"));
            else if (!a.get("day").equals(b.get("day")))
                return Integer.parseInt(a.get("day")) - Integer.parseInt(b.get("day"));
            else
                return Integer.parseInt(a.get("period")) - Integer.parseInt(b.get("period"));
        });
        //--Start combining all practice-schedule to save them into DB.
        List<SubjectSchedule> combinedPracticeSchedule = new ArrayList<>();
        for (var index = 0; index < extractedPlainData.size(); index++) {
            //--Initialization.
            SubjectSchedule practiceSchedule = SubjectSchedule.builder()
                .sectionClass(sectionClass)
                .teacher(teacher)
                .classroom(classroomObjects.get(extractedPlainData.get(index).get("roomId")))
                .day(Byte.parseByte(extractedPlainData.get(index).get("day")))
                .startingWeek(Byte.parseByte(extractedPlainData.get(index).get("week")))
                .totalWeek((byte) 1)
                .startingPeriod(Byte.parseByte(extractedPlainData.get(index).get("period")))
                .lastPeriod(Byte.parseByte(extractedPlainData.get(index).get("period")))
                .teacherRequest(teacherRequest)
                .build();
            //--Combining the period in a 'day' of a 'week'.
            for (index = index + 1; index < extractedPlainData.size(); index++) {
                if (extractedPlainData.get(index).get("roomId").equals(practiceSchedule.getClassroom().getRoomId())
                    && extractedPlainData.get(index).get("day").equals(Byte.toString(practiceSchedule.getDay()))
                    && extractedPlainData.get(index).get("week").equals(Byte.toString(practiceSchedule.getStartingWeek()))
                    && Byte.parseByte(extractedPlainData.get(index).get("period")) - practiceSchedule.getLastPeriod() == 1
                ) {
                    practiceSchedule.setLastPeriod((byte) (practiceSchedule.getLastPeriod() + 1));
                } else {
                    index--;
                    break;
                }
            }
            //--Combining the practice-schedule similar to each other with the compatible 'week'.
            if (!combinedPracticeSchedule.isEmpty()
                && combinedPracticeSchedule.getLast().canBeCombined(practiceSchedule)) {
                combinedPracticeSchedule.getLast().setTotalWeek(
                    (byte) (combinedPracticeSchedule.getLast().getTotalWeek() + practiceSchedule.getTotalWeek())
                );
            }
            //--If it's not compatible, save it into main-result as a new practice-schedule.
            else combinedPracticeSchedule.add(practiceSchedule);
        }
        //--Save the main-result into DB.
        //--May throw DataIntegrityViolationException because of custom Trigger.
        subjectScheduleRepository.deleteScheduleByPendingRequestId(teacherRequest.getRequestId());
        subjectScheduleRepository.saveAll(combinedPracticeSchedule);
        //--Update teacher-request status.
        teacherRequest.setInteractionStatus(EntityInteractionStatus.CREATED);
        teacherRequest.setUpdatingTime(LocalDateTime.now());
        teacherRequestRepository.updateByRequestId(teacherRequest);
    }

    @Transactional(rollbackOn = {Exception.class})
    public void updatePracticeSchedule(ReqDtoUpdatePracticeSchedule practiceScheduleObj) {
        if (!subjectScheduleRepository.existsById(practiceScheduleObj.getUpdatedPracticeScheduleId()))
            throw new NoSuchElementException("Updated Practice Schedule Id not found.");

        //--Get all practice-schedule created from this teacher-request to combine with this updated one.
        List<SubjectSchedule> schedules = subjectScheduleRepository.findAllByTeacherRequestRequestIdToUpdate(
            practiceScheduleObj.getUpdatedPracticeScheduleId(),
            practiceScheduleObj.getRequestId()
        );
        //--Convert it as str-schedule.
        for (SubjectSchedule schedule : schedules) {
            int lastWeek = schedule.getStartingWeek() + schedule.getTotalWeek();
            for (int week = schedule.getStartingWeek(); week < lastWeek; week++) {
                for (int period = schedule.getStartingPeriod(); period <= schedule.getLastPeriod(); period++) {
                    practiceScheduleObj.setPracticeScheduleListAsString(String.format(
                        "%s, week:%s_day:%s_period:%s_roomId:%s",
                        practiceScheduleObj.getPracticeScheduleListAsString(),
                        week, schedule.getDay(), period, schedule.getClassroom().getRoomId()
                    ));
                }
            }
        }

        //--May throw SQLException.
        subjectScheduleRepository.deleteAllByTeacherRequestRequestId(practiceScheduleObj.getRequestId());

        //--Add these updated practice-schedules again.
        this.addPracticeSchedule(practiceScheduleObj);
    }

    public void deletePracticeSchedule(String practiceScheduleId) throws SQLIntegrityConstraintViolationException {
        //--May throw NumberFormatException
        Long id = Long.parseLong(practiceScheduleId);

        SubjectSchedule deletedSubjectSchedule = subjectScheduleRepository
            .findById(id).orElseThrow(() -> new NoSuchElementException("Schedule Id not found!"));

        int totalSchedulesQuantityOfTeacherRequest = subjectScheduleRepository.countByTeacherRequestRequestId(
            deletedSubjectSchedule.getTeacherRequest().getRequestId()
        );
        if (totalSchedulesQuantityOfTeacherRequest == 1)
            throw new SQLIntegrityConstraintViolationException("error_schedule_02");

        Semester currentSemester = semesterRepository
            .findByCurrentDate(new java.sql.Date(System.currentTimeMillis()))
            .orElseThrow(() -> new NoSuchElementException("There no semester data for this current date"));

        Optional<SubjectSchedule> firstSchedule = subjectScheduleRepository
            .findFirstBySubjectScheduleIdAndSemester(RoomType.PRC, id, currentSemester);

        if (firstSchedule.isPresent()) {
            Date startingDate = getFirstSqlDateOfSchedule(firstSchedule.get(), currentSemester);

            //--If the starting-date of this first practice-schedule happened before this current-date.
            if (new Date(System.currentTimeMillis()).after(startingDate))
                throw new SQLIntegrityConstraintViolationException("error_schedule_03");
        }

        subjectScheduleRepository.deleteById(id);
    }

    @NotNull
    public static Date getFirstSqlDateOfSchedule(SubjectSchedule schedule, Semester currentSemester) {
        //--Total weeks after the first week of current-semester to now-date.
        int totalWeekFromCurrentSemester = schedule.getStartingWeek() - currentSemester.getFirstWeek();

        //--Total dates, from starting-date of current-semester, to starting-date of schedule .
        int totalDatesToFistPracticeDate = (totalWeekFromCurrentSemester * 7) + (schedule.getDay() - 2);

        //--Get java.sql.Date object from the starting-date of this first practice-schedule.
        long startingSemesterDateAsMillis = currentSemester.getStartingDate().getTime();

        return new Date(startingSemesterDateAsMillis + (long) totalDatesToFistPracticeDate *24*60*60*1000);
    }
}
