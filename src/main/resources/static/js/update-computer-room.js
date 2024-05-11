
(function main() {
    const validatingBlocks = {
        maxQuantity: {
            tag: $('input[name=maxQuantity]'),
            validate: (value) => value.length != 0,
            errorMessage: "Giá trị không hợp lệ.",
        },
        maxComputerQuantity: {
            tag: $('input[name=maxComputerQuantity]'),
            validate: (value) => (value.length != 0) && ($('input[name=availableComputerQuantity]').value <= value),
            errorMessage: "Giá trị không hợp lệ.",
        },
        availableComputerQuantity: {
            tag: $('input[name=availableComputerQuantity]'),
            validate: (value) => (value.length != 0) && ($('input[name=maxComputerQuantity]').value >= value),
            errorMessage: "Giá trị không hợp lệ.",
        },
    };
    
    customizeClosingNoticeMessageEvent();
    createErrBlocksOfInputTags(validatingBlocks);
    customizeValidateEventInputTags(validatingBlocks);
    customizeSubmitFormAction('div#update-computer-room-page > form', validatingBlocks);
    recoveryAllSelectTagDataInForm();
    removePathAttributes();
    buildHeader();
})();
