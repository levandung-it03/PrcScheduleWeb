<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/category.css">
    <div id="category">
        <div id="category_header">
                <div id="category_header_logo">
                    <img src="${pageContext.request.contextPath}/img/ptit_logo.png" alt="">
            </div>
        </div>
        <div id="category_body">
            <div id="category_body_home">
                <ul class="category-index-container" id="cat_home_box">
                    <li class="index">
                        <a class="index_wrapper" href="/home">
                            <i class="fa-solid fa-tv"></i>
                            <p>Trang chủ</p>
                        </a>
                    </li>
                </ul>
            </div>
            <div id="category_body_practice-schedule">
                <label>LỊCH THỰC HÀNH</label>
                <ul class="category-index-container" id="cat_practice-schedule_box">
                    <li class="index">
                        <a class="index_wrapper" href="/manager/category/practice-schedule/add-practice-schedule">
                        <i class="fa-regular fa-calendar-plus"></i>
                            <p>Thêm lịch thực hành</p>
                        </a>
                    </li>
                    <li class="index">
                        <a class="index_wrapper" href="">
                            <i class="fa-regular fa-envelope"></i>
                            <p>Toàn bộ yêu cầu</p>
                        </a>
                    </li>
                    <li class="index">
                        <a class="index_wrapper" href="">
                            <i class="fa-regular fa-calendar-minus"></i>
                            <p>Toàn bộ lịch</p>
                        </a>
                    </li>
                </ul>
            </div>
            <div id="category_body_computer-room">
                <label>PHÒNG MÁY</label>
                <ul class="category-index-container" id="cat_practice-schedule_box">
                    <li class="index">
                        <a class="index_wrapper" href="/manager/category/computer-room/add-computer-room">
                            <i class="fa-solid fa-house-laptop"></i>
                            <p>Thêm phòng máy mới</p>
                        </a>
                    </li>
                    <li class="index">
                        <a class="index_wrapper" href="/manager/category/computer-room/computer-room-list">
                            <i class="fa-solid fa-book-bookmark"></i>
                            <p>Toàn bộ phòng</p>
                        </a>
                    </li>
                </ul>
            </div>
            <div id="category_body_teacher-account">
                <label>TÀI KHOẢN</label>
                <ul class="category-index-container" id="cat_practice-schedule_box">
                    <li class="index">
                        <a class="index_wrapper" href="/manager/category/teacher/add-teacher-account">
                            <i class="fa-solid fa-user-plus"></i>
                            <p>Thêm tài khoản</p>
                        </a>
                    </li>
                    <li class="index">
                        <a class="index_wrapper" href="/manager/category/teacher/teacher-account-list">
                            <i class="fa-regular fa-address-book"></i>
                            <p>Danh sách tài khoản</p>
                        </a>
                    </li>
                    <li class="index">
                        <a class="index_wrapper" href="/manager/category/teacher/teacher-list">
                            <i class="fa-solid fa-address-book"></i>
                            <p>Danh sách Giảng viên</p>
                        </a>
                    </li>
                </ul>
            </div>
            <div id="category_body_extra-features">
                <label>TÍNH NĂNG PHỤ</label>
                <ul class="category-index-container" id="cat_practice-schedule_box">
                    <li class="index">
                        <a class="index_wrapper" href="/manager/category/teacher/extra-features">
                            <i class="fa-solid fa-screwdriver-wrench"></i>
                            <p>Danh sách tính năng</p>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
        <div id="category_footer">
            <ul class="category-index-container">
                <li class="index">
                    <a class="index_wrapper" href="">
                        <i class="fa-regular fa-life-ring"></i>
                        <p>Support</p>
                    </a>
                </li>
            </ul>
        </div>
    </div>