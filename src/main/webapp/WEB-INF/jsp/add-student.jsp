<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Student</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css"
        integrity="sha512-SzlrxWUlpfuzQ+pcUCosxcglQRNAq/DZjVsC0lE40xsADsfeQoEypE+enwcOiGjk/bSuGGKHEyjSoQ1zVisanQ=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/base.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/add-student.css">
</head>
<body>
    <%@ include file="/WEB-INF/jsp/manager-category.jsp" %>
    <div class="center-page" id="add-student-page">
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
        <form method="POST" action="/service/v1/manager/add-student" modelAttribute="studentObject">
            <div class="form-input capitalized-text" id="studentId">
                <label for="studentId">Nhập mã sinh viên</label>
                <input onblur="this.value = this.value.toUpperCase();" name="studentId" type="text"
                value="${studentObject.studentId}" autocomplete="off" required/>
                    <div class="form_text-input_err-message"></div>
            </div>
            <div class="form-input capitalized-text" id="grade">
                <label for="grade">Nhập mã lớp</label>
                    <input name="grade" type="text" value="${studentObject.grade}" autocomplete="off" required/>
                <div class="form_text-input_err-message"></div>
            </div>
            <div class="form-input strong-text" id="lastName">
                <label for="lastName">Tên cuối</label>
                <input name="lastName" type="text" value="${studentObject.lastName}" autocomplete="off" required/>
                <div class="form_text-input_err-message"></div>
            </div>
            <div class="form-input strong-text" id="firstName">
                <label for="firstName">Tên đầu</label>
                <input name="firstName" type="text" value="${studentObject.firstName}" autocomplete="off" required/>
                <div class="form_text-input_err-message"></div>
            </div>
            <div class="form-input" id="gender">
                <label for="gender">Giới tính</label>
                <select data="${student.gender}" name="gender">
                    <option value="" disabled hidden selected>Chọn giới tính</option>
                    <option value="BOY">Nam</option>
                    <option value="GIRL">Nữ</option>
                </select>
            </div>
            <div class="form-input" id="instituteEmail">
                <label for="instituteEmail">Email</label>
                <input name="instituteEmail" type="text" value="${studentObject.instituteEmail}" autocomplete="off" required/>
                <div class="form_text-input_err-message"></div>
            </div>
            <input type="submit" value="Xác nhận">
        </form>
        <%@ include file="/WEB-INF/jsp/footer.jsp" %>
    </div>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/base.js"></script>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/header.js"></script>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/add-student.js"></script>
</body>
</html>


