const inputSemester = document.querySelector('input[name="semesterId"]');
const selectSemester = document.querySelector('select[name="selectSemesterId"]');

/*** default value ***/
if (selectSemester.value === ""){
    inputSemester.value = null;
}

selectSemester.addEventListener('change',function (){
    inputSemester.value = selectSemester.value;
});


(function main() {
    const validatingBlocks = {
        gradeId: {
            tag: $('input[name=gradeId]'),
            confirm: function (value) {
                this.isValid = (value.length !== 0);
                return this.isValid;
            },
            errorMessage: "Mã lớp không hợp lệ.",
            isValid: false,
        },
        subjectId: {
            tag: $('input[name=subjectId]'),
            confirm: function (value) {
                this.isValid = (value.length !== 0);
                return this.isValid;
            },
            errorMessage: "Mã môn không hợp lệ.",
            isValid: false,
        },
        groupFromSubject: {
            tag: $('input[name=groupFromSubject]'),
            confirm: function (value) {
                this.isValid = (value.length != 0) && (1 <= value) && (value<=100);
                return this.isValid;
            },
            errorMessage: "Số nhóm không hợp lệ.",
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
