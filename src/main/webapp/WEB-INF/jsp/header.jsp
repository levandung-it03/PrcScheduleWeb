<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css">
    <header>
        <div id="header_left">
            <div class="header_items" id="toggle-hide-category">
                <i class="fa-solid fa-bars"></i>
            </div>
        </div>
        <div id="header_center">
            <p id="category-name"></p>
            <p id="page-description"></p>
        </div>
        <div id="header_right">
            <div class="header_items" id="setting">
                <i class="fa-solid fa-gear"></i>
            </div>
            <div class="header_items" id="avatar">
                <a id="avatar_user" href="/home">MN</a>
            </div>
        </div>
    </header>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/header.js"></script>