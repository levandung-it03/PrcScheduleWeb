package com.SoftwareTech.PrcScheduleWeb.service;

import com.SoftwareTech.PrcScheduleWeb.config.StaticUtilMethods;
import com.SoftwareTech.PrcScheduleWeb.model.enums.EntityInteractionStatus;
import com.SoftwareTech.PrcScheduleWeb.model.enums.RoomType;
import com.SoftwareTech.PrcScheduleWeb.repository.AccountRepository;
import com.SoftwareTech.PrcScheduleWeb.repository.ClassroomRepository;
import com.SoftwareTech.PrcScheduleWeb.repository.TeacherRequestRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class HomeService {
    @Autowired
    private final StaticUtilMethods staticUtilMethods;
    @Autowired
    private final TeacherRequestRepository teacherRequestRepository;
    @Autowired
    private final AccountRepository accountRepository;
    @Autowired
    private final ClassroomRepository classroomRepository;
    @Autowired
    private final Logger logger;

    public ModelAndView handleGettingHomeRequestFromBothRoles(
        HttpServletRequest request,
        HttpServletResponse response,
        Model model
    ) {
        String loggedInRole = staticUtilMethods.isAValidAccessTokenInCookies(request);
        try {
            if (loggedInRole.equals("manager"))
                return this.getManagerHomePage(request, model);
            else if (loggedInRole.equals("teacher"))
                return this.getManagerHomePage(request, model);
        } catch (Exception e) {
            logger.info(e.toString());
            //--Delete cookie from Client Local Storage Browser.
            Cookie cookieToDelete = new Cookie("accessToken", "");
            cookieToDelete.setMaxAge(0);
            cookieToDelete.setPath("/");
            response.addCookie(cookieToDelete);
        }
        return new ModelAndView("redirect:/public/login");
    }

    public ModelAndView getManagerHomePage(HttpServletRequest request, Model model) {
        ModelAndView modelAndView = staticUtilMethods.customResponseModelView(request, model.asMap(), "manager-home");

        //--Prepare data to transmit to home.jsp
        int createdRequests = teacherRequestRepository.countAllByInteractionStatus(EntityInteractionStatus.CREATED);
        int deniedRequests = teacherRequestRepository.countAllByInteractionStatus(EntityInteractionStatus.DENIED);
        if (deniedRequests == 0)
            modelAndView.addObject("successfullyCreatedRequestsRatio", 0);
        else
            modelAndView.addObject("successfullyCreatedRequestsRatio",
                createdRequests / (createdRequests + deniedRequests) * 100);

        modelAndView.addObject("pendingRequests",
            teacherRequestRepository.countAllByInteractionStatus(EntityInteractionStatus.PENDING));
        modelAndView.addObject("createdRequests", createdRequests);
        modelAndView.addObject("deniedRequests", deniedRequests);
        modelAndView.addObject("canceledRequests",
            teacherRequestRepository.countAllByInteractionStatus(EntityInteractionStatus.CANCEL)
        );
        modelAndView.addObject("accountsQuantity", accountRepository.countAllByStatus(true));
        modelAndView.addObject("computerRoomQuantity",
            classroomRepository.countAllByRoomTypeAndStatus(RoomType.PRC, true));
        modelAndView.addObject("pendingRequestsList", teacherRequestRepository
            .findAllTeacherRequestInSubjectScheduleWithInteractionStatus(EntityInteractionStatus.PENDING));

        return modelAndView;
    }
}
