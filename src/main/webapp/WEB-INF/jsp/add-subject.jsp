<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Computer Room</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css"
        integrity="sha512-SzlrxWUlpfuzQ+pcUCosxcglQRNAq/DZjVsC0lE40xsADsfeQoEypE+enwcOiGjk/bSuGGKHEyjSoQ1zVisanQ=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/base.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/add-subject.css">
</head>
<body>
    <%@ include file="/WEB-INF/jsp/category.jsp" %>
    <div id="center-page" id="add-computer-room-page">
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
        <%@ include file="/WEB-INF/jsp/header.jsp" %>
        <form method="POST" action="/service/v1/manager/add-subject" modelAttribute="subjectObject">
            <div class="form-input capitalized-text" id="subjectId">
                <label for="subjectId">Nhập mã môn</label>
                <input name="subjectId" type="text" value="${subjectObject.subjectId}" required/>
                <div class="form_text-input_err-message"></div>
            </div>
            <div class="form-input strong-text" id="subjectName">
                <label for="subjectName">Nhập tên môn</label>
                <input name="subjectName" type="text" value="${subjectObject.subjectName}" required/>
                <div class="form_text-input_err-message"></div>
            </div>
            <div class="form-input" id="creditsNumber">
                <label for="creditsNumber">Nhập số tín chỉ</label>
                <input name="creditsNumber" type="number" value="${subjectObject.creditsNumber}" maxlength="2" required/>
                <div class="form_text-input_err-message"></div>
            </div>
            <input type="submit" value="Xác nhận">
        </form>
        <%@ include file="/WEB-INF/jsp/footer.jsp" %>
    </div>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/base.js"></script>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/add-subject.js"></script>
</body>
</html>


