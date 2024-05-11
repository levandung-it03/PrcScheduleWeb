<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update Teacher</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css"
        integrity="sha512-SzlrxWUlpfuzQ+pcUCosxcglQRNAq/DZjVsC0lE40xsADsfeQoEypE+enwcOiGjk/bSuGGKHEyjSoQ1zVisanQ=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/base.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/update-teacher.css">
</head>
<body>
    <%@ include file="/WEB-INF/jsp/category.jsp" %>
    <div class="center-page" id="update-teacher-page">
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
        <form method="POST" action="/service/v1/manager/update-teacher" modelAttribute="teacher">
            <input name="instituteEmail" type="text" value="${teacher.instituteEmail}" hidden/>
            <div class="form-input" id="teacherId">
                <label for="teacherId">Mã giảng viên</label>
                <input name="teacherId" type="text" value="${teacher.teacherId}" readonly disabled/>
                <input name="teacherId" type="text" value="${teacher.teacherId}" hidden/>
            </div>
            <div class="form-input strong-text" id="lastName">
                <label for="lastName">Họ giảng viên</label>
                <input name="lastName" type="text" value="${teacher.lastName}" autocomplete="off" required/>
                <div class="form_text-input_err-message"></div>
            </div>
            <div class="form-input strong-text" id="firstName">
                <label for="firstName">Tên giảng viên</label>
                <input name="firstName" type="text" value="${teacher.firstName}" autocomplete="off" required/>
                <div class="form_text-input_err-message"></div>
            </div>
            <div class="form-input" id="birthday">
                <label for="birthday">Ngày sinh</label>
                <input name="birthday" type="date" value="${teacher.birthday}" autocomplete="off" required/>
                <div class="form_text-input_err-message"></div>
            </div>
            <div class="form-input" id="gender">
                <label for="gender">Giới tính</label>
                <select data="${teacher.gender}" name="gender">
                    <option value="" disabled hidden selected>Chọn giới tính</option>
                    <option value="BOY">Nam</option>
                    <option value="GIRL">Nữ</option>
                </select>
            </div>
            <div class="form-input" id="departmentId">
                <label for="departmentId">Khoa giảng viên thuộc</label>
                <select data="${teacher.departmentId}" name="departmentId">
                    <option value="" disabled hidden selected>Chọn mã khoa</option>
                    <c:forEach var="department" items="${departmentList}">
                        <option value="${department.departmentId}">${department.departmentId}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-input" id="phone">
                <label for="phone">Số điện thoại</label>
                <input name="phone" type="text" value="${teacher.phone}" autocomplete="off" required/>
                <div class="form_text-input_err-message"></div>
            </div>
            <input name="pageNumber" value="${pageNumber}" hidden/>
            <input type="submit" value="Xác nhận"/>
        </form>
        <%@ include file="/WEB-INF/jsp/footer.jsp" %>
    </div>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/base.js"></script>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/header.js"></script>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/update-teacher.js"></script>
</body>
</html>


