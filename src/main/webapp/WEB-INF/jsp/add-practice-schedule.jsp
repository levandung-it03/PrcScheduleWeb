<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Date" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Practice Schedule</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css"
          integrity="sha512-SzlrxWUlpfuzQ+pcUCosxcglQRNAq/DZjVsC0lE40xsADsfeQoEypE+enwcOiGjk/bSuGGKHEyjSoQ1zVisanQ=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/base.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/add-practice-schedule.css">
</head>
<body>
    <%@ include file="/WEB-INF/jsp/category.jsp" %>
    <div id="center-page" id="add-computer-room-page">
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
        <%@ include file="/WEB-INF/jsp/header.jsp" %>
        <div class="detail-block">
            <input name="teacherId" type="text" value="${customTeacherRequest.teacher.teacherId}" hidden/>
            <input name="sectionClassId" type="text" value="${customTeacherRequest.sectionClass.sectionClassId}" hidden/>
            <div class="detail-block_description" id="request-info">
                <i class="fa-regular fa-envelope"></i>
                <p>CHI TIẾT YÊU CẦU</p>
            </div>
            <div class="data-row" id="teacher-id">
                <label for="teacher-id">Giảng viên</label>
                <p>${customTeacherRequest.teacher.teacherId}
                    - ${customTeacherRequest.teacher.lastName} ${customTeacherRequest.teacher.firstName}</p>
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
        </div>
        <div id="add-schedule-block">
            <div id="table-tools">
                <div class="form-input" id="semester">
                    <label for="semester">Học kỳ hiện tại</label>
                    <input type="text" disabled
                        value="Học kỳ ${customTeacherRequest.sectionClass.semester.semester} - Năm học: ${customTeacherRequest.sectionClass.semester.rangeOfYear} "/>
                </div>
                <div class="form-input" id="list-of-week">
                    <label for="list-of-week">Chọn tuần muốn xem</label>
                    <select name="list-of-week" id="list-of-week">
                        <%-- Get the milisecond-time of java.sql.Date: startingDate from MySQL --%>
                        <c:set var="startingTime" value="${customTeacherRequest.sectionClass.semester.startingDate.time}"/>

                        <c:forEach begin="0" end="${customTeacherRequest.sectionClass.semester.totalWeek - 1}" var="weekUnit">
                            <c:set var="udpatedStartingTime"
                                value="${startingTime + (weekUnit * 7 * 24 * 60 * 60 * 1000)}"/>
                            <c:set var="endingTime" value="${udpatedStartingTime + (6 * 24 * 60 * 60 * 1000)}"/>
                            <%
                                Date startingDateObj = new Date((Long) pageContext.getAttribute("udpatedStartingTime"));
                                Date endingDateObj = new Date((Long) pageContext.getAttribute("endingTime"));
                                pageContext.setAttribute("startingDateObj", startingDateObj);
                                pageContext.setAttribute("endingDateObj", endingDateObj);
                            %>
                            <fmt:formatDate var="startingDateResult" value="${startingDateObj}" pattern="dd/MM/yyyy"/>
                            <fmt:formatDate var="endingDateResult" value="${endingDateObj}" pattern="dd/MM/yyyy"/>
                            <c:set var="week" value="${customTeacherRequest.sectionClass.semester.firstWeek + weekUnit}"/>
                            <option week="${week}" startingDate="${startingDateResult}">
                                Tuần ${week} - Ngày ${startingDateResult} đến ${endingDateResult}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <table id="subject-schedule">
                <thead>
                <tr>
                    <th class="table-description-column-item change-schedule-btn">
                        <span id="left"><i style="padding-right: 5px;" class="fa-solid fa-arrow-left"></i>Trước</span>
                    </th>
                    <th class="schedule-item" id="2"><span>Thứ 2</span></th>
                    <th class="schedule-item" id="3"><span>Thứ 3</span></th>
                    <th class="schedule-item" id="4"><span>Thứ 4</span></th>
                    <th class="schedule-item" id="5"><span>Thứ 5</span></th>
                    <th class="schedule-item" id="6"><span>Thứ 6</span></th>
                    <th class="schedule-item" id="7"><span>Thứ 7</span></th>
                    <th class="schedule-item" id="8" style="border-color: black;"><span>Chủ nhật</span></th>
                    <th class="table-description-column-item change-schedule-btn">
                        <span id="right">Sau<i style="padding-left: 5px;" class="fa-solid fa-arrow-right"></i></span>
                    </th>
                    <script>
                        //--Prevent selected of button-text with double click.
                        document.querySelectorAll('th span').forEach(tag => {
                            tag.addEventListener('mousedown', function (e) {
                                e.preventDefault();
                            }, false);
                        })
                    </script>
                </tr>
                </thead>
                <tbody>
                <c:forEach begin="1" end="16" var="period">
                    <tr class="period-row" id="${period}">
                        <td class="table-description-column-item">
                            <span class="schedule-data-item">Tiết ${period}</span>
                        </td>
                        <c:forEach begin="2" end="8" var="day">
                            <td class="schedule-item" period="${period}" day="${day}">
                                <span class="schedule-data-item"></span>
                            </td>
                        </c:forEach>
                        <td class="table-description-column-item">
                            <span class="schedule-data-item"></span>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
                <thead>
                <tr>
                    <th class="table-description-column-item"></th>
                    <th class="schedule-item" style="border-color: black transparent black transparent"></th>
                    <th class="schedule-item"></th>
                    <th class="schedule-item"></th>
                    <th class="schedule-item"></th>
                    <th class="schedule-item"></th>
                    <th class="schedule-item"></th>
                    <th class="schedule-item"></th>
                    <th class="table-description-column-item"></th>
                </tr>
                </thead>
            </table>
            <span id="convert-btn">Chuyển đổi</span>
        </div>
        <div id="adjust-schedule-block">
            <table id="ajdust-subject-schedule">
                <thead>
                    <tr>
                        <th id="week">Tuần thực hành</th>
                        <th id="day">Thứ</th>
                        <th id="period">Tiết thực hành</th>
                        <th id="roomId">Phòng thực hành</th>
                        <th id="delete">Xoá</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td style="font-size: 1.2rem;" class="week">...</td>
                        <td style="font-size: 1.2rem;" class="day">...</td>
                        <td style="font-size: 1.2rem;" class="period">...</td>
                        <td style="font-size: 1.2rem;" class="roomId">...</td>
                        <td style="font-size: 1.2rem;" class="delete">...</td>
                    </tr>
                </tbody>
            </table>
            <c:choose>
                <c:when test="${updatedPracticeSchedule == null}">
                    <form method="POST" modelAttribute="practiceScheduleObj" action="/service/v1/manager/add-practice-schedule">
                </c:when>
                <c:otherwise>
                    <form method="POST" modelAttribute="practiceScheduleObj" action="/service/v1/manager/update-practice-schedule">
                </c:otherwise>
            </c:choose>
                <input name="teacherId" type="text" value="${customTeacherRequest.teacher.teacherId}" hidden>
                <input name="requestId" type="number" value="${customTeacherRequest.requestId}" hidden>
                <input name="sectionClassId" type="number" value="${customTeacherRequest.sectionClass.sectionClassId}" hidden>
                <c:if test="${updatedPracticeSchedule != null}">
                    <input name="updatedPracticeScheduleId" type="number" value="${updatedPracticeSchedule.subjectScheduleId}" hidden>
                </c:if>
                <input name="practiceScheduleListAsString" type="text" value="" hidden>
                <span id="submit">Xác nhận</span>
            </form>
        </div>
    </div>
    <div style="display:none;" id="hidden-blocks">
        <div id="all-unavailable-subject-schedule">
            <c:forEach items="${subjectScheduleList}" var="subjectSchedule" varStatus="iterator">
                <div class="subject-schedule-hidden-block" id="${iterator.index}">
                    <c:if test="${updatedPracticeSchedule != null}">
                        <span class="data-field" name="subjectScheduleId" type="text">${subjectSchedule.subjectScheduleId}</span>
                    </c:if>
                    <span class="data-field" name="subjectName" type="text">${subjectSchedule.subjectName}</span>
                    <span class="data-field" name="day" type="number">${subjectSchedule.day}</span>
                    <span class="data-field" name="startingWeek" type="number">${subjectSchedule.startingWeek}</span>
                    <span class="data-field" name="totalWeek" type="number">${subjectSchedule.totalWeek}</span>
                    <span class="data-field" name="startingPeriod" type="number">${subjectSchedule.startingPeriod}</span>
                    <span class="data-field" name="lastPeriod" type="number">${subjectSchedule.lastPeriod}</span>
                    <span class="data-field" name="roomId" type="text">${subjectSchedule.roomId}</span>
                </div>
            </c:forEach>
        </div>
        <div id="all-practice-schedule-in-this-semester">
            <c:forEach items="${allPrcScheduleInSemester}" var="practiceSchedule" varStatus="iterator">
                <div class="subject-schedule-hidden-block" id="${iterator.index}">
                    <c:if test="${updatedPracticeSchedule != null}">
                        <span class="data-field" name="subjectScheduleId" type="text">${practiceSchedule.subjectScheduleId}</span>
                    </c:if>
                    <span class="data-field" name="day" type="number">${practiceSchedule.day}</span>
                    <span class="data-field" name="startingWeek" type="number">${practiceSchedule.startingWeek}</span>
                    <span class="data-field" name="totalWeek" type="number">${practiceSchedule.totalWeek}</span>
                    <span class="data-field" name="startingPeriod" type="number">${practiceSchedule.startingPeriod}</span>
                    <span class="data-field" name="lastPeriod" type="number">${practiceSchedule.lastPeriod}</span>
                    <span class="data-field" name="roomId" type="text">${practiceSchedule.roomId}</span>
                </div>
            </c:forEach>
        </div>
        <div id="all-computer-room">
            <c:forEach items="${computerRoomList}" var="roomId" varStatus="iterator">
                <span class="item-in-list" name="roomId" type="text">${roomId}</span>
            </c:forEach>
        </div>
        <c:if test="${updatedPracticeSchedule != null}">
            <div id="updated-subject-schedule-block">
                <span class="data-field" name="subjectScheduleId" type="text">${updatedPracticeSchedule.subjectScheduleId}</span>
                <span class="data-field" name="subjectName" type="text">${updatedPracticeSchedule.sectionClass.subject.subjectName}</span>
                <span class="data-field" name="day" type="number">${updatedPracticeSchedule.day}</span>
                <span class="data-field" name="startingWeek" type="number">${updatedPracticeSchedule.startingWeek}</span>
                <span class="data-field" name="totalWeek" type="number">${updatedPracticeSchedule.totalWeek}</span>
                <span class="data-field" name="startingPeriod" type="number">${updatedPracticeSchedule.startingPeriod}</span>
                <span class="data-field" name="lastPeriod" type="number">${updatedPracticeSchedule.lastPeriod}</span>
                <span class="data-field" name="roomId" type="text">${updatedPracticeSchedule.classroom.roomId}</span>
            </div>
        </c:if>
    </div>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/base.js"></script>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/add-practice-schedule.js"></script>
</body>
</html>
