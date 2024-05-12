<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Date" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Show Info</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css"
        integrity="sha512-SzlrxWUlpfuzQ+pcUCosxcglQRNAq/DZjVsC0lE40xsADsfeQoEypE+enwcOiGjk/bSuGGKHEyjSoQ1zVisanQ=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/base.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/show-person-info.css">
</head>

<body>
    <c:choose>
        <c:when test="${role == 'manager'}">
            <%@ include file="/WEB-INF/jsp/manager-category.jsp" %>
        </c:when>
        <c:otherwise>
            <%@ include file="/WEB-INF/jsp/teacher-category.jsp" %>
        </c:otherwise>
    </c:choose>
    <div class="center-page" id="show-person-info-page">
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
        <div class="show-person-info-page_detail-container">
            <div class="show-person-info-page_detail-container_left-grid">
                <span class="mock-avatar" id="left-grid-avatar">${person.firstName.charAt(0)}</span>
                <span id="left-grid-fullName">${person.firstName} ${person.lastName}</span>
            </div>
            <ul class="show-person-info-page_detail-container_right-grid">
                <li class="right-grid-lines">
                    <c:choose>
                        <c:when test="${user.role == 'MANAGER'}">
                            <span class="right-grid-lines_field-name"><i><b>Mã quản lý:</b></i></span>
                            <span class="right-grid-lines_value">${person.managerId}</span>
                        </c:when>
                        <c:otherwise>
                            <span class="right-grid-lines_field-name"><i><b>Mã giảng viên:</b></i></span>
                            <span class="right-grid-lines_value">${person.teacherId}</span>
                        </c:otherwise>
                    </c:choose>
                </li>
                <li class="right-grid-lines">
                    <span class="right-grid-lines_field-name"><i><b>Ngày sinh:</b></i></span>
                    <fmt:formatDate var="birthday" value="${person.birthday}" pattern="dd/MM/yyyy"/>
                    <span class="right-grid-lines_value" id="birthday">${birthday}</span>
                </li>
                <li class="right-grid-lines">
                    <span class="right-grid-lines_field-name"><i><b>Giới tính:</b></i></span>
                    <span class="right-grid-lines_value">${person.gender == 'BOY' ? 'Nam' : 'Nữ'}</span>
                </li>
                <li class="right-grid-lines">
                    <span class="right-grid-lines_field-name"><i><b>Số điện thoại:</b></i></span>
                    <span class="right-grid-lines_value" id="phone-number"><i>${person.phone}</i></span>
                </li>
                <li class="right-grid-lines">
                    <span class="right-grid-lines_field-name"><i><b>Email học viện:</b></i></span>
                    <span class="right-grid-lines_value">${user.instituteEmail}</span>
                </li>
                <li class="right-grid-lines">
                    <c:choose>
                        <c:when test="${user.role == 'MANAGER'}">
                            <span class="right-grid-lines_field-name"><i><b>Quyền thao tác:</b></i></span>
                            <span class="right-grid-lines_value">Quản lý</span>
                        </c:when>
                        <c:otherwise>
                            <span class="right-grid-lines_field-name"><i><b>Mã khoa thuộc:</b></i></span>
                            <span class="right-grid-lines_value">${person.department.departmentName}</span>
                        </c:otherwise>
                    </c:choose>
                </li>
                <li class="right-grid-last-lines">
                    <span class="right-grid-lines_field-name"><i><b>Ngày tham gia:</b></i></span>
                    <c:set var="str" value="Hello, World!"/>
                    <span class="right-grid-lines_value">${user.creatingTime}</span>
                </li>
            </ul>
        </div>
        <%@ include file="/WEB-INF/jsp/footer.jsp" %>
    </div>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/base.js"></script>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/header.js"></script>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/show-person-info.js"></script>
</body>

</html>