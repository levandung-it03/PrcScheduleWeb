package com.SoftwareTech.PrcScheduleWeb.controller;

import com.SoftwareTech.PrcScheduleWeb.service.PublicPagesService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
@RequestMapping(path = "/public")
@RequiredArgsConstructor
public class PublicController {
    @Autowired
    private final PublicPagesService publicPagesService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView getLoginView(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return publicPagesService.renderLoginPage(request, response);
    }

}
