const dialogOfPage = $('div.dialog-of-page');

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
                if (validatingBlocks.retypePassword.tag.value !== "")
                    validatingBlocks.retypePassword.tag.dispatchEvent(new Event("keyup"));
                return value.length >= 8;
            },
            errorMessage: "Mật khẩu không đủ dài."
        },
        retypePassword: {
            tag: $('input[name=retypePassword]'),
            validate: (value) => validatingBlocks.password.tag.value === value,
            errorMessage: "Mật khẩu không khớp."
        },
    };

    customizeClosingNoticeMessageEvent();
    createErrBlocksOfInputTags(validatingBlocks, 'div#change-password-page > form');
    customizeValidateEventInputTags(validatingBlocks);
    // buildHeader();
    customizeToggleDisplayPasswordEvent();
    buildHeader();


    (function customizeSubmitFormToGetOtpCodeAction() {
        $('div#change-password-page > form > a.mock-submit-form-btn').addEventListener("click", e => {
            if (confirm("Bạn chắc chắn muốn thực hiện thao tác?") === true) {
                let isValid = Object.entries(validatingBlocks)
                    .every(elem => {
                        //--trim() all input-tag-values on UI.
                        elem[1].tag.value = elem[1].tag.value.trim();
                        return elem[1].validate(elem[1].tag.value);
                    });
                if (!isValid) alert("Thông tin đầu vào bị lỗi!");
                else {
                    Object.entries(validatingBlocks).forEach(elem => {
                        $(`div.dialog-of-page input[name=${elem[0]}]`).value = elem[1].tag.value;
                    });
                    dialogOfPage.classList.remove("closed");
                    $('div#change-password-page > form').submit();
                }
            }
        });
    })();
    
    (function customizeDialogOfPage() {
        const validatingBlocks = {
            otpCode: {
                tag: $('input[name=otpCode]'),
                validate: (value) => /^[A-Z0-9]+$/.test(value),
                errorMessage: "Mã OTP không hợp lệ",
            },
        };
        createErrBlocksOfInputTags(validatingBlocks, 'div.dialog-of-page_container > form');
        customizeSubmitFormAction('div.dialog-of-page_container > form', validatingBlocks);
    
        //--Customize closing form-dialog action.
        $('div.dialog-of-page_wrapper')
            .addEventListener("click", e => dialogOfPage.classList.add("closed"));
        $('div.dialog-of-page_container div.closing-dialog-btn')
            .addEventListener("click", e => dialogOfPage.classList.add("closed"));
    })();
})();
