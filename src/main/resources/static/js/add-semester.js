(function main() {
    const selectRangeYear = $('select[name="SelectRangeOfYear"]');
    const inputRangeYear = $('input[name="rangeOfYear"]');
    let defaultYear = new Date().getFullYear() - 1;

    /*** default value ***/
    if (selectRangeYear.value === ""){
        inputRangeYear.value = defaultYear+"_"+(defaultYear+1);
    }
    for (let i=0;i<5;i++){
        let option = document.createElement("option");
        option.text = defaultYear+" - "+(defaultYear+1);
        option.value = defaultYear+"_"+(defaultYear+1);
        selectRangeYear.add(option);
        defaultYear++;
    }

    selectRangeYear.addEventListener('change', function() {
        inputRangeYear.value = this.value;
    });

    const validatingBlocks = {
        semester: {
            tag: $('input[name=semester]'),
            validate: (value) => (value.length != 0) && (1 <= value) && (value <= 10),
            errorMessage: "Học kì không hợp lệ.",
        },
        firstWeek: {
            tag: $('input[name=firstWeek]'),
            validate: (value) => (value.length != 0) && (1 <= value) && (value<=100),
            errorMessage: "Tuần bắt đầu không hợp lệ.",
        },
        totalWeek: {
            tag: $('input[name=totalWeek]'),
            validate: (value) => (value.length != 0) && (1 <= value) && (value<=100),
            errorMessage: "Tổng số tuần không hợp lệ.",
        },
    };

    customizeClosingNoticeMessageEvent();
    createErrBlocksOfInputTags(validatingBlocks);
    customizeValidateEventInputTags(validatingBlocks);
    customizeSubmitFormAction('div#add-semester-page > form', validatingBlocks);
    customizeAutoFormatStrongInputTextEvent();
    recoveryAllSelectTagDataInForm();
    removePathAttributes();
    buildHeader();
})();
