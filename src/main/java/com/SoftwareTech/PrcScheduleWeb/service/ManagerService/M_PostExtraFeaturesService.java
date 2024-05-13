package com.SoftwareTech.PrcScheduleWeb.service.ManagerService;

import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.*;
import com.SoftwareTech.PrcScheduleWeb.model.*;
import com.SoftwareTech.PrcScheduleWeb.model.enums.RoomType;
import com.SoftwareTech.PrcScheduleWeb.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class M_PostExtraFeaturesService {
    @Autowired
    private final SubjectRepository subjectRepository;
    @Autowired
    private final StudentRepository studentRepository;
    @Autowired
    private final GradeRepository gradeRepository;
    @Autowired
    private final SemesterRepository semesterRepository;
    @Autowired
    private final SectionClassRepository sectionClassRepository;
    @Autowired
    private final ClassroomRepository classroomRepository;
    @Autowired
    private final SubjectRegistrationRepository subjectRegistrationRepository;
    @Autowired
    private final DepartmentRepository departmentRepository;
    @Autowired
    private final EntityDataFormatterRepository entityDataFormatterRepository;
    /**Author: Le Van Dung**/
    public void addSubject(ReqDtoAddSubject subjectObject) {
        if (subjectRepository.findByIdOrSubjectName(
                subjectObject.getSubjectId(), subjectObject.getSubjectName()).isPresent()
        )
            throw new DuplicateKeyException("Subject is already existing");

        //--May throw SQLException
        subjectRepository.save(Subject.builder()
            .subjectId(subjectObject.getSubjectId())
            .subjectName(subjectObject.getSubjectName())
            .creditsNumber(subjectObject.getCreditsNumber())
            .status(true)
            .build());
    }
    /*----------------------*/

    /**Author: Nguyen Quang Linh**/
    public void addStudent(ReqDtoAddStudent studentObject) {
        if (studentRepository.findByStudentId(studentObject.getStudentId()).isPresent()) {
            throw new DuplicateKeyException("Student is already existing");
        }

        //--May throw SQLException
        studentRepository.save(Student.builder()
                .studentId(studentObject.getStudentId())
                .grade(studentObject.getGrade())
                .lastName(studentObject.getLastName())
                .firstName(studentObject.getFirstName())
                .gender(studentObject.getGender())
                .instituteEmail(studentObject.getInstituteEmail())
                .build());
    }
    public void addGrade(ReqDtoAddGrade gradeObject) {
        if (gradeRepository.findByGradeId(gradeObject.getGradeId()).isPresent()) {
            throw new DuplicateKeyException("Grade is already existing");
        }

        //--May throw SQLException
        gradeRepository.save(Grade.builder()
                .gradeId(gradeObject.getGradeId())
                .department(gradeObject.getDepartment())
                .build());
    }
    /*----------------------*/

    /**Author: Luong Dat Thien**/
    public void addSemester(ReqDtoAddSemester semesterObject) {
        if (semesterRepository.findBySemesterAndRangeOfYear(semesterObject.getSemester(),
                semesterObject.getRangeOfYear()).isPresent()) {
            throw new DuplicateKeyException("Semester is already existing");
        }

        LocalDate localDate = LocalDate.now();
        Date date = Date.valueOf(localDate);

        //--May throw SQLException
        semesterRepository.save(Semester.builder()
            .semester(semesterObject.getSemester())
            .rangeOfYear(semesterObject.getRangeOfYear())
            .firstWeek(semesterObject.getFirstWeek())
            .totalWeek(semesterObject.getTotalWeek())
            .startingDate(date)
            .build());
    }

    public void addSectionClass(ReqDtoAddSectionClass sectionClassObject) throws IllegalArgumentException {
        Semester semester = semesterRepository
            .findById(sectionClassObject.getSemesterId())
            .orElseThrow(() -> new IllegalArgumentException("error_semester_02"));
        Grade grade = gradeRepository
            .findById(sectionClassObject.getGradeId())
            .orElseThrow(() -> new IllegalArgumentException("error_section_class_02"));
        Subject subject = subjectRepository
            .findById(sectionClassObject.getSubjectId())
            .orElseThrow(() -> new IllegalArgumentException("error_section_class_03"));

        if (sectionClassRepository.findByGradeAndSemesterAndSubject(semester.getSemesterId()
                ,grade.getGradeId(), subject.getSubjectId()).isPresent()
        )
            throw new DuplicateKeyException("SectionClass is already existing");

        //--May throw SQLException
        sectionClassRepository.save(SectionClass.builder()
                .semester(semester)
                .grade(grade)
                .subject(subject)
                .groupFromSubject(sectionClassObject.getGroupFromSubject())
                .build());
    }

    public void addDepartment(ReqDtoAddDepartment departmentObject) {
        if (departmentRepository.findByDepartmentIdAndDepartmentName(departmentObject.getDepartmentId(),
                departmentObject.getDepartmentName()).isPresent()) {
            throw new DuplicateKeyException("Department is already existing");
        }

        //--May throw SQLException
        departmentRepository.save(Department.builder()
                .departmentId(departmentObject.getDepartmentId())
                .departmentName(departmentObject.getDepartmentName())
                .build());
    }
    /*----------------------*/

    /**Author: Huynh Nhu Y**/
    public void addClassroom(ReqDtoAddClassroom classroomObject) throws DuplicateKeyException {
        final String area = classroomObject.getArea().trim().toUpperCase();
        final String inpClassRoom = String.format("2%s%s", area, classroomObject.getRoomCode());

        //--Query result will be ignored because it belongs to validate.
        if (classroomRepository.existsById(inpClassRoom))
            throw new DuplicateKeyException("error_computerRoom_01");

        //--Preparing added data.
        Classroom classRoom = Classroom.builder()
            .roomId(inpClassRoom)
            .roomType(RoomType.NORM)
            .maxQuantity(classroomObject.getMaxQuantity())
            .status(true)
            .build();

        classroomRepository.save(classRoom);
    }

    public void addSubjectRegistration(ReqDtoAddSubjectRegistration subjectRegistrationObject) {
        if (subjectRegistrationRepository.findBySectionClassIdAndStudentId(
            subjectRegistrationObject.getSectionClassId(), subjectRegistrationObject.getStudentId()).isPresent()
        )
            throw new DuplicateKeyException("Data is already existing");

        SectionClass chosenSectionClass = sectionClassRepository
            .findById(subjectRegistrationObject.getSectionClassId())
            .orElseThrow(()-> new NoSuchElementException("SectionClass Id not found"));

        Student chosenStudent = studentRepository
            .findById(subjectRegistrationObject.getStudentId())
            .orElseThrow(()-> new NoSuchElementException("Student Id not found"));

        subjectRegistrationRepository.save(SubjectRegistration.builder()
            .sectionClass(chosenSectionClass)
            .student(chosenStudent)
            .build());
    }

    public int importDataFromTxt(MultipartFile file) throws DataIntegrityViolationException {
        EntityDataFormatInTxtFiles formatter = new EntityDataFormatInTxtFiles(file);
        StringBuilder query = formatter.extractTxtFilesDataIntoInsertionQueryFormat();
        return entityDataFormatterRepository.runInsertionQuery(query.toString());
    }
    /*----------------------*/
}
