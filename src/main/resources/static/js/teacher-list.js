const plainTableRows = [...$$('table tbody tr')];

(function main() {
    removePathAttributes();
    customizeAllAvatarColor();
    customizeClosingErrMessageEvent();
    customizeSearchingListEvent(plainTableRows);
    customizeSortingListEvent();
    customizeSubmitFormAction({mockTag: {isValid: true}});
    mappingCategoryNameWithCurrentPage();
})();

function customizeAllAvatarColor() {
    [...$$('table tbody tr td.baseProfile span.mock-avatar')].forEach(avatarTag => {
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