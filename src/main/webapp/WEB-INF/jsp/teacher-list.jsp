<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Teacher List</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css"
        integrity="sha512-SzlrxWUlpfuzQ+pcUCosxcglQRNAq/DZjVsC0lE40xsADsfeQoEypE+enwcOiGjk/bSuGGKHEyjSoQ1zVisanQ=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/base.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/teacher-list.css">
</head>

<body>
    <%@ include file="/WEB-INF/jsp/manager-category.jsp" %>
    <div class="center-page" id="teacher-list-page">
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
        <div class="center-page_list">
            <div class="table-tools">
                <div class="table-description">
                    <b>Danh sách</b>
                    <span id="quantity">${teacherList.size()} người</span>
                </div>
                <div class="table-search-box">
                    <select id="search">
                        <option value="" selected disabled hidden>Chọn trường cần tìm</option>
                        <option value="0">Thông tin cơ bản</option>
                        <option value="1">Mã giảng viên</option>
                        <option value="2">Mã khoa</option>
                        <option value="3">Ngày sinh</option>
                        <option value="4">Giới tính</option>
                        <option value="5">Điện thoại</option>
                        <option value="6">Trạng thái</option>
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
                        <th id="teacher-id">
                            Mã giảng viên
                            <i class="fa-solid fa-arrow-down-a-z"></i>
                        </th>
                        <th id="department-id">
                            Mã khoa
                            <i class="fa-solid fa-arrow-down-a-z"></i>
                        </th>
                        <th id="birthday">
                            Ngày sinh
                            <i class="fa-solid fa-arrow-down-a-z"></i>
                        </th>
                        <th id="gender">
                            Giới tính
                            <i class="fa-solid fa-arrow-down-a-z"></i>
                        </th>
                        <th id="phone">
                            Điện thoại
                            <i class="fa-solid fa-arrow-down-a-z"></i>
                        </th>
                        <th id="update">Cập nhật</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${teacherList}" var="teacher">
                        <tr id="${teacher.teacherId}">
                            <td plain-value="${teacher.lastName} ${teacher.firstName} ${teacher.account.instituteEmail}" class="base-profile">
                                <span class="mock-avatar">
                                    <i style="font-style:normal">${teacher.firstName.charAt(0)}</i>
                                </span>
                                <div class="teacher-info">
                                    <b class="teacher-name">${teacher.lastName} ${teacher.firstName}</b>
                                    <p class="institute-email">${teacher.account.instituteEmail}</p>
                                </div>
                            </td>
                            <td plain-value="${teacher.teacherId}" class="teacher-id">
                                ${teacher.teacherId}
                            </td>
                            <td plain-value="${teacher.department.departmentId}" class="department-id">
                                ${teacher.department.departmentId}
                            </td>
                            <td plain-value="${teacher.birthday}" class="birthday">
                                ${teacher.birthday}
                            </td>
                            <td plain-value="${teacher.gender == "BOY" ? "Nam" : "Nữ"}" class="gender">
                                ${teacher.gender == "BOY" ? "Nam" : "Nữ"}
                            </td>
                            <td plain-value="${teacher.phone}" class="phone">
                                ${teacher.phone}
                            </td>
                            <td class="table-row-btn update">
                                <a href="/manager/sub-page/teacher/update-teacher?teacherId=${teacher.teacherId}">
                                    <i class="fa-regular fa-pen-to-square"></i>
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div class="table-footer">
                <c:set var="prefixUrl" value="/manager/category/teacher/teacher-list?page=" scope="page"/>
                <div class="table-footer_main">
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
                        <c:if test="${teacherList.size() != 0}">
                            <span class="index-btn">
                                <a href="${prefixUrl}${currentPage + 1}">${currentPage + 1}</a>
                            </span>
                        </c:if>
                    </div>
                    <span class="interact-page-btn">
                        <a href="${prefixUrl}${(teacherList.size() == 0) ? currentPage : (currentPage + 1)}">
                            <i class="fa-solid fa-angle-right"></i>
                        </a>
                    </span>
                </div>
            </div>
        </div>
        <%@ include file="/WEB-INF/jsp/footer.jsp" %>
    </div>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/base.js"></script>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/header.js"></script>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/teacher-list.js"></script>
</body>

</html>