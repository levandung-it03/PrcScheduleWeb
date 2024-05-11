
(function main() {
    const validatingBlocks = {
        subjectId: {
            tag: $('input[name=subjectId]'),
            validate: (value) => (value.length != 0) && (value.search(" ") == -1),
            errorMessage: "Mã môn học không hợp lệ.",
        },
        subjectName: {
            tag: $('input[name=subjectName]'),
            validate: (value) => (/^[A-Za-zÀ-ỹ]{1,50}( [A-Za-zÀ-ỹ]{1,50})*$/).test(value),
            errorMessage: "Tên môn học không hợp lệ.",
        },
        creditsNumber: {
            tag: $('input[name=creditsNumber]'),
            validate: (value) => (0 < value) && (value < 21),
            errorMessage: "Số tín chỉ không hợp lệ.",
            isValid: false,
        },
    };

    customizeClosingNoticeMessageEvent();
    createErrBlocksOfInputTags(validatingBlocks);
    customizeValidateEventInputTags(validatingBlocks);
    customizeSubmitFormAction('div#add-subject-page > form', validatingBlocks);
    customizeAutoFormatStrongInputTextEvent();
    recoveryAllSelectTagDataInForm();
    removePathAttributes();
    buildHeader();
})();
