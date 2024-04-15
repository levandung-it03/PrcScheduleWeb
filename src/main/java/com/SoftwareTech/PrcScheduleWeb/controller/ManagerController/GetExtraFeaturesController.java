package com.SoftwareTech.PrcScheduleWeb.controller.ManagerController;

import com.SoftwareTech.PrcScheduleWeb.service.ManagerService.GetExtraFeaturesService;
import jakarta.servlet.http.HttpServletRequest;
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
public class GetExtraFeaturesController {
    @Autowired
    private final GetExtraFeaturesService getExtraFeaturesService;

    @RequestMapping(value = "/add-subject", method = GET)
    public ModelAndView getAddSubjectPage(HttpServletRequest request, Model model) {
        return getExtraFeaturesService.getAddSubjectPage(request, model);
    }
}