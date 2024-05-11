const plainTableRows = [...$$('div#student-list table tbody tr')];

(function main() {
    buildHeader();
    customizeClosingNoticeMessageEvent();
    customizeSubmitFormAction('div#teacher-request-detail-page div#practice-schedule > form');
    customizeSearchingListEvent(plainTableRows);
    customizeSortingListEvent();
})();