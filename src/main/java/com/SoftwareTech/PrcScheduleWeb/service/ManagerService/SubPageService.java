package com.SoftwareTech.PrcScheduleWeb.service.ManagerService;

import com.SoftwareTech.PrcScheduleWeb.config.StaticUtilMethods;
import com.SoftwareTech.PrcScheduleWeb.model.ComputerRoom;
import com.SoftwareTech.PrcScheduleWeb.model.Department;
import com.SoftwareTech.PrcScheduleWeb.model.Teacher;
import com.SoftwareTech.PrcScheduleWeb.repository.ComputerRoomRepository;
import com.SoftwareTech.PrcScheduleWeb.repository.DepartmentRepository;
import com.SoftwareTech.PrcScheduleWeb.repository.TeacherRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubPageService {
    @Autowired
    private final StaticUtilMethods staticUtilMethods;
    @Autowired
    private final ComputerRoomRepository computerRoomRepository;
    @Autowired
    private final TeacherRepository teacherRepository;
    @Autowired
    private final DepartmentRepository departmentRepository;

    public ModelAndView getUpdateComputerRoomPage(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws IOException {
        final String computerRoom = request.getParameter("computerRoom");
        ModelAndView modelAndView = staticUtilMethods
            .customizeResponsiveModelAndView(request, "update-computer-room");
        Optional<ComputerRoom> computerRoomObject = computerRoomRepository.findByComputerRoom(computerRoom);

        if (computerRoomObject.isEmpty()) {
            response.sendRedirect("/manager/category/computer-room/computer-room-list");
            return null;
        }
        else {
            modelAndView.addObject("computerRoomObject", computerRoomObject.get());
            return modelAndView;
        }
    }

    public ModelAndView getUpdateTeacherPage(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws IOException {
        final String teacherId = request.getParameter("teacherId");
        ModelAndView modelAndView = staticUtilMethods
            .customizeResponsiveModelAndView(request, "update-teacher");

        try {
            Teacher teacher = teacherRepository.findById(teacherId).orElseThrow();
            List<Department> departmentList = departmentRepository.findAll();

            modelAndView.addObject("teacher", teacher);
            modelAndView.addObject("departmentList", departmentList);

            return modelAndView;
        } catch (Exception ignored) {
            response.sendRedirect("/manager/category/teacher/teacher-list");
            return null;
        }
    }
}
