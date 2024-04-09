
(function main() {
    const validatingBlocks = {
        lastName: {
            tag: $('input[name=lastName]'),
            confirm: function (value) {
                this.isValid = (/^[A-Za-zÀ-ỹ]{1,50}( [A-Za-zÀ-ỹ]{1,50})*$/).test(value);
                return this.isValid;
            },
            errorMessage: "Họ giảng viên không hợp lệ.",
            isValid: false,
        },
        firstName: {
            tag: $('input[name=firstName]'),
            confirm: function (value) {
                this.isValid = (/^[A-Za-zÀ-ỹ]{1,50}$/).test(value);
                return this.isValid;
            },
            errorMessage: "Tên giảng viên không hợp lệ.",
            isValid: false,
        },
        birthday: {
            tag: $('input[name=birthday]'),
            confirm: function (value) {                
                const isNotNaN = !isNaN(new Date(value));
                const isInThePast = (new Date(value) < new Date());
                const isNotTooFar = (new Date().getFullYear() - new Date(value).getFullYear()) < 150;
                this.isValid = isNotNaN && isInThePast && isNotTooFar
                return this.isValid;
            },
            errorMessage: "Ngày sinh không hợp lệ.",
            isValid: false,
        },
        phone: {
            tag: $('input[name=phone]'),
            confirm: function (value) {
                this.isValid = /^[0-9]{4,12}$/.test(value);
                return this.isValid;
            },
            errorMessage: "Số điện thoại không hợp lệ.",
            isValid: false,
        },
    };
    
    customizeClosingNoticeMessageEvent();
    createErrBlocksOfInputTags(validatingBlocks);
    customizeValidateEventInputTags(validatingBlocks);
    customizeSubmitFormAction(validatingBlocks);
    recoveryAllSelectTagDataInForm();
    removePathAttributes();
    mappingCategoryNameWithCurrentPage();
    customizeAutoFormatStrongInputTextEvent();
})();
