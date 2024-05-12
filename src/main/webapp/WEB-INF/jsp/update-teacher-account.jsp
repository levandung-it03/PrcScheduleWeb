<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update Teacher Account</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css"
        integrity="sha512-SzlrxWUlpfuzQ+pcUCosxcglQRNAq/DZjVsC0lE40xsADsfeQoEypE+enwcOiGjk/bSuGGKHEyjSoQ1zVisanQ=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/base.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/update-teacher-account.css">
</head>
<body>
    <%@ include file="/WEB-INF/jsp/manager-category.jsp" %>
    <div class="center-page" id="update-teacher-account-page">
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
        <form method="POST" action="/service/v1/manager/update-teacher-account?accountId=${account.accountId}"
        modelAttribute="account">
            <div class="form-input" id="instituteEmail">
                <label for="instituteEmail">Tài khoản giảng viên (Email)</label>
                <input name="instituteEmail" type="text" value="${account.instituteEmail}" disabled/>
                <input name="instituteEmail" type="text" value="${account.instituteEmail}" hidden/>
            </div>
            <div class="form-input" id="creatingTime">
                <label for="creatingTime">Thời gian tạo tài khoản</label>
                <input name="creatingTime" type="text" value="${account.creatingTime}" disabled/>
                <input name="creatingTime" type="text" value="${account.creatingTime}" hidden/>
            </div>
            <div class="form-input" id="status">
                <label for="status">Trạng thái hoạt động</label>
                <select data="${account.status}" name="status">
                    <option value="true">Còn hoạt động</option>
                    <option value="false">Ngắt hoạt động</option>
                </select>
            </div>
            <input name="pageNumber" value="${pageNumber}" hidden/>
            <input type="submit" value="Xác nhận"/>
        </form>
        <%@ include file="/WEB-INF/jsp/footer.jsp" %>
    </div>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/base.js"></script>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/header.js"></script>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/update-teacher-account.js"></script>
</body>
</html>


