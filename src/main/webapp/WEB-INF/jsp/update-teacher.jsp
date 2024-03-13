<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update Computer Room</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css"
        integrity="sha512-SzlrxWUlpfuzQ+pcUCosxcglQRNAq/DZjVsC0lE40xsADsfeQoEypE+enwcOiGjk/bSuGGKHEyjSoQ1zVisanQ=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/base.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/update-teacher.css">
</head>
<body>
    <%@ include file="/WEB-INF/jsp/category.jsp" %>
    <div id="center-page" id="add-computer-room-page">
        <div class="error-service-message" ${errorMessage == "none" ? "style='display:none'" : "style='display:flex'"}>
            <span>${errorMessage}</span>
            <i id="error-service-message_close-btn" class="fa fa-times-circle" aria-hidden="true"></i>
        </div>
        <div class="succeed-service-message" ${succeedMessage == "none" ? "style='display:none'" : "style='display:flex'"}>
            <span>${succeedMessage}</span>
            <i id="succeed-service-message_close-btn" class="fa fa-times-circle" aria-hidden="true"></i>
        </div>
        <%@ include file="/WEB-INF/jsp/header.jsp" %>
        <form method="POST" action="/service/v1/manager/update-teacher?teacherId=${teacher.teacherId}" modelAttribute="teacher">
            <div class="form-input" id="teacherId">
                <label for="teacherId">Mã giảng viên</label>
                <input name="teacherId" type="text" value="${teacher.teacherId}" readonly disabled/>
            </div>
            <div class="form-input" id="lastName">
                <label for="lastName">Họ giảng viên</label>
                <input name="lastName" type="text" value="${teacher.lastName}" required/>
                <div class="form_text-input_err-message"></div>
            </div>
            <div class="form-input" id="firstName">
                <label for="firstName">Tên giảng viên</label>
                <input name="firstName" type="text" value="${teacher.firstName}" required/>
                <div class="form_text-input_err-message"></div>
            </div>
            <div class="form-input" id="birthday">
                <label for="birthday">Ngày sinh</label>
                <input name="birthday" type="date" value="${teacher.birthday}" required/>
                <div class="form_text-input_err-message"></div>
            </div>
            <div class="form-input" id="gender">
                <label for="gender">Giới tính</label>
                <select data="${teacher.gender}" name="gender">
                    <option value="${teacher.gender}" disabled hidden selected>Chọn giới tính</option>
                    <option value="BOY">Nam</option>
                    <option value="GIRL">Nữ</option>
                <select>
            </div>
            <div class="form-input" id="departmentId">
                <label for="departmentId">Khoa giảng viên thuộc</label>
                <select data="${teacher.department.departmentId}" name="departmentId">
                    <option value="" disabled hidden selected>Chọn mã khoa</option>
                    <c:forEach var="department" items="${departmentList}">
                        <option value="${department.departmentId}">${department.departmentId}</option>
                    </c:forEach>
                <select>
            </div>
            <div class="form-input" id="phone">
                <label for="phone">Số điện thoại</label>
                <input name="phone" type="text" value="${teacher.phone}" required/>
                <div class="form_text-input_err-message"></div>
            </div>
            <input type="submit" value="Xác nhận">
        </form>
    </div>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/base.js"></script>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/update-teacher.js"></script>
</body>
</html>


