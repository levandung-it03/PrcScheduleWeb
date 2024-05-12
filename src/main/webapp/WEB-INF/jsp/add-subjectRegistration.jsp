<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Subject Registration</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css"
        integrity="sha512-SzlrxWUlpfuzQ+pcUCosxcglQRNAq/DZjVsC0lE40xsADsfeQoEypE+enwcOiGjk/bSuGGKHEyjSoQ1zVisanQ=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/base.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/add-subjectRegistration.css">
</head>
<body>
    <%@ include file="/WEB-INF/jsp/manager-category.jsp" %>
    <div class="center-page" id="add-subjectRegistration-page">
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
        <form method="POST" action="/service/v1/manager/add-subjectRegistration" modelAttribute="subjectRegistrationObject">
            <div class="form-input" id="studentId">
                <label for="studentId">Mã số sinh viên - Họ và tên</label>
                <select data="${subjectRegistration.studentId}" name="studentId">
                    <option value="" disabled hidden selected></option>
                    <c:forEach var="student" items="${studentList}">
                        <option value="${student.studentId}">
                            ${student.studentId} - ${student.lastName} ${student.firstName}
                        </option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-input" id="sectionId">
                <label for="sectionId">Chọn học phần</label>
                <select data="${subjectRegistration.sectionClassId}" name="sectionClassId">
                    <option value="" disabled hidden selected></option>
                    <c:forEach var="sectionClass" items="${sectionClassList}">
                        <option value="${sectionClass.sectionClassId}">
                        Nhóm ${sectionClass.groupFromSubject} - ${sectionClass.grade.gradeId} - ${sectionClass.subject.subjectName} - Học kỳ ${sectionClass.semester.semester}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <input type="submit" value="Xác nhận">
        </form>
        <%@ include file="/WEB-INF/jsp/footer.jsp" %>
    </div>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/base.js"></script>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/header.js"></script>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/add-subjectRegistration.js"></script>
</body>
</html>


