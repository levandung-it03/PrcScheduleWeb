package com.SoftwareTech.PrcScheduleWeb.controller.ManagerController;

import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.ReqDtoPracticeSchedule;
import com.SoftwareTech.PrcScheduleWeb.service.ManagerService.SubjectScheduleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "${url.post.manager.prefix.v1}")
public class SubjectScheduleController {
    @Autowired
    private final SubjectScheduleService subjectScheduleService;
    @Autowired
    private final Logger logger;

    @RequestMapping(value = "/add-practice-schedule", method = POST)
    public String addPracticeSchedule(
        @Valid @ModelAttribute("practiceScheduleObj") ReqDtoPracticeSchedule practiceScheduleObj,
        HttpServletRequest request,
        RedirectAttributes redirectAttributes,
        BindingResult bindingResult
    ) {
        final String standingUrl = request.getHeader("Referer") + "?requestId=" + practiceScheduleObj.getRequestId();
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorCode", bindingResult.getFieldErrors().getFirst());
            return "redirect:" + standingUrl;
        }

        try {
            subjectScheduleService.addPracticeSchedule(practiceScheduleObj);
            redirectAttributes.addFlashAttribute("succeedCode", "succeed_add_01");
            return "redirect:/manager/category/practice-schedule/teacher-request-list";
        } catch (NoSuchElementException e) {
            redirectAttributes.addFlashAttribute("errorCode", "error_entity_01");
            logger.info(e.toString());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorCode", "error_systemApplication_01");
            logger.info(e.toString());
        }
        return "redirect:" + standingUrl;
    }
}
