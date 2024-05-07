(function main() {
    const validatingBlocks = {
        departmentId: {
            tag: $('input[name=departmentId]'),
            confirm: function (value) {
                var regex = /^[a-zA-Z\d\s]+$/;
                this.isValid = (value.length > 0) && (value.length <=20) && regex.test(value);
                return this.isValid;
            },
            errorMessage: "Mã khoa không hợp lệ.",
            isValid: false,
        },
        departmentName: {
            tag: $('input[name=departmentName]'),
            confirm: function (value) {
                var regex = /^[\p{L}\d\s]+$/u;
                this.isValid = (value.length !== 0) && regex.test(value);
                return this.isValid;
            },
            errorMessage: "Tên khoa không hợp lệ.",
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