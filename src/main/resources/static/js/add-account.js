let canSubmitForm = false;

(function main() {
    const validatingBlocks = {
        instituteEmail: {
            tag: $('input[name=instituteEmail]'),
            confirm: function (value) {
                this.isValid = (/^([^@\s]+)@(ptithcm\.edu\.vn|ptit\.edu\.vn|student\.ptithcm\.edu\.vn)$/).test(value);
                return this.isValid;
            },
            errorMessage: "Nhập đúng định dạng name.01@gmail.com",
            isValid: false,
        },
        password: {
            tag: $('input[name=password]'),
            confirm: function (value) {
                if (value.length < 8) {
                    this.isValid = false;
                } else this.isValid = true;
                return this.isValid;
            },
            errorMessage: "Mật khẩu không đủ dài.",
            isValid: false,
        },
        retypePassword: {
            tag: $('input[name=retypePassword]'),
            confirm: function (value) {
                if (value != $('input[name=password]').value) {
                    this.isValid = false;
                } else this.isValid = true;
                return this.isValid;
            },
            errorMessage: "Mật khẩu không chính xác.",
            isValid: false,
        },
    };

    
    customizeClosingErrMessageEvent();
    createErrBlocksOfInputTags(validatingBlocks);
    customizeValidateEventInputTags(validatingBlocks);
    customizeToggleDisplayPasswordEvent();
    customizeSubmitFormAction(validatingBlocks);
    removePathAttributes();
    mappingCategoryNameWithCurrentPage();
})();
