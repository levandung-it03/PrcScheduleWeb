<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${actionType.toUpperCase()} ${roleName.toUpperCase()}</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css"
        integrity="sha512-SzlrxWUlpfuzQ+pcUCosxcglQRNAq/DZjVsC0lE40xsADsfeQoEypE+enwcOiGjk/bSuGGKHEyjSoQ1zVisanQ=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/base.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/add-person.css">
</head>
<body>
    <c:if test="${actionType == 'update'}">
        <c:choose>
            <c:when test="${role == 'manager'}">
                <%@ include file="/WEB-INF/jsp/manager-category.jsp" %>
            </c:when>
            <c:otherwise>
                <%@ include file="/WEB-INF/jsp/teacher-category.jsp" %>
            </c:otherwise>
        </c:choose>
    </c:if>
    <div class="center-page" id="${actionType}-person-page">
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
        <c:choose>
            <c:when test="${actionType == 'update'}">
                <%@ include file="/WEB-INF/jsp/header.jsp" %>
            </c:when>
            <c:otherwise>
                <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css">
                <header>
                    <div id="header_left">
                        <div id="header_left_category-content">
                            <p id="header_left_category-name"></p>
                            <p id="header_left_page-description"></p>
                        </div>
                    </div>
                    <div id="header_right">
                        <div class="header_items" class="avatar">
                            <style>
                                #header_left_category-content {
                                    padding-left: 80px;
                                }
                            </style>
                            <span class="avatar_user">
                                <i class="fa-solid fa-gear"></i>
                            </span>
                        </div>
                        <div id="panel-info" class="hide">
                            <ul class="panel-info_last-component" id="panel-info_app-features">
                                <li>
                                    <a class="panel-info_sub-components_options" href="/manager/sub-page/manage-info/info">
                                        <i class="fa-solid fa-triangle-exclamation"></i>
                                        Báo lỗi
                                    </a>
                                </li>
                                <li>
                                    <form id="logout-form" action="/service/v1/auth/logout" method="POST">
                                        <a class="panel-info_sub-components_options">
                                            <i class="fa-solid fa-right-from-bracket"></i>
                                            Đăng xuất
                                        </a>
                                    </form>
                                </li>
                            </ul>
                        </div>
                    </div>
                </header>
            </c:otherwise>
        </c:choose>
        <form method="POST" action="/service/v1/${actionTail}" modelAttribute="person">
            <c:choose>
                <c:when test="${actionType == 'add'}">
                    <input name="instituteEmail" type="text" value="${instituteEmail}" hidden/>
                </c:when>
                <c:otherwise>
                    <input name="instituteEmail" type="text" value="${person.account.instituteEmail}" hidden/>
                </c:otherwise>
            </c:choose>
            <c:set var="roleNameAsVietnamese" value="${roleName == 'manager' ? 'quản lý' : 'giảng viên'}" />
            <div class="form-input" id="${roleName}Id">
                <span id="id-name" style="display:none;">${roleName}Id</span>
                <label for="${roleName}Id">Mã ${roleNameAsVietnamese}</label>
                <c:choose>
                    <c:when test="${roleName == 'teacher'}">
                        <c:choose>
                            <c:when test="${actionType == 'add'}">
                                <input name="${roleName}Id" type="text" value="${person.teacherId}"/>
                                <div class="form_text-input_err-message"></div>
                            </c:when>
                            <c:otherwise>
                                <input name="${roleName}Id" type="text" value="${person.teacherId}" readonly disabled/>
                                <input name="${roleName}Id" type="text" value="${person.teacherId}" hidden/>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:when test="${roleName == 'manager'}">
                        <c:choose>
                            <c:when test="${actionType == 'add'}">
                                <input name="${roleName}Id" type="text" value="${person.managerId}"/>
                                <div class="form_text-input_err-message"></div>
                            </c:when>
                            <c:otherwise>
                                <input name="${roleName}Id" type="text" value="${person.managerId}" readonly disabled/>
                                <input name="${roleName}Id" type="text" value="${person.managerId}" hidden/>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                </c:choose>
            </div>
            <div class="form-input strong-text" id="lastName">
                <label for="lastName">Họ ${roleNameAsVietnamese}</label>
                <input name="lastName" type="text" value="${person.lastName}" autocomplete="off" required/>
                <div class="form_text-input_err-message"></div>
            </div>
            <div class="form-input strong-text" id="firstName">
                <label for="firstName">Tên ${roleNameAsVietnamese}</label>
                <input name="firstName" type="text" value="${person.firstName}" autocomplete="off" required/>
                <div class="form_text-input_err-message"></div>
            </div>
            <div class="form-input" id="birthday">
                <label for="birthday">Ngày sinh</label>
                <input name="birthday" type="date" value="${person.birthday}" autocomplete="off" required/>
                <div class="form_text-input_err-message"></div>
            </div>
            <div class="form-input" id="gender">
                <label for="gender">Giới tính</label>
                <select data="${person.gender}" name="gender">
                    <option value="" disabled hidden selected>Chọn giới tính</option>
                    <option value="BOY">Nam</option>
                    <option value="GIRL">Nữ</option>
                </select>
            </div>
            <c:if test="${roleName == 'teacher'}">
                <div class="form-input" id="departmentId">
                    <label for="departmentId">Khoa giảng viên thuộc</label>
                    <select data="${person.department.departmentId}" name="departmentId">
                        <option value="" disabled hidden selected>Chọn mã khoa</option>
                        <c:forEach var="department" items="${departmentList}">
                            <option value="${department.departmentId}">
                                ${department.departmentId} - ${department.departmentName}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </c:if>
            <div class="form-input" id="phone">
                <label for="phone">Số điện thoại</label>
                <input name="phone" type="text" value="${person.phone}" autocomplete="off" required/>
                <div class="form_text-input_err-message"></div>
            </div>
            <input name="pageNumber" value="${pageNumber}" hidden/>
            <input type="submit" value="Xác nhận"/>
        </form>
        <%@ include file="/WEB-INF/jsp/footer.jsp" %>
    </div>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/base.js"></script>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/header.js"></script>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/add-person.js"></script>
</body>
</html>


