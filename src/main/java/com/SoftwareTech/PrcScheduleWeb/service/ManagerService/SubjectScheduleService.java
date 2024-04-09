package com.SoftwareTech.PrcScheduleWeb.service.ManagerService;

import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.ReqDtoPracticeSchedule;
import com.SoftwareTech.PrcScheduleWeb.model.*;
import com.SoftwareTech.PrcScheduleWeb.model.enums.DBInteraction;
import com.SoftwareTech.PrcScheduleWeb.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SubjectScheduleService {
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
    private final PracticeScheduleInteractHistoryRepository scheduleHistoryRepository;

    @Transactional(rollbackOn = {Exception.class})
    public void addPracticeSchedule(ReqDtoPracticeSchedule practiceScheduleObj) throws SQLException {
        //--Prepare data to add into practice-schedule.
        SectionClass sectionClass = sectionClassRepository
            .findById(practiceScheduleObj.getSectionClassId())
            .orElseThrow(() -> new NoSuchElementException("Request Id is invalid"));
        Teacher teacher = teacherRepository
            .findById(practiceScheduleObj.getTeacherId())
            .orElseThrow(() -> new NoSuchElementException("Teacher Id is invalid"));
        HashMap<String, Classroom> classroomObjects = new HashMap<>();

        ArrayList<HashMap<String, String>> extractedPlainData = new ArrayList<>();
        //--Split the plain-data, which is separated by ", " string.
        String[] practiceScheduleRows = practiceScheduleObj.getPracticeScheduleListAsString().split(", ");
        //--Loop through the split plain-data result. Example: ["week:10_day:2_period:3_roomId:2B11", ...].
        for (String row: practiceScheduleRows) {
            HashMap<String, String> currentDataFields = new HashMap<>();
            String[] tempSplitDataField;
            //--Extract data as: ["field:data", ...].
            for (String plainDataField: row.split("_")) {
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
        extractedPlainData.sort(Comparator.comparing(a -> a.get("week")));
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
                .status(true)
                .build();
            //--Combining the period in a 'day' of a 'week'.
            for (index = index + 1; index < extractedPlainData.size(); index++) {
                if (extractedPlainData.get(index).get("roomId").equals(practiceSchedule.getClassroom().getRoomId())
                    &&  extractedPlainData.get(index).get("day").equals(Byte.toString(practiceSchedule.getDay()))
                    &&  extractedPlainData.get(index).get("week").equals(Byte.toString(practiceSchedule.getStartingWeek()))
                    &&  Byte.parseByte(extractedPlainData.get(index).get("period")) - practiceSchedule.getLastPeriod() == 1
                ) {
                    practiceSchedule.setLastPeriod((byte) (practiceSchedule.getLastPeriod() + 1));
                } else {
                    index--;
                    break;
                }
            }
            //--Combining the practice-schedule similar to each other with the compatible 'week'.
            if (!combinedPracticeSchedule.isEmpty()
            &&  combinedPracticeSchedule.getLast().canBeCombined(practiceSchedule)) {
                combinedPracticeSchedule.getLast().setTotalWeek(
                    (byte) (combinedPracticeSchedule.getLast().getTotalWeek() + practiceSchedule.getTotalWeek())
                );
            }
            //--If it's not compatible, save it into main-result as a new practice-schedule.
            else    combinedPracticeSchedule.add(practiceSchedule);
        }
        //--Save the main-result into DB.
        //--May throw DataIntegrityViolationException because of custom Trigger.
        subjectScheduleRepository.saveAll(combinedPracticeSchedule);
        //--Create histories list to store into DB.
        List<PracticeScheduleInteractionHistory> histories = new ArrayList<>();
        LocalDateTime nowDateTime = LocalDateTime.now();
        TeacherRequest teacherRequest = teacherRequestRepository
            .findById(practiceScheduleObj.getRequestId())
            .orElseThrow(() -> new NoSuchElementException("Request Id is invalid!"));
        //--Loop through each PracticeSchedule we have just created.
        for (SubjectSchedule practiceSchedule: combinedPracticeSchedule) {
            histories.add(PracticeScheduleInteractionHistory.builder()
                .subjectSchedule(practiceSchedule)
                .teacherRequest(teacherRequest)
                .creatingTime(nowDateTime)
                .interactType(DBInteraction.ADD)
                .build()
            );
        }
        //--Save the practice-schedule-interaction history.
        scheduleHistoryRepository.saveAll(histories);
    }
}
