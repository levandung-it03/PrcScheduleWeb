
(function main() {
    customizeClosingNoticeMessageEvent();
    customizeSubmitFormAction('div#import-file-page > form');
    recoveryAllSelectTagDataInForm();
    removePathAttributes();
    buildHeader();

    (function createInstructionDocumentBlock() {
        const specifiedSyntaxDataContent = [
            {
                subIndex: "1. Sinh viên(Mã sinh viên, Tên, Họ, Giới tính, Email Học Viện, Mã lớp)",
                baseCodeReview: {
                    tableName: "Student",
                    orderedFields: "(student_id, last_name, first_name, gender_enum, institute_email, grade_id)",
                    simpleLineDataExample: "('N20DCCN024', 'Lê Trung', 'BOY', 'n20dccn024@student.ptithcm.edu.vn', 'Hải', 'D21CQCN02-N');",
                    multipleLinesDatExample: [
                        "('N20DCCN025', 'Lê Thị', 'Huyền', 'GIRL', 'n20dccn025@student.ptithcm.edu.vn', 'D21CQCN02-N'),",
                        "(....., ...., ....., ...., ...., ....),",
                        "('N20DCCN024', 'Lê Trung', 'BOY', 'n20dccn024@student.ptithcm.edu.vn', 'Hải', 'D21CQCN02-N');"
                    ]
                },

            },
            {
                subIndex: "2. Khoa(Mã khoa, Tên khoa)",
                baseCodeReview: {
                    tableName: "Department",
                    orderedFields: "(department_id, department_name)",
                    simpleLineDataExample: "('CNTT01', 'Công nghệ thông tin 01');",
                    multipleLinesDatExample: [
                        "('CNTT01', 'Công nghệ thông tin 01'),",
                        "(......., .........),",
                        "('DTVT02', 'Điện tử 02');"
                    ]
                },

            },
            {
                subIndex: "3. Lớp(Mã lớp, Mã khoa)",
                baseCodeReview: {
                    tableName: "Grade",
                    orderedFields: "(grade_id, department_id)",
                    simpleLineDataExample: "('D21CQCN01-N', 'CNTT01');",
                    multipleLinesDatExample: [
                        "('D21CQCN01-N', 'CNTT01'),",
                        "(......., .........),",
                        "('D21CQDT02-N', 'DTVT02');"
                    ]
                },

            },
            {
                subIndex: "4. Học kì(Học kì, Năm học, Tuần bắt đầu, tổng số tuần học)",
                baseCodeReview: {
                    tableName: "Semester",
                    orderedFields: "(semester, range_of_year, first_week, total_week)",
                    simpleLineDataExample: "('1', '2021_2022', '1', '10');",
                    multipleLinesDatExample: [
                        "('1', '2023_2024', '4', '10'),",
                        "(......., .........),",
                        "('2', '2023_2024', '14', '10');"
                    ]
                },

            },
            {
                subIndex: "5. Môn học(Mã môn, Tên môn, Số tín chỉ)",
                baseCodeReview: {
                    tableName: "Subject",
                    orderedFields: "(subject_id , subject_name, credits_number)",
                    simpleLineDataExample: "('INT13147', 'Python', '3');",
                    multipleLinesDatExample: [
                        "('INT13147', 'Python', '3'),",
                        "(......., .........),",
                        "('INT13142', 'Hệ điều hành', '2');"
                    ]
                },

            },
            {
                subIndex: "6. Lớp học phần(Mã học kì, Mã lớp, Mã môn học, Tổng số nhóm)",
                baseCodeReview: {
                    tableName: "Section_class",
                    orderedFields: "(semester_id , grade_id , subject_id, group_from_subject)",
                    simpleLineDataExample: "('1', 'D21CQCN01-N', 'INT13147', '4');",
                    multipleLinesDatExample: [
                        "('1', 'D21CQCN01-N', 'INT13147', '4'),",
                        "(......., .........),",
                        "('2', 'D21CQCN01-N', 'INT13142', '2');"
                    ]
                },

            },
            {
                subIndex: "7. Sinh viên đăng ký học phần(Mã lớp học phần, Mã sinh viên)",
                baseCodeReview: {
                    tableName: "Subject_registration",
                    orderedFields: "(section_class_id, student_id)",
                    simpleLineDataExample: "('1', 'N21DCCN152');",
                    multipleLinesDatExample: [
                        "('1', 'N21DCCN152'),",
                        "(......., .........),",
                        "('3', 'N21DCCN057');"
                    ]
                },

            },
            {
                subIndex: "8. Thông tin phòng học(Mã phòng học, Loại phòng, Số lượng tối đa)",
                baseCodeReview: {
                    tableName: "Classroom",
                    orderedFields: "(room_id, room_type_enum, max_quantity)",
                    simpleLineDataExample: "('2A12', 'PRC', '60');",
                    multipleLinesDatExample: [
                        "('2A12', 'PRC', '60'),",
                        "(......., .........),",
                        "('2B31', 'NORM', '40');",
                        "--Quy ước: Phòng học=NORM, Phòng thực hành=PRC.--"
                    ]
                },

            },
        ];
        $('div#instruction-block div#replaced-data').outerHTML = specifiedSyntaxDataContent.map(dataBlock => `
            </br><span class="sub-index"><i>${dataBlock.subIndex}</i></span>
            <p class="content">
                - Cú pháp cơ bản:
                <span class="code-review">
                    </br><span class="code-review_table-name">${dataBlock.baseCodeReview.tableName}</span>
                    </br><span class="code-review_fields-order">${dataBlock.baseCodeReview.orderedFields}</span>
                    </br><span class="code-review_data-example">${dataBlock.baseCodeReview.simpleLineDataExample}</span>
                </span>
                </br>- Hoặc với nhiều dữ liệu hơn:
                <span class="code-review">
                    </br><span class="code-review_table-name">${dataBlock.baseCodeReview.tableName}</span>
                    </br><span class="code-review_fields-order">${dataBlock.baseCodeReview.orderedFields}</span>
                    ${dataBlock.baseCodeReview.multipleLinesDatExample
                        .map(dataLine => `</br><span class="code-review_data-example">${dataLine}</span>`)
                        .join("")}
                </span>
            </p>
        `).join("");
    })();
})();
