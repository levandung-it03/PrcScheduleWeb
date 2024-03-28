package com.SoftwareTech.PrcScheduleWeb.service.ManagerService;

import com.SoftwareTech.PrcScheduleWeb.config.StaticUtilMethods;
import com.SoftwareTech.PrcScheduleWeb.dto.AuthDto.DtoRegisterAccount;
import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.DtoAsRequests.DtoAddComputerRoom;
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

@Service
@RequiredArgsConstructor
public class CategoryService {
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
        ModelAndView modelAndView = staticUtilMethods.customResponseModelView(model.asMap(), "add-account");

        //--Refill data form after an error occurs.
        DtoRegisterAccount registerObject = (DtoRegisterAccount) model.asMap().get("registerObject");

        if (registerObject != null)
            modelAndView.addObject(registerObject);
        return modelAndView;
    }

    public ModelAndView getAddComputerRoomPage(HttpServletRequest request, Model model) {
        ModelAndView modelAndView = staticUtilMethods.customResponseModelView(model.asMap(), "add-computer-room");

        //--Refill data form after an error occurs.
        DtoAddComputerRoom roomObject = (DtoAddComputerRoom) model.asMap().get("roomObject");

        if (roomObject != null)
            modelAndView.addObject(roomObject);
        return modelAndView;
    }

    public ModelAndView getComputerRoomListPage(HttpServletRequest request, Model model) {
        ModelAndView modelAndView = staticUtilMethods.customResponseModelView(model.asMap(), "computer-room-list");
        PageRequest pageRequest = staticUtilMethods.getPageRequest(request);

        modelAndView.addObject("currentPage", pageRequest.getPageNumber() + 1);
        modelAndView.addObject("computerRoomList", classroomRepository.findAllInSpecifiedPage(pageRequest));

        return modelAndView;
    }

    public ModelAndView getTeacherListPage(HttpServletRequest request, Model model) {
        ModelAndView modelAndView = staticUtilMethods.customResponseModelView(model.asMap(), "teacher-list");
        PageRequest pageRequest = staticUtilMethods.getPageRequest(request);

        modelAndView.addObject("currentPage", pageRequest.getPageNumber() + 1);
        modelAndView.addObject("teacherList", teacherRepository.findAllInSpecifiedPage(pageRequest));

        return modelAndView;
    }

    public ModelAndView getTeacherAccountListPage(HttpServletRequest request, Model model) {
        ModelAndView modelAndView = staticUtilMethods.customResponseModelView(model.asMap(), "teacher-account-list");
        PageRequest pageRequest = staticUtilMethods.getPageRequest(request);

        modelAndView.addObject("currentPage", pageRequest.getPageNumber() + 1);
        modelAndView.addObject("accountList", accountRepository.findAllInSpecifiedPage(pageRequest));

        return modelAndView;
    }

    public ModelAndView getTeacherRequestListPage(HttpServletRequest request, Model model) {
        ModelAndView modelAndView = staticUtilMethods.customResponseModelView(model.asMap(), "teacher-request-list");
        PageRequest pageRequest = staticUtilMethods.getPageRequest(request);

        modelAndView.addObject("currentPage", pageRequest.getPageNumber() + 1);
        modelAndView.addObject("teacherRequestList", teacherRequestRepository.findAllInSpecifiedPage(pageRequest));

        return modelAndView;
    }
}
