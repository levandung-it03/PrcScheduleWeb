<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add SectionClass</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css"
          integrity="sha512-SzlrxWUlpfuzQ+pcUCosxcglQRNAq/DZjVsC0lE40xsADsfeQoEypE+enwcOiGjk/bSuGGKHEyjSoQ1zVisanQ=="
          crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/base.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/add-section-class.css">
</head>
<body>
    <%@ include file="/WEB-INF/jsp/manager-category.jsp" %>
    <div class="center-page" id="add-section-class-page">
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
        <form method="POST" action="/service/v1/manager/add-section-class" modelAttribute="sectionClassObject">
            <div class="form-input" id="semesterId">
                <label for="semesterId">Nhập học kì</label>
                <input name="semesterId" type="number" value="${sectionClassObject.semesterId}" style="display: none" autocomplete="off" required/>
                <select name="selectSemesterId">
                    <option value="" disabled selected hidden>Chọn học kỳ</option>
                    <c:forEach items="${semesters}" var="semester">
                    <option value="${semester.semesterId}">Học kì ${semester.semester} - Năm học ${semester.rangeOfYear}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-input" id="gradeId">
                <label for="gradeId">Nhập mã lớp</label>
                <input name="gradeId" type="text" value="${sectionClassObject.gradeId}" autocomplete="off" required/>
                <div class="form_text-input_err-message"></div>
            </div>

            <div class="form-input" id="subjectId">
                <label for="subjectId">Nhập mã môn học</label>
                <input name="subjectId" type="text" value="${sectionClassObject.subjectId}" autocomplete="off" required/>
                <div class="form_text-input_err-message"></div>
            </div>

            <div class="form-input" id="groupFromSubject">
                <label for="groupFromSubject">Nhập số nhóm</label>
                <input name="groupFromSubject" type="number" value="${sectionClassObject.groupFromSubject}" autocomplete="off" required/>
                <div class="form_text-input_err-message"></div>
            </div>
            <input type="submit" value="Xác nhận">
        </form>
        <%@ include file="/WEB-INF/jsp/footer.jsp" %>
    </div>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/base.js"></script>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/header.js"></script>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/add-section-class.js"></script>
</body>
</html>


