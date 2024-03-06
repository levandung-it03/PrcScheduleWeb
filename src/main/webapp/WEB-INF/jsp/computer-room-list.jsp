<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Teacher Account</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css"
        integrity="sha512-SzlrxWUlpfuzQ+pcUCosxcglQRNAq/DZjVsC0lE40xsADsfeQoEypE+enwcOiGjk/bSuGGKHEyjSoQ1zVisanQ=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/computer-room-list.css">
</head>

<body>
    <%@ include file="/WEB-INF/jsp/category.jsp" %>
    <div id="center-page" id="add-account-page">
        <div class="error-service-message" ${errorMessage == "none" ? "style='display:none'" : "style='display:flex'"}>
            <span>${errorMessage}</span>
            <i id="error-service-message_close-btn" class="fa fa-times-circle" aria-hidden="true"></i>
        </div>
        <div class="succeed-service-message" ${succeedMessage == "none" ? "style='display:none'" : "style='display:flex'"}>
            <span>${succeedMessage}</span>
            <i id="succeed-service-message_close-btn" class="fa fa-times-circle" aria-hidden="true"></i>
        </div>
        <%@ include file="/WEB-INF/jsp/header.jsp" %>
        <div id="center-page_list">
            <div id="table-tools">
                <div id="table-discription">
                    <b>Danh sách</b>
                    <span id="quantity">10 phòng</span>
                </div>
                <div id="table-search-box">
                    <select id="search">
                        <option value="" selected disabled hidden>Chọn trường cần tìm</option>
                        <option value="0">Mã phòng</option>
                        <option value="1">Tổng lượng máy</option>
                        <option value="2">Số máy hiện hành</option>
                        <option value="3">Trạng thái</option>
                    </select>
                    <input type="text" id="search">
                    <i class="fa-solid fa-magnifying-glass"></i>
                </div>
            </div>
            <form action="/service/v1/manager/computer-room-list-active-btn" method="POST">
                <table>
                    <thead>
                        <tr>
                            <th id="computerRoom">
                                Phòng thực hành
                                <i class="fa-solid fa-arrow-down-a-z"></i>
                            </th>
                            <th id="maxComputerQuantity">
                                Tổng lượng máy
                                <i class="fa-solid fa-arrow-down-a-z"></i>
                            </th>
                            <th id="availableComputerQuantity">
                                Số máy hiện hành
                                <i class="fa-solid fa-arrow-down-a-z"></i>
                            </th>
                            <th id="status">
                                Trạng thái
                                <i class="fa-solid fa-arrow-down-a-z"></i>
                            </th>
                            <th id="update">Cập nhật</th>
                            <th id="delete">Xoá phòng</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${computerRoomList}" var="computerRoom">
                            <tr id="${computerRoom.computerRoom}">
                                <td plain-value="${computerRoom.computerRoom}" class="computerRoom">
                                    ${computerRoom.computerRoom}
                                </td>
                                <td plain-value="${computerRoom.maxComputerQuantity}" class="maxComputerQuantity">
                                    ${computerRoom.maxComputerQuantity}
                                </td>
                                <td plain-value="${computerRoom.availableComputerQuantity}" class="availableComputerQuantity">
                                    ${computerRoom.availableComputerQuantity}
                                </td>
                                <td plain-value="${computerRoom.status ? "Còn hoạt động" : "Đã niêm phong"}" class="status">
                                    <span class="status-is-${computerRoom.status}">
                                        ${computerRoom.status ? "Còn hoạt động" : "Đã niêm phong"}
                                    </span>
                                </td>
                                <td class="table-row-btn update">
                                    <a href="/manager/sub-page/computer-room/update-computer-room?computerRoom=${computerRoom.computerRoom}">
                                        <i class="fa-regular fa-pen-to-square"></i>
                                    </a>
                                </td>
                                <td class="table-row-btn delete">
                                    <button name="deleteBtn" value="${computerRoom.computerRoom}">
                                        <i class="fa-regular fa-trash-can"></i>
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </form>
        </div>
    </div>
    <script src="${pageContext.request.contextPath}/js/base.js"></script>
    <script src="${pageContext.request.contextPath}/js/computer-room-list.js"></script>
</body>

</html>