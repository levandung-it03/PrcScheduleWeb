package com.SoftwareTech.PrcScheduleWeb.service.ManagerService;

import com.SoftwareTech.PrcScheduleWeb.config.StaticUtilMethods;
import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.ReqDtoAddTeacherAccount;
import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.ReqDtoAddComputerRoom;
import com.SoftwareTech.PrcScheduleWeb.model.Account;
import com.SoftwareTech.PrcScheduleWeb.model.Teacher;
import com.SoftwareTech.PrcScheduleWeb.repository.AccountRepository;
import com.SoftwareTech.PrcScheduleWeb.repository.ClassroomRepository;
import com.SoftwareTech.PrcScheduleWeb.repository.TeacherRepository;
import com.SoftwareTech.PrcScheduleWeb.repository.TeacherRequestRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class M_CategoryService {
    @Autowired
    private final StaticUtilMethods staticUtilMethods;
    @Autowired
    private final ClassroomRepository classroomRepository;
    @Autowired
    private final TeacherRepository teacherRepository;
    @Autowired
    private final AccountRepository accountRepository;
    @Autowired
    private final TeacherRequestRepository teacherRequestRepository;

    public ModelAndView getAddTeacherAccountPage(HttpServletRequest request, Model model) {
        ModelAndView modelAndView = staticUtilMethods.customResponsiveModelView(request, model, "add-account");
        modelAndView = staticUtilMethods.insertingHeaderDataOfModelView(request, modelAndView);

        //--Refill data form after an error occurs.
        ReqDtoAddTeacherAccount newAccountObject = (ReqDtoAddTeacherAccount) model.asMap().get("newAccountObject");

        if (newAccountObject != null)
            modelAndView.addObject(newAccountObject);
        return modelAndView;
    }

    public ModelAndView getAddComputerRoomPage(HttpServletRequest request, Model model) {
        ModelAndView modelAndView = staticUtilMethods
            .customResponsiveModelView(request, model, "add-computer-room");
        modelAndView = staticUtilMethods.insertingHeaderDataOfModelView(request, modelAndView);

        //--Refill data form after an error occurs.
        ReqDtoAddComputerRoom roomObject = (ReqDtoAddComputerRoom) model.asMap().get("roomObject");

        if (roomObject != null)
            modelAndView.addObject(roomObject);
        return modelAndView;
    }

    public ModelAndView getComputerRoomListPage(HttpServletRequest request, Model model) {
        ModelAndView modelAndView = staticUtilMethods
            .customResponsiveModelView(request, model, "computer-room-list");
        modelAndView = staticUtilMethods.insertingHeaderDataOfModelView(request, modelAndView);
        PageRequest pageRequest = staticUtilMethods.getPageRequest(request);

        modelAndView.addObject("currentPage", pageRequest.getPageNumber() + 1);
        modelAndView.addObject("computerRoomList", classroomRepository.findAllInSpecifiedPage(pageRequest));

        return modelAndView;
    }

    public ModelAndView getTeacherListPage(HttpServletRequest request, Model model) {
        ModelAndView modelAndView = staticUtilMethods.customResponsiveModelView(request, model, "teacher-list");
        modelAndView = staticUtilMethods.insertingHeaderDataOfModelView(request, modelAndView);
        PageRequest pageRequest = staticUtilMethods.getPageRequest(request);

        modelAndView.addObject("currentPage", pageRequest.getPageNumber() + 1);
        modelAndView.addObject("teacherList", teacherRepository.findAllInSpecifiedPage(pageRequest));

        return modelAndView;
    }

    public ModelAndView getTeacherAccountListPage(HttpServletRequest request, Model model) {
        ModelAndView modelAndView = staticUtilMethods
            .customResponsiveModelView(request, model, "teacher-account-list");
        modelAndView = staticUtilMethods.insertingHeaderDataOfModelView(request, modelAndView);
        PageRequest pageRequest = staticUtilMethods.getPageRequest(request);

        modelAndView.addObject("currentPage", pageRequest.getPageNumber() + 1);
        modelAndView.addObject("accountList", accountRepository.findAllInSpecifiedPage(pageRequest));

        return modelAndView;
    }

    public ModelAndView getTeacherRequestListPage(HttpServletRequest request, Model model) {
        ModelAndView modelAndView = staticUtilMethods
            .customResponsiveModelView(request, model, "teacher-request-list");
        modelAndView = staticUtilMethods.insertingHeaderDataOfModelView(request, modelAndView);
        PageRequest pageRequest = staticUtilMethods.getPageRequest(request);

        modelAndView.addObject("role", "manager");
        modelAndView.addObject("currentPage", pageRequest.getPageNumber() + 1);
        modelAndView.addObject("teacherRequestList", teacherRequestRepository
            .findAllTeacherRequestInSubjectScheduleWithSpecifiedPage(pageRequest));

        return modelAndView;
    }

    public ModelAndView getExtraFeaturePage(HttpServletRequest request, Model model) {
        ModelAndView modelAndView = staticUtilMethods.customResponsiveModelView(request, model, "extra-features");
        modelAndView = staticUtilMethods.insertingHeaderDataOfModelView(request, modelAndView);
        return modelAndView;
    }
}
