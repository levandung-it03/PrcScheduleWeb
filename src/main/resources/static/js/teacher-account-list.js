const plainTableRows = [...$$('table tbody tr')];

(function main() {
    removePathAttributes();
    customizeClosingNoticeMessageEvent();
    customizeSearchingListEvent(plainTableRows);
    customizeSortingListEvent();
    customizeSubmitFormAction('div#teacher-account-list-page div.center-page_list > form');
    buildHeader();
})();