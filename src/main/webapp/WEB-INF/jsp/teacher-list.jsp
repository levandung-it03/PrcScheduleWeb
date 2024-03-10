<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Teacher Account</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css"
        integrity="sha512-SzlrxWUlpfuzQ+pcUCosxcglQRNAq/DZjVsC0lE40xsADsfeQoEypE+enwcOiGjk/bSuGGKHEyjSoQ1zVisanQ=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/teacher-list.css">
</head>

<body>
    <%@ include file="/WEB-INF/jsp/category.jsp" %>
    <div id="center-page" id="teacher-list-page">
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
                <div id="table-discription">
                    <b>Danh sách</b>
                    <span id="quantity">${teacherList.size()} người</span>
                </div>
                <div id="table-search-box">
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
            <form action="/service/v1/manager/teacher-list-active-btn" method="POST">
                <table>
                    <thead>
                        <tr>
                            <th id="baseProfile">
                                Thông tin cơ bản
                                <i class="fa-solid fa-arrow-down-a-z"></i>
                            </th>
                            <th id="teacherId">
                                Mã giảng viên
                                <i class="fa-solid fa-arrow-down-a-z"></i>
                            </th>
                            <th id="departmentId">
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
                                <td plain-value="${teacher.lastName} ${teacher.firstName} ${teacher.account.instituteEmail}" class="baseProfile">
                                    <span class="mock-avatar">${teacher.firstName.charAt(0)}</span>
                                    <div class="teacher-info">
                                        <b class="teacher-name">${teacher.lastName} ${teacher.firstName}</b>
                                        <p class="institute-email">${teacher.account.instituteEmail}</p>
                                    </div>
                                </td>
                                <td plain-value="${teacher.teacherId}" class="teacherId">
                                    ${teacher.teacherId}
                                </td>
                                <td plain-value="${teacher.department.departmentId}" class="departmentId">
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
            </form>
        </div>
    </div>
    <script src="${pageContext.request.contextPath}/js/base.js"></script>
    <script src="${pageContext.request.contextPath}/js/teacher-list.js"></script>
</body>

</html>