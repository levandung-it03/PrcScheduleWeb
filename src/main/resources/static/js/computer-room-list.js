const plainTableRows = [...$$('table tbody tr')];

(function main() {
    removePathAttributes();
    customizeClosingNoticeMessageEvent();
    customizeSearchingListEvent(plainTableRows);
    customizeSortingListEvent();
    customizeSubmitFormAction({mockTag: {isValid: true}});
    mappingCategoryNameWithCurrentPage();
})();