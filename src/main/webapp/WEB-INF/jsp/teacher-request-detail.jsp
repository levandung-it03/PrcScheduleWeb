<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Teacher Request Detail</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css"
        integrity="sha512-SzlrxWUlpfuzQ+pcUCosxcglQRNAq/DZjVsC0lE40xsADsfeQoEypE+enwcOiGjk/bSuGGKHEyjSoQ1zVisanQ=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/base.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/teacher-request-detail.css">
</head>
<body>
    <%@ include file="/WEB-INF/jsp/category.jsp" %>
    <div id="center-page" id="teacher-request-detail-page">
        <%@ include file="/WEB-INF/jsp/header.jsp" %>
        <div class="detail-block">
            <div class="detail-block_discription" id="teacher-info">
                <i class="fa-regular fa-address-card"></i>
                <p>THÔNG TIN GIẢNG VIÊN</p>
            </div>
            <div class="data-row" id="teacher-id">
                <label for="teacher-id">Mã giảng viên</label>
                <p>${teacherRequest.teacher.teacherId}</p>
            </div>
            <div class="data-row" id="full-name">
                <label for="full-name">Họ và tên</label>
                <p>${teacherRequest.teacher.lastName} ${teacherRequest.teacher.firstName}</p>
            </div>
            <div class="data-row" id="institute-email">
                <label for="institute-email">Email</label>
                <p>${teacherRequest.teacher.account.instituteEmail}</p>
            </div>
            <div class="data-row" id="phone">
                <label for="phone">Số điện thoại</label>
                <p>${teacherRequest.teacher.phone}</p>
            </div>
        </div>
        <div class="detail-block">
            <div class="detail-block_discription" id="request-info">
                <i class="fa-regular fa-envelope"></i>
                <p>CHI TIẾT YÊU CẦU</p>
            </div>
            <div class="data-row" id="subject-name">
                <label for="subject-name">Tên môn học</label>
                <p>${teacherRequest.sectionClass.subject.subjectName}</p>
            </div>
            <div class="data-row" id="grade-id">
                <label for="grade-id">Lớp mở môn</label>
                <p>${teacherRequest.sectionClass.grade.gradeId}</p>
            </div>
            <div class="data-row" id="group-from-subject">
                <label for="group-from-subject">Tổ mà giảng viên dạy</label>
                <p>${teacherRequest.sectionClass.groupFromSubject}</p>
            </div>
            <div class="data-row" id="request-message-detail">
                <label for="request-message-detail">Mô tả yêu cầu</label>
                <p>${teacherRequest.requestMessageDetail}</p>
            </div>
        </div>
    </div>
    </div>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/base.js"></script>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/teacher-request-detail.js"></script>
</body>
</html>
