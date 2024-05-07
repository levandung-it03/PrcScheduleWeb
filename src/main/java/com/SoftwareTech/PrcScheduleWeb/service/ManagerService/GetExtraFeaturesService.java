package com.SoftwareTech.PrcScheduleWeb.service.ManagerService;

import com.SoftwareTech.PrcScheduleWeb.config.StaticUtilMethods;
import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.*;
import com.SoftwareTech.PrcScheduleWeb.model.*;
import com.SoftwareTech.PrcScheduleWeb.repository.SectionClassRepository;
import com.SoftwareTech.PrcScheduleWeb.repository.SemesterRepository;
import com.SoftwareTech.PrcScheduleWeb.repository.StudentRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetExtraFeaturesService {
    @Autowired
    private final StaticUtilMethods staticUtilMethods;
    @Autowired
    private final SemesterRepository semesterRepository;
    @Autowired
    private final StudentRepository studentRepository;
    @Autowired
    private final SectionClassRepository sectionClassRepository;
    /*----------------------*/


    /**Author: Le Van Dung**/
    public ModelAndView getAddSubjectPage(HttpServletRequest request, Model model) {
        ModelAndView modelAndView = staticUtilMethods
            .customResponseModelView(request, model.asMap(), "add-subject");

        ReqAddSubject subjectObject = (ReqAddSubject) model.asMap().get("subjectObject");

        if (subjectObject != null)
            modelAndView.addObject("subjectObject", model.asMap().get("subjectObject"));

        return modelAndView;
    }
    /*----------------------*/

    /**Author: Nguyen Quang Linh**/
    public ModelAndView getAddStudentPage(HttpServletRequest request, Model model) {
        ModelAndView modelAndView = staticUtilMethods
                .customResponseModelView(request, model.asMap(), "add-student");

        ReqAddStudent studentObject = (ReqAddStudent) model.asMap().get("studentObject");

        if (studentObject != null)
            modelAndView.addObject("studentObject", model.asMap().get("studentObject"));

        return modelAndView;
    }

    public ModelAndView getAddGradePage(HttpServletRequest request, Model model) {
        ModelAndView modelAndView = staticUtilMethods
                .customResponseModelView(request, model.asMap(), "add-grade");

        ReqAddGrade gradeObject = (ReqAddGrade) model.asMap().get("gradeObject");

        if (gradeObject != null)
            modelAndView.addObject("gradeObject", model.asMap().get("gradeObject"));

        return modelAndView;
    }
    /*----------------------*/

    /**Author: Luong Dat Thien**/
    public ModelAndView getAddSemesterPage(HttpServletRequest request, Model model) {
        ModelAndView modelAndView = staticUtilMethods
                .customResponseModelView(request, model.asMap(), "add-semester");

        ReqAddSemester semesterObject = (ReqAddSemester) model.asMap().get("semesterObject");

        if (semesterObject != null)
            modelAndView.addObject("semesterObject", model.asMap().get("semesterObject"));

        return modelAndView;
    }

    public ModelAndView getAddSectionClassPage(HttpServletRequest request, Model model) {

        ModelAndView modelAndView = staticUtilMethods
                .customResponseModelView(request, model.asMap(), "add-section-class");

        ReqAddSectionClass sectionClassObject = (ReqAddSectionClass) model.asMap().get("sectionClassObject");

        List<Semester> semesters = semesterRepository.findAll();

        modelAndView.addObject("semesters",semesters);
        if (sectionClassObject != null)
            modelAndView.addObject("sectionClassObject", model.asMap().get("sectionClassObject"));

        return modelAndView;
    }

    public ModelAndView getAddDepartmentPage(HttpServletRequest request, Model model) {

        ModelAndView modelAndView = staticUtilMethods
                .customResponseModelView(request, model.asMap(), "add-department");

        ReqAddDepartment departmentObject = (ReqAddDepartment) model.asMap().get("departmentObject");

        if (departmentObject != null)
            modelAndView.addObject("departmentObject", model.asMap().get("departmentObject"));

        return modelAndView;
    }
    /*----------------------*/

    /**Author: Huynh Nhu Y**/
    public ModelAndView getClassroomPage(HttpServletRequest request, Model model) {
        ModelAndView modelAndView = staticUtilMethods
            .customResponseModelView(request, model.asMap(), "add-classroom");

        ReqAddClassroom classroomObject = (ReqAddClassroom) model.asMap().get("classroomObject");

        if (classroomObject != null)
            modelAndView.addObject("classroomObject", model.asMap().get("classroomObject"));

        return modelAndView;
    }

    public ModelAndView getSubjectRegistrationPage(HttpServletRequest request, Model model){
        List<Student> studentList = studentRepository.findAll();
        List<SectionClass> sectionClassList = sectionClassRepository.findAll();
        ModelAndView modelAndView = staticUtilMethods
            .customResponseModelView(request, model.asMap(),"add-subjectRegistration");
        ReqAddSubjectRegistration subjectRegistrationObject = (ReqAddSubjectRegistration) model.asMap()
            .get("subjectRegistrationObject");
        if (subjectRegistrationObject != null){
            modelAndView.addObject("subjectRegistrationObject", model.asMap().get("subjectRegistrationObject"));
        }
        modelAndView.addObject(studentList);
        modelAndView.addObject(sectionClassList);
        return modelAndView;
    }
    /*----------------------*/
}
