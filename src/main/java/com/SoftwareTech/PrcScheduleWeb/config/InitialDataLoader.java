package com.SoftwareTech.PrcScheduleWeb.config;

import com.SoftwareTech.PrcScheduleWeb.model.enums.Gender;
import com.SoftwareTech.PrcScheduleWeb.model.enums.Role;
import com.SoftwareTech.PrcScheduleWeb.model.*;
import com.SoftwareTech.PrcScheduleWeb.repository.*;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitialDataLoader implements CommandLineRunner {
    @Autowired
    private final AccountRepository accountRepository;
    @Autowired
    private final DepartmentRepository departmentRepository;
    @Autowired
    private final GradeRepository gradeRepository;
    @Autowired
    private final SubjectRepository subjectRepository;
    @Autowired
    private final SemesterRepository semesterRepository;
    @Autowired
    private final TeacherRepository teacherRepository;
    @Autowired
    private final SubjectDetailRepository subjectDetailRepository;
    @Autowired
    private final PracticeScheduleRepository practiceScheduleRepository;
    @Autowired
    private final ComputerRoomRepository computerRoomRepository;

    @Override
    public void run(String... args) {
        if (accountRepository.count() == 0) {
            accountRepository.saveAll(List.of(
                Account.builder()
                    .status(true)
                    .creatingTime(new Timestamp(System.currentTimeMillis()))
                    .instituteEmail("manager0@ptithcm.edu.vn")
                    .password("$2a$12$dzSEHUe6lixG0EkEzrcQfuV18XLaZpvDvF9apXe9.9PigXDgGw9p.")
                    .role(Role.MANAGER)
                    .build(),
                Account.builder()
                    .status(true)
                    .creatingTime(new Timestamp(System.currentTimeMillis()))
                    .instituteEmail("giangvien0@ptithcm.edu.vn")
                    .password("$2a$12$dzSEHUe6lixG0EkEzrcQfuV18XLaZpvDvF9apXe9.9PigXDgGw9p.")
                    .role(Role.TEACHER)
                    .build(),
                Account.builder()
                    .status(true)
                    .creatingTime(new Timestamp(System.currentTimeMillis()))
                    .instituteEmail("giangvien1@ptithcm.edu.vn")
                    .password("$2a$12$dzSEHUe6lixG0EkEzrcQfuV18XLaZpvDvF9apXe9.9PigXDgGw9p.")
                    .role(Role.TEACHER)
                    .build()
            ));
        }
        if (computerRoomRepository.count() == 0) {
            computerRoomRepository.saveAll(List.of(
                ComputerRoom.builder()
                    .computerRoom("2B11")
                    .maxComputerQuantity(30)
                    .availableComputerQuantity(30)
                    .status(true)
                    .build()
            ));
        }
        if (semesterRepository.count() == 0) {
            semesterRepository.saveAll(List.of(
                Semester.builder()
                    .semester((byte)3)
                    .rangeOfYear("2022_2023")
                    .fistWeek((byte)1)
                    .totalWeek((byte)28)
                    .startingDate(Date.valueOf(LocalDate.of(2022, 1, 8)))
                    .build()
            ));
        }
        if (subjectRepository.count() == 0) {
            subjectRepository.saveAll(List.of(
                Subject.builder().subjectId("INT13147").subjectName("Python").creditsNumber((byte)3).status(true).build()
            ));
        }
        if (departmentRepository.count() == 0) {
            departmentRepository.saveAll(List.of(
                Department.builder().departmentId("CNTT02").status(true).build(),
                Department.builder().departmentId("DTVT02").status(true).build()
            ));
            if (teacherRepository.count() == 0) {
                teacherRepository.saveAll(List.of(
                    Teacher.builder()
                        .teacherId("GV111")
                        .department(departmentRepository.findById("CNTT02").orElseThrow())
                        .lastName("Dinh Van")
                        .firstName("Han")
                        .birthday(Date.valueOf(LocalDate.of(1991, 3, 25)))
                        .gender(Gender.BOY)
                        .phone("0377869998")
                        .account(accountRepository.findByInstituteEmail("giangvien0@ptithcm.edu.vn").orElseThrow())
                        .build(),
                    Teacher.builder()
                        .teacherId("GV112")
                        .department(departmentRepository.findById("CNTT02").orElseThrow())
                        .lastName("Nguyen Thi")
                        .firstName("Huong")
                        .birthday(Date.valueOf(LocalDate.of(1991, 3, 25)))
                        .gender(Gender.GIRL)
                        .phone("0377869999")
                        .account(accountRepository.findByInstituteEmail("giangvien1@ptithcm.edu.vn").orElseThrow())
                        .build()
                ));
            }
            gradeRepository.saveAll(List.of(
                Grade.builder()
                    .gradeId("D21CQCN01-N")
                    .department(departmentRepository.findById("CNTT02").orElseThrow())
                    .status(true)
                    .build()
            ));
            subjectDetailRepository.saveAll(List.of(
                SubjectDetail.builder()
                    .groupFromSubject((byte)1)
                    .grade(gradeRepository.findById("D21CQCN01-N").orElseThrow())
                    .subject(subjectRepository.findById("INT13147").orElseThrow())
                    .semester(semesterRepository
                        .findBySemesterAndRangeOfYear((byte)3, "2022_2023").orElseThrow())
                    .build()
            ));
            practiceScheduleRepository.saveAll(List.of(
                PracticeSchedule.builder()
                    .subjectDetail(subjectDetailRepository.findById(1L).orElseThrow())
                    .day((byte)2)
                    .startingWeek((byte)2)
                    .totalWeek((byte)11)
                    .startingPeriod((byte)1)
                    .lastPeriod((byte)4)
                    .computerRoom(computerRoomRepository.findById("2B11").orElseThrow())
                    .teacher(teacherRepository.findById("GV111").orElseThrow())
                    .status(true)
                    .build()
            ));
        }
    }
}