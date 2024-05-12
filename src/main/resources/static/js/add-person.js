
(function main() {
    const validatingBlocks = {
        [`${$('span#id-name').textContent}`]: {
            tag: $(`input[name=${$('span#id-name').textContent}]`),
            validate: function (value) {
                this.tag.value = value.toUpperCase();
                return 0 < value.length && value.length <= 10;
            },
            errorMessage: "Mã quá dài hoặc rỗng",
        },
        lastName: {
            tag: $('input[name=lastName]'),
            validate: (value) => (/^ *[A-Za-zÀ-ỹ]{1,50}( *[A-Za-zÀ-ỹ]{1,50})* *$/).test(value),
            errorMessage: "Họ không hợp lệ.",
        },
        firstName: {
            tag: $('input[name=firstName]'),
            validate: (value) => (/^ *[A-Za-zÀ-ỹ]{1,50} *$/).test(value),
            errorMessage: "Tên không hợp lệ.",
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
            validate: (value) => /^[0-9]{4,12}$/.test(value),
            errorMessage: "Số điện thoại không hợp lệ.",
        },
    };
    
    customizeClosingNoticeMessageEvent();
    createErrBlocksOfInputTags(validatingBlocks);
    customizeValidateEventInputTags(validatingBlocks);
    customizeSubmitFormAction('div.center-page > form', validatingBlocks);
    recoveryAllSelectTagDataInForm();
    removePathAttributes();
    buildHeader();
    customizeAutoFormatStrongInputTextEvent();
})();
