(function toggleHideCategory() {
    document.querySelector("div#header_left i").addEventListener("click", (e) =>
        document.querySelector("div#category").classList.toggle("hide")
    );
})();

function mappingCategoryNameWithCurrentPage() {
    const pageName = new URL(window.location.href).pathname.split("/").pop();
    const categoryNameObjects = {
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
        }
    }

    document.querySelector('header #header_center p#category-name').innerText = categoryNameObjects[pageName]["category-name"];
    document.querySelector('header #header_center p#page-description').innerText = categoryNameObjects[pageName]["page-description"];
}
