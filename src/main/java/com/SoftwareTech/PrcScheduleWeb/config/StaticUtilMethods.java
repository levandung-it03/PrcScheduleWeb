package com.SoftwareTech.PrcScheduleWeb.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class StaticUtilMethods {
    @Autowired
    private final Map<String, String> responseMessages;

    /**Spring MVC: Customize returned ModelAndView to show ErrMessage or SucceedMessages.**/
    public ModelAndView customizeModelAndView(@NonNull HttpServletRequest request, String model) {
        String errCode = request.getParameter("errorMessage");
        String errMess = (errCode == null) ? "none" : responseMessages.get(errCode);

        String succeedCode = request.getParameter("succeedMessage");
        String succeedMess = (succeedCode == null) ? "none" : responseMessages.get(succeedCode);

        ModelAndView modelAndView = new ModelAndView(model);
        modelAndView.addObject("errorMessage", errMess);
        modelAndView.addObject("succeedMessage", succeedMess);

        return modelAndView;
    }
}
