let canSubmitForm = false;

(function main() {
    const validatingBlocks = {
        roomCode: {
            tag: $('input[name=roomCode]'),
            confirm: function (value) {
                this.isValid = value.length != 0;
                return this.isValid;
            },
            errorMessage: "Bạn chưa nhập giá trị.",
            isValid: false,
        },
        maxComputerQuantity: {
            tag: $('input[name=maxComputerQuantity]'),
            confirm: function (value) {
                this.isValid = value.length != 0;
                return this.isValid;
            },
            errorMessage: "Bạn chưa nhập giá trị.",
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