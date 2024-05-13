function toggleHideCategory() {
    try {
        $("div#header_left i").addEventListener("click", (e) =>
            $("div#category").classList.toggle("hide")
        );
    } catch (ignored) {}
    try {
        $('div#header_right .avatar_user i').addEventListener("click", (e) => {
            console.log(1)
            $("div#panel-info").classList.toggle("hide")
        });
    } catch (ignored) {}
    window.addEventListener("click", e => {
        if (!$("div#panel-info").classList.contains("hide") && !e.target.parentElement.classList.contains("avatar_user")) {
            try {
                $("div#panel-info").classList.add("hide")
            } catch (ignored) {}
        }
    });
    $('form#logout-form > a').addEventListener("click", e => {
        if (confirm("Bạn chắc chắn muốn thực hiện thao tác?"))
            $('form#logout-form').submit();
    });
}

function mappingCategoryNameWithCurrentPage() {
    const pageName = new URL(window.location.href).pathname.split("/").pop();
    const categoryNameObjects = {
        ["update-teacher-request"]:{
            ["category-name"]: "Chỉnh sửa một yêu cầu tạo lịch",
            ["page-description"]: "Giúp giảng viên sửa yêu cầu đã gửi tới quản lý để tạo lịch thực hành."
        },
        ["add-teacher-request"]:{
            ["category-name"]: "Thêm một yêu cầu tạo lịch",
            ["page-description"]: "Giúp giảng viên tạo yêu cầu tới quản lý để tạo lịch thực hành."
        },
        ["show-info"]:{
            ["category-name"]: "Dữ liệu cá nhân",
            ["page-description"]: "Xem dữ liệu đang có hiện tại trên hệ thống."
        },
        ["set-manager-info"]:{
            ["category-name"]: "Thêm dữ liệu mới",
            ["page-description"]: "Thêm dữ liệu cho tài khoản 'quản lý' mới tạo."
        },
        ["update-manager-info"]:{
            ["category-name"]: "Cập nhật dữ liệu",
            ["page-description"]: "Cập nhật dữ liệu của tài khoản 'quản lý'."
        },
        ["set-teacher-info"]:{
            ["category-name"]: "Thêm dữ liệu mới",
            ["page-description"]: "Thêm dữ liệu cho tài khoản 'giảng viên' mới tạo."
        },
        ["change-password"]:{
            ["category-name"]: "Đổi mật khẩu",
            ["page-description"]: "Đổi một mật khẩu mới cho tài khoản của bạn khi cần thiết."
        },
        ["update-teacher-info"]:{
            ["category-name"]: "Cập nhật dữ liệu",
            ["page-description"]: "Cập nhật dữ liệu của tài khoản 'giảng viên'."
        },
        ["home"]: {
            ["category-name"]: "Chào mừng bạn quay trở lại với ứng dụng - Phân công lịch thực hành",
            ["page-description"]: "Hãy tiếp tục xử lý yêu cầu nào!"
        },
        ["add-teacher-account"]: {
            ["category-name"]: "Thêm tài khoản",
            ["page-description"]: "Thêm một tài khoản giảng viên đã có của học viện để cấp quyền sử dụng."
        },
        ["add-computer-room"]: {
            ["category-name"]: "Thêm phòng máy",
            ["page-description"]: "Thêm một phòng máy mới được xây dựng thuộc học viện."
        },
        ["computer-room-list"]: {
            ["category-name"]: "Toàn bộ phòng máy",
            ["page-description"]: "Toàn bộ phòng máy thuộc học viện."
        },
        ["update-computer-room"]: {
            ["category-name"]: "Cập nhật thông tin phòng máy",
            ["page-description"]: "Cập nhật thông tin, trạng thái của phòng máy."
        },
        ["teacher-list"]: {
            ["category-name"]: "Danh sách Giảng viên",
            ["page-description"]: "Danh sách toàn bộ Giảng viên thuộc học viên được cấp tài khoản để sử dụng."
        },
        ["update-teacher"]: {
            ["category-name"]: "Cập nhật thông tin Giảng viên",
            ["page-description"]: "Cập nhật toàn bộ thông tin của Giảng viên chỉ định."
        },
        ["teacher-account-list"]: {
            ["category-name"]: "Danh sách tài khoản Giảng viên",
            ["page-description"]: "Danh sách toàn bộ thông tin tài khoản của Giảng viên đã tạo."
        },
        ["update-teacher-account"]: {
            ["category-name"]: "Cập nhật tài khoản Giảng viên",
            ["page-description"]: "Cập nhật thông tin tài khoản cơ bản của Giảng viên."
        },
        ["teacher-request-list"]: {
            ["category-name"]: "Danh sách yêu cầu tạo lịch",
            ["page-description"]: "Danh sách yêu cầu của Giảng viên muốn tạo lịch thực hành."
        },
        ["teacher-request-detail"]: {
            ["category-name"]: "Chi tiết yêu cầu tạo lịch",
            ["page-description"]: "Bảng chi tiết về yêu cầu và giảng viên gửi yêu cầu."
        },
        ["add-practice-schedule"]: {
            ["category-name"]: "Tạo lịch thực hành từ yêu cầu",
            ["page-description"]: "Cung cấp các tính năng hỗ trợ tạo lịch thực hành từ yêu cầu giảng viên."
        },
        ["update-practice-schedule"]: {
            ["category-name"]: "Cập nhật lịch thực hành đã tạo",
            ["page-description"]: "Cung cấp các tính năng hỗ trợ cập nhật lịch thực hành đã tạo từ yêu cầu."
        },
        ["extra-features-page"]: {
            ["category-name"]: "Danh sách các tính năng thêm dữ liệu",
            ["page-description"]: "Cung cấp các tính năng hỗ trợ thêm dữ liệu còn thiếu sót."
        },
        ["add-file"]:{
            ["category-name"]: "Nhập dữ liệu từ file",
            ["page-description"]: "Nhập lượng lớn dữ liệu từ file theo bảng riêng."
        },
        ["add-subject"]: {
            ["category-name"]: "Thêm dữ liệu cho môn học",
            ["page-description"]: "Thêm dữ liệu cho một môn học còn sót hoặc mới mở."
        },
        ["add-student"]: {
           ["category-name"]: "Thêm dữ liệu cho sinh viên",
           ["page-description"]: "Thêm dữ liệu cho một sinh viên còn sót hoặc mới mở."
        },
        ["add-grade"]: {
           ["category-name"]: "Thêm dữ liệu cho lớp",
           ["page-description"]: "Thêm dữ liệu cho một lớp còn sót hoặc mới mở."
        },
        ["add-classroom"]: {
            ["category-name"]: "Thêm dữ liệu cho phòng học",
            ["page-description"]: "Thêm dữ liệu cho một phòng mới mở."
        },
        ["add-subjectRegistration"]: {
            ["category-name"]: "Thêm dữ liệu sinh viên đăng kí học phần",
            ["page-description"]: "Thêm dữ liệu thông tin mà sinh viên đã đăng kí học phần tùy chọn."
        },
        ["add-sectionClass"]:{
            ["category-name"]: "Thêm dữ liệu cho lớp học phần",
            ["page-description"]: "Thêm dữ liệu cho một lớp học phần mới mở."
        },
        ["add-semester"]:{
            ["category-name"]: "Thêm dữ liệu cho học kì",
            ["page-description"]: "Thêm dữ liệu cho một học kì mới mở."
        },
        ["add-department"]:{
            ["category-name"]: "Thêm dữ liệu cho khoa",
            ["page-description"]: "Thêm dữ liệu cho một khoa mới mở."
        }
    }

    $('header p#header_left_category-name').textContent = categoryNameObjects[pageName]["category-name"];
    $('header p#header_left_page-description').textContent = categoryNameObjects[pageName]["page-description"];
}

function buildHeader() {
    mappingCategoryNameWithCurrentPage();
    toggleHideCategory();
    customizeAllAvatarColor();
}