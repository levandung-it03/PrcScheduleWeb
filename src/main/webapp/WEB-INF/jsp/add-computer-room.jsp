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
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/add-computer-room.css">
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
        <form method="POST" action="/service/v1/manager/add-computer-room" modelAttribute="roomObject">
            <div class="form-input" id="area">
                <label for="area">Chọn khu vực</label>
                <select data="${roomObject.area}" name="area">
                    <option value="A">Khu A Học Viện</option>
                    <option value="B">Khu B Học Viện</option>
                    <option value="C">Khu C Học Viện</option>
                    <option value="D">Khu D Học Viện</option>
                    <option value="E">Khu E Học Viện</option>
                </select>
            </div>
            <div class="form-input" id="roomCode">
                <label for="roomCode">Nhập mã phòng</label>
                <input onblur="cuttingStringValueOfInputTag(this, 2)" name="roomCode" type="number" min="1" max="99"
                    value="${roomObject.roomCode}" required/>
                <div class="form_text-input_err-message"></div>
            </div>
            <div class="form-input" id="maxQuantity">
                <label for="maxQuantity">Nhập số lượng người tối đa</label>
                <input onblur="cuttingStringValueOfInputTag(this, 3)" name="maxQuantity" type="number" min="1" max="999"
                    value="${roomObject.maxQuantity}" required/>
                <div class="form_text-input_err-message"></div>
            </div>
            <div class="form-input" id="maxComputerQuantity">
                <label for="maxComputerQuantity">Nhập số lượng máy tối đa</label>
                <input onblur="cuttingStringValueOfInputTag(this, 3)" name="maxComputerQuantity" type="number" min="1" max="999"
                    value="${roomObject.maxComputerQuantity}" required/>
                <div class="form_text-input_err-message"></div>
            </div>
            <input type="submit" value="Xác nhận">
        </form>
        <%@ include file="/WEB-INF/jsp/footer.jsp" %>
    </div>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/base.js"></script>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/add-computer-room.js"></script>
</body>
</html>


