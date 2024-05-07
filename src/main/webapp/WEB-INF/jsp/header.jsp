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
            <div class="header_items" id="avatar">
                <span id="avatar_user">${user.firstName.charAt(0)}</span>
                <p id="avatar_name">${user.lastName} ${user.firstName}</p>
            </div>
            <div id="panel-info" class="hide">
                <ul class="panel-info_sub-components" id="panel-info_managing-client-info-features">
                    <li>
                        <a class="panel-info_sub-components_options" href="/manager/sub-page/manage-info/info">
                            <i class="fa-solid fa-circle-info"></i>
                            Thông tin cá nhân
                        </a>
                    </li>
                    <li>
                        <a class="panel-info_sub-components_options" href="/manager/sub-page/manage-info/update-info">
                            <i class="fa-solid fa-user-pen"></i>
                            Cập nhật thông tin
                        </a>
                    </li>
                    <li>
                        <a class="panel-info_sub-components_options" href="/manager/sub-page/manage-info/update-account/password">
                            <i class="fa-solid fa-lock"></i>
                            Đổi mật khẩu
                        </a>
                    </li>
                </ul>
                <ul id="panel-info_last-component" id="panel-info_app-features">
                    <li>
                        <form class="panel-info_sub-components_options" action="/service/v1/manager/logout">
                            <button class="panel-info_sub-components_options" name="logout" value="${id}">
                                <i class="fa-solid fa-right-from-bracket"></i>
                                Đăng xuất
                            </button>
                        </form>
                    </li>
                </ul>
            </div>
        </div>
    </header>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/header.js"></script>