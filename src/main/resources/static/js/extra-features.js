
(function main() {
    renderFeatureBlocks();
    customizeClosingNoticeMessageEvent();
    buildHeader();
})();

function renderFeatureBlocks() {
    $('div.center-page div.center-page_list').innerHTML = [
        {
            name: "file",
            avatar: `<i class="fa-solid fa-file-arrow-up"></i>`,
            title: "Nhập một file dữ liệu",
            content: "Nhập một file chứa tập dữ liệu, làm nền tảng cho các dữ liệu khác"
        },
        {
            name: "department",
            avatar: `<i class="fa-solid fa-building-user"></i>`,
            title: "Thêm một khoa mới",
            content: "Thêm một khoa mới thuộc Học viện cơ sở, làm nền tảng cho các dữ liệu khác"
        },
        {
            name: "student",
            avatar: `<i class="fa-solid fa-user-graduate"></i>`,
            title: "Dữ liệu về Sinh viên",
            content: "Thêm dữ liệu về Sinh viên, phục vụ cho việc thêm lịch thực hành"
        },
        {
            name: "grade",
            avatar: `<i class="fa-solid fa-users-rectangle"></i>`,
            title: "Thêm một lớp mới",
            content: "Thêm một lớp mới thuộc Học viện cơ sở, làm nền tảng cho các dữ liệu khác"
        },
        {
            name: "semester",
            avatar: `<i class="fa-regular fa-calendar"></i>`,
            title: "Học kỳ",
            content: "Thêm dữ liệu về các mốc thời gian cho học kỳ mới"
        },
        {
            name: "subject",
            avatar: `<i class="fa-solid fa-book"></i>`,
            title: "Môn học",
            content: "Thêm dữ liệu về một môn học mới"
        },
        {
            name: "sectionClass",
            avatar: `<i class="fa-solid fa-book-open-reader"></i>`,
            title: "Thêm lớp học phần",
            content: "Một lớp học phần được mở ra và cần cập nhật dữ liệu"
        },
        {
            name: "subjectRegistration",
            avatar: `<i class="fa-solid fa-user-check"></i>`,
            title: "Dữ liệu sinh viên đăng ký lớp học phần",
            content: "Thêm dữ liệu về việc đăng ký lớp học phần của một sinh viên"
        },
        {
            name: "classroom",
            avatar: `<i class="fa-solid fa-people-roof"></i>`,
            title: "Thông tin phòng học",
            content: "Một phòng học mới được xây và bạn đang thêm dữ liệu"
        },
    ].map((infoOfComponent, index) =>
        `<div class="feature-blocks" id="${infoOfComponent.name}">
            <a href="/manager/extra-features/add-${infoOfComponent.name}">
                <span class="index">${index}</span>
                <span class="avatar">${infoOfComponent.avatar}</span>
                <span class="title""><b>${infoOfComponent.title}</b></span>
                <span class="content"><p class="content">${infoOfComponent.content}</p></span>
            </a>
        </div>`
    ).join("");
}