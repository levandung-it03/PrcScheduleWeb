
(function main() {
    const validatingBlocks = {
        instituteEmail: {
            tag: $('input[name=instituteEmail]'),
            validate: (value) => (/^([^@\s]+)@(ptithcm\.edu\.vn|ptit\.edu\.vn|student\.ptithcm\.edu\.vn)$/).test(value),
            errorMessage: "Nhập đúng định dạng name01@ptithcm.edu.vn",
        },
        password: {
            tag: $('input[name=password]'),
            validate: function (value) {
                //--Using function to make "this" work correctly.
                if (validatingBlocks.retypePassword.tag.value !== "")
                    validatingBlocks.retypePassword.tag.dispatchEvent(new Event("keyup"));
                return value.length >= 8;
            },
            errorMessage: "Mật khẩu không đủ dài.",
            isValid: false,
        },
        retypePassword: {
            tag: $('input[name=retypePassword]'),
            validate: (value) => (value == validatingBlocks.password.tag.value),
            errorMessage: "Mật khẩu không chính xác."
        },
    };

    customizeClosingNoticeMessageEvent();
    createErrBlocksOfInputTags(validatingBlocks);
    customizeValidateEventInputTags(validatingBlocks);
    customizeToggleDisplayPasswordEvent();
    customizeSubmitFormAction('div#add-account-page > form', validatingBlocks);
    removePathAttributes();
    buildHeader();
})();
