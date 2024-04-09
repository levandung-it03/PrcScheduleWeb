
(function main() {
    const validatingBlocks = {
        instituteEmail: {
            tag: $('input[name=instituteEmail]'),
            confirm: function (value) {
                this.isValid = (/^([^@\s]+)@(ptithcm\.edu\.vn|ptit\.edu\.vn|student\.ptithcm\.edu\.vn)$/).test(value);
                return this.isValid;
            },
            errorMessage: "Nhập đúng định dạng name01@ptithcm.edu.vn",
            isValid: false,
        },
        password: {
            tag: $('input[name=password]'),
            confirm: function (value) {
                this.isValid = value.length >= 8;
                validatingBlocks.retypePassword.confirm(validatingBlocks.retypePassword.tag.value);
                return this.isValid;
            },
            errorMessage: "Mật khẩu không đủ dài.",
            isValid: false,
        },
        retypePassword: {
            tag: $('input[name=retypePassword]'),
            confirm: function (value) {
                this.isValid = value == validatingBlocks.password.tag.value;
                return this.isValid;
            },
            errorMessage: "Mật khẩu không chính xác.",
            isValid: false,
        },
    };

    
    customizeClosingNoticeMessageEvent();
    createErrBlocksOfInputTags(validatingBlocks);
    customizeValidateEventInputTags(validatingBlocks);
    customizeToggleDisplayPasswordEvent();
    customizeSubmitFormAction(validatingBlocks);
    removePathAttributes();
    mappingCategoryNameWithCurrentPage();
})();
