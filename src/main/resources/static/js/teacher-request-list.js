const plainTableRows = [...$$('table tbody tr')];

(function main() {
    removePathAttributes();
    customizeAllAvatarColor();
    customizeClosingNoticeMessageEvent();
    customizeSearchingListEvent(plainTableRows);
    customizeSortingListEvent();
    buildHeader();
    customizeDenyingTeacherRequestAction();
    customizeCancelingTeacherRequestAction();
})();

function customizeDenyingTeacherRequestAction() {
    const denyBtns = $$('button[name=denyTeacherRequestBtn]');
    if (denyBtns != null){
        denyBtns.forEach(btn => {
            btn.addEventListener("click", e => {
                const denyingForm = $('form#deny-request');
                //--Set value for 'input name="requestId"'.
                denyingForm.querySelector('input[name=requestId]').value = btn.id;
    
                //--Get value for 'input name="interactionReason"' from manager.
                denyingForm.querySelector('input[name=interactionReason]').value = prompt("Vui lòng nhập lý do");
                if (confirm("Bạn chắc chắn muốn thực hiện thao tác?") == true) {
                    denyingForm.submit();
                }
            })
        });
    }
}

function customizeCancelingTeacherRequestAction() {
    const cancelBtns = $$('button[name=cancelTeacherRequestBtn]');
    if (cancelBtns != null){
        cancelBtns.forEach(btn => {
            btn.addEventListener("click", e => {
                const cancelingForm = $('form#cancel-request');
                //--Set value for 'input name="requestId"'.
                cancelingForm.querySelector('input[name=requestId]').value = btn.id;
    
                //--Get value for 'input name="interactionReason"' from manager.
                cancelingForm.querySelector('input[name=interactionReason]').value = prompt("Vui lòng nhập lý do");
                if (confirm("Bạn chắc chắn muốn thực hiện thao tác?") == true) {
                    cancelingForm.submit();
                }
            })
        });
    }
}