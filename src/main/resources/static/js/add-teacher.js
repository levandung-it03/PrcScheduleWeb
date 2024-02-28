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
                if (value != globalThis.password.tag.value) {
                    this.isValid = false;
                } else this.isValid = true;
                return this.isValid;
            },
            errorMessage: "Mật khẩu không chính xác.",
            isValid: false,
        },
    };

    cutomizeClosingErrMessageEvent();
    createErrBlocksOfInputTags(validatingBlocks);
    customizeInputTagValidateEvents(validatingBlocks);
    customizeToggleDisplayPasswordEvent(validatingBlocks.password.tag);
    customizeSubmitFormAction(validatingBlocks);
})();

function customizeToggleDisplayPasswordEvent(passwordTag) {
    $$('.register-input_toggle-hidden i').forEach((e) => {
        e.onclick = (event) => {
            if ([...event.target.classList].some((e) => e == "show-pass")) {
                $('.register-input_toggle-hidden .show-pass').classList.add("hidden");
                $('.register-input_toggle-hidden .hide-pass').classList.remove("hidden");
                passwordTag.type = "text";
            } else {
                $('.register-input_toggle-hidden .hide-pass').classList.add("hidden");
                $('.register-input_toggle-hidden .show-pass').classList.remove("hidden");
                passwordTag.type = "password";
            }
        }
    })
}