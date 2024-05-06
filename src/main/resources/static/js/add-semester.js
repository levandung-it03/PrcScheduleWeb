const selectRangeYear = document.querySelector('select[name="SelectRangeOfYear"]');
const inputRangeYear = document.querySelector('input[name="rangeOfYear"]');

let defaultYear = new Date().getFullYear()-1;

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

(function main() {
    const validatingBlocks = {
        semester: {
            tag: $('input[name=semester]'),
            confirm: function (value) {
                this.isValid = (value.length != 0) && (1 <= value) && (value <= 10);
                return this.isValid;
            },
            errorMessage: "Học kì không hợp lệ.",
            isValid: false,
        },
        firstWeek: {
            tag: $('input[name=firstWeek]'),
            confirm: function (value) {
                this.isValid = (value.length != 0) && (1 <= value) && (value<=100);
                return this.isValid;
            },
            errorMessage: "Tuần bắt đầu không hợp lệ.",
            isValid: false,
        },
        totalWeek: {
            tag: $('input[name=totalWeek]'),
            confirm: function (value) {
                this.isValid = (value.length != 0) && (1 <= value) && (value<=100);
                return this.isValid;
            },
            errorMessage: "Tổng số tuần không hợp lệ.",
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
