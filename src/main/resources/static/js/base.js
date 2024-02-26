const $ = document.querySelector.bind(document);
const $$ = document.querySelectorAll.bind(document);

function cutomizeClosingErrMessageEvent() {
    const errMessageCloseBtn = $('div.error-message #error-message-close-btn');
    if (errMessageCloseBtn != null) {
        errMessageCloseBtn.addEventListener("click", (e) => {
            $('div.error-message').classList.add("hide");
        });
        setInterval(() => $('div.error-message').classList.add("hide"), 4000);
    }
    return;
}

function createErrBlocksOfInputTags(validatingBlocks) {
    [...$$('.login-input .login-input_err-message')].forEach((e) => {
        e.innerHTML = `
        <span class='err-message-block' id='${e.parentNode.id}'>
            ${validatingBlocks[e.parentNode.id].errorMessage}
        </span>`;
    })
}

function customizeInputTagValidateEvents(validatingBlocks) {
    Object.entries(validatingBlocks).forEach(elem => {
        elem[1].tag.addEventListener("keyup", e => {
            if (elem[1].confirm(elem[1].tag.value))
                $('span#' + elem[0]).style.display = "none";
            else
                $('span#' + elem[0]).style.display = "inline";
        })
    });
}

function customizeSubmitFormAction(validatingBlocks) {  
    $('form').onsubmit = e => {
        let isValid = Object.entries(validatingBlocks).every((elem) => elem[1].isValid);
        if (!isValid)   alert("Thông tin đầu vào bị lỗi!");
        return isValid;
    }
}