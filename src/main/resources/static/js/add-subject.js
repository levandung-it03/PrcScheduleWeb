
(function main() {
    const validatingBlocks = {
        subjectId: {
            tag: $('input[name=subjectId]'),
            confirm: function (value) {
                this.isValid = (value.length != 0) && (value.search(" ") == -1);
                return this.isValid;
            },
            errorMessage: "Mã môn học không hợp lệ.",
            isValid: false,
        },
        subjectName: {
            tag: $('input[name=subjectName]'),
            confirm: function (value) {
                this.isValid = (/^[A-Za-zÀ-ỹ]{1,50}( [A-Za-zÀ-ỹ]{1,50})*$/).test(value);
                return this.isValid;
            },
            errorMessage: "Tên môn học không hợp lệ.",
            isValid: false,
        },
        creditsNumber: {
            tag: $('input[name=creditsNumber]'),
            confirm: function (value) {
                this.isValid = (0 < value) && (value < 21);
                return this.isValid;
            },
            errorMessage: "Số tín chỉ không hợp lệ.",
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
