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
                if (value.length < 8) {
                    this.isValid = false;
                } else this.isValid = true;
                return this.isValid;
            },
            errorMessage: "Mật khẩu không đủ dài.",
            isValid: false,
        },
    };

    customizeClosingNoticeMessageEvent();
    createErrBlocksOfInputTags(validatingBlocks);
    customizeValidateEventInputTags(validatingBlocks);
    customizeToggleDisplayPasswordEvent();
    customizeSubmitFormAction(validatingBlocks);
    removePathAttributes();
})();
