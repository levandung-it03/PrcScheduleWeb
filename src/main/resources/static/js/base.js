const $ = document.querySelector.bind(document);
const $$ = document.querySelectorAll.bind(document);
const urlParams = new URLSearchParams(window.location.search);
const colorMap = {
    A: "#FFBF00",
    B: "#0000FF",
    C: "#00FFFF",
    D: "#1560BD",
    E: "#50C878",
    F: "#FF00FF",
    G: "#FFD700",
    H: "#DF73FF",
    I: "#4B0082",
    J: "#00A86B",
    K: "#F0E68C",
    L: "#E6E6FA",
    M: "#FF00FF",
    N: "#000080",
    O: "#808000",
    P: "#FFC0CB",
    Q: "#51484F",
    R: "#FF0000",
    S: "#C0C0C0",
    T: "#40E0D0",
    U: "#3F00FF",
    V: "#8A2BE2",
    W: "#FFFFFF",
    X: "#738678",
    Y: "#FFFF00",
    Z: "#0014A8"
};
  

function customizeClosingNoticeMessageEvent() {
    const errMessageCloseBtn = $('div.error-service-message i#error-service-message_close-btn');
    const succeedMessageCloseBtn = $('div.succeed-service-message i#succeed-service-message_close-btn');

    if (errMessageCloseBtn != null) {
        setTimeout(() => $('div.error-service-message').classList.add("hide"), 6000);
        errMessageCloseBtn.addEventListener("click", (e) => {
            $('div.error-service-message').classList.add("hide");
        });
    }
    if (succeedMessageCloseBtn != null) {
        setTimeout(() => $('div.succeed-service-message').classList.add("hide"), 6000);
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

function customizeValidateEventInputTags(validatingBlocks) {
    Object.entries(validatingBlocks).forEach(elem => {
        let ignoredResult = elem[1].confirm(elem[1].tag.value);
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
        if (confirm("Bạn chắc chắn muốn thực hiện thao tác?") == true) {
            let isValid = Object.entries(validatingBlocks).every((elem) => elem[1].isValid);
            if (!isValid) alert("Thông tin đầu vào bị lỗi!");
            return isValid;
        } else return false;
    }
}

function removePathAttributes() {
    let newUrl = `${window.location.pathname}`;
    if (urlParams.has("page"))
        newUrl += `?page=${urlParams.get("page")}`;

    history.replaceState(null, '', newUrl);
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

function cuttingStringValueOfInputTag(tag, len) {
    if (tag.value.length > len)
        tag.value = tag.value.slice(0, len);
}


function recoveryAllSelectTagDataInForm() {
    [...$$('form select')].forEach(selectTag => {
        const data = selectTag.getAttribute('data');
        if (data != null) {
            [...selectTag.querySelectorAll('option')].forEach(optionTag => {
                if (optionTag.value == data)
                    optionTag.selected = "true";
            });
        }
    });
}

function customizeSearchingListEvent(plainTableRows) {
    const searchingInputTag = $('#table-search-box input#search');
    const selectedOption = $('#table-search-box select#search');
    const handleSearchingListEvent = e => {
        const tableBody = $('table tbody');

        //--Reset table data.
        if (searchingInputTag.value == "") {
            tableBody.innerHTML = plainTableRows.reduce((accumulator, elem) => accumulator += elem.innerHTML, "");
            return;
        }

        if (selectedOption.value == "") {
            alert("Bạn hãy chọn trường cần tìm kiếm trước!");
            return;
        }

        let searchingResult = plainTableRows.reduce((accumulator, row) => {
            let currentCellElement = row.querySelectorAll('td')[selectedOption.value];
            let currentCellValue = currentCellElement.getAttribute("plain-value").trim().toUpperCase();
            let isBeingFoundValue = currentCellValue.search(searchingInputTag.value.trim().toUpperCase()) != -1;
            
            return accumulator + (isBeingFoundValue ? row.outerHTML : "");
        }, "");

        if (searchingResult == "")
            tableBody.innerHTML = '<tr><td style="width: 100%">Không tìm thấy dữ liệu vừa nhập</td></tr>';
        else
            tableBody.innerHTML = searchingResult;

        return null;
    }

    $('#table-search-box i').addEventListener("click", handleSearchingListEvent);
    searchingInputTag.addEventListener("keyup", handleSearchingListEvent);
}

function customizeSortingListEvent() {
    [...$$('table thead th i')].forEach(btn => {
        btn.addEventListener("click", e => {
            const fieldId = e.target.parentElement.id;
            const cellsOfFieldId = [...$$('table tbody td.' + fieldId)].sort((a, b) => {
                const firstCell = a.getAttribute('plain-value');
                const secondCell = b.getAttribute('plain-value');
                return firstCell.localeCompare(secondCell);
            });
            alert("Sắp xếp thành công!");
            $('table tbody').innerHTML = cellsOfFieldId.reduce((accumulator, cell) => {
                return accumulator + cell.parentElement.outerHTML;
            }, "");
        })
    })
}

function customizeAutoFormatStrongInputTextEvent() {
    [...$$('div.strong-text input')].forEach(inputTag => {
        inputTag.addEventListener("blur", e => {
            inputTag.value = inputTag.value.trim().split(" ")
                .filter(word => word != "")
                .map(word => word.slice(0, 1).toUpperCase() + word.slice(1))
                .join(" ");
        });
    });
}

function convertStrDateToDateObj(strDate) {
    const startDateAsArr = strDate.split("/");
    return new Date(startDateAsArr[2], startDateAsArr[1] - 1, startDateAsArr[0])
}