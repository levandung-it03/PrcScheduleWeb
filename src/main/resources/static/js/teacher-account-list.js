const plainTableRows = [...$$('table tbody tr')];

(function main() {
    removePathAttributes();
    customizeClosingErrMessageEvent();
    customizeSearchingListEvent(plainTableRows);
    customizeSortingListEvent();
    customizeSubmitFormAction({mockTag: {isValid: true}});
    mappingCategoryNameWithCurrentPage();
})();