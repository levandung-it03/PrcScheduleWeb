<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update Computer Room</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css"
        integrity="sha512-SzlrxWUlpfuzQ+pcUCosxcglQRNAq/DZjVsC0lE40xsADsfeQoEypE+enwcOiGjk/bSuGGKHEyjSoQ1zVisanQ=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/base.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/update-computer-room.css">
</head>
<body>
    <%@ include file="/WEB-INF/jsp/category.jsp" %>
    <div id="center-page" id="add-computer-room-page">
        <div class="error-service-message" ${errorMessage == "none" ? "style='display:none'" : "style='display:flex'"}>
            <span>${errorMessage}</span>
            <i id="error-service-message_close-btn" class="fa fa-times-circle" aria-hidden="true"></i>
        </div>
        <div class="succeed-service-message" ${succeedMessage == "none" ? "style='display:none'" : "style='display:flex'"}>
            <span>${succeedMessage}</span>
            <i id="succeed-service-message_close-btn" class="fa fa-times-circle" aria-hidden="true"></i>
        </div>
        <%@ include file="/WEB-INF/jsp/header.jsp" %>
        <form method="POST" action="/service/v1/manager/update-computer-room?roomId=${roomObject.roomId}"
        modelAttribute="roomObject">
            <div class="form-input" id="roomId">
                <label for="roomId">Phòng thực hành</label>
                <input name="roomId" type="text" value="${roomObject.roomId}" disabled/>
            </div>

            <div class="form-input" id="maxComputerQuantity">
                <label for="maxComputerQuantity">Nhập số lượng máy tối đa</label>
                <input onblur="cuttingStringValueOfInputTag(this, 3)" name="maxComputerQuantity" type="number" min="1" max="999"
                    value="${roomObject.maxComputerQuantity}" required/>
                <div class="form_text-input_err-message"></div>
            </div>

            <div class="form-input" id="status">
                <label for="status">Trạng thái hiện tại</label>
                <select data="${roomObject.status}" name="status">
                    <option value="true">Còn hoạt động</option>
                    <option value="false">Đã niêm phong</option>
                <select>
            </div>

            <div class="form-input" id="availableComputerQuantity">
                <label for="availableComputerQuantity">Nhập số lượng máy hiện hành</label>
                <input onblur="cuttingStringValueOfInputTag(this, 3)" name="availableComputerQuantity" type="number" min="1" max="999"
                    value="${roomObject.availableComputerQuantity}" required/>
                <div class="form_text-input_err-message"></div>
            </div>
            <input name="pageNumber" value="${pageNumber}" hidden/>
            <input type="submit" type="text" value="Xác nhận"/>
        </form>
    </div>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/base.js"></script>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/update-computer-room.js"></script>
</body>
</html>


