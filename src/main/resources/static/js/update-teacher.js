
(function main() {
    const validatingBlocks = {
        lastName: {
            tag: $('input[name=lastName]'),
            validate: (value) => (/^[A-Za-zÀ-ỹ]{1,50}( [A-Za-zÀ-ỹ]{1,50})*$/).test(value),
            errorMessage: "Họ giảng viên không hợp lệ.",
        },
        firstName: {
            tag: $('input[name=firstName]'),
            validate: (value) => (/^[A-Za-zÀ-ỹ]{1,50}$/).test(value),
            errorMessage: "Tên giảng viên không hợp lệ.",
        },
        birthday: {
            tag: $('input[name=birthday]'),
            validate: (value) =>!isNaN(new Date(value))  //--is not "NaN"
                && (new Date(value) < new Date())       //--is not in the past
                && (new Date().getFullYear() - new Date(value).getFullYear()) < 150,    //--is not too long.,
            errorMessage: "Ngày sinh không hợp lệ.",
        },
        phone: {
            tag: $('input[name=phone]'),
            validate: (value) => (/^[0-9]{4,12}$/).test(value),
            errorMessage: "Số điện thoại không hợp lệ.",
        },
    };
    
    customizeClosingNoticeMessageEvent();
    createErrBlocksOfInputTags(validatingBlocks);
    customizeValidateEventInputTags(validatingBlocks);
    customizeSubmitFormAction('div#update-teacher-page > form', validatingBlocks);
    recoveryAllSelectTagDataInForm();
    removePathAttributes();
    buildHeader();
    customizeAutoFormatStrongInputTextEvent();
})();
