
(function main() {
    const validatingBlocks = {
        studentId: {
            tag: $('input[name=studentId]'),
            confirm: function (value) {
                this.isValid = (value.length != 0) && (value.search(" ")== -1);
                return this.isValid;
            },
            errorMessage: "Mã sinh viên không hợp lệ.",
            isValid: false,
        },
        grade: {
             tag: $('input[name=grade]'),
             confirm: function (value) {
                  this.isValid = (value.length != 0) && (value.search(" ")== -1);
                  return this.isValid;
             },
             errorMessage: "Mã lớp không được để trống.",
             isValid: false,
        },
        lastName: {
            tag: $('input[name=lastName]'),
            confirm: function (value) {
                this.isValid = (/^[A-Za-zÀ-ỹ]{1,50}( [A-Za-zÀ-ỹ]{1,50})*$/).test(value);
                return this.isValid;
            },
            errorMessage: "Tên cuối không hợp lệ.",
            isValid: false,
        },
        firstName: {
                    tag: $('input[name=firstName]'),
                    confirm: function (value) {
                        this.isValid = (/^[A-Za-zÀ-ỹ]{1,50}( [A-Za-zÀ-ỹ]{1,50})*$/).test(value);
                        return this.isValid;
                    },
                    errorMessage: "Tên cuối không hợp lệ.",
                    isValid: false,
                },
        instituteEmail: {
            tag: $('input[name=instituteEmail]'),
            confirm: function (value) {
                this.isValid = (/^([^@\s]+)@(ptithcm\.edu\.vn|ptit\.edu\.vn|student\.ptithcm\.edu\.vn)$/).test(value);
                return this.isValid;
            },
            errorMessage: "Định dạng email chưa đúng.",
            isValid: false,
        },
    };

    customizeClosingNoticeMessageEvent();
    createErrBlocksOfInputTags(validatingBlocks);
    customizeValidateEventInputTags(validatingBlocks);
    customizeSubmitFormAction(validatingBlocks);
    customizeAutoFormatStrongInputTextEvent();
    recoveryAllSelectTagDataInForm();
    removePathAttributes();
    mappingCategoryNameWithCurrentPage();
})();
