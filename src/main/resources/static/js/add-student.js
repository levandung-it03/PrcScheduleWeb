
(function main() {
    const validatingBlocks = {
        studentId: {
            tag: $('input[name=studentId]'),
            validate: (value) => (value.length != 0) && (value.search(" ") == -1),
            errorMessage: "Mã sinh viên không hợp lệ.",
        },
        grade: {
            tag: $('input[name=grade]'),
            validate: (value) => (value.length != 0) && (value.search(" ") == -1),
            errorMessage: "Mã lớp không được để trống.",
        },
        lastName: {
            tag: $('input[name=lastName]'),
            validate: (value) => (/^ *[A-Za-zÀ-ỹ]{1,50}( *[A-Za-zÀ-ỹ]{1,50})* *$/).test(value),
            errorMessage: "Tên cuối không hợp lệ.",
        },
        firstName: {
            tag: $('input[name=firstName]'),
            validate: (value) => (/^ *[A-Za-zÀ-ỹ]{1,50}( *[A-Za-zÀ-ỹ]{1,50})* *$/).test(value),
            errorMessage: "Tên cuối không hợp lệ.",
        },
        instituteEmail: {
            tag: $('input[name=instituteEmail]'),
            validate: (value) => (/^([^@\s]+)@(ptithcm\.edu\.vn|ptit\.edu\.vn|student\.ptithcm\.edu\.vn)$/).test(value),
            errorMessage: "Định dạng email chưa đúng.",
        },
    };

    customizeClosingNoticeMessageEvent();
    createErrBlocksOfInputTags(validatingBlocks);
    customizeValidateEventInputTags(validatingBlocks);
    customizeSubmitFormAction('div#add-student-page > form', validatingBlocks);
    customizeAutoFormatStrongInputTextEvent();
    recoveryAllSelectTagDataInForm();
    removePathAttributes();
    buildHeader();
})();
