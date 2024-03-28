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
        crossorigin="anonymous" referrerpolicy="no-referrer" />
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
        <form method="POST" action="/service/v1/manager/update-teacher" modelAttribute="teacher">
            <div class="detail-block">
                <input name="teacherId" type="text" value="${teacherRequest.teacher.teacherId}" hidden/>
                <input name="sectionClassId" type="text" value="${teacherRequest.sectionClass.sectionClassId}" hidden/>
                <div class="detail-block_description" id="request-info">
                    <i class="fa-regular fa-envelope"></i>
                    <p>CHI TIẾT YÊU CẦU</p>
                </div>
                <div class="data-row" id="teacher-id">
                    <label for="teacher-id">Giảng viên</label>
                    <p>${teacherRequest.teacher.teacherId} - ${teacherRequest.teacher.lastName} ${teacherRequest.teacher.firstName}</p>
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
            <div id="add-schedule-block">
                <div id="table-tools">
                    <div class="form-input" id="semester">
                        <label for="semester">Học kỳ hiện tại</label>
                        <input type="text" disabled
                        value="Học kỳ ${teacherRequest.sectionClass.semester.semester} - Năm học: ${teacherRequest.sectionClass.semester.rangeOfYear} "/>
                    </div>
                    <div class="form-input" id="list-of-week">
                        <label for="list-of-week">Chọn tuần muốn xem</label>
                        <select name="list-of-week" id="list-of-week">
                            <%-- Get the milisecond-time of java.sql.Date: startingDate from MySQL --%>
                            <c:set var="startingTime" value="${teacherRequest.sectionClass.semester.startingDate.time}" />
                            
                            <c:forEach begin="0" end="${teacherRequest.sectionClass.semester.totalWeek - 1}" var="weekUnit">
                                <c:set var="udpatedStartingTime" value="${startingTime + (weekUnit * 7 * 24 * 60 * 60 * 1000)}" />
                                <c:set var="endingTime" value="${udpatedStartingTime + (6 * 24 * 60 * 60 * 1000)}" />
                                <%
                                    Date startingDateObj = new Date((Long) pageContext.getAttribute("udpatedStartingTime"));
                                    Date endingDateObj = new Date((Long) pageContext.getAttribute("endingTime"));
                                    pageContext.setAttribute("startingDateObj", startingDateObj);
                                    pageContext.setAttribute("endingDateObj", endingDateObj);
                                %>
                                <fmt:formatDate var="startingDateResult" value="${startingDateObj}" pattern="dd/MM/yyyy" />
                                <fmt:formatDate var="endingDateResult" value="${endingDateObj}" pattern="dd/MM/yyyy" />
                                <c:set var="week" value="${teacherRequest.sectionClass.semester.firstWeek + weekUnit}" />
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
                                //--Prevent selected text with double click.
                                document.querySelectorAll('th span').forEach(tag => {
                                    tag.addEventListener('mousedown', function(e) { e.preventDefault(); }, false);
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
                            <th id="firstWeek">Tuần bắt đầu</th>
                            <th id="totalWeek">Tổng tuần</th>
                            <th id="day">Thứ</th>
                            <th id="startingPeriod">Tiết bắt đầu</th>
                            <th id="lastPeriod">Tiết kết thúc</th>
                            <th id="roomId">Phòng thực hành</th>
                            <th id="delete">Xoá</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td style="font-size: 1.2rem;" class="firstWeek">...</td>
                            <td style="font-size: 1.2rem;" class="totalWeek">...</td>
                            <td style="font-size: 1.2rem;" class="day">...</td>
                            <td style="font-size: 1.2rem;" class="startingPeriod">...</td>
                            <td style="font-size: 1.2rem;" class="lastPeriod">...</td>
                            <td style="font-size: 1.2rem;" class="roomId">...</td>
                            <td style="font-size: 1.2rem;" class="delete">...</td>
                        </tr>
                    </tbody>
                </table>
                <input type="submit" value="Xác nhận">
            </div>
        </form>
    </div>
    <div style="display:none;" id="hidden-blocks">
        <div id="subject-schedule-of-grade">
            <c:forEach items="${subjectScheduleList}" var="subjectSchedule" varStatus="iterator">
                <div class="subject-schedule-hidden-block" id="${iterator.index}">
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
        <div id="all-subject-schedule-in-this-semester">
            <c:forEach items="${allPrcScheduleInSemester}" var="practiceSchedule" varStatus="iterator">
                <div class="subject-schedule-hidden-block" id="${iterator.index}">
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
        <div id="student-quantity">
            <span class="data-field" name="studentQuantity">${studentQuantity}</span>
        </div>
    </div>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/base.js"></script>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/add-practice-schedule.js"></script>
</body>
</html>
