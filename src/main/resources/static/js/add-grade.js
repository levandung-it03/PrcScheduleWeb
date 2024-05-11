
(function main() {
    const validatingBlocks = {
        gradeId: {
            tag: $('input[name=gradeId]'),
            validate: (value) => (value.length != 0) && (value.search(" ") == -1),
            errorMessage: "Mã lớp không hợp lệ.",
        },
        department: {
            tag: $('input[name=department]'),
            validate: (value) => (value.length != 0) && (value.search(" ") == -1),
            errorMessage: "Mã ngành không được để trống.",
        },
    };

    customizeClosingNoticeMessageEvent();
    createErrBlocksOfInputTags(validatingBlocks);
    customizeValidateEventInputTags(validatingBlocks);
    customizeSubmitFormAction('div#add-grade-page > form', validatingBlocks);
    customizeAutoFormatStrongInputTextEvent();
    recoveryAllSelectTagDataInForm();
    removePathAttributes();
    buildHeader();
})();
