<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Teacher Request Detail</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css"
        integrity="sha512-SzlrxWUlpfuzQ+pcUCosxcglQRNAq/DZjVsC0lE40xsADsfeQoEypE+enwcOiGjk/bSuGGKHEyjSoQ1zVisanQ=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/base.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/teacher-request-detail.css">
</head>
<body>
    <%@ include file="/WEB-INF/jsp/category.jsp" %>
    <div id="center-page" id="teacher-request-detail-page">
        <%@ include file="/WEB-INF/jsp/header.jsp" %>
        <div class="detail-block">
            <div class="detail-block_description" id="teacher-info">
                <i class="fa-regular fa-address-card"></i>
                <p>THÔNG TIN GIẢNG VIÊN</p>
            </div>
            <div class="data-row" id="teacher-id">
                <label for="teacher-id">Mã giảng viên</label>
                <p>${teacherRequest.teacher.teacherId}</p>
            </div>
            <div class="data-row" id="full-name">
                <label for="full-name">Họ và tên</label>
                <p>${teacherRequest.teacher.lastName} ${teacherRequest.teacher.firstName}</p>
            </div>
            <div class="data-row" id="institute-email">
                <label for="institute-email">Email</label>
                <p>${teacherRequest.teacher.account.instituteEmail}</p>
            </div>
            <div class="data-row" id="phone">
                <label for="phone">Số điện thoại</label>
                <p>${teacherRequest.teacher.phone}</p>
            </div>
        </div>
        <div class="detail-block">
            <div class="detail-block_description" id="request-info">
                <i class="fa-regular fa-envelope"></i>
                <p>CHI TIẾT YÊU CẦU</p>
            </div>
            <div class="data-row" id="subject-name">
                <label for="subject-name">Tên môn học</label>
                <p>${teacherRequest.sectionClass.subject.subjectName}</p>
            </div>
            <div class="data-row" id="grade-id">
                <label for="grade-id">Lớp mở môn</label>
                <p>${teacherRequest.sectionClass.grade.gradeId}</p>
            </div>
            <div class="data-row" id="group-from-subject">
                <label for="group-from-subject">Tổ mà giảng viên dạy</label>
                <p>${teacherRequest.sectionClass.groupFromSubject}</p>
            </div>
            <div class="data-row" id="request-message-detail">
                <label for="request-message-detail">Mô tả yêu cầu</label>
                <p>${teacherRequest.requestMessageDetail}</p>
            </div>
        </div>
        <div class="detail-block" id="practice-schedule">
            <div class="detail-block_description" id="practice-schedule-info">
                <i class="fa-regular fa-calendar-minus"></i>
                <p>LỊCH THỰC HÀNH</p>
            </div>
            <form action="" method="">
                <table>
                    <thead>
                        <tr>
                            <th id="startingWeek">Tuần bắt đầu</th>
                            <th id="totalWeek">Tổng tuần</th>
                            <th id="day">Thứ</th>
                            <th id="startingPeriod">Tiết bắt đầu</th>
                            <th id="lastPeriod">Tiết kết thúc</th>
                            <th id="roomId">Phòng thực hành</th>
                            <th id="delete">Xoá</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${not empty practiceSchedules}">
                                <c:forEach items="${practiceSchedules}" var="practiceSchedule">
                                    <tr>
                                        <td class="startingWeek">${practiceSchedule.startingWeek}</td>
                                        <td class="totalWeek">${practiceSchedule.totalWeek}</td>
                                        <td class="day">${practiceSchedule.day}</td>
                                        <td class="startingPeriod">${practiceSchedule.startingPeriod}</td>
                                        <td class="lastPeriod">${practiceSchedule.lastPeriod}</td>
                                        <td class="roomId">${practiceSchedule.classroom.roomId}</td>
                                        <td class="delete table-row-btn">
                                            <button id="${teacherRequest.requestId}_${practiceSchedule.subjectScheduleId}">
                                                <i class="fa-regular fa-trash-can"></i>
                                            </button>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr><td style="width:100%">Chưa có lịch thực hành nào được thêm từ yêu cầu này.</td></tr>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </form>
        </div>
    </div>
    </div>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/base.js"></script>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/teacher-request-detail.js"></script>
</body>
</html>
