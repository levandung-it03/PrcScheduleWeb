package com.SoftwareTech.PrcScheduleWeb.controller.ManagerController;

import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.*;
import com.SoftwareTech.PrcScheduleWeb.service.ManagerService.M_PostExtraFeaturesService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import jakarta.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Set;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "${url.post.manager.prefix.v1}")
public class M_PostExtraFeaturesController {
    @Autowired
    private final Validator hibernateValidator;
    @Autowired
    private final M_PostExtraFeaturesService postExtraFeaturesService;

    /**
     * Author: Le Van Dung
     **/
    @RequestMapping(value = "/add-subject", method = POST)
    public String addSubject(
        @ModelAttribute("subjectObject") ReqDtoAddSubject subjectObject,
        HttpServletRequest request,
        RedirectAttributes redirectAttributes
    ) {
        final String standingUrl = request.getHeader("Referer");
        Set<ConstraintViolation<ReqDtoAddSubject>> violations = hibernateValidator.validate(subjectObject);
        if (!violations.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorCode", violations.iterator().next().getMessage());
            redirectAttributes.addFlashAttribute("subjectObject", subjectObject);
            return "redirect:" + standingUrl;
        }

        try {
            postExtraFeaturesService.addSubject(subjectObject);
            redirectAttributes.addFlashAttribute("succeedCode", "succeed_add_01");
        } catch (DuplicateKeyException ignored) {
            redirectAttributes.addFlashAttribute("subjectObject", subjectObject);
            redirectAttributes.addFlashAttribute("errorCode", "error_subject_01");
        } catch (Exception ignored) {
            redirectAttributes.addFlashAttribute("subjectObject", subjectObject);
            redirectAttributes.addFlashAttribute("errorCode", "error_systemApplication_01");
        }
        return "redirect:" + standingUrl;
    }

    @RequestMapping(value = "/import-data-from-txt")
    public String importDataFromTxt(
        @RequestParam("file") MultipartFile file,
        HttpServletRequest request,
        RedirectAttributes redirectAttributes
    ) {
        final String standingUrl = request.getHeader("Referer");
        try {
            int result = postExtraFeaturesService.importDataFromTxt(file);
            redirectAttributes.addFlashAttribute(
                "succeedCode",
                "Đã thêm " + result + " đối tượng dữ liệu"
            );
        } catch (DataIntegrityViolationException ignored) {
            redirectAttributes.addFlashAttribute("errorCode", "error_subject_01");
        } catch (Exception ignored) {
            redirectAttributes.addFlashAttribute("errorCode", "error_systemApplication_01");
        }
        return "redirect:" + standingUrl;
    }
    /*----------------------*/

    /**
     * Author: Nguyen Quang Linh
     **/
    @RequestMapping(value = "/add-student", method = POST)
    public String addStudent(
        @ModelAttribute("studentObject") ReqDtoAddStudent studentObject,
        HttpServletRequest request,
        RedirectAttributes redirectAttributes
    ) {
        final String standingUrl = request.getHeader("Referer");
        Set<ConstraintViolation<ReqDtoAddStudent>> violations = hibernateValidator.validate(studentObject);
        if (!violations.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorCode", violations.iterator().next().getMessage());
            redirectAttributes.addFlashAttribute("studentObject", studentObject);
            return "redirect:" + standingUrl;
        }

        try {
            postExtraFeaturesService.addStudent(studentObject);
            redirectAttributes.addFlashAttribute("succeedCode", "succeed_add_01");
        } catch (DuplicateKeyException ignored) {
            redirectAttributes.addFlashAttribute("studentObject", studentObject);
            redirectAttributes.addFlashAttribute("errorCode", "error_subject_01");
        } catch (Exception ignored) {
            redirectAttributes.addFlashAttribute("studentObject", studentObject);
            redirectAttributes.addFlashAttribute("errorCode", "error_systemApplication_01");
        }
        return "redirect:" + standingUrl;
    }

