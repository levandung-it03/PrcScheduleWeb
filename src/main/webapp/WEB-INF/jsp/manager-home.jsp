<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Home</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css"
        integrity="sha512-SzlrxWUlpfuzQ+pcUCosxcglQRNAq/DZjVsC0lE40xsADsfeQoEypE+enwcOiGjk/bSuGGKHEyjSoQ1zVisanQ=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/base.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/manager-home.css">
</head>
<body>
    <%@ include file="/WEB-INF/jsp/manager-category.jsp" %>
    <div class="center-page" id="manager-home-page">
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
        <div id="manager-home">
            <div id="redirecting-blocks">
                <a id="pending-requests" class="redirecting-blocks_items" href="/manager/category/practice-schedule/teacher-request-list">
                    <div class="redirecting-blocks_items_contents">
                        <span class="redirecting-blocks_items_contents_quantity">${pendingRequests}</span>
                        <i class="redirecting-blocks_items_contents_description">
                            Yêu cầu đang chờ giải quyết
                        </i>
                    </div>
                    <span class="redirecting-blocks_items_labels">
                        <i class="fa-regular fa-envelope"></i>
                    </span> 
                </a>
                <a id="created-requests" class="redirecting-blocks_items" href="/manager/category/practice-schedule/teacher-request-list">
                    <div class="redirecting-blocks_items_contents">
                        <span class="redirecting-blocks_items_contents_quantity">${createdRequests}</span>
                        <i class="redirecting-blocks_items_contents_description">
                            Yêu cầu đã được xử lý
                        </i>
                    </div>
                    <span class="redirecting-blocks_items_labels">
                        <i class="fa-solid fa-envelope-circle-check"></i>
                    </span> 
                </a>
                <a id="successfully-created-requests" class="redirecting-blocks_items" href="/manager/category/practice-schedule/teacher-request-list">
                    <div class="redirecting-blocks_items_contents">
                        <span class="redirecting-blocks_items_contents_quantity">${successfullyCreatedRequestsRatio}%</span>
                        <i class="redirecting-blocks_items_contents_description">
                            Tỉ lệ yêu cầu được tạo lịch thành công
                        </i>
                    </div>
                    <span class="redirecting-blocks_items_labels">
                        <i class="fa-solid fa-envelope-circle-check"></i>
                    </span> 
                </a>
                <a id="accounts" class="redirecting-blocks_items" href="/manager/category/teacher/teacher-account-list">
                    <div class="redirecting-blocks_items_contents">
                        <span class="redirecting-blocks_items_contents_quantity">${accountsQuantity}</span>
                        <i class="redirecting-blocks_items_contents_description">
                            Người dùng hiện tại
                        </i>
                    </div>
                    <span class="redirecting-blocks_items_labels">
                        <i class="fa-solid fa-users-between-lines"></i>
                    </span>
                </a>
                <a id="computer-rooms" class="redirecting-blocks_items" href="/manager/category/computer-room/computer-room-list">
                    <div class="redirecting-blocks_items_contents">
                        <span class="redirecting-blocks_items_contents_quantity">${computerRoomQuantity}</span>
                        <i class="redirecting-blocks_items_contents_description">
                            Phòng thực hành hiện tại đang hoạt động
                        </i>
                    </div>
                    <span class="redirecting-blocks_items_labels">
                        <i class="fa-solid fa-house-laptop"></i>
                    </span> 
                </a>
            </div>
            <div id="pending-teacher-request-list">
                <div class="title" style="height: 71px;">
                    <span class="table-description">
                        Yêu cầu đang chờ
                        <span id="quantity">${pendingRequestsList.size()} yêu cầu</span>
                    </span>
                </div>
                <div id="table-as-list">
                    <table>
                        <thead>
                            <tr>
                                <th id="base-profile">
                                    Thông tin cơ bản
                                    <i class="fa-solid fa-arrow-down-a-z"></i>
                                </th>
                                <th id="request-interaction-status">
                                    Trạng thái
                                    <i class="fa-solid fa-arrow-down-a-z"></i>
                                </th>
                                <th id="section-class-info">
                                    Thông tin lớp học phần
                                    <i class="fa-solid fa-arrow-down-a-z"></i>
                                </th>
                                <th id="add">Tạo lịch</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:if test="${pendingRequestsList.size() == 0}">
                                <tr><td style="width:100%; text-align:center;">Không có yêu cầu nào đang chờ để tạo lịch.</td></tr>
                            </c:if>
                            <c:forEach items="${pendingRequestsList}" var="teacherRequest">
                                <tr id="${teacherRequest.requestId}">
                                    <td plain-value="" class="base-profile">
                                        <span class="mock-avatar">
                                            <i style="font-style:normal">${teacherRequest.teacher.firstName.charAt(0)}</i>
                                        </span>
                                        <div class="teacher-info">
                                            <b class="teacher-name">[${teacherRequest.teacher.teacherId}]
                                                ${teacherRequest.teacher.lastName}
                                                ${teacherRequest.teacher.firstName}</b>
                                            <p class="institute-email">${teacherRequest.teacher.account.instituteEmail}</p>
                                        </div>
                                    </td>
                                    <c:choose>
                                        <c:when test="${teacherRequest.interactionStatus == 'PENDING'}">
                                            <td plain-value="Chờ giải quyết Cho giai quyet"
                                                class="request-interaction-status">
                                                <span class="status-is-pending">Chờ giải quyết</span>
                                            </td>
                                        </c:when>
                                        <c:when test="${teacherRequest.interactionStatus == 'CANCEL'}">
                                            <td plain-value="Đã huỷ Da huy" class="request-interaction-status">
                                                <span class="status-is-cancel">Đã huỷ</span>
                                            </td>
                                        </c:when>
                                        <c:when test="${teacherRequest.interactionStatus == 'CREATED'}">
                                            <td plain-value="Đã tạo lịch Da tao lich"
                                                class="request-interaction-status">
                                                <span class="status-is-true">Đã tạo lịch</span>
                                            </td>
                                        </c:when>
                                        <c:when test="${teacherRequest.interactionStatus == 'DENIED'}">
                                            <td plain-value="Đã từ chối Da tu choi" class="request-interaction-status">
                                                <span class="status-is-false">Đã từ chối</span>
                                            </td>
                                        </c:when>
                                    </c:choose>
                                    <td plain-value="" class="section-class-info">
                                        ${teacherRequest.sectionClass.grade.gradeId} - ${teacherRequest.sectionClass.subject.subjectName}
                                        - ${teacherRequest.sectionClass.groupFromSubject}
                                    </td>
                                    <td class="table-row-btn add">
                                        <a href="/manager/sub-page/practice-schedule/add-practice-schedule?requestId=${teacherRequest.requestId}">
                                            <i class="fa-regular fa-calendar-plus"></i>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <div id="main-chart">
                <div class="title">
                    <span id="chart-title">
                        Thống kê
                        <i style="color:grey;font-weight: 100;font-size: 1rem;">(toàn bộ yêu cầu trong phần mềm)</i>
                    </span>
                </div>
                <div id="chart-container">
                    <canvas id="myChart" style="max-width:400px"></canvas>
                    <div id="labels">
                    </div>
                </div>
            </div>
        </div>
        <div id="hidden-blocks" style="display: none;">
            <span class="hidden-items" type="number" name="pendingRequests">${pendingRequests}</span>
            <span class="hidden-items" type="number" name="createdRequests">${createdRequests}</span>
            <span class="hidden-items" type="number" name="deniedRequests">${deniedRequests}</span>
            <span class="hidden-items" type="number" name="canceledRequests">${canceledRequests}</span>
        </div>
        <%@ include file="/WEB-INF/jsp/footer.jsp" %>
    </div>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/base.js"></script>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/header.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.js"></script>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/manager-home.js"></script>
</body>
</html>