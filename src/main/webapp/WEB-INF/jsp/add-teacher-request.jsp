<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Date" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Teacher Request</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css"
        integrity="sha512-SzlrxWUlpfuzQ+pcUCosxcglQRNAq/DZjVsC0lE40xsADsfeQoEypE+enwcOiGjk/bSuGGKHEyjSoQ1zVisanQ=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/base.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/add-teacher-request.css">
</head>
<body>
    <%@ include file="/WEB-INF/jsp/teacher-category.jsp" %>
    <div class="center-page" id="add-teacher-request-page">
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
        <div id="schedule-block">
            <div class="table-tools">
                <div class="form-input" id="semester">
                    <label for="semester">Học kỳ hiện tại</label>
                    <input type="text" value="Học kỳ ${currentSemester.semester} - Năm học: ${currentSemester.rangeOfYear}" disabled/>
                </div>
                <div class="form-input" id="list-of-week">
                    <label for="list-of-week">Chọn tuần muốn xem</label>
                    <select name="list-of-week" id="list-of-week">
                        <%-- Get the milisecond-time of java.sql.Date: startingDate from MySQL --%>
                        <c:set var="startingTime" value="${currentSemester.startingDate.time}"/>

                        <c:forEach begin="0" end="${currentSemester.totalWeek - 1}" var="weekUnit">
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
                            <c:set var="week" value="${currentSemester.firstWeek + weekUnit}"/>
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
            </div>
        </div>
        <c:choose>
            <c:when test="${action == 'add'}">
                <form method="POST" action="/service/v1/teacher/add-teacher-request" modelAttribute="teacherRequest">
            </c:when>
            <c:otherwise>
                <form method="POST" action="/service/v1/teacher/update-teacher-request?requestId=${teacherRequest.requestId}"
                modelAttribute="teacherRequest">
            </c:otherwise>
        </c:choose>
            <div class="form-input" id="currentSemester">
                <label for="currentSemester">Học kỳ hiện tại</label>
                <input name="currentSemester" type="text" disabled
                    value="Năm ${currentSemester.rangeOfYear} - Học kỳ ${currentSemester.semester}"/>
            </div>
            <div class="form-input" id="sectionClassId">
                <label for="sectionClassId">Lớp học phần</label>
                <select data="${sectionClass.sectionClassId}" name="sectionClassId">
                    <c:forEach items="${sectionClassList}" var="sectionClass">
                        <option value="${sectionClass.sectionClassId}">
                            ${sectionClass.grade.gradeId} - ${sectionClass.groupFromSubject} - ${sectionClass.subject.subjectName}
                        </option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-input" id="requestMessageDetail">
                <label for="requestMessageDetail">Lời nhắn</label>
                <input name="requestMessageDetail" type="text" maxlength="100000" value="${teacherRequest.requestMessageDetail}" autocomplete="off"/>
            </div>
            <input name="pageNumber" value="${pageNumber}" hidden/>
            <input type="submit" value="Xác nhận"/>
        </form>
        <%@ include file="/WEB-INF/jsp/footer.jsp" %>
    </div>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/base.js"></script>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/header.js"></script>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/add-teacher-request.js"></script>
</body>
</html>


