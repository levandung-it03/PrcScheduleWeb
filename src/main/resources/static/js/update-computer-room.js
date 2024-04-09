
(function main() {
    const validatingBlocks = {
        maxQuantity: {
            tag: $('input[name=maxQuantity]'),
            confirm: function (value) {
                this.isValid = value.length != 0;
                return this.isValid;
            },
            errorMessage: "Giá trị không hợp lệ.",
            isValid: false,
        },
        maxComputerQuantity: {
            tag: $('input[name=maxComputerQuantity]'),
            confirm: function (value) {
                this.isValid = (value.length != 0) && ($('input[name=availableComputerQuantity]').value <= value);
                return this.isValid;
            },
            errorMessage: "Giá trị không hợp lệ.",
            isValid: false,
        },
        availableComputerQuantity: {
            tag: $('input[name=availableComputerQuantity]'),
            confirm: function (value) {
                this.isValid = (value.length != 0) && ($('input[name=maxComputerQuantity]').value >= value);
                return this.isValid;
            },
            errorMessage: "Giá trị không hợp lệ.",
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
})();
