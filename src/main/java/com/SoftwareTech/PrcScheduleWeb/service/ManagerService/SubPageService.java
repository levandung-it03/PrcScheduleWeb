package com.SoftwareTech.PrcScheduleWeb.service.ManagerService;

import com.SoftwareTech.PrcScheduleWeb.config.StaticUtilMethods;
import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.DtoComputerRoom;
import com.SoftwareTech.PrcScheduleWeb.model.ComputerRoomDetail;
import com.SoftwareTech.PrcScheduleWeb.model.Department;
import com.SoftwareTech.PrcScheduleWeb.model.Teacher;
import com.SoftwareTech.PrcScheduleWeb.repository.ComputerRoomDetailRepository;
import com.SoftwareTech.PrcScheduleWeb.repository.DepartmentRepository;
import com.SoftwareTech.PrcScheduleWeb.repository.TeacherRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubPageService {
    @Autowired
    private final StaticUtilMethods staticUtilMethods;
    @Autowired
    private final TeacherRepository teacherRepository;
    @Autowired
    private final DepartmentRepository departmentRepository;
    @Autowired
    private final ComputerRoomDetailRepository computerRoomDetailRepository;

    public ModelAndView getUpdateComputerRoomPage(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws IOException {
        final String roomId = request.getParameter("roomId");
        ModelAndView modelAndView = staticUtilMethods.customResponseModelView(request, "update-computer-room");

        try {
            ComputerRoomDetail computerRoomDetail = computerRoomDetailRepository.findByRoomId(roomId).orElseThrow();
            modelAndView.addObject("roomObject", DtoComputerRoom.builder()
                .roomId(roomId)
                .maxComputerQuantity(computerRoomDetail.getMaxComputerQuantity())
                .availableComputerQuantity(computerRoomDetail.getAvailableComputerQuantity())
                .build()
            );
            return modelAndView;
        } catch (Exception ignored){
            response.sendRedirect("/manager/category/computer-room/computer-room-list");
            return null;
        }
    }

    public ModelAndView getUpdateTeacherPage(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws IOException {
        final String teacherId = request.getParameter("teacherId");
        ModelAndView modelAndView = staticUtilMethods
            .customResponseModelView(request, "update-teacher");

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
