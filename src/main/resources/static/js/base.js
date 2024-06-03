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

const log = (str) => console.log(str);

function getDateObjFromCommonFormat(dateInCurrentFormatAsStr) {
    return new Date(dateInCurrentFormatAsStr.split("/").reverse().join("-"));
}

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

function createErrBlocksOfInputTags(validatingBlocks, parentFormElement="html") {
    [...$$(`${parentFormElement} .form-input .form_text-input_err-message`)].forEach((e) => {
        e.innerHTML = `<span class='err-message-block' id='${e.parentNode.id}'>
            ${validatingBlocks[e.parentNode.id].errorMessage}
        </span>`;
    })
}

function customizeValidateEventInputTags(validatingBlocks) {
    Object.entries(validatingBlocks).forEach(elem => {
        const toggleShowMessage = (elem) => {
            if (elem[1].validate(elem[1].tag.value)) $('span#' + elem[0]).style.display = "none";
            else    $('span#' + elem[0]).style.display = "inline";
        };
        elem[1].tag.addEventListener("keyup", e => toggleShowMessage(elem));
        elem[1].tag.addEventListener("change", e => toggleShowMessage(elem));
    });
}

function customizeSubmitFormAction(formSelector, validatingBlocks=null) {
    $(formSelector).addEventListener("submit", e => {
        if (confirm("Bạn chắc chắn muốn thực hiện thao tác?")) {
            let isValid = validatingBlocks == null ? true : Object.entries(validatingBlocks)
                .every(elem => {
                    elem[1].tag.value = elem[1].tag.value.trim();
                    return elem[1].validate(elem[1].tag.value);
                });

            if (isValid)    return true;
            else {
                e.preventDefault();
                alert("Thông tin đầu vào bị lỗi!");
            }
        } else  e.preventDefault();
    });
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
    const searchingInputTag = $('.table-search-box input#search');
    const selectedOption = $('.table-search-box select#search');
    const handleSearchingListEvent = e => {
        const tableBody = e.target.parentElement.parentElement.parentElement.querySelector('tbody');

        //--Reset table data.
        if (searchingInputTag.value == "") {
            tableBody.innerHTML = plainTableRows.reduce((accumulator, elem) => accumulator + elem.innerHTML, "");
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
            tableBody.innerHTML = '<tr><td style="width:100%; text-align:center;">Không tìm thấy dữ liệu vừa nhập</td></tr>';
        else
            tableBody.innerHTML = searchingResult;

        return null;
    }

    $('.table-search-box i').addEventListener("click", handleSearchingListEvent);
    searchingInputTag.addEventListener("keyup", handleSearchingListEvent);
}


function customizeSortingListEvent() {
    [...$$('table thead th i')].forEach(btn => {
        btn.addEventListener("click", e => {
            const table = e.target.parentElement.parentElement.parentElement.parentElement;
            const fieldId = e.target.parentElement.id;
            const cellOfFields = [...table.querySelectorAll('tbody td.' + fieldId)];
            const firstCellOfSearchingColumn = cellOfFields[0].getAttribute('plain-value');
            let searchingDataFieldType = null;

            if (Number.parseInt(firstCellOfSearchingColumn) !== null)   searchingDataFieldType = "Number";
            else if (new Date(firstCellOfSearchingColumn) !== null)     searchingDataFieldType = "Date";
            else    searchingDataFieldType = "String";

            cellOfFields.sort((a, b) => {
                const firstCell = a.getAttribute('plain-value');
                const secondCell = b.getAttribute('plain-value');

                if (searchingDataFieldType === "Number") return Number.parseInt(firstCell) - Number.parseInt(secondCell);
                else if (searchingDataFieldType === "Date")   return new Date(firstCell) < new Date(secondCell);
                else    return firstCell.localeCompare(secondCell);
            });
            alert("Sắp xếp thành công!");
            table.querySelector('tbody').innerHTML = cellOfFields.reduce((accumulator, cell) => {
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
                .map(word => word.slice(0, 1).toUpperCase() + word.slice(1).toLowerCase())
                .join(" ");
        });
    });
    [...$$('div.capitalized-text input')].forEach(inputTag => {
        inputTag.addEventListener("blur", e => {
            inputTag.value = inputTag.value.trim().toUpperCase();
        });
    });
}

function convertStrDateToDateObj(strDate) {
    const startDateAsArr = strDate.split("/");
    return new Date(startDateAsArr[2], startDateAsArr[1] - 1, startDateAsArr[0])
}

function customizeAllAvatarColor() {
    [...$$('.mock-avatar i')].forEach(avatarTag => {
        const avatarColor = colorMap[avatarTag.innerText.trim().toUpperCase()];

        // Convert background color to RGB
        let r = parseInt(avatarColor.slice(1, 3), 16);
        let g = parseInt(avatarColor.slice(3, 5), 16);
        let b = parseInt(avatarColor.slice(5, 7), 16);

        // Calculate luminance
        let luminance = (0.299 * r + 0.587 * g + 0.114 * b) / 255;

        // Get the right letter's color
        const letterColor = (luminance > 0.5) ? "#000000" : "#FFFFFF";

        avatarTag.style.backgroundColor = avatarColor;
        avatarTag.style.color = letterColor;
    })
}