(function main() {
    const validatingBlocks = {
        instituteEmail: {
            tag: $('input[name=instituteEmail]'),
            validate: (value) => (/^([^@\s]+)@(ptithcm\.edu\.vn|ptit\.edu\.vn|student\.ptithcm\.edu\.vn)$/).test(value),
            errorMessage: "Nhập đúng định dạng name01@ptithcm.edu.vn",
        },
        password: {
            tag: $('input[name=password]'),
            validate: (value) => value.length >= 8,
            errorMessage: "Mật khẩu không đủ dài.",
        },
    };

    customizeClosingNoticeMessageEvent();
    createErrBlocksOfInputTags(validatingBlocks);
    customizeValidateEventInputTags(validatingBlocks);
    customizeToggleDisplayPasswordEvent();
    customizeSubmitFormAction('div#center-block > form', validatingBlocks);
    removePathAttributes();
})();
