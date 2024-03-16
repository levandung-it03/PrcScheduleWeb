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
    <div id="center-page" id="teacher-request-list-page">
        <div class="error-service-message" ${errorMessage == "none" ? "style='display:none'" : "style='display:flex'"}>
            <span>${errorMessage}</span>
            <i id="error-service-message_close-btn" class="fa fa-times-circle" aria-hidden="true"></i>
        </div>
        <div class="succeed-service-message" ${succeedMessage == "none" ? "style='display:none'" : "style='display:flex'"}>
            <span>${succeedMessage}</span>
            <i id="succeed-service-message_close-btn" class="fa fa-times-circle" aria-hidden="true"></i>
        </div>
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
                        <option value="1">Tên môn học</option>
                        <option value="2">Mã lớp mở môn</option>
                        <option value="3">Tổ</option>
                    </select>
                    <input type="text" id="search">
                    <i class="fa-solid fa-magnifying-glass"></i>
                </div>
            </div>
            <form action="/service/v1/manager/delete-teacher-request" method="POST">
                <table>
                    <thead>
                        <tr>
                            <th id="base-profile">
                                Thông tin cơ bản
                                <i class="fa-solid fa-arrow-down-a-z"></i>
                            </th>
                            <th id="teacher-id">
                                Mã giảng viên
                                <i class="fa-solid fa-arrow-down-a-z"></i>
                            </th>
                            <th id="subject-name">
                                Tên môn học
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
                            <th id="delete">Xoá</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${teacherRequestList}" var="teacherReqObj">
                            <tr id="${teacherReqObj.requestId}">
                                <td plain-value="${teacherReqObj.teacher.lastName} ${teacherReqObj.teacher.firstName} ${teacherReqObj.teacher.account.instituteEmail}" class="base-profile">
                                    <span class="mock-avatar">${teacherReqObj.teacher.firstName.charAt(0)}</span>
                                    <div class="teacher-info">
                                        <b class="teacher-name">${teacherReqObj.teacher.lastName} ${teacherReqObj.teacher.firstName}</b>
                                        <p class="institute-email">${teacherReqObj.teacher.account.instituteEmail}</p>
                                    </div>
                                </td>
                                <td plain-value="${teacherReqObj.teacher.teacherId}" class="teacher-id">
                                    ${teacherReqObj.teacher.teacherId}
                                </td>
                                <td plain-value="${teacherReqObj.sectionClass.subject.subjectName}" class="subject-name">
                                    ${teacherReqObj.sectionClass.subject.subjectName}
                                </td>
                                <td plain-value="${teacherReqObj.sectionClass.grade.gradeId}" class="grade-id">
                                    ${teacherReqObj.sectionClass.grade.gradeId}
                                </td>
                                <td plain-value="${teacherReqObj.sectionClass.groupFromSubject}" class="group-from-subject">
                                    ${teacherReqObj.sectionClass.groupFromSubject}
                                </td>
                                <td class="table-row-btn view">
                                    <a href="/manager/sub-page/practice-schedule/teacher-request-detail?requestId=${teacherReqObj.requestId}">
                                        <i class="fa-solid fa-eye"></i>
                                    </a>
                                </td>
                                <td class="table-row-btn add">
                                    <a href="/manager/sub-page/practice-schedule/add-practice-schedule?requestId=${teacherReqObj.requestId}">
                                        <i class="fa-regular fa-calendar-plus"></i>
                                    </a>
                                </td>
                                <td class="table-row-btn delete">
                                    <button name="delete-btn" value="${teacherReqObj.requestId}">
                                        <i class="fa-regular fa-trash-can"></i>
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
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