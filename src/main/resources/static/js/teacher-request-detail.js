const plainTableRows = [...$$('div#student-list table tbody tr')];

(function main() {
    mappingCategoryNameWithCurrentPage();
    customizeClosingNoticeMessageEvent();
    customizeSearchingListEvent(plainTableRows);
    customizeSortingListEvent();
    customizeSubmitFormAction({mockTag: {isValid: true}});
})();