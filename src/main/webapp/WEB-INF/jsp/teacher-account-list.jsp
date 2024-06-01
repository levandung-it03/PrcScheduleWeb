<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Teacher Account List</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css"
        integrity="sha512-SzlrxWUlpfuzQ+pcUCosxcglQRNAq/DZjVsC0lE40xsADsfeQoEypE+enwcOiGjk/bSuGGKHEyjSoQ1zVisanQ=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/base.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/teacher-account-list.css">
</head>

<body>
    <%@ include file="/WEB-INF/jsp/manager-category.jsp" %>
    <div class="center-page" id="teacher-account-list-page">
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
                    <span id="quantity">${accountList.size()} tài khoản</span>
                </div>
                <div class="table-search-box">
                    <select id="search">
                        <option value="" selected disabled hidden>Chọn trường cần tìm</option>
                        <option value="0">Email Học viện</option>
                        <option value="1">Ngày tạo</option>
                        <option value="2">Trạng thái liên kết</option>
                        <option value="3">Trạng thái hoạt động</option>
                    </select>
                    <input type="text" id="search">
                    <i class="fa-solid fa-magnifying-glass"></i>
                </div>
            </div>
            <form action="/service/v1/manager/teacher-account-list-active-btn" method="POST">
                <table>
                    <thead>
                        <tr>
                            <th id="institute-email">
                                Email Học viện
                                <i class="fa-solid fa-arrow-down-a-z"></i>
                            </th>
                            <th id="creating-time">
                                Ngày tạo
                                <i class="fa-solid fa-arrow-down-a-z"></i>
                            </th>
                            <th id="linking-status">
                                Trạng thái liên kết
                                <i class="fa-solid fa-arrow-down-a-z"></i>
                            </th>
                            <th id="status">
                                Trạng thái hoạt động
                                <i class="fa-solid fa-arrow-down-a-z"></i>
                            </th>
                            <th id="update">Cập nhật</th>
                            <th id="delete">Xoá tài khoản</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${accountList}" var="account">
                            <tr id="${account.accountId}">
                                <td plain-value="${account.instituteEmail}" class="institute-email">
                                    ${account.instituteEmail}
                                </td>
                                <td plain-value="${account.creatingTime}" class="creating-time">${account.creatingTime}</td>
                                <td plain-value="${(account.teacherId == null) ? "Chưa liên kết" : "Đã liên kết"}" class="linking-status">
                                    <span class="status-is-${account.teacherId != null}">
                                        ${(account.teacherId == null) ? "Chưa liên kết" : "Đã liên kết"}
                                    </span>
                                </td>
                                <td plain-value="${account.status ? "Còn hoạt động" : "Ngưng hoạt động"}" class="status">
                                    <span class="status-is-${account.status}">
                                        ${account.status ? "Còn hoạt động" : "Ngưng hoạt động"}
                                    </span>
                                </td>
                                <td class="table-row-btn update">
                                    <a href="/manager/sub-page/teacher/update-teacher-account?accountId=${account.accountId}">
                                        <i class="fa-regular fa-pen-to-square"></i>
                                    </a>
                                </td>
                                <td class="table-row-btn delete">
                                    <button name="deleteBtn" value="${account.accountId}">
                                        <i class="fa-regular fa-trash-can"></i>
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            <div class="table-footer">
                <c:set var="prefixUrl" value="/manager/category/teacher/teacher-account-list?page=" scope="page"/>
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
                        <c:if test="${accountList.size() != 0}">
                            <span class="index-btn">
                                <a href="${prefixUrl}${currentPage + 1}">${currentPage + 1}</a>
                            </span>
                        </c:if>
                    </div>
                    <span class="interact-page-btn">
                        <a href="${prefixUrl}${(accountList.size() == 0) ? currentPage : (currentPage + 1)}">
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
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/teacher-account-list.js"></script>
</body>

</html>