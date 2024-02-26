<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css"
        integrity="sha512-SzlrxWUlpfuzQ+pcUCosxcglQRNAq/DZjVsC0lE40xsADsfeQoEypE+enwcOiGjk/bSuGGKHEyjSoQ1zVisanQ=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />
    <style>html {background-image: url("${pageContext.request.contextPath}/img/form_img.jpeg");}</style>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/base.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css">
</head>

<body>
    <div class="error-message" style="display:${errorMessage}">
        <span>${errorMessage}</span>
        <i id="error-message-close-btn" class="fa fa-times-circle" aria-hidden="true"></i>
    </div>
    <form method="POST" action="/service/v1/auth/authenticate" modelAttribute="authObject">
        <span id="form-title">Đăng nhập</span>
        <div class="login-input" id="instituteEmail">
            <label for="instituteEmail">Email Học Viện</label>
            <input name="instituteEmail" type="text" placeholder="nguoiquanly@ptithcm.edu.vn" required>
            <div class="login-input_err-message"></div>
        </div>

        <div class="login-input" id="password">
            <label for="password">Mật khẩu</label>
            <span id="forgot-pass">
                <a href="/password">Quên mật khẩu?</a>
            </span>
            <input name="password" type="password" required>
            <div class="login-input_err-message"></div>
            <div class="login-input_toggle-hidden">
                <i class="show-pass fa-solid fa-eye"></i>
                <i class="hide-pass hidden fa-regular fa-eye-slash"></i>
            </div>
        </div>
        <input type="submit" value="Đăng nhập">
    </form>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/base.js"></script>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/login.js"></script>
</body>

</html>