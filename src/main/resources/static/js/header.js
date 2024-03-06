(function toggleHideCategory() {
    document.querySelector("div#header_left i").addEventListener("click", (e) =>
        document.querySelector("div#category").classList.toggle("hide")
    );
})();

function mappingCategoryNameWithCurrentPage() {
    const pageName = new URLSearchParams(window.location.href).toString().split("%2F").pop();
    const categoryNameObjects = {
        ["add-teacher-account="]: {
            ["category-name"]: "Thêm tài khoản",
            ["page-discription"]: "Thêm một tài khoản giảng viên đã có của học viện để cấp quyền sử dụng."
        },
        ["add-computer-room="]: {
            ["category-name"]: "Thêm phòng máy",
            ["page-discription"]: "Thêm một phòng máy mới được xây dựng thuộc học viện."
        },
        ["computer-room-list="]: {
            ["category-name"]: "Toàn bộ phòng máy",
            ["page-discription"]: "Toàn bộ phòng máy thuộc học viện."
        },
        ["update-computer-room="]: {
            ["category-name"]: "Cập nhật thông tin phòng máy",
            ["page-discription"]: "Cập nhật thông tin, trạng thái của phòng máy."
        },
    }

    document.querySelector('header #header_center p#category-name').innerText = categoryNameObjects[pageName]["category-name"];
    document.querySelector('header #header_center p#page-discription').innerText = categoryNameObjects[pageName]["page-discription"];
}
