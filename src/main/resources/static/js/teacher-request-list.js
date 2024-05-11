const plainTableRows = [...$$('table tbody tr')];

(function main() {
    removePathAttributes();
    customizeAllAvatarColor();
    customizeClosingNoticeMessageEvent();
    customizeSearchingListEvent(plainTableRows);
    customizeSortingListEvent();
    buildHeader();
    customizeDenyingTeacherRequestAction();
})();

function customizeDenyingTeacherRequestAction() {
    const denyBtn = $('button[name=denyTeacherRequestBtn]');
    denyBtn.addEventListener("click", e => {
        const denyingForm = $('form#deny-request');
        //--Set value for 'input name="requestId"'.
        denyingForm.querySelector('input[name=requestId]').value = denyBtn.id;

        //--Get value for 'input name="interactionReason"' from manager.
        denyingForm.querySelector('input[name=interactionReason]').value = prompt("Vui lòng nhập lý do");
        if (confirm("Bạn chắc chắn muốn thực hiện thao tác?") == true) {
            denyingForm.submit();
        }
    });
}