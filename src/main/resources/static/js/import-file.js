
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

            }
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
