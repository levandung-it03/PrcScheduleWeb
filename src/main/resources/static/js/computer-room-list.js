const plainTableRows = [...$$('table tbody tr')];

(function main() {
    removePathAttributes();
    customizeClosingNoticeMessageEvent();
    customizeSearchingListEvent(plainTableRows);
    customizeSortingListEvent();
    customizeSubmitFormAction('div#computer-room-list-page div.center-page_list > form');
    buildHeader();
})();