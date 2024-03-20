let canSubmitForm = false;
let subjectScheduleList = [];

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
        }
    };
    popAllSubjectScheduleDataFields();
    customizeClosingNoticeMessageEvent();
    // createErrBlocksOfInputTags(validatingBlocks);
    // customizeValidateEventInputTags(validatingBlocks);
    // customizeSubmitFormAction(validatingBlocks);
    // recoveryAllSelectTagDataInForm();
    removePathAttributes();
    mappingCategoryNameWithCurrentPage();
})();

function popAllSubjectScheduleDataFields() {
    [...$$("div.subject-schedule-hidden-block")].forEach((block, index) => {
        subjectScheduleList[index] = {}
        block.querySelectorAll("span.data-field").forEach(tagField => {
            subjectScheduleList[index][tagField.getAttribute("name")] = tagField.innerText;
        });
        block.outerHTML = "";
    });
}