
(function main() {
    const inputSemester = $('input[name="semesterId"]');
    const selectSemester = $('select[name="selectSemesterId"]');
    const validatingBlocks = {
        gradeId: {
            tag: $('input[name=gradeId]'),
            validate: (value) => (value.length !== 0),
            errorMessage: "Mã lớp không hợp lệ.",
        },
        subjectId: {
            tag: $('input[name=subjectId]'),
            validate: (value) => (value.length !== 0),
            errorMessage: "Mã môn không hợp lệ.",
        },
        groupFromSubject: {
            tag: $('input[name=groupFromSubject]'),
            validate: (value) => (value.length != 0) && (1 <= value) && (value<=100),
            errorMessage: "Số nhóm không hợp lệ.",
        },
    };

    /*** default value ***/
    if (selectSemester.value === ""){
        inputSemester.value = null;
    }

    selectSemester.addEventListener('change', e => {
        inputSemester.value = selectSemester.value;
    });

    customizeClosingNoticeMessageEvent();
    createErrBlocksOfInputTags(validatingBlocks);
    customizeValidateEventInputTags(validatingBlocks);
    customizeSubmitFormAction('div#add-section-class-page > form', validatingBlocks);
    customizeAutoFormatStrongInputTextEvent();
    recoveryAllSelectTagDataInForm();
    removePathAttributes();
    buildHeader();
})();
