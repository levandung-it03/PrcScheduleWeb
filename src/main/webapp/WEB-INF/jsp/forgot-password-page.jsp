<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Forgot Password</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css"
        integrity="sha512-SzlrxWUlpfuzQ+pcUCosxcglQRNAq/DZjVsC0lE40xsADsfeQoEypE+enwcOiGjk/bSuGGKHEyjSoQ1zVisanQ=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />
    <style>
    </style>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/base.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css">
</head>

<body>
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
    <div id="center-block">
        <div id="left-column">
            <span id="des-component-0" class="web-description">PHÂN CÔNG</span>
            <span id="des-component-1" class="web-description">LỊCH THỰC HÀNH</span>
            <img src="${pageContext.request.contextPath}/img/form_img.jpeg" alt="">
        </div>
        <form method="POST" action="/service/v1/auth/change-password/otp-for-new-password">
            <span class="form-title">Lấy lại mật khẩu</span>
            <div class="form-input" id="instituteEmail">
                <label for="instituteEmail">Email Học Viện</label>
                <input name="instituteEmail" type="text" placeholder="nguoiquanly@ptithcm.edu.vn" required/>
                <div class="form_text-input_err-message"></div>
            </div>
            <input type="submit" value="Xác nhận">
        </form>
    </div>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/base.js"></script>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/login.js"></script>
</body>

</html>