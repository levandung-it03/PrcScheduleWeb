<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Computer Room List</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css"
        integrity="sha512-SzlrxWUlpfuzQ+pcUCosxcglQRNAq/DZjVsC0lE40xsADsfeQoEypE+enwcOiGjk/bSuGGKHEyjSoQ1zVisanQ=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/base.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/computer-room-list.css">
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
                <div id="table-description">
                    <b>Danh sách</b>
                    <span id="quantity">${roomIdList.size()} phòng</span>
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
                            <th id="room-id">
                                Phòng thực hành
                                <i class="fa-solid fa-arrow-down-a-z"></i>
                            </th>
                            <th id="max-computer-quantity">
                                Tổng lượng máy
                                <i class="fa-solid fa-arrow-down-a-z"></i>
                            </th>
                            <th id="available-computer-quantity">
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
                            <tr id="${computerRoom.roomId}">
                                <td plain-value="${computerRoom.roomId}" class="room-id">
                                    ${computerRoom.roomId}
                                </td>
                                <td plain-value="${computerRoom.maxComputerQuantity}" class="max-computer-quantity">
                                    ${computerRoom.maxComputerQuantity}
                                </td>
                                <td plain-value="${computerRoom.availableComputerQuantity}" class="available-computer-quantity">
                                    ${computerRoom.availableComputerQuantity}
                                </td>
                                <td plain-value="${computerRoom.status ? "Còn hoạt động" : "Đã niêm phong"}" class="status">
                                    <span class="status-is-${computerRoom.status}">
                                        ${computerRoom.status ? "Còn hoạt động" : "Đã niêm phong"}
                                    </span>
                                </td>
                                <td class="table-row-btn update">
                                    <a href="/manager/sub-page/computer-room/update-computer-room?roomId=${computerRoom.roomId}">
                                        <i class="fa-regular fa-pen-to-square"></i>
                                    </a>
                                </td>
                                <td class="table-row-btn delete">
                                    <button name="deleteBtn" value="${computerRoom.roomId}">
                                        <i class="fa-regular fa-trash-can"></i>
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </form>
            <div id="table-footer">
                <c:set var="prefixUrl" value="/manager/category/computer-room/computer-room-list?page=" scope="page"/>
                <div id="table-footer_main">
                    <span class="interact-page-btn">
                        <a href="${prefixUrl}${(currentPage == 1) ? currentPage : (currentPage - 1)}">
                            <i class="fa-solid fa-angle-left"></i>
                        </a>
                    </span>
                    <div id="pages-content">
                        <c:if test="${currentPage > 1}">
                            <span class="index-btn">
                                <a href="${prefixUrl}${currentPage - 1}">${currentPage - 1}</a>
                            </span>
                        </c:if>
                        <span class="index-btn">
                            <a href="${prefixUrl}${currentPage}">${currentPage}</a>
                        </span>
                        <c:if test="${computerRoomList.size() != 0}">
                            <span class="index-btn">
                                <a href="${prefixUrl}${currentPage + 1}">${currentPage + 1}</a>
                            </span>
                        </c:if>
                    </div>
                    <span class="interact-page-btn">
                        <a href="${prefixUrl}${(computerRoomList.size() == 0) ? currentPage : (currentPage + 1)}">
                            <i class="fa-solid fa-angle-right"></i>
                        </a>
                    </span>
                </div>
            </div>
        </div>
    </div>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/base.js"></script>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/computer-room-list.js"></script>
</body>

</html>