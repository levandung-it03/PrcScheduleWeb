(function main() {
    const hiddenData = {};
    (function popAllHiddenDataFields() {
        [...$$("div#hidden-blocks span.hidden-items")].forEach(hiddenItem => {
            const fieldName = hiddenItem.getAttribute("name");
            const fieldValue = hiddenItem.textContent.trim();
            const fieldDataType = hiddenItem.getAttribute("type");
            //--Initialize
            hiddenData[fieldName] = {};
            if (fieldDataType == "text") {
                hiddenData[fieldName] = fieldValue;
            } else if (fieldDataType == "number") {
                hiddenData[fieldName] = Number.parseInt(fieldValue);
            }
        });
    })();
    customizeClosingNoticeMessageEvent();
    drawStatisticRequestsChart(hiddenData);
    customizeSortingListEvent();
    buildHeader();
    customizeAllAvatarColor();
})();

function drawStatisticRequestsChart(hiddenData) {
    var labels = ["Đã từ chối", "Đã bị huỷ", "Đã tạo", "Chờ giải quyết",];
    var values = [
        hiddenData["deniedRequests"],
        hiddenData["canceledRequests"],
        hiddenData["createdRequests"],
        hiddenData["pendingRequests"],
    ];
    var barColors = ["#d7431b", "#555555", "#14a468", "#3a44ff",];

    new Chart("myChart", {
        type: "doughnut",
        data: {
            labels: labels,
            datasets: [{
                backgroundColor: barColors,
                data: values
            }]
        },
        options: { legend: { display: false } }
    });

    $('div#chart-container div#labels').innerHTML = labels.map((label, index) => `
        <span class="label-for-chart" style="background-color: ${barColors[index]};">
            ${values[index]} - ${label}
        </span>
    `).join("");
}