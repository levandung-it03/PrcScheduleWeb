
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
        maxQuantity: {
            tag: $('input[name=maxQuantity]'),
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
    
    customizeClosingNoticeMessageEvent();
    createErrBlocksOfInputTags(validatingBlocks);
    customizeValidateEventInputTags(validatingBlocks);
    customizeSubmitFormAction(validatingBlocks);
    recoveryAllSelectTagDataInForm();
    removePathAttributes();
    mappingCategoryNameWithCurrentPage();
})();
