<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Teacher Request Account List</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css"
        integrity="sha512-SzlrxWUlpfuzQ+pcUCosxcglQRNAq/DZjVsC0lE40xsADsfeQoEypE+enwcOiGjk/bSuGGKHEyjSoQ1zVisanQ=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/base.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/teacher-request-list.css">
</head>

<body>
    <%@ include file="/WEB-INF/jsp/category.jsp" %>
    <div id="center-page">
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
        <div id="center-page_list">
            <div id="table-tools">
                <div id="table-description">
                    <b>Danh sách</b>
                    <span id="quantity">${teacherRequestList.size()} yêu cầu</span>
                </div>
                <div id="table-search-box">
                    <select id="search">
                        <option value="" selected disabled hidden>Chọn trường cần tìm</option>
                        <option value="0">Thông tin cơ bản</option>
                        <option value="1">Mã giảng viên</option>
                        <option value="2">Tên môn</option>
                        <option value="3">Mã lớp mở môn</option>
                        <option value="4">Tổ</option>
                        <option value="5">Trạng thái của yêu cầu</option>
                    </select>
                    <input type="text" id="search">
                    <i class="fa-solid fa-magnifying-glass"></i>
                </div>
            </div>
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
                        <th id="subject-name">
                            Tên môn
                            <i class="fa-solid fa-arrow-down-a-z"></i>
                        </th>
                        <th id="grade-id">
                            Mã lớp mở môn
                            <i class="fa-solid fa-arrow-down-a-z"></i>
                        </th>
                        <th id="group-from-subject">
                            Tổ
                            <i class="fa-solid fa-arrow-down-a-z"></i>
                        </th>
                        <th id="view">Chi tiết</th>
                        <th id="add">Tạo lịch</th>
                        <th id="delete">Từ chối</th>
                        </tr>
                </thead>
                <tbody>
                    <c:forEach items="${teacherRequestList}" var="customTeacherRequest">
                        <tr id="${customTeacherRequest.requestId}">
                            <td plain-value="${customTeacherRequest.teacher.lastName}
                                ${customTeacherRequest.teacher.firstName}
                                ${customTeacherRequest.teacher.account.instituteEmail}
                                ${customTeacherRequest.teacher.teacherId}"
                                class="base-profile">
                                <span class="mock-avatar">${customTeacherRequest.teacher.firstName.charAt(0)}</span>
                                <div class="teacher-info">
                                    <b class="teacher-name">[${customTeacherRequest.teacher.teacherId}] ${customTeacherRequest.teacher.lastName} ${customTeacherRequest.teacher.firstName}</b>
                                    <p class="institute-email">${customTeacherRequest.teacher.account.instituteEmail}</p>
                                </div>
                            </td>
                            <c:choose>
                                <c:when test="${customTeacherRequest.interactionStatus == 'PENDING'}">
                                    <td plain-value="Chờ giải quyết Cho giai quyet" class="request-interaction-status">
                                        <span class="status-is-pending">Chờ giải quyết</span>
                                    </td>
                                </c:when>
                                <c:when test="${customTeacherRequest.interactionStatus == 'CANCEL'}">
                                    <td plain-value="Đã huỷ Da huy" class="request-interaction-status">
                                        <span class="status-is-cancel">Đã huỷ</span>
                                    </td>
                                </c:when>
                                <c:when test="${customTeacherRequest.interactionStatus == 'CREATED'}">
                                    <td plain-value="Đã tạo lịch Da tao lich" class="request-interaction-status">
                                        <span class="status-is-true">Đã tạo lịch</span>
                                    </td>
                                </c:when>
                                <c:when test="${customTeacherRequest.interactionStatus == 'DENIED'}">
                                    <td plain-value="Đã từ chối Da tu choi" class="request-interaction-status">
                                        <span class="status-is-false">Đã từ chối</span>
                                    </td>
                                </c:when>
                            </c:choose>
                            <td plain-value="${customTeacherRequest.sectionClass.subject.subjectName}" class="subject-name">
                                ${customTeacherRequest.sectionClass.subject.subjectName}
                            </td>
                            <td plain-value="${customTeacherRequest.sectionClass.grade.gradeId}" class="grade-id">
                                ${customTeacherRequest.sectionClass.grade.gradeId}
                            </td>
                            <td plain-value="${customTeacherRequest.sectionClass.groupFromSubject}" class="group-from-subject">
                                ${customTeacherRequest.sectionClass.groupFromSubject}
                            </td>
                            <td class="table-row-btn view">
                                <a href="/manager/sub-page/practice-schedule/teacher-request-detail?requestId=${customTeacherRequest.requestId}">
                                    <i class="fa-solid fa-eye"></i>
                                </a>
                            </td>
                            <td class="table-row-btn add">
                                <a href="/manager/sub-page/practice-schedule/add-practice-schedule?requestId=${customTeacherRequest.requestId}">
                                    <i class="fa-regular fa-calendar-plus"></i>
                                </a>
                            </td>
                            <td class="table-row-btn delete">
                                <button name="denyTeacherRequestBtn" id="${customTeacherRequest.requestId}">
                                    <i class="fa-regular fa-trash-can"></i>
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <form id="deny-request" action="/service/v1/manager/deny-teacher-request" method="POST" modelAttribute="requestInteraction">
                <input name="requestId" type="number" value="" hidden />
                <input name="interactionReason" type="text" value="" hidden />
            </form>
            <div id="table-footer">
                <c:set var="prefixUrl" value="/manager/category/practice-schedule/teacher-request-list?page=" scope="page"/>
                <div id="table-footer_main">
                    <span class="interact-page-btn">
                        <a href="${prefixUrl}${(currentPage == 1) ? currentPage : (currentPage - 1)}">
                            <i class="fa-solid fa-angle-left"></i>
                        </a>
                    </span>
                    <div id="pages-content">
                        <c:if test="${currentPage > 1}">
                            <span class="index-btn">
                                <a href="${prefixUrl}${currentPage - 1}">${currentPage - 1}</a>
                            </span>
                        </c:if>
                        <span class="index-btn">
                            <a href="${prefixUrl}${currentPage}">${currentPage}</a>
                        </span>
                        <c:if test="${teacherRequestList.size() != 0}">
                            <span class="index-btn">
                                <a href="${prefixUrl}${currentPage + 1}">${currentPage + 1}</a>
                            </span>
                        </c:if>
                    </div>
                    <span class="interact-page-btn">
                        <a href="${prefixUrl}${(teacherRequestList.size() == 0) ? currentPage : (currentPage + 1)}">
                            <i class="fa-solid fa-angle-right"></i>
                        </a>
                    </span>
                </div>
            </div>
        </div>
    </div>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/base.js"></script>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/teacher-request-list.js"></script>
</body>

</html>