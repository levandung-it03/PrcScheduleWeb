let canSubmitForm = false;

(function main() {
    customizeClosingErrMessageEvent();
    customizeSubmitFormAction({mockTag: {isValid: true}});
    recoveryAllSelectTagDataInForm();
    removePathAttributes();
    mappingCategoryNameWithCurrentPage();
})();
