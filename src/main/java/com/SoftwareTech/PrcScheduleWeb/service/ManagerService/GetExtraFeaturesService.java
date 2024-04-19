package com.SoftwareTech.PrcScheduleWeb.service.ManagerService;

import com.SoftwareTech.PrcScheduleWeb.config.StaticUtilMethods;
import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.ReqAddStudent;
import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.ReqAddSubject;
import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.ReqAddGrade;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

@Service
@RequiredArgsConstructor
public class GetExtraFeaturesService {
    @Autowired
    private final StaticUtilMethods staticUtilMethods;

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
}
