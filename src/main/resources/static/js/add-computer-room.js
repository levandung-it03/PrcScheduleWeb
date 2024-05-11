
(function main() {
    const validatingBlocks = {
        roomCode: {
            tag: $('input[name=roomCode]'),
            validate: (value) => (value.length != 0),
            errorMessage: "Bạn chưa nhập giá trị.",
        },
        maxQuantity: {
            tag: $('input[name=maxQuantity]'),
            validate: (value) => (value.length != 0),
            errorMessage: "Bạn chưa nhập giá trị.",
        },
        maxComputerQuantity: {
            tag: $('input[name=maxComputerQuantity]'),
            validate: (value) => (value.length != 0),
            errorMessage: "Bạn chưa nhập giá trị.",
        },
    };
    
    customizeClosingNoticeMessageEvent();
    createErrBlocksOfInputTags(validatingBlocks);
    customizeValidateEventInputTags(validatingBlocks);
    customizeSubmitFormAction('div#add-computer-room-page > form', validatingBlocks);
    recoveryAllSelectTagDataInForm();
    removePathAttributes();
    buildHeader();
})();
