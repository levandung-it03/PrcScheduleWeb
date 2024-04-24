package com.SoftwareTech.PrcScheduleWeb.service.ManagerService;

import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.*;
import com.SoftwareTech.PrcScheduleWeb.model.*;
import com.SoftwareTech.PrcScheduleWeb.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PostExtraFeaturesService {
    @Autowired
    private final SubjectRepository subjectRepository;
    private final StudentRepository studentRepository;
    private final GradeRepository gradeRepository;
    private final SemesterRepository semesterRepository;
    private final SectionClassRepository sectionClassRepository;

    /**Author: Le Van Dung**/
    public void addSubject(ReqAddSubject subjectObject) {
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
    public void addStudent(ReqAddStudent studentObject) {
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
    public void addGrade(ReqAddGrade gradeObject) {
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
    public void addSemester(ReqAddSemester semesterObject) {
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

    public void addSectionClass(ReqAddSectionClass sectionClassObject) {
        Semester semester = null;
        Grade grade = null;
        Subject subject = null;
        try {
             semester = semesterRepository.findById(sectionClassObject.getSemesterId()).orElseThrow(()
                    -> new IllegalArgumentException("error_section_class_02"));
             grade = gradeRepository.findById(sectionClassObject.getGradeId()).orElseThrow(()
                    -> new IllegalArgumentException("error_section_class_03"));
             subject = subjectRepository.findById(sectionClassObject.getSubjectId()).orElseThrow(()
                    -> new IllegalArgumentException("error_section_class_04"));
        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        }

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
    /*----------------------*/
}
