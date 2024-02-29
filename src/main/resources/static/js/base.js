const $ = document.querySelector.bind(document);
const $$ = document.querySelectorAll.bind(document);
const urlParams = new URLSearchParams(window.location.search);

function cutomizeClosingErrMessageEvent() {
    const errMessageCloseBtn = $('div.error-service-message i#error-service-message_close-btn');
    const succeedMessageCloseBtn = $('div.succeed-service-message i#succeed-service-message_close-btn');

    if (errMessageCloseBtn != null) {
        setTimeout(() => $('div.error-service-message').classList.add("hide"), 4000);
        errMessageCloseBtn.addEventListener("click", (e) => {
            $('div.error-service-message').classList.add("hide");
        });
    }
    if (succeedMessageCloseBtn != null) {
        setTimeout(() => $('div.succeed-service-message').classList.add("hide"), 4000);
        succeedMessageCloseBtn.addEventListener("click", (e) => {
            $('div.succeed-service-message').classList.add("hide");
        });
    }
    return;
}

function createErrBlocksOfInputTags(validatingBlocks) {
    [...$$('.form-input .form_text-input_err-message')].forEach((e) => {
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
        if (!isValid) alert("Thông tin đầu vào bị lỗi!");
        return isValid;
    }
}

function removePathAttributes() {
    if (urlParams.has('errorMessage')) {
        urlParams.delete('errorMessage');
        const newUrl = `${window.location.pathname}`;
        history.replaceState(null, '', newUrl);
    }
    if (urlParams.has('succeedMessage')) {
        urlParams.delete('succeedMessage');
        const newUrl = `${window.location.pathname}`;
        history.replaceState(null, '', newUrl);
    }
}

function customizeToggleDisplayPasswordEvent() {
    $$('.password_toggle-hidden i').forEach((eye) => {
        eye.onclick = (e) => {
            if ([...e.target.classList].some((e) => e == "show-pass")) {
                e.target.classList.add("hidden");
                e.target.parentElement.querySelector(".hide-pass").classList.remove("hidden");
                $(`input[name=${e.target.id}]`).type = "text";
            } else {
                e.target.classList.add("hidden");
                e.target.parentElement.querySelector(".show-pass").classList.remove("hidden");
                $(`input[name=${e.target.id}]`).type = "password";
            }
        }
    })
}