    @RequestMapping(value = "/add-grade", method = POST)
    public String addGrade(
        @ModelAttribute("gradeObject") ReqDtoAddGrade gradeObject,
        HttpServletRequest request,
        RedirectAttributes redirectAttributes
    ) {
        final String standingUrl = request.getHeader("Referer");
        Set<ConstraintViolation<ReqDtoAddGrade>> violations = hibernateValidator.validate(gradeObject);
        if (!violations.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorCode", violations.iterator().next().getMessage());
            redirectAttributes.addFlashAttribute("studentObject", gradeObject);
            return "redirect:" + standingUrl;
        }

        try {
            postExtraFeaturesService.addGrade(gradeObject);
            redirectAttributes.addFlashAttribute("succeedCode", "succeed_add_01");
        } catch (DuplicateKeyException ignored) {
            redirectAttributes.addFlashAttribute("studentObject", gradeObject);
            redirectAttributes.addFlashAttribute("errorCode", "error_subject_01");
        } catch (Exception ignored) {
            redirectAttributes.addFlashAttribute("studentObject", gradeObject);
            redirectAttributes.addFlashAttribute("errorCode", "error_systemApplication_01");
        }
        return "redirect:" + standingUrl;
    }
    /*----------------------*/

    /**
     * Author: Luong Dat Thien
     **/
    @RequestMapping(value = "/add-semester", method = POST)
    public String addSemester(
        @ModelAttribute("semesterObject") ReqDtoAddSemester semesterObject,
        HttpServletRequest request,
        RedirectAttributes redirectAttributes
    ) {
        final String standingUrl = request.getHeader("Referer");
        Set<ConstraintViolation<ReqDtoAddSemester>> violations = hibernateValidator.validate(semesterObject);
        if (!violations.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorCode", violations.iterator().next().getMessage());
            redirectAttributes.addFlashAttribute("semesterObject", semesterObject);
            return "redirect:" + standingUrl;
        }

        try {
            postExtraFeaturesService.addSemester(semesterObject);
            redirectAttributes.addFlashAttribute("succeedCode", "succeed_add_01");
        } catch (DuplicateKeyException ignored) {
            redirectAttributes.addFlashAttribute("semesterObject", semesterObject);
            redirectAttributes.addFlashAttribute("errorCode", "error_semester_01");
        } catch (Exception ignored) {
            redirectAttributes.addFlashAttribute("semesterObject", semesterObject);
            redirectAttributes.addFlashAttribute("errorCode", "error_systemApplication_01");
        }
        return "redirect:" + standingUrl;
    }

    @RequestMapping(value = "/add-section-class", method = POST)
    public String addSectionClass(
        @ModelAttribute("sectionClassObject") ReqDtoAddSectionClass sectionClassObject,
        HttpServletRequest request,
        RedirectAttributes redirectAttributes
    ) {
        final String standingUrl = request.getHeader("Referer");
        Set<ConstraintViolation<ReqDtoAddSectionClass>> violations = hibernateValidator.validate(sectionClassObject);
        if (!violations.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorCode", violations.iterator().next().getMessage());
            redirectAttributes.addFlashAttribute("sectionClassObject", sectionClassObject);
            return "redirect:" + standingUrl;
        }

        try {
            postExtraFeaturesService.addSectionClass(sectionClassObject);
            redirectAttributes.addFlashAttribute("succeedCode", "succeed_add_01");
        } catch (DuplicateKeyException ignored) {
            redirectAttributes.addFlashAttribute("sectionClassObject", sectionClassObject);
            redirectAttributes.addFlashAttribute("errorCode", "error_section_class_01");
        } catch (IllegalArgumentException e) {
            String errorMessage = trimErrorMessage(e.getMessage());
            redirectAttributes.addFlashAttribute("sectionClassObject", sectionClassObject);
            redirectAttributes.addFlashAttribute("errorCode", errorMessage);
        } catch (Exception ignored) {
            redirectAttributes.addFlashAttribute("sectionClassObject", sectionClassObject);
            redirectAttributes.addFlashAttribute("errorCode", "error_systemApplication_01");
        }
        return "redirect:" + standingUrl;
    }

