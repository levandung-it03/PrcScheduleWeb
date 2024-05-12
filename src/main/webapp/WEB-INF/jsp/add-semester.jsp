<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Semester</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css"
          integrity="sha512-SzlrxWUlpfuzQ+pcUCosxcglQRNAq/DZjVsC0lE40xsADsfeQoEypE+enwcOiGjk/bSuGGKHEyjSoQ1zVisanQ=="
          crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/base.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/add-semester.css">
</head>
<body>
    <%@ include file="/WEB-INF/jsp/manager-category.jsp" %>
    <div class="center-page" id="add-semester-page">
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
        <form method="POST" action="/service/v1/manager/add-semester" modelAttribute="semesterObject">
            <div class="form-input" id="semester">
                <label for="semester">Nhập học kì</label>
                <input name="semester" type="number" value="${semesterObject.semester}" autocomplete="off" required/>
                <div class="form_text-input_err-message"></div>
            </div>
            <div class="form-input" id="rangeOfYear">
                <label for="rangeOfYear">Nhập năm học</label>
                <input name="rangeOfYear" type="text" style="display: none" value="${semesterObject.rangeOfYear}">
                <select name="SelectRangeOfYear" required></select>
            </div>
            <div class="form-input" id="firstWeek">
                <label for="firstWeek">Nhập tuần bắt đầu</label>
                <input name="firstWeek" type="number" value="${semesterObject.firstWeek}" autocomplete="off" required/>
                <div class="form_text-input_err-message"></div>
            </div>
            <div class="form-input" id="totalWeek">
                <label for="totalWeek">Nhập tổng số tuần học</label>
                <input name="totalWeek" type="number" value="${semesterObject.totalWeek}" autocomplete="off" required/>
                <div class="form_text-input_err-message"></div>
            </div>
            <input type="submit" value="Xác nhận">
        </form>
        <%@ include file="/WEB-INF/jsp/footer.jsp" %>
    </div>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/base.js"></script>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/header.js"></script>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/add-semester.js"></script>
</body>
</html>


