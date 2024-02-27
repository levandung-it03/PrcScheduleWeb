package com.SoftwareTech.PrcScheduleWeb.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping(path = "/public")
@RequiredArgsConstructor
public class PublicController {
    @Autowired
    private final Map<String, String> errMessages;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView getLoginView(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("login");
        String errCode = request.getParameter("errorMessage");
        String errMess = (errCode == null) ? "none" : errMessages.get(errCode);

        modelAndView.addObject("errorMessage", errMess);
        return modelAndView;
    }

}
