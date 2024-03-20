let canSubmitForm = false;

(function main() {
    customizeClosingNoticeMessageEvent();
    customizeSubmitFormAction({mockTag: {isValid: true}});
    recoveryAllSelectTagDataInForm();
    removePathAttributes();
    mappingCategoryNameWithCurrentPage();
})();
