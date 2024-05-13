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
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/import-file.css">
</head>
<body>
    <%@ include file="/WEB-INF/jsp/manager-category.jsp" %>
    <div class="center-page" id="import-file-page">
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
        <form method="POST" action="/service/v1/manager/import-data-from-txt" enctype="multipart/form-data" modelAttribute="file">
            <div class="form-input" id="file">
                <label for="file">Nhập file vào đây (định dạng .txt)</label>
                <input type="file" name="file" accept=".txt" required/>
            </div>
            <input type="submit" value="Nhập file" />
        </form>
        <%@ include file="/WEB-INF/jsp/footer.jsp" %>
    </div>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/base.js"></script>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/header.js"></script>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/import-file.js"></script>
</body>
</html>


