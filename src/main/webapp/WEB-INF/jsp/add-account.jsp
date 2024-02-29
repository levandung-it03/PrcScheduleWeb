<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/add-account.css">
</head>
<body>
    <%@ include file="/WEB-INF/jsp/category.jsp" %>
    <div class="center-page" id="add-account-page">
        <div class="error-service-message" ${errorMessage == "none" ? "style='display:none'" : "style='display:flex'"}>
            <span>${errorMessage}</span>
            <i id="error-service-message_close-btn" class="fa fa-times-circle" aria-hidden="true"></i>
        </div>
        <div class="succeed-service-message" ${succeedMessage == "none" ? "style='display:none'" : "style='display:flex'"}>
            <span>${succeedMessage}</span>
            <i id="succeed-service-message_close-btn" class="fa fa-times-circle" aria-hidden="true"></i>
        </div>
        <form method="POST" action="/service/v1/manager/add-teacher-account" modelAttribute="registerObject">
            <span id="form-title">Tạo tài khoản</span>
            <div class="form-input" id="instituteEmail">
                <label for="instituteEmail">Email giảng viên</label>
                <input name="instituteEmail" type="text" placeholder="giangvien@ptithcm.edu.vn" value="${registerObject.instituteEmail}" required/>
                <div class="form_text-input_err-message"></div>
            </div>

            <div class="form-input" id="password">
                <label for="password">Mật khẩu</label>
                <input name="password" type="password" value="${registerObject.password}" required/>
                <div class="form_text-input_err-message"></div>
                <div class="password_toggle-hidden">
                    <i id="password" class="show-pass fa-solid fa-eye"></i>
                    <i id="password" class="hide-pass hidden fa-regular fa-eye-slash"></i>
                </div>
            </div>

            <div class="form-input" id="retypePassword">
                <label for="retypePassword">Mật khẩu xác nhận</label>
                <input name="retypePassword" type="password" value="${registerObject.retypePassword}" required/>
                <div class="form_text-input_err-message"></div>
                <div class="password_toggle-hidden">
                    <i id="retypePassword" class="show-pass fa-solid fa-eye"></i>
                    <i id="retypePassword" class="hide-pass hidden fa-regular fa-eye-slash"></i>
                </div>
            </div>
            <input type="submit" value="Xác nhận">
        </form>
    </div>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/base.js"></script>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/add-account.js"></script>
</body>
</html>