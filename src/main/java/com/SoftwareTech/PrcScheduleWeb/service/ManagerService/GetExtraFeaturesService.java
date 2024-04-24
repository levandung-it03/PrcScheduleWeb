package com.SoftwareTech.PrcScheduleWeb.service.ManagerService;

import com.SoftwareTech.PrcScheduleWeb.config.StaticUtilMethods;
import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.*;
import com.SoftwareTech.PrcScheduleWeb.model.Semester;
import com.SoftwareTech.PrcScheduleWeb.repository.SemesterRepository;
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

    /**Author: Luong Dat Thien**/
    @Autowired
    private final SemesterRepository semesterRepository;
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

    /*----------------------*/
}