    @RequestMapping(value = "/add-department", method = POST)
    public String addDepartment(
        @ModelAttribute("departmentObject") ReqDtoAddDepartment departmentObject,
        HttpServletRequest request,
        RedirectAttributes redirectAttributes
    ) {
        final String standingUrl = request.getHeader("Referer");
        Set<ConstraintViolation<ReqDtoAddDepartment>> violations = hibernateValidator.validate(departmentObject);
        if (!violations.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorCode", violations.iterator().next().getMessage());
            redirectAttributes.addFlashAttribute("departmentObject", departmentObject);
            return "redirect:" + standingUrl;
        }

        try {
            postExtraFeaturesService.addDepartment(departmentObject);
            redirectAttributes.addFlashAttribute("succeedCode", "succeed_add_01");
        } catch (DuplicateKeyException ignored) {
            redirectAttributes.addFlashAttribute("departmentObject", departmentObject);
            redirectAttributes.addFlashAttribute("errorCode", "error_department_01");
        } catch (Exception ignored) {
            redirectAttributes.addFlashAttribute("departmentObject", departmentObject);
            redirectAttributes.addFlashAttribute("errorCode", "error_systemApplication_01");
        }
        return "redirect:" + standingUrl;
    }

    private String trimErrorMessage(String errorMessage) {
        int index = errorMessage.indexOf(":");
        if (index != -1) {
            return errorMessage.substring(index + 1).trim();
        }
        return errorMessage;
    }
    /*----------------------*/

    /**
     * Author: Huynh Nhu Y
     **/
    @RequestMapping(value = "/add-classroom", method = POST)
    public String addClassroom(
        @ModelAttribute("classroomObject") ReqDtoAddClassroom classroomObject,
        HttpServletRequest request,
        RedirectAttributes redirectAttributes
    ) {
        final String standingUrl = request.getHeader("Referer");
        Set<ConstraintViolation<ReqDtoAddClassroom>> violations = hibernateValidator.validate(classroomObject);
        if (!violations.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorCode", violations.iterator().next().getMessage());
            redirectAttributes.addFlashAttribute("classroomObject", classroomObject);
            return "redirect:" + standingUrl;
        }

        try {
            postExtraFeaturesService.addClassroom(classroomObject);
            redirectAttributes.addFlashAttribute("succeedCode", "succeed_add_01");
        } catch (DuplicateKeyException e) {
            redirectAttributes.addFlashAttribute("classroomObject", classroomObject);
            redirectAttributes.addFlashAttribute("errorCode", e.getMessage());
        } catch (Exception ignored) {
            redirectAttributes.addFlashAttribute("roomObject", classroomObject);
            redirectAttributes.addFlashAttribute("errorCode", "error_systemApplication_01");
        }
        return "redirect:" + standingUrl;
    }

    @RequestMapping(value = "/add-subjectRegistration", method = POST)
    public String addSubjectRegistration(
        @ModelAttribute("subjectRegistrationObject") ReqDtoAddSubjectRegistration subjectRegistrationObject,
        HttpServletRequest request,
        RedirectAttributes redirectAttributes
    ) {
        final String standingUrl = request.getHeader("Referer");
        Set<ConstraintViolation<ReqDtoAddSubjectRegistration>> violations = hibernateValidator.validate(subjectRegistrationObject);
        if (!violations.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorCode", violations.iterator().next().getMessage());
            redirectAttributes.addFlashAttribute("subjectRegistrationObject", subjectRegistrationObject);
            return "redirect:" + standingUrl;
        }

        try {
            postExtraFeaturesService.addSubjectRegistration(subjectRegistrationObject);
            redirectAttributes.addFlashAttribute("succeedCode", "succeed_add_01");
        } catch (DuplicateKeyException ignored) {
            redirectAttributes.addFlashAttribute("subjectRegistrationObject", subjectRegistrationObject);
            redirectAttributes.addFlashAttribute("errorCode", "error_entity_04");
        } catch (Exception ignored) {
            redirectAttributes.addFlashAttribute("subjectObject", subjectRegistrationObject);
            redirectAttributes.addFlashAttribute("errorCode", "error_systemApplication_01");
        }
        return "redirect:" + standingUrl;
    }
    /*----------------------*/
}
