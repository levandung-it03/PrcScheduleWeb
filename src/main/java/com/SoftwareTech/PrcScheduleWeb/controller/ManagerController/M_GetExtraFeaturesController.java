package com.SoftwareTech.PrcScheduleWeb.controller.ManagerController;

import com.SoftwareTech.PrcScheduleWeb.service.ManagerService.M_GetExtraFeaturesService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/manager/extra-features")
public class M_GetExtraFeaturesController {
    @Autowired
    private final M_GetExtraFeaturesService getExtraFeaturesService;

    /**Author: Le Van Dung**/
    @RequestMapping(value = "/add-subject", method = GET)
    public ModelAndView getAddSubjectPage(HttpServletRequest request, Model model) {
        return getExtraFeaturesService.getAddSubjectPage(request, model);
    }

    @RequestMapping(value = "/add-file", method = GET)
    public ModelAndView getImportFilePage(HttpServletRequest request, Model model) {
        return getExtraFeaturesService.getImportFilePage(request, model);
    }
    /*----------------------*/

    /**Author: Nguyen Quang Linh**/
    @RequestMapping(value = "/add-student", method = GET)
    public ModelAndView getAddStudentPage(HttpServletRequest request, Model model) {

        return getExtraFeaturesService.getAddStudentPage(request, model);
    }
    @RequestMapping(value = "/add-grade", method = GET)
    public ModelAndView getAddGradePage(HttpServletRequest request, Model model) {

        return getExtraFeaturesService.getAddGradePage(request, model);
    }
    /*----------------------*/

    /**Author: Luong Dat Thien**/
    @RequestMapping(value = "/add-semester", method = GET)
    public ModelAndView getAddSemesterPage(HttpServletRequest request, Model model) {

        return getExtraFeaturesService.getAddSemesterPage(request, model);
    }

    @RequestMapping(value = "/add-sectionClass", method = GET)
    public ModelAndView getAddSectionClassPage(HttpServletRequest request, Model model) {

        return getExtraFeaturesService.getAddSectionClassPage(request, model);
    }

    @RequestMapping(value = "/add-department", method = GET)
    public ModelAndView getAddDepartmentPage(HttpServletRequest request, Model model) {

        return getExtraFeaturesService.getAddDepartmentPage(request, model);
    }
    /*----------------------*/

    /**Author: Huynh Nhu Y**/
    @RequestMapping(value = "/add-classroom", method = GET)
    public ModelAndView getAddClassroomPage (HttpServletRequest request, Model model){
        return getExtraFeaturesService.getClassroomPage(request, model);
    }

    @RequestMapping(value = "/add-subjectRegistration", method = GET)
    public ModelAndView getSubjectRegistrationPage (HttpServletRequest request, Model model){
        return getExtraFeaturesService.getSubjectRegistrationPage(request, model);
    }
    /*----------------------*/
}
