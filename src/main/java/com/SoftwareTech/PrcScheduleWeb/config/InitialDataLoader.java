package com.SoftwareTech.PrcScheduleWeb.config;

import com.SoftwareTech.PrcScheduleWeb.model.enums.EntityInteractionStatus;
import com.SoftwareTech.PrcScheduleWeb.model.enums.Gender;
import com.SoftwareTech.PrcScheduleWeb.model.enums.Role;
import com.SoftwareTech.PrcScheduleWeb.model.*;
import com.SoftwareTech.PrcScheduleWeb.model.enums.RoomType;
import com.SoftwareTech.PrcScheduleWeb.repository.*;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private final SectionClassRepository sectionClassRepository;
    @Autowired
    private final ComputerRoomDetailRepository computerRoomDetailRepository;
    @Autowired
    private final ClassroomRepository classroomRepository;
    @Autowired
    private final SubjectScheduleRepository subjectScheduleRepository;
    @Autowired
    private final TeacherRequestRepository teacherRequestRepository;
    @Autowired
    private final StudentRepository studentRepository;
    @Autowired
    private final SubjectRegistrationRepository subjectRegistrationRepository;

    @Override
    public void run(String... args) {
        if (accountRepository.count() == 0) {
            accountRepository.saveAll(List.of(
                Account.builder()
                    .status(true)
                    .creatingTime(LocalDateTime.now())
                    .instituteEmail("manager0@ptithcm.edu.vn")
                    .password("$2a$12$dzSEHUe6lixG0EkEzrcQfuV18XLaZpvDvF9apXe9.9PigXDgGw9p.")
                    .role(Role.MANAGER)
                    .build(),
                Account.builder()
                    .status(true)
                    .creatingTime(LocalDateTime.now())
                    .instituteEmail("giangvien0@ptithcm.edu.vn")
                    .password("$2a$12$dzSEHUe6lixG0EkEzrcQfuV18XLaZpvDvF9apXe9.9PigXDgGw9p.")
                    .role(Role.TEACHER)
                    .build(),
                Account.builder()
                    .status(true)
                    .creatingTime(LocalDateTime.now())
                    .instituteEmail("giangvien1@ptithcm.edu.vn")
                    .password("$2a$12$dzSEHUe6lixG0EkEzrcQfuV18XLaZpvDvF9apXe9.9PigXDgGw9p.")
                    .role(Role.TEACHER)
                    .build()
            ));
        }
        if (classroomRepository.count() == 0) {
            classroomRepository.saveAll(List.of(
                Classroom.builder()
                    .roomId("2B11")
                    .roomType(RoomType.PRC)
                    .maxQuantity(60)
                    .status(true)
                    .build(),
                Classroom.builder()
                    .roomId("2B21")
                    .roomType(RoomType.PRC)
                    .maxQuantity(60)
                    .status(true)
                    .build(),
                Classroom.builder()
                    .roomId("2B12")
                    .roomType(RoomType.NORM)
                    .maxQuantity(60)
                    .status(true)
                    .build(),
                Classroom.builder()
                    .roomId("2B13")
                    .roomType(RoomType.NORM)
                    .maxQuantity(60)
                    .status(true)
                    .build()
            ));
            computerRoomDetailRepository.saveAll(List.of(
                ComputerRoomDetail.builder()
                    .classroom(classroomRepository.findById("2B11").orElseThrow())
                    .maxComputerQuantity(30)
                    .availableComputerQuantity(30)
                    .build(),
                ComputerRoomDetail.builder()
                    .classroom(classroomRepository.findById("2B21").orElseThrow())
                    .maxComputerQuantity(30)
                    .availableComputerQuantity(30)
                    .build()
            ));
        }
        if (semesterRepository.count() == 0) {
            semesterRepository.saveAll(List.of(
                Semester.builder()
                    .semester((byte) 2)
                    .rangeOfYear("2023_2024")
                    .firstWeek((byte) 1)
                    .totalWeek((byte) 28)
                    .startingDate(Date.valueOf(LocalDate.of(2024, 1, 8)))
                    .build()
            ));
        }
        if (subjectRepository.count() == 0) {
            subjectRepository.saveAll(List.of(
                Subject.builder().subjectId("INT13147").subjectName("Python").creditsNumber((byte) 3).status(true).build(),
                Subject.builder().subjectId("INT13148").subjectName("Hệ điều hành").creditsNumber((byte) 3).status(true).build()
            ));
        }
        if (departmentRepository.count() == 0) {
            departmentRepository.saveAll(List.of(
                Department.builder().departmentId("CNTT02").departmentName("Công nghệ thông tin 02").build(),
                Department.builder().departmentId("DTVT02").departmentName("Điện tử 02").build()
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
                    .build(),
                Grade.builder()
                    .gradeId("D21CQCN02-N")
                    .department(departmentRepository.findById("CNTT02").orElseThrow())
                    .build()
            ));
            sectionClassRepository.saveAll(List.of(
                SectionClass.builder()
                    .groupFromSubject((byte) 1)
                    .grade(gradeRepository.findById("D21CQCN01-N").orElseThrow())
                    .subject(subjectRepository.findById("INT13147").orElseThrow())
                    .semester(semesterRepository
                        .findBySemesterAndRangeOfYear((byte) 2, "2023_2024").orElseThrow())
                    .build(),
                SectionClass.builder()
                    .groupFromSubject((byte) 2)
                    .grade(gradeRepository.findById("D21CQCN01-N").orElseThrow())
                    .subject(subjectRepository.findById("INT13147").orElseThrow())
                    .semester(semesterRepository
                        .findBySemesterAndRangeOfYear((byte) 2, "2023_2024").orElseThrow())
                    .build(),
                SectionClass.builder()
                    .groupFromSubject((byte) 1)
                    .grade(gradeRepository.findById("D21CQCN01-N").orElseThrow())
                    .subject(subjectRepository.findById("INT13148").orElseThrow())
                    .semester(semesterRepository
                        .findBySemesterAndRangeOfYear((byte) 2, "2023_2024").orElseThrow())
                    .build(),
                SectionClass.builder()
                    .groupFromSubject((byte) 1)
                    .grade(gradeRepository.findById("D21CQCN02-N").orElseThrow())
                    .subject(subjectRepository.findById("INT13148").orElseThrow())
                    .semester(semesterRepository
                        .findBySemesterAndRangeOfYear((byte) 2, "2023_2024").orElseThrow())
                    .build()
            ));
            subjectScheduleRepository.saveAll(List.of(
                SubjectSchedule.builder()
                    .sectionClass(sectionClassRepository.findById(1L).orElseThrow())
                    .day((byte) 2)
                    .startingWeek((byte) 2)
                    .totalWeek((byte) 2)
                    .startingPeriod((byte) 1)
                    .lastPeriod((byte) 4)
                    .classroom(classroomRepository.findById("2B12").orElseThrow())
                    .teacher(teacherRepository.findById("GV111").orElseThrow())
                    .teacherRequest(null)
                    .build(),
                SubjectSchedule.builder()
                    .sectionClass(sectionClassRepository.findById(1L).orElseThrow())
                    .day((byte) 2)
                    .startingWeek((byte) 7)
                    .totalWeek((byte) 9)
                    .startingPeriod((byte) 1)
                    .lastPeriod((byte) 4)
                    .classroom(classroomRepository.findById("2B12").orElseThrow())
                    .teacher(teacherRepository.findById("GV111").orElseThrow())
                    .teacherRequest(null)
                    .build(),
                SubjectSchedule.builder()
                    .sectionClass(sectionClassRepository.findById(1L).orElseThrow())
                    .day((byte) 3)
                    .startingWeek((byte) 10)
                    .totalWeek((byte) 3)
                    .startingPeriod((byte) 1)
                    .lastPeriod((byte) 4)
                    .classroom(classroomRepository.findById("2B11").orElseThrow())
                    .teacher(teacherRepository.findById("GV111").orElseThrow())
                    .teacherRequest(null)
                    .build(),
                SubjectSchedule.builder()
                    .sectionClass(sectionClassRepository.findById(2L).orElseThrow())
                    .day((byte) 2)
                    .startingWeek((byte) 7)
                    .totalWeek((byte) 9)
                    .startingPeriod((byte) 1)
                    .lastPeriod((byte) 4)
                    .classroom(classroomRepository.findById("2B12").orElseThrow())
                    .teacher(teacherRepository.findById("GV112").orElseThrow())
                    .teacherRequest(null)
                    .build(),
                SubjectSchedule.builder()
                    .sectionClass(sectionClassRepository.findById(3L).orElseThrow())
                    .day((byte) 5)
                    .startingWeek((byte) 7)
                    .totalWeek((byte) 11)
                    .startingPeriod((byte) 7)
                    .lastPeriod((byte) 10)
                    .classroom(classroomRepository.findById("2B13").orElseThrow())
                    .teacher(teacherRepository.findById("GV111").orElseThrow())
                    .teacherRequest(null)
                    .build(),
                SubjectSchedule.builder()
                    .sectionClass(sectionClassRepository.findById(4L).orElseThrow())
                    .day((byte) 5)
                    .startingWeek((byte) 13)
                    .totalWeek((byte) 11)
                    .startingPeriod((byte) 7)
                    .lastPeriod((byte) 10)
                    .classroom(classroomRepository.findById("2B11").orElseThrow())
                    .teacher(teacherRepository.findById("GV112").orElseThrow())
                    .teacherRequest(null)
                    .build()
            ));
            teacherRequestRepository.saveAll(List.of(
                TeacherRequest.builder()
                    .requestMessageDetail("Quản lý tạo cho em 4 tuần, 1 buổi 1 tuần, 4 tiết 1 buổi nhé, ưu tiên thứ 2,3,6")
                    .interactRequestReason(null)
                    .interactionStatus(EntityInteractionStatus.PENDING)
                    .updatingTime(LocalDateTime.now())
                    .build()
            ));
            subjectScheduleRepository.save(SubjectSchedule.builder()
                    .sectionClass(sectionClassRepository.findById(1L).orElseThrow())
                    .day(null)
                    .startingWeek(null)
                    .totalWeek(null)
                    .startingPeriod(null)
                    .lastPeriod(null)
                    .classroom(null)
                    .teacher(teacherRepository.findById("GV111").orElseThrow())
                    .teacherRequest(teacherRequestRepository.findById(1L).orElseThrow())
                    .build()
            );
        }
        if (studentRepository.count() == 0) {
            Grade cn1Grade = gradeRepository.findById("D21CQCN01-N").orElseThrow();
            Grade cn2Grade = gradeRepository.findById("D21CQCN02-N").orElseThrow();
            studentRepository.saveAll(List.of(
                Student.builder().studentId("N20DCCN001").grade(cn1Grade).lastName("Nguyễn Hồng").firstName("Nguyễn").gender(Gender.BOY).instituteEmail("N20DCCN001@gmail.edu.com").build(),
                Student.builder().studentId("N20DCCN002").grade(cn1Grade).lastName("Trần Hoàng").firstName("Trần").gender(Gender.GIRL).instituteEmail("N20DCCN002@gmail.edu.com").build(),
                Student.builder().studentId("N20DCCN003").grade(cn1Grade).lastName("Lê Nghiêm").firstName("Lê").gender(Gender.BOY).instituteEmail("N20DCCN003@gmail.edu.com").build(),
                Student.builder().studentId("N20DCCN004").grade(cn1Grade).lastName("Phạm Lương").firstName("Phạm").gender(Gender.BOY).instituteEmail("N20DCCN004@gmail.edu.com").build(),
                Student.builder().studentId("N20DCCN005").grade(cn1Grade).lastName("Hoàng Nhật").firstName("Hoàng").gender(Gender.GIRL).instituteEmail("N20DCCN005@gmail.edu.com").build(),
                Student.builder().studentId("N20DCCN006").grade(cn1Grade).lastName("Huỳnh Hồng").firstName("Huỳnh").gender(Gender.BOY).instituteEmail("N20DCCN006@gmail.edu.com").build(),
                Student.builder().studentId("N20DCCN007").grade(cn1Grade).lastName("Phan Đặng").firstName("Phan").gender(Gender.BOY).instituteEmail("N20DCCN007@gmail.edu.com").build(),
                Student.builder().studentId("N20DCCN008").grade(cn1Grade).lastName("Võ Đồng").firstName("Võ").gender(Gender.GIRL).instituteEmail("N20DCCN008@gmail.edu.com").build(),
                Student.builder().studentId("N20DCCN009").grade(cn1Grade).lastName("Đặng Phùng").firstName("Đặng").gender(Gender.BOY).instituteEmail("N20DCCN009@gmail.edu.com").build(),
                Student.builder().studentId("N20DCCN010").grade(cn1Grade).lastName("Bùi Hồng").firstName("Bùi").gender(Gender.GIRL).instituteEmail("N20DCCN010@gmail.edu.com").build(),
                Student.builder().studentId("N20DCCN011").grade(cn1Grade).lastName("Nguyễn Thành").firstName("Đỗ").gender(Gender.BOY).instituteEmail("N20DCCN011@gmail.edu.com").build(),
                Student.builder().studentId("N20DCCN012").grade(cn2Grade).lastName("Trần Thuyết").firstName("Hồ").gender(Gender.BOY).instituteEmail("N20DCCN012@gmail.edu.com").build(),
                Student.builder().studentId("N20DCCN013").grade(cn2Grade).lastName("Lê Tiết").firstName("Dương").gender(Gender.GIRL).instituteEmail("N20DCCN013@gmail.edu.com").build(),
                Student.builder().studentId("N20DCCN014").grade(cn2Grade).lastName("Phạm Đỗ").firstName("Lý").gender(Gender.BOY).instituteEmail("N20DCCN014@gmail.edu.com").build(),
                Student.builder().studentId("N20DCCN015").grade(cn2Grade).lastName("Hoàng Lâm").firstName("Phùng").gender(Gender.BOY).instituteEmail("N20DCCN015@gmail.edu.com").build(),
                Student.builder().studentId("N20DCCN016").grade(cn2Grade).lastName("Huỳnh Vĩnh").firstName("Vũ").gender(Gender.GIRL).instituteEmail("N20DCCN016@gmail.edu.com").build(),
                Student.builder().studentId("N20DCCN017").grade(cn2Grade).lastName("Phan Châu").firstName("Trịnh").gender(Gender.BOY).instituteEmail("N20DCCN017@gmail.edu.com").build(),
                Student.builder().studentId("N20DCCN018").grade(cn2Grade).lastName("Võ Sơn").firstName("Đinh").gender(Gender.GIRL).instituteEmail("N20DCCN018@gmail.edu.com").build(),
                Student.builder().studentId("N20DCCN019").grade(cn2Grade).lastName("Đặng Tôn").firstName("Trương").gender(Gender.BOY).instituteEmail("N20DCCN019@gmail.edu.com").build(),
                Student.builder().studentId("N20DCCN020").grade(cn2Grade).lastName("Bùi Tưởng").firstName("Lương").gender(Gender.BOY).instituteEmail("N20DCCN020@gmail.edu.com").build(),
                Student.builder().studentId("N20DCCN021").grade(cn2Grade).lastName("Nguyễn Hồng").firstName("Mai").gender(Gender.GIRL).instituteEmail("N20DCCN021@gmail.edu.com").build(),
                Student.builder().studentId("N20DCCN022").grade(cn2Grade).lastName("Trần Hoàng").firstName("Tô").gender(Gender.BOY).instituteEmail("N20DCCN022@gmail.edu.com").build(),
                Student.builder().studentId("N20DCCN023").grade(cn2Grade).lastName("Lê Nghiêm").firstName("Dịch").gender(Gender.BOY).instituteEmail("N20DCCN023@gmail.edu.com").build()
            ));
            subjectScheduleRepository.saveAll(List.of(

            ));
        }
    }
}