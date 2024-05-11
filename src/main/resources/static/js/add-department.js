(function main() {
    const validatingBlocks = {
        departmentId: {
            tag: $('input[name=departmentId]'),
            validate: (value) => (value.length > 0) && (value.length <=20) && (/^[a-zA-Z\d\s]+$/).test(value),
            errorMessage: "Mã khoa không hợp lệ.",
        },
        departmentName: {
            tag: $('input[name=departmentName]'),
            validate: (value) => ((value.length !== 0) && (/^[\p{L}\d\s]+$/u).test(value)),
            errorMessage: "Tên khoa không hợp lệ.",
        },
    };

    customizeClosingNoticeMessageEvent();
    createErrBlocksOfInputTags(validatingBlocks);
    customizeValidateEventInputTags(validatingBlocks);
    customizeSubmitFormAction('div#add-department-page > form', validatingBlocks);
    customizeAutoFormatStrongInputTextEvent();
    recoveryAllSelectTagDataInForm();
    removePathAttributes();
    buildHeader();
})();