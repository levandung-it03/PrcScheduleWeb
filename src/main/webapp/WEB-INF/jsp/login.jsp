<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css"
        integrity="sha512-SzlrxWUlpfuzQ+pcUCosxcglQRNAq/DZjVsC0lE40xsADsfeQoEypE+enwcOiGjk/bSuGGKHEyjSoQ1zVisanQ=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />
    <style>
    </style>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/base.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css">
</head>

<body>
    <c:if test="${errorMessage != null}">
        <div class="error-service-message">
            <span>${errorMessage}</span>
            <i id="error-service-message_close-btn" class="fa fa-times-circle" aria-hidden="true"></i>
        </div>
    </c:if>
    <style>
        body {
            background-color: #eae3de;
            min-height: 100vh;
        }
        div#center-block {
            width: 925px;
            height: 600px;
            display: flex;
            margin: auto;
            margin-top: 60px;
            background-color: rgb(176, 25, 58);
            border: 30px rgb(176, 25, 58) solid;
            border-radius: 35px;
            box-shadow: 0 0 100px var(--blur-black);
        }
        div#center-block div#left-column {
            background-size: cover;
            width: 50%;
            display: flex;
            flex-wrap: wrap;
            flex-direction: column;
            justify-content: center;
            align-items: center;
        }
        
        div#center-block div#left-column img {
            width: 100px;
            padding: 15px 0 105px 0;
        }
        div#center-block div#left-column span.web-description {
            font-weight: 1000;
            font-size: 2.4rem;
            color: var(--white-text);
            text-align: end;
            display: block;
            width: 100%;
            padding: 0 50px;
        }
    </style>
    <div id="center-block">
        <div id="left-column">
            <span id="des-component-0" class="web-description">PHÂN CÔNG</span>
            <span id="des-component-1" class="web-description">LỊCH THỰC HÀNH</span>
            <img src="${pageContext.request.contextPath}/img/form_img.jpeg" alt="">
        </div>
        <form method="POST" action="/service/v1/auth/authenticate" modelAttribute="authObject">
            <span id="form-title">Đăng nhập</span>
            <div class="form-input" id="instituteEmail">
                <label for="instituteEmail">Email Học Viện</label>
                <input name="instituteEmail" type="text" placeholder="nguoiquanly@ptithcm.edu.vn" required/>
                <div class="form_text-input_err-message"></div>
            </div>

            <div class="form-input" id="password">
                <label for="password">Mật khẩu</label>
                <span id="forgot-pass">
                    <a href="/public/forgot-password">Quên mật khẩu?</a>
                </span>
                <input name="password" type="password" required/>
                <div class="form_text-input_err-message"></div>
                <div class="password_toggle-hidden">
                    <i id="password" class="show-pass fa-solid fa-eye"></i>
                    <i id="password" class="hide-pass hidden fa-regular fa-eye-slash"></i>
                </div>
            </div>
            <input type="submit" value="Đăng nhập">
        </form>
    </div>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/base.js"></script>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/login.js"></script>
</body>

</html>