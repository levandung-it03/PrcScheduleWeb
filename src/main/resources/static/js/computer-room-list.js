const plainTableRows = [...$$('table tbody tr')];

(function main() {
    removePathAttributes();
    customizeClosingErrMessageEvent();
    customizeSearchingListEvent(plainTableRows);
    customizeSortingListEvent();
    mappingCategoryNameWithCurrentPage();
})();