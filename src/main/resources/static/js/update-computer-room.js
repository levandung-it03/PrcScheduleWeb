let canSubmitForm = false;

(function main() {
    const validatingBlocks = {
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
    
    customizeClosingErrMessageEvent();
    createErrBlocksOfInputTags(validatingBlocks);
    customizeValidateEventInputTags(validatingBlocks);
    customizeSubmitFormAction(validatingBlocks);
    recoveryAllSelectTagDataInForm();
    removePathAttributes();
    mappingCategoryNameWithCurrentPage();
})();
