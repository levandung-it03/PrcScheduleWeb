<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/category.css">
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
                        <a class="index_wrapper" href="">
                            <i class="fa-regular fa-envelope"></i>
                            <p>Các yêu cầu</p>
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
            <div id="category_body_teacher">
                <label>GIẢNG VIÊN</label>
                <ul class="category-index-container" id="cat_practice-schedule_box">
                    <li class="index">
                        <a class="index_wrapper" href="/manager/category/teacher/add-teacher-account">
                            <i class="fa-regular fa-envelope"></i>
                            <p>Thêm tài khoản</p>
                        </a>
                    </li>
                    <li class="index">
                        <a class="index_wrapper" href="">
                            <i class="fa-regular fa-calendar-minus"></i>
                            <p>Toàn bộ tài khoản</p>
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