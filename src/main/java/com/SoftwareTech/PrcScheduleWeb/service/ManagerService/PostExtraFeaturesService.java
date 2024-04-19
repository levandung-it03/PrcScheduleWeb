package com.SoftwareTech.PrcScheduleWeb.service.ManagerService;

import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.ReqAddGrade;
import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.ReqAddStudent;
import com.SoftwareTech.PrcScheduleWeb.model.Grade;
import com.SoftwareTech.PrcScheduleWeb.model.Student;
import com.SoftwareTech.PrcScheduleWeb.model.Subject;
import com.SoftwareTech.PrcScheduleWeb.repository.GradeRepository;
import com.SoftwareTech.PrcScheduleWeb.repository.StudentRepository;
import com.SoftwareTech.PrcScheduleWeb.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.ReqAddSubject;

@Service
@RequiredArgsConstructor
public class PostExtraFeaturesService {
    @Autowired
    private final SubjectRepository subjectRepository;
    private final StudentRepository studentRepository;
    private final GradeRepository gradeRepository;

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

}
