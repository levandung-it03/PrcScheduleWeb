package com.SoftwareTech.PrcScheduleWeb.service.ManagerService;

import com.SoftwareTech.PrcScheduleWeb.config.StaticUtilMethods;
import com.SoftwareTech.PrcScheduleWeb.dto.AuthDto.DtoRegisterAccount;
import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.DtoAddComputerRoom;
import com.SoftwareTech.PrcScheduleWeb.repository.AccountRepository;
import com.SoftwareTech.PrcScheduleWeb.repository.ClassroomRepository;
import com.SoftwareTech.PrcScheduleWeb.repository.TeacherRepository;
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

    public ModelAndView getAddTeacherAccountPage(HttpServletRequest request, Model model) {
        ModelAndView modelAndView = staticUtilMethods.customResponseModelView(request, "add-account");
        DtoRegisterAccount registerObject = (DtoRegisterAccount) model.asMap().get("registerObject");

        if (registerObject != null)
            modelAndView.addObject(registerObject);
        return modelAndView;
    }

    public ModelAndView getAddComputerRoomPage(HttpServletRequest request, Model model) {
        ModelAndView modelAndView = staticUtilMethods.customResponseModelView(request, "add-computer-room");

        //--Refill data form after an error occurs.
        DtoAddComputerRoom roomObject = (DtoAddComputerRoom) model.asMap().get("roomObject");

        if (roomObject != null)
            modelAndView.addObject(roomObject);
        return modelAndView;
    }

    public ModelAndView getComputerRoomListPage(HttpServletRequest request) {
        ModelAndView modelAndView = staticUtilMethods.customResponseModelView(request, "computer-room-list");
        PageRequest pageRequest = staticUtilMethods.getPageRequest(request);

        modelAndView.addObject("currentPage", pageRequest.getPageNumber() + 1);
        modelAndView.addObject("computerRoomList", classroomRepository.findAllInSpecifiedPage(pageRequest));

        return modelAndView;
    }

    public ModelAndView getTeacherListPage(HttpServletRequest request) {
        ModelAndView modelAndView = staticUtilMethods.customResponseModelView(request, "teacher-list");
        PageRequest pageRequest = staticUtilMethods.getPageRequest(request);

        modelAndView.addObject("currentPage", pageRequest.getPageNumber() + 1);
        modelAndView.addObject("teacherList", teacherRepository.findAllInSpecifiedPage(pageRequest));

        return modelAndView;
    }

    public ModelAndView getTeacherAccountListPage(HttpServletRequest request) {
        ModelAndView modelAndView = staticUtilMethods.customResponseModelView(request, "teacher-account-list");
        PageRequest pageRequest = staticUtilMethods.getPageRequest(request);

        modelAndView.addObject("currentPage", pageRequest.getPageNumber() + 1);
        modelAndView.addObject("accountList", accountRepository.findAllInSpecifiedPage(pageRequest));

        return modelAndView;
    }
}
