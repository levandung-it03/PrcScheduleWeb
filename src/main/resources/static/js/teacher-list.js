const plainTableRows = [...$$('table tbody tr')];

(function main() {
    removePathAttributes();
    customizeAllAvatarColor();
    customizeClosingNoticeMessageEvent();
    customizeSearchingListEvent(plainTableRows);
    customizeSortingListEvent();
    customizeSubmitFormAction({mockTag: {isValid: true}});
    mappingCategoryNameWithCurrentPage();
})();