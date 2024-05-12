<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Change Password</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css"
        integrity="sha512-SzlrxWUlpfuzQ+pcUCosxcglQRNAq/DZjVsC0lE40xsADsfeQoEypE+enwcOiGjk/bSuGGKHEyjSoQ1zVisanQ=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/base.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/change-password.css">
</head>
<body>
    <%@ include file="/WEB-INF/jsp/manager-category.jsp" %>
    <div class="center-page" id="change-password-page">
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
        <form method="POST" action="/service/v1/auth/change-password/get-otp" modelAttribute="changePasswordObj">
            <div class="form-input" id="instituteEmail">
                <label for="instituteEmail">Email Học Viện</label>
                <input name="instituteEmail" type="text" placeholder="nguoiquanly@ptithcm.edu.vn" value="${changePasswordObj.instituteEmail}"
                    autocomplete="off"/>
                <div class="form_text-input_err-message"></div>
            </div>
            <div class="form-input" id="password">
                <label for="password">Nhập mật khẩu mới</label>
                <input name="password" type="password" value="${changePasswordObj.password}" autocomplete="off" required/>
                <div class="form_text-input_err-message"></div>
                <div class="password_toggle-hidden">
                    <i id="password" class="show-pass fa-solid fa-eye"></i>
                    <i id="password" class="hide-pass hidden fa-regular fa-eye-slash"></i>
                </div>
            </div>
            <div class="form-input" id="retypePassword">
                <label for="retypePassword">Mật khẩu xác nhận</label>
                <input name="retypePassword" type="password" value="${changePasswordObj.retypePassword}" autocomplete="off" required/>
                <div class="form_text-input_err-message"></div>
                <div class="password_toggle-hidden">
                    <i id="retypePassword" class="show-pass fa-solid fa-eye"></i>
                    <i id="retypePassword" class="hide-pass hidden fa-regular fa-eye-slash"></i>
                </div>
            </div>
            <a class="mock-submit-form-btn">Xác nhận</a>
        </form>
        <%@ include file="/WEB-INF/jsp/footer.jsp" %>
    </div>
    <div class="dialog-of-page ${changePasswordObj == null ? 'closed' : ''}">
        <div class="dialog-of-page_container">
            <form method="POST" action="/service/v1/auth/change-password" modelAttribute="changePasswordObj">
                <span class="form-title">Xác minh OTP</span>
                <div class="form-input" id="otpCode">
                    <label for="otpCode"><i>Vui lòng kiểm tra Email của bạn.</i></label>
                    <input name="otpCode" type="text" maxlength="6" required autocomplete="off" spellcheck="false"
                        onkeyup="this.value=this.value.toUpperCase()"/>
                    <div class="form_text-input_err-message"></div>
                </div>
                <div class="input-tags-form-previous-form">
                    <input name="instituteEmail" type="text" value="${changePasswordObj.instituteEmail}" readonly hidden/>
                    <input name="password" type="text" value="${changePasswordObj.password}" readonly hidden/>
                    <input name="retypePassword" type="text" value="${changePasswordObj.retypePassword}" readonly hidden/>
                </div>
                <input type="submit" value="Gửi" />
            </form>
            <div class="closing-dialog-btn"><i class="fa-solid fa-xmark"></i></div>
        </div>
        <div class="dialog-of-page_wrapper"></div>
    </div>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/base.js"></script>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/header.js"></script>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/change-password.js"></script>
</body>
</html>
