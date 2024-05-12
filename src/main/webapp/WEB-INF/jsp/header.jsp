    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css">
    <header>
        <div id="header_left">
            <div class="header_items" id="header_left_menu-btn" id="toggle-hide-category">
                <i class="fa-solid fa-bars"></i>
            </div>
            <div id="header_left_category-content">
                <p id="header_left_category-name"></p>
                <p id="header_left_page-description"></p>
            </div>
        </div>
        <div id="header_right">
            <div class="header_items avatar">
                <span class="mock-avatar avatar_user">
                    <i style="font-style:normal">${person.firstName.charAt(0)}</i>
                </span>
                <p class="avatar_name">${person.lastName} ${person.firstName}</p>
            </div>
            <div id="panel-info" class="hide">
                <ul class="panel-info_sub-components" id="panel-info_managing-client-info-features">
                    <c:if test="${role == 'manager'}">
                        <li>
                            <a class="panel-info_sub-components_options" href="/manager/sub-page/manager/show-info">
                                <i class="fa-solid fa-circle-info"></i>
                                Thông tin cá nhân
                            </a>
                        </li>
                    </c:if>
                    <li>
                        <a class="panel-info_sub-components_options" href="/${role}/sub-page/${role}/update-${role}-info">
                            <i class="fa-solid fa-user-pen"></i>
                            Cập nhật thông tin
                        </a>
                    </li>
                    <li>
                        <a class="panel-info_sub-components_options" href="/${role}/sub-page/${role}/update-account/change-password">
                            <i class="fa-solid fa-lock"></i>
                            Đổi mật khẩu
                        </a>
                    </li>
                </ul>
                <ul class="panel-info_last-component" id="panel-info_app-features">
                    <li>
                        <form id="logout-form" action="/service/v1/auth/logout" method="POST">
                            <a class="panel-info_last-components_options">
                                <i class="fa-solid fa-right-from-bracket"></i>
                                Đăng xuất
                            </a>
                        </form>
                    </li>
                </ul>
            </div>
        </div>
    </header>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/header.js"></script>