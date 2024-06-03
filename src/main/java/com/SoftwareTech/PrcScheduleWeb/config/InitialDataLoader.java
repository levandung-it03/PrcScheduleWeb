package com.SoftwareTech.PrcScheduleWeb.config;

import com.SoftwareTech.PrcScheduleWeb.model.enums.EntityInteractionStatus;
import com.SoftwareTech.PrcScheduleWeb.model.enums.Gender;
import com.SoftwareTech.PrcScheduleWeb.model.enums.Role;
import com.SoftwareTech.PrcScheduleWeb.model.*;
import com.SoftwareTech.PrcScheduleWeb.model.enums.RoomType;
import com.SoftwareTech.PrcScheduleWeb.repository.*;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @Autowired
    private final Logger logger;

    @Override
    public void run(String... args) {
        if (accountRepository.count() == 0) {
            List<Account> accounts = List.of(
                Account.builder()
                    .status(true).creatingTime(LocalDateTime.now())
                    .instituteEmail("manager0@ptithcm.edu.vn")
                    .password("$2a$12$dzSEHUe6lixG0EkEzrcQfuV18XLaZpvDvF9apXe9.9PigXDgGw9p.")
                    .role(Role.MANAGER).build(),
                Account.builder()
                    .status(true).creatingTime(LocalDateTime.now())
                    .instituteEmail("giangvien0@ptithcm.edu.vn")
                    .password("$2a$12$dzSEHUe6lixG0EkEzrcQfuV18XLaZpvDvF9apXe9.9PigXDgGw9p.")
                    .role(Role.TEACHER).build(),
                Account.builder()
                    .status(true).creatingTime(LocalDateTime.now())
                    .instituteEmail("giangvien1@ptithcm.edu.vn")
                    .password("$2a$12$dzSEHUe6lixG0EkEzrcQfuV18XLaZpvDvF9apXe9.9PigXDgGw9p.")
                    .role(Role.TEACHER).build(),
                Account.builder()
                    .status(true).creatingTime(LocalDateTime.now())
                    .instituteEmail("giangvien2@ptithcm.edu.vn")
                    .password("$2a$12$dzSEHUe6lixG0EkEzrcQfuV18XLaZpvDvF9apXe9.9PigXDgGw9p.")
                    .role(Role.TEACHER).build()
            );
            accountRepository.saveAll(accounts);
        }
        if (classroomRepository.count() == 0) {
            List<Classroom> classrooms = List.of(
                Classroom.builder().roomId("2B11").roomType(RoomType.PRC).maxQuantity(60).status(true).build(),
                Classroom.builder().roomId("2B21").roomType(RoomType.PRC).maxQuantity(60).status(true).build(),
                Classroom.builder().roomId("2B12").roomType(RoomType.NORM).maxQuantity(60).status(true).build(),
                Classroom.builder().roomId("2B13").roomType(RoomType.NORM).maxQuantity(60).status(true).build()
            );
            classroomRepository.saveAll(classrooms);
            computerRoomDetailRepository.saveAll(List.of(
                ComputerRoomDetail.builder().classroom(classrooms.getFirst()).maxComputerQuantity(30).availableComputerQuantity(30).build(),
                ComputerRoomDetail.builder().classroom(classrooms.get(1)).maxComputerQuantity(30).availableComputerQuantity(30).build()
            ));
            if (semesterRepository.count() == 0) {
                Semester semester = Semester.builder()
                    .semester((byte) 2).rangeOfYear("2023_2024").firstWeek((byte) 1).totalWeek((byte) 28)
                    .startingDate(Date.valueOf(LocalDate.of(2024, 1, 8))).build();
                semesterRepository.save(semester);
                List<Subject> subjects = List.of(
                    Subject.builder().subjectId("INT13147").subjectName("Python").creditsNumber((byte) 3).status(true).build(),
                    Subject.builder().subjectId("INT13148").subjectName("Hệ điều hành").creditsNumber((byte) 3).status(true).build(),
                    Subject.builder().subjectId("INT13149").subjectName("Mạng máy tính").creditsNumber((byte) 3).status(true).build(),
                    Subject.builder().subjectId("INT13150").subjectName("Đại số").creditsNumber((byte) 3).status(true).build()
                );
                subjectRepository.saveAll(subjects);
                if (departmentRepository.count() == 0) {
                    List<Department> departments = List.of(
                        Department.builder().departmentId("CNTT02").departmentName("Công nghệ thông tin 02").build(),
                        Department.builder().departmentId("DTVT02").departmentName("Điện tử 02").build()
                    );
                    departmentRepository.saveAll(departments);
                    List<Teacher> teachers = List.of(
                        Teacher.builder()
                            .teacherId("GV111")
                            .department(departments.getFirst())
                            .lastName("Dinh Van")
                            .firstName("Han")
                            .birthday(Date.valueOf(LocalDate.of(1991, 3, 25)))
                            .gender(Gender.BOY)
                            .phone("0377869998")
                            .account(accountRepository.findByInstituteEmail("giangvien0@ptithcm.edu.vn").orElseThrow())
                            .build(),
                        Teacher.builder()
                            .teacherId("GV112")
                            .department(departments.getFirst())
                            .lastName("Nguyen Thi")
                            .firstName("Huong")
                            .birthday(Date.valueOf(LocalDate.of(1991, 3, 25)))
                            .gender(Gender.GIRL)
                            .phone("0377869999")
                            .account(accountRepository.findByInstituteEmail("giangvien1@ptithcm.edu.vn").orElseThrow())
                            .build(),
                        Teacher.builder()
                            .teacherId("GV113")
                            .department(departments.get(1))
                            .lastName("Nguyen Thi")
                            .firstName("Van")
                            .birthday(Date.valueOf(LocalDate.of(1991, 3, 25)))
                            .gender(Gender.GIRL)
                            .phone("0377862345")
                            .account(accountRepository.findByInstituteEmail("giangvien2@ptithcm.edu.vn").orElseThrow())
                            .build()
                    );
                    teacherRepository.saveAll(teachers);
                    List<Grade> grades = List.of(
                        Grade.builder().gradeId("D21CQCN01-N").department(departments.getFirst()).build(),
                        Grade.builder().gradeId("D21CQCN02-N").department(departments.getFirst()).build(),
                        Grade.builder().gradeId("D21CQDT01-N").department(departments.get(1)).build()
                    );
                    gradeRepository.saveAll(grades);
                    List<SectionClass> sectionClasses = List.of(
                        //--D21CN01
                        SectionClass.builder().groupFromSubject((byte) 1).grade(grades.getFirst())
                            .subject(subjects.getFirst()).semester(semester).build(),
                        SectionClass.builder().groupFromSubject((byte) 2).grade(grades.getFirst())
                            .subject(subjects.getFirst()).semester(semester).build(),
                        SectionClass.builder().groupFromSubject((byte) 1).grade(grades.getFirst())
                            .subject(subjects.get(1)).semester(semester).build(),
                        SectionClass.builder().groupFromSubject((byte) 2).grade(grades.getFirst())
                            .subject(subjects.get(1)).semester(semester).build(),
                        SectionClass.builder().groupFromSubject((byte) 1).grade(grades.getFirst())
                            .subject(subjects.get(2)).semester(semester).build(),
                        SectionClass.builder().groupFromSubject((byte) 1).grade(grades.getFirst())
                            .subject(subjects.get(3)).semester(semester).build(),
                        //--D21CN02
                        SectionClass.builder().groupFromSubject((byte) 1).grade(grades.get(1))
                            .subject(subjects.getFirst()).semester(semester).build(),
                        SectionClass.builder().groupFromSubject((byte) 2).grade(grades.get(1))
                            .subject(subjects.getFirst()).semester(semester).build(),
                        SectionClass.builder().groupFromSubject((byte) 1).grade(grades.get(1))
                            .subject(subjects.get(1)).semester(semester).build(),
                        SectionClass.builder().groupFromSubject((byte) 2).grade(grades.get(1))
                            .subject(subjects.get(1)).semester(semester).build(),
                        //--D21DT01
                        SectionClass.builder().groupFromSubject((byte) 1).grade(grades.get(2))
                            .subject(subjects.get(2)).semester(semester).build()
                    );
                    sectionClassRepository.saveAll(sectionClasses);
                    subjectScheduleRepository.saveAll(List.of(
                        //--D21CN01
                        //--Python(NORM) HDH MMT DS
                        SubjectSchedule.builder()
                            .startingWeek((byte) 2).totalWeek((byte) 2).day((byte) 2).startingPeriod((byte) 1).lastPeriod((byte) 4).classroom(classrooms.get(3))
                            .sectionClass(sectionClasses.getFirst()).teacher(teachers.getFirst()).teacherRequest(null).build(),
                        SubjectSchedule.builder()
                            .startingWeek((byte) 5).totalWeek((byte) 9).day((byte) 2).startingPeriod((byte) 1).lastPeriod((byte) 4).classroom(classrooms.get(3))
                            .sectionClass(sectionClasses.getFirst()).teacher(teachers.getFirst()).teacherRequest(null).build(),
                        SubjectSchedule.builder()
                            .startingWeek((byte) 2).totalWeek((byte) 2).day((byte) 2).startingPeriod((byte) 1).lastPeriod((byte) 4).classroom(classrooms.get(3))
                            .sectionClass(sectionClasses.get(1)).teacher(teachers.getFirst()).teacherRequest(null).build(),
                        SubjectSchedule.builder()
                            .startingWeek((byte) 5).totalWeek((byte) 9).day((byte) 2).startingPeriod((byte) 1).lastPeriod((byte) 4).classroom(classrooms.get(3))
                            .sectionClass(sectionClasses.get(1)).teacher(teachers.getFirst()).teacherRequest(null).build(),
                        //--Python(PRC)
                        SubjectSchedule.builder()
                            .startingWeek((byte) 6).totalWeek((byte) 4).day((byte) 2).startingPeriod((byte) 7).lastPeriod((byte) 10).classroom(classrooms.get(1))
                            .sectionClass(sectionClasses.getFirst()).teacher(teachers.getFirst()).teacherRequest(null).build(),
                        SubjectSchedule.builder()
                            .startingWeek((byte) 11).totalWeek((byte) 4).day((byte) 2).startingPeriod((byte) 7).lastPeriod((byte) 10).classroom(classrooms.get(1))
                            .sectionClass(sectionClasses.get(1)).teacher(teachers.getFirst()).teacherRequest(null).build(),
                        //--He Dieu Hanh(NORM)
                        SubjectSchedule.builder()
                            .startingWeek((byte) 2).totalWeek((byte) 2).day((byte) 4).startingPeriod((byte) 1).lastPeriod((byte) 4).classroom(classrooms.get(3))
                            .sectionClass(sectionClasses.get(2)).teacher(teachers.get(1)).teacherRequest(null).build(),
                        SubjectSchedule.builder()
                            .startingWeek((byte) 5).totalWeek((byte) 9).day((byte) 4).startingPeriod((byte) 1).lastPeriod((byte) 4).classroom(classrooms.get(3))
                            .sectionClass(sectionClasses.get(2)).teacher(teachers.get(1)).teacherRequest(null).build(),
                        SubjectSchedule.builder()
                            .startingWeek((byte) 2).totalWeek((byte) 2).day((byte) 4).startingPeriod((byte) 1).lastPeriod((byte) 4).classroom(classrooms.get(3))
                            .sectionClass(sectionClasses.get(3)).teacher(teachers.get(1)).teacherRequest(null).build(),
                        SubjectSchedule.builder()
                            .startingWeek((byte) 5).totalWeek((byte) 9).day((byte) 4).startingPeriod((byte) 1).lastPeriod((byte) 4).classroom(classrooms.get(3))
                            .sectionClass(sectionClasses.get(3)).teacher(teachers.get(1)).teacherRequest(null).build(),
                        //--He Dieu Hanh(PRC)
                        SubjectSchedule.builder()
                            .startingWeek((byte) 18).totalWeek((byte) 4).day((byte) 4).startingPeriod((byte) 7).lastPeriod((byte) 10).classroom(classrooms.get(1))
                            .sectionClass(sectionClasses.get(2)).teacher(teachers.get(1)).teacherRequest(null).build(),
                        SubjectSchedule.builder()
                            .startingWeek((byte) 23).totalWeek((byte) 4).day((byte) 4).startingPeriod((byte) 7).lastPeriod((byte) 10).classroom(classrooms.get(1))
                            .sectionClass(sectionClasses.get(3)).teacher(teachers.get(1)).teacherRequest(null).build(),
                        //--Mang May Tinh(NORM)
                        SubjectSchedule.builder()
                            .startingWeek((byte) 16).totalWeek((byte) 11).day((byte) 5).startingPeriod((byte) 7).lastPeriod((byte) 10).classroom(classrooms.get(3))
                            .sectionClass(sectionClasses.get(4)).teacher(teachers.getFirst()).teacherRequest(null).build(),
                        //--Mang May Tinh(PRC)
                        SubjectSchedule.builder()
                            .startingWeek((byte) 25).totalWeek((byte) 2).day((byte) 6).startingPeriod((byte) 1).lastPeriod((byte) 4).classroom(classrooms.getFirst())
                            .sectionClass(sectionClasses.get(4)).teacher(teachers.get(2)).teacherRequest(null).build(),
                        //--DS(NORM)
                        SubjectSchedule.builder()
                            .startingWeek((byte) 16).totalWeek((byte) 11).day((byte) 5).startingPeriod((byte) 1).lastPeriod((byte) 4).classroom(classrooms.get(3))
                            .sectionClass(sectionClasses.get(5)).teacher(teachers.get(2)).teacherRequest(null).build(),
                        //--D21CN02
                        //--Python(NORM) HDH MMT DS
                        SubjectSchedule.builder()
                            .startingWeek((byte) 2).totalWeek((byte) 2).day((byte) 2).startingPeriod((byte) 1).lastPeriod((byte) 4).classroom(classrooms.get(2))
                            .sectionClass(sectionClasses.get(6)).teacher(teachers.get(1)).teacherRequest(null).build(),
                        SubjectSchedule.builder()
                            .startingWeek((byte) 5).totalWeek((byte) 9).day((byte) 2).startingPeriod((byte) 1).lastPeriod((byte) 4).classroom(classrooms.get(2))
                            .sectionClass(sectionClasses.get(6)).teacher(teachers.get(1)).teacherRequest(null).build(),
                        SubjectSchedule.builder()
                            .startingWeek((byte) 2).totalWeek((byte) 2).day((byte) 2).startingPeriod((byte) 1).lastPeriod((byte) 4).classroom(classrooms.get(2))
                            .sectionClass(sectionClasses.get(7)).teacher(teachers.get(1)).teacherRequest(null).build(),
                        SubjectSchedule.builder()
                            .startingWeek((byte) 5).totalWeek((byte) 9).day((byte) 2).startingPeriod((byte) 1).lastPeriod((byte) 4).classroom(classrooms.get(2))
                            .sectionClass(sectionClasses.get(7)).teacher(teachers.get(1)).teacherRequest(null).build(),
                        //--Python(PRC)
                        SubjectSchedule.builder()
                            .startingWeek((byte) 6).totalWeek((byte) 4).day((byte) 2).startingPeriod((byte) 7).lastPeriod((byte) 10).classroom(classrooms.getFirst())
                            .sectionClass(sectionClasses.get(6)).teacher(teachers.get(1)).teacherRequest(null).build(),
                        SubjectSchedule.builder()
                            .startingWeek((byte) 11).totalWeek((byte) 4).day((byte) 2).startingPeriod((byte) 7).lastPeriod((byte) 10).classroom(classrooms.getFirst())
                            .sectionClass(sectionClasses.get(7)).teacher(teachers.get(1)).teacherRequest(null).build(),
                        //--He Dieu Hanh(NORM)
                        SubjectSchedule.builder()
                            .startingWeek((byte) 2).totalWeek((byte) 2).day((byte) 4).startingPeriod((byte) 1).lastPeriod((byte) 4).classroom(classrooms.get(2))
                            .sectionClass(sectionClasses.get(8)).teacher(teachers.getFirst()).teacherRequest(null).build(),
                        SubjectSchedule.builder()
                            .startingWeek((byte) 5).totalWeek((byte) 9).day((byte) 4).startingPeriod((byte) 1).lastPeriod((byte) 4).classroom(classrooms.get(2))
                            .sectionClass(sectionClasses.get(8)).teacher(teachers.getFirst()).teacherRequest(null).build(),
                        SubjectSchedule.builder()
                            .startingWeek((byte) 2).totalWeek((byte) 2).day((byte) 4).startingPeriod((byte) 1).lastPeriod((byte) 4).classroom(classrooms.get(2))
                            .sectionClass(sectionClasses.get(9)).teacher(teachers.getFirst()).teacherRequest(null).build(),
                        SubjectSchedule.builder()
                            .startingWeek((byte) 5).totalWeek((byte) 9).day((byte) 4).startingPeriod((byte) 1).lastPeriod((byte) 4).classroom(classrooms.get(2))
                            .sectionClass(sectionClasses.get(9)).teacher(teachers.getFirst()).teacherRequest(null).build(),
                        //--He Dieu Hanh(PRC)
                        SubjectSchedule.builder()
                            .startingWeek((byte) 18).totalWeek((byte) 4).day((byte) 4).startingPeriod((byte) 7).lastPeriod((byte) 10).classroom(classrooms.getFirst())
                            .sectionClass(sectionClasses.get(8)).teacher(teachers.getFirst()).teacherRequest(null).build(),
                        SubjectSchedule.builder()
                            .startingWeek((byte) 23).totalWeek((byte) 4).day((byte) 4).startingPeriod((byte) 7).lastPeriod((byte) 10).classroom(classrooms.getFirst())
                            .sectionClass(sectionClasses.get(9)).teacher(teachers.getFirst()).teacherRequest(null).build(),
                        //--D21DT01
                        //--Mang May Tinh(NORM)
                        SubjectSchedule.builder()
                            .startingWeek((byte) 16).totalWeek((byte) 11).day((byte) 5).startingPeriod((byte) 7).lastPeriod((byte) 10).classroom(classrooms.get(3))
                            .sectionClass(sectionClasses.get(10)).teacher(teachers.get(2)).teacherRequest(null).build(),
                        //--Mang May Tinh(PRC)
                        SubjectSchedule.builder()
                            .startingWeek((byte) 18).totalWeek((byte) 8).day((byte) 6).startingPeriod((byte) 1).lastPeriod((byte) 4).classroom(classrooms.get(1))
                            .sectionClass(sectionClasses.get(10)).teacher(teachers.get(2)).teacherRequest(null).build()

                    ));
                    List<TeacherRequest> teacherRequests = List.of(
                        TeacherRequest.builder()
                            .requestMessageDetail("Quản lý tạo cho em 4 tuần, 1 buổi 1 tuần, 4 tiết 1 buổi nhé, ưu tiên thứ 2,3,6")
                            .interactRequestReason(null)
                            .interactionStatus(EntityInteractionStatus.PENDING)
                            .updatingTime(LocalDateTime.now())
                            .build(),
                        TeacherRequest.builder()
                            .requestMessageDetail("Tôi cần dạy thêm 2 tuần 4 tiết vào tuần 3,4 lịch bất kỳ")
                            .interactRequestReason(null)
                            .interactionStatus(EntityInteractionStatus.PENDING)
                            .updatingTime(LocalDateTime.now())
                            .build(),
                        TeacherRequest.builder()
                            .requestMessageDetail("Tạo giúp chị 1 tuần Thực Hành 4 tiết gần nhất nha!")
                            .interactRequestReason(null)
                            .interactionStatus(EntityInteractionStatus.PENDING)
                            .updatingTime(LocalDateTime.now())
                            .build()
                    );
                    teacherRequestRepository.saveAll(teacherRequests);
                    List<SubjectSchedule> emptySubjectSchedules = List.of(
                        SubjectSchedule.builder()
                            .sectionClass(sectionClassRepository.findById(1L).orElseThrow())
                            .day(null)
                            .startingWeek(null)
                            .totalWeek(null)
                            .startingPeriod(null)
                            .lastPeriod(null)
                            .classroom(null)
                            .teacher(teachers.getFirst())
                            .teacherRequest(teacherRequests.getFirst())
                            .build(),
                        SubjectSchedule.builder()
                            .sectionClass(sectionClassRepository.findById(7L).orElseThrow())
                            .day(null)
                            .startingWeek(null)
                            .totalWeek(null)
                            .startingPeriod(null)
                            .lastPeriod(null)
                            .classroom(null)
                            .teacher(teachers.get(1))
                            .teacherRequest(teacherRequests.get(1))
                            .build(),
                        SubjectSchedule.builder()
                            .sectionClass(sectionClassRepository.findById(10L).orElseThrow())
                            .day(null)
                            .startingWeek(null)
                            .totalWeek(null)
                            .startingPeriod(null)
                            .lastPeriod(null)
                            .classroom(null)
                            .teacher(teachers.get(2))
                            .teacherRequest(teacherRequests.get(2))
                            .build()
                    );
                    subjectScheduleRepository.saveAll(emptySubjectSchedules);

                    if (studentRepository.count() == 0) {
                        Grade cn1Grade = gradeRepository.findById("D21CQCN01-N").orElseThrow();
                        Grade cn2Grade = gradeRepository.findById("D21CQCN02-N").orElseThrow();
                        List<Student> students = List.of(
                            Student.builder().studentId("N20DCCN001").grade(cn1Grade).lastName("Nguyễn Hồng").firstName("Nguyễn").gender(Gender.BOY).instituteEmail("n20dccn001@student.ptithcm.edu.vn").build(),
                            Student.builder().studentId("N20DCCN002").grade(cn1Grade).lastName("Trần Hoàng").firstName("Trần").gender(Gender.GIRL).instituteEmail("n20dccn002@student.ptithcm.edu.vn").build(),
                            Student.builder().studentId("N20DCCN003").grade(cn1Grade).lastName("Lê Nghiêm").firstName("Lê").gender(Gender.BOY).instituteEmail("n20dccn003@student.ptithcm.edu.vn").build(),
                            Student.builder().studentId("N20DCCN004").grade(cn1Grade).lastName("Phạm Lương").firstName("Phạm").gender(Gender.BOY).instituteEmail("n20dccn004@student.ptithcm.edu.vn").build(),
                            Student.builder().studentId("N20DCCN005").grade(cn1Grade).lastName("Hoàng Nhật").firstName("Hoàng").gender(Gender.GIRL).instituteEmail("n20dccn005@student.ptithcm.edu.vn").build(),
                            Student.builder().studentId("N20DCCN006").grade(cn1Grade).lastName("Huỳnh Hồng").firstName("Huỳnh").gender(Gender.BOY).instituteEmail("n20dccn006@student.ptithcm.edu.vn").build(),
                            Student.builder().studentId("N20DCCN007").grade(cn1Grade).lastName("Phan Đặng").firstName("Phan").gender(Gender.BOY).instituteEmail("n20dccn007@student.ptithcm.edu.vn").build(),
                            Student.builder().studentId("N20DCCN008").grade(cn1Grade).lastName("Võ Đồng").firstName("Võ").gender(Gender.GIRL).instituteEmail("n20dccn008@student.ptithcm.edu.vn").build(),
                            Student.builder().studentId("N20DCCN009").grade(cn1Grade).lastName("Đặng Phùng").firstName("Đặng").gender(Gender.BOY).instituteEmail("n20dccn009@student.ptithcm.edu.vn").build(),
                            Student.builder().studentId("N20DCCN010").grade(cn1Grade).lastName("Bùi Hồng").firstName("Bùi").gender(Gender.GIRL).instituteEmail("n20dccn010@student.ptithcm.edu.vn").build(),
                            Student.builder().studentId("N20DCCN011").grade(cn1Grade).lastName("Nguyễn Thành").firstName("Đỗ").gender(Gender.BOY).instituteEmail("n20dccn011@student.ptithcm.edu.vn").build(),
                            Student.builder().studentId("N20DCCN012").grade(cn2Grade).lastName("Trần Thuyết").firstName("Hồ").gender(Gender.BOY).instituteEmail("n20dccn012@student.ptithcm.edu.vn").build(),
                            Student.builder().studentId("N20DCCN013").grade(cn2Grade).lastName("Lê Tiết").firstName("Dương").gender(Gender.GIRL).instituteEmail("n20dccn013@student.ptithcm.edu.vn").build(),
                            Student.builder().studentId("N20DCCN014").grade(cn2Grade).lastName("Phạm Đỗ").firstName("Lý").gender(Gender.BOY).instituteEmail("n20dccn014@student.ptithcm.edu.vn").build(),
                            Student.builder().studentId("N20DCCN015").grade(cn2Grade).lastName("Hoàng Lâm").firstName("Phùng").gender(Gender.BOY).instituteEmail("n20dccn015@student.ptithcm.edu.vn").build(),
                            Student.builder().studentId("N20DCCN016").grade(cn2Grade).lastName("Huỳnh Vĩnh").firstName("Vũ").gender(Gender.GIRL).instituteEmail("n20dccn016@student.ptithcm.edu.vn").build(),
                            Student.builder().studentId("N20DCCN017").grade(cn2Grade).lastName("Phan Châu").firstName("Trịnh").gender(Gender.BOY).instituteEmail("n20dccn017@student.ptithcm.edu.vn").build(),
                            Student.builder().studentId("N20DCCN018").grade(cn2Grade).lastName("Võ Sơn").firstName("Đinh").gender(Gender.GIRL).instituteEmail("n20dccn018@student.ptithcm.edu.vn").build(),
                            Student.builder().studentId("N20DCCN019").grade(cn2Grade).lastName("Đặng Tôn").firstName("Trương").gender(Gender.BOY).instituteEmail("n20dccn019@student.ptithcm.edu.vn").build(),
                            Student.builder().studentId("N20DCCN020").grade(cn2Grade).lastName("Bùi Tưởng").firstName("Lương").gender(Gender.BOY).instituteEmail("n20dccn020@student.ptithcm.edu.vn").build(),
                            Student.builder().studentId("N20DCCN021").grade(cn2Grade).lastName("Nguyễn Hồng").firstName("Mai").gender(Gender.GIRL).instituteEmail("n20dccn021@student.ptithcm.edu.vn").build(),
                            Student.builder().studentId("N20DCCN022").grade(cn2Grade).lastName("Trần Hoàng").firstName("Tô").gender(Gender.BOY).instituteEmail("n20dccn022@student.ptithcm.edu.vn").build(),
                            Student.builder().studentId("N20DCCN023").grade(cn2Grade).lastName("Lê Nghiêm").firstName("Dịch").gender(Gender.BOY).instituteEmail("n20dccn023@student.ptithcm.edu.vn").build()
                        );
                        studentRepository.saveAll(students);
                        List<SubjectRegistration> registrations = new ArrayList<>();

                        int sectionClassIndex;
                        for (int index = 0; index < students.size(); index++) {
                            if (index % 3 == 0) sectionClassIndex = 2;
                            else if (index % 2 == 0) sectionClassIndex = 1;
                            else sectionClassIndex = 0;

                            registrations.add(
                                SubjectRegistration.builder()
                                    .student(students.get(index))
                                    .sectionClass(sectionClasses.get(sectionClassIndex))
                                    .build()
                            );
                        }
                        subjectRegistrationRepository.saveAll(registrations);
                    }
                }
            }
        }
    }
}

