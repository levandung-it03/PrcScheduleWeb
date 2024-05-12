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
    <%@ include file="/WEB-INF/jsp/manager-category.jsp" %>
    <div class="center-page" id="teacher-request-detail-page">
        <div id="message-blocks">
            <c:if test="${errorMessage != null}">
                <div class="error-service-message">
                    <span>${errorMessage}</span>
                    <i id="error-service-message_close-btn" class="fa fa-times-circle" aria-hidden="true"></i>
                </div>
            </c:if>
            <c:if test="${succeedMessage != null}">
                <div class="succeed-service-message">
                    <span>${succeedMessage}</span>
                    <i id="succeed-service-message_close-btn" class="fa fa-times-circle" aria-hidden="true"></i>
                </div>
            </c:if>
        </div>
        <%@ include file="/WEB-INF/jsp/header.jsp" %>
        <div class="detail-block">
            <div class="detail-block_description" id="teacher-info">
                <i class="fa-regular fa-address-card"></i>
                <p>THÔNG TIN GIẢNG VIÊN</p>
            </div>
            <div class="data-row" id="teacher-id">
                <label for="teacher-id">Mã giảng viên</label>
                <p>${customTeacherRequest.teacher.teacherId}</p>
            </div>
            <div class="data-row" id="full-name">
                <label for="full-name">Họ và tên</label>
                <p>${customTeacherRequest.teacher.lastName} ${customTeacherRequest.teacher.firstName}</p>
            </div>
            <div class="data-row" id="institute-email">
                <label for="institute-email">Email</label>
                <p>${customTeacherRequest.teacher.account.instituteEmail}</p>
            </div>
            <div class="data-row" id="phone">
                <label for="phone">Số điện thoại</label>
                <p>${customTeacherRequest.teacher.phone}</p>
            </div>
        </div>
        <div class="detail-block">
            <div class="detail-block_description" id="request-info">
                <i class="fa-regular fa-envelope"></i>
                <p>CHI TIẾT YÊU CẦU</p>
            </div>
            <div class="data-row" id="subject-name">
                <label for="subject-name">Tên môn học</label>
                <p>${customTeacherRequest.sectionClass.subject.subjectName}</p>
            </div>
            <div class="data-row" id="grade-id">
                <label for="grade-id">Lớp mở môn</label>
                <p>${customTeacherRequest.sectionClass.grade.gradeId}</p>
            </div>
            <div class="data-row" id="group-from-subject">
                <label for="group-from-subject">Tổ mà giảng viên dạy</label>
                <p>${customTeacherRequest.sectionClass.groupFromSubject}</p>
            </div>
            <div class="data-row" id="request-message-detail">
                <label for="request-message-detail">Mô tả yêu cầu</label>
                <p>${customTeacherRequest.requestMessageDetail}</p>
            </div>
            <div class="data-row" id="interaction-request-reason">
                <label for="interaction-request-reason">Lời nhắn khi tương tác</label>
                <p>${customTeacherRequest.interactRequestReason == null ? 'Không' : customTeacherRequest.interactRequestReason}</p>
            </div>
        </div>
        <div class="detail-block" id="practice-schedule">
            <div class="detail-block_description" id="practice-schedule-info">
                <i class="fa-regular fa-calendar-minus"></i>
                <p>LỊCH THỰC HÀNH</p>
            </div>
            <form action="/service/v1/manager/delete-practice-schedule" method="POST">
                <table>
                    <thead>
                        <tr>
                            <th id="startingWeek">Tuần bắt đầu</th>
                            <th id="totalWeek">Tổng tuần</th>
                            <th id="day">Thứ</th>
                            <th id="startingPeriod">Tiết bắt đầu</th>
                            <th id="lastPeriod">Tiết kết thúc</th>
                            <th id="roomId" style="${role == 'teacher' ? 'width:27%' : ''}">Phòng thực hành</th>
                            <c:if test="${role == 'manager'}">
                                <th id="update">Cập nhật</th>
                                <th id="delete">Xoá</th>
                            </c:if>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${practiceSchedules}" var="practiceSchedule">
                            <c:choose>
                                <c:when test="${practiceSchedule.day == null}">
                                    <tr><td style="width:100%">Chưa có lịch thực hành nào được thêm từ yêu cầu này.</td></tr>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td class="startingWeek">${practiceSchedule.startingWeek}</td>
                                        <td class="totalWeek">${practiceSchedule.totalWeek}</td>
                                        <td class="day">${practiceSchedule.day}</td>
                                        <td class="startingPeriod">${practiceSchedule.startingPeriod}</td>
                                        <td class="lastPeriod">${practiceSchedule.lastPeriod}</td>
                                        <td class="roomId" style="${role == 'teacher' ? 'width:27%' : ''}">
                                            ${practiceSchedule.classroom.roomId}
                                        </td>
                                        <c:if test="${role == 'manager'}">
                                            <td class="update table-row-btn">
                                                <a href="/manager/sub-page/practice-schedule/update-practice-schedule?practiceScheduleId=${practiceSchedule.subjectScheduleId}">
                                                    <i class="fa-regular fa-pen-to-square"></i>
                                                </a>
                                            </td>
                                            <td class="delete table-row-btn">
                                                <button name="deleteBtn" value="${practiceSchedule.subjectScheduleId}">
                                                    <i class="fa-regular fa-trash-can"></i>
                                                </button>
                                            </td>
                                        </c:if>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </tbody>
                </table>
            </form>
        </div>
        <div class="detail-block" id="student-list">
            <div class="table-tools">
                <div class="table-description">
                    <i class="fa-solid fa-users"></i>
                    <p>DANH SÁCH SINH VIÊN</p>
                    <span id="quantity">${students.size()} người</span>
                </div>
                <div class="table-search-box">
                    <select id="search">
                        <option value="" selected disabled hidden>Chọn trường cần tìm</option>
                        <option value="0">Mã sinh viên</option>
                        <option value="1">Họ sinh viên</option>
                        <option value="2">Tên</option>
                        <option value="4">Giới tính</option>
                        <option value="5">Mã lớp</option>
                        <option value="6">Địa chỉ email</option>
                    </select>
                    <input type="text" id="search">
                    <i class="fa-solid fa-magnifying-glass"></i>
                </div>
            </div>
            <table>
                <thead>
                    <tr>
                        <th id="studentId">
                            Mã sinh viên
                            <i class="fa-solid fa-arrow-down-a-z"></i>
                        </th>
                        <th id="lastName">
                            Họ sinh viên
                            <i class="fa-solid fa-arrow-down-a-z"></i>
                        </th>
                        <th id="firstName">
                            Tên
                            <i class="fa-solid fa-arrow-down-a-z"></i>
                        </th>
                        <th id="gender">
                            Giới tính
                            <i class="fa-solid fa-arrow-down-a-z"></i>
                        </th>
                        <th id="gradeId">
                            Mã lớp
                            <i class="fa-solid fa-arrow-down-a-z"></i>    
                        </th>
                        <th id="instituteEmail">
                            Địa chỉ email
                            <i class="fa-solid fa-arrow-down-a-z"></i>  
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${students.size() == 0}">
                            <tr><td style="width:100%; text-align:center;">Không có sinh viên thuộc lớp này.</td></tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${students}" var="student">                        
                                <tr>
                                    <td plain-value="${student.studentId}" class="studentId">${student.studentId}</td>
                                    <td plain-value="${student.lastName}" class="lastName">${student.lastName}</td>
                                    <td plain-value="${student.firstName}" class="firstName">${student.firstName}</td>
                                    <td plain-value="${(student.gender == "BOY") ? "Nam" : "Nữ"}" class="gender">${(student.gender == "BOY") ? "Nam" : "Nữ"}</td>
                                    <td plain-value="${student.grade.gradeId}" class="gradeId">${student.grade.gradeId}</td>
                                    <td plain-value="${student.instituteEmail}" class="instituteEmail">${student.instituteEmail}</td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
        <%@ include file="/WEB-INF/jsp/footer.jsp" %>
    </div>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/base.js"></script>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/header.js"></script>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/teacher-request-detail.js"></script>
</body>
</html>
