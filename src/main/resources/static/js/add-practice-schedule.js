let canSubmitForm = false;
const subjectScheduleOfGradeList = [];
const allPracticeScheduleInSemester = [];
const allComputerRoom = [];
const selectedCellList = {};
const availableRooms = {};
let selectedWeekOptionTag = null;

(function main() {
    const validatingBlocks = {
        roomCode: {
            tag: $('input[name=roomCode]'),
            confirm: function (value) {
                this.isValid = value.length != 0;
                return this.isValid;
            },
            errorMessage: "Bạn chưa nhập giá trị.",
            isValid: false,
        }
    };

    customizeClosingNoticeMessageEvent();
    // createErrBlocksOfInputTags(validatingBlocks);
    // customizeValidateEventInputTags(validatingBlocks);
    // customizeSubmitFormAction(validatingBlocks);
    // recoveryAllSelectTagDataInForm();
    removePathAttributes();
    mappingCategoryNameWithCurrentPage();
    /*---------------Own-methods------------------*/
    popAllHiddenDataFields();
    selectCurrentWeekOptionTagAndRenderTimeTable();
    continouslyUpdateSelectedWeekOptionTag();
    customizeSelectedTableCellsEvent();
    customizeConvertingScheduleAction();
    /*--------------------------------------------*/

    function popAllHiddenDataFields() {
        [...$$("div#hidden-blocks div#subject-schedule-of-grade div.subject-schedule-hidden-block")].forEach((block, index) => {
            subjectScheduleOfGradeList[index] = {};
            block.querySelectorAll("span.data-field").forEach(tagField => {
                if (tagField.getAttribute("type") == "text") {
                    subjectScheduleOfGradeList[index][tagField.getAttribute("name")] = tagField.innerText;
                } else if (tagField.getAttribute("type") == "number") {
                    subjectScheduleOfGradeList[index][tagField.getAttribute("name")] = Number.parseInt(tagField.innerText);
                }
            });
        });
    
        [...$$("div#hidden-blocks div#all-subject-schedule-in-this-semester div.subject-schedule-hidden-block")].forEach((block, index) => {
            allPracticeScheduleInSemester[index] = {};
            block.querySelectorAll("span.data-field").forEach(tagField => {
                if (tagField.getAttribute("type") == "text") {
                    allPracticeScheduleInSemester[index][tagField.getAttribute("name")] = tagField.innerText;
                } else if (tagField.getAttribute("type") == "number") {
                    allPracticeScheduleInSemester[index][tagField.getAttribute("name")] = Number.parseInt(tagField.innerText);
                }
            });
        });
    
        [...$$("div#hidden-blocks div#all-computer-room span.item-in-list")].forEach(tag => {
            allComputerRoom.push(tag.innerText);
        });
        studentQuantity = $("div#hidden-blocks div#student-quantity span.data-field").innerText;
    
        $("div#hidden-blocks").outerHTML = "";
    }
    
    function selectCurrentWeekOptionTagAndRenderTimeTable() {
        const nowDate = new Date();
        for (var optionTag of [...$$('select[name="list-of-week"] option')]) {
            const startingDateFromOptionTag = convertStrDateToDateObj(optionTag.getAttribute("startingDate"));
            startingDateFromOptionTag.setDate(startingDateFromOptionTag.getDate() + 7)
    
            if (nowDate.getTime() <= startingDateFromOptionTag.getTime()) {
                selectedWeekOptionTag = optionTag;
                selectedWeekOptionTag.selected = true;
                break;
            }
        }
    
        if (selectedWeekOptionTag == null)
            return;

        renderTimeTable();
    }
    
    function continouslyUpdateSelectedWeekOptionTag() {
        $('select[name="list-of-week"]').addEventListener("change", e => {
            selectedWeekOptionTag = e.target.options[e.target.selectedIndex];
            renderTimeTable();
        });
    
        $("th.change-schedule-btn span#left").addEventListener("click", e => {
            changeTheGlobalSelectedWeekOptionTag(Number.parseInt(selectedWeekOptionTag.getAttribute("week")) - 1);
            renderTimeTable();
        });
    
        $("th.change-schedule-btn span#right").addEventListener("click", e => {
            changeTheGlobalSelectedWeekOptionTag(Number.parseInt(selectedWeekOptionTag.getAttribute("week")) + 1);
            renderTimeTable();
        });
    }
    
    function renderTimeTable() {
        //--Reset time-table
        $$('tbody tr td.schedule-item').forEach(cell => {
            //--Reset cell status before setting-up subject schedule.
            cell.querySelector('span').innerText = "";
            cell.classList.remove("unhover");
            cell.classList.remove("selected");
        });
    
        //--Color each cell that already has subject-schedule.
        subjectScheduleOfGradeList.forEach((schedule, index) => {
            const selectedWeek = Number.parseInt(selectedWeekOptionTag.getAttribute("week"));
    
            //--This subject is having a schedule in this seleted_week.
            if ((schedule.startingWeek <= selectedWeek) && (selectedWeek <= (schedule.startingWeek + schedule.totalWeek - 1))) {
                //--Add the subject name into schedule-item. 
                const periodAsRowOfSubjectNameCell = Number.parseInt((schedule.lastPeriod + schedule.startingPeriod) / 2);
                $(`tr[id="${periodAsRowOfSubjectNameCell}"] td[day="${schedule.day}"] span`).innerText = schedule.subjectName;
    
                for (let period = schedule.startingPeriod; period <= schedule.lastPeriod; period++) {
                    //--Make every schedule cells this subject "unhover" (change: background-color, cursor).
                    $(`tr[id="${period}"] td[day="${schedule.day}"]`).classList.add("unhover");
                }
            }
        });
    
        //--Fill the MarkingArray with rent computer-room quantity
        let markingArr = Array.from({ length: 17 }, () => Array(8).fill(0));
        allPracticeScheduleInSemester.forEach(schedule => {
            //--Step to next schedule if this subject-schedule is not in this selected-week.
            if ((selectedWeekOptionTag.getAttribute("week") < schedule.startingWeek)
                || ((schedule.startingWeek + schedule.totalWeek - 1) < selectedWeekOptionTag.getAttribute("week")))
                return;
    
            for (var period = schedule.startingPeriod; period <= schedule.lastPeriod; period++)
                markingArr[period][schedule.day]++;
    
        });
    
        //--Color each cell that has no quantity.
        for (var periodRow = 1; periodRow <= 16; periodRow++) {
            for (var dayColumn = 2; dayColumn <= 8; dayColumn++) {
                if ($(`tr[id="${periodRow}"] td[day="${dayColumn}"]`).classList.contains("unhover"))
                    continue;
    
                if (markingArr[periodRow][dayColumn] >= allComputerRoom.length) {
                    $(`tr[id="${periodRow}"] td[day="${dayColumn}"] span`).innerText = "Hết phòng";
                    $(`tr[id="${periodRow}"] td[day="${dayColumn}"]`).classList.add("unhover");
                }
            }
        }
    
        //--Color each cell that was selected before.
        const weekAsKey = selectedWeekOptionTag.getAttribute("week");
        for (var dayAsKey in selectedCellList[weekAsKey]) {
            for (var periodAskey in selectedCellList[weekAsKey][dayAsKey]) {
                $(`td.schedule-item[period="${periodAskey}"][day="${dayAsKey}"]`).classList.add("selected");
            }
        }
    }
    
    function changeTheGlobalSelectedWeekOptionTag(newSelectedWeek) {
        let currentSelectedOptionTag = $(`option[week="${newSelectedWeek}"]`);
    
        if (currentSelectedOptionTag == null) {
            //--Get the first option-tag.
            currentSelectedOptionTag = $('select[name="list-of-week"] option');
        }
    
        selectedWeekOptionTag.selected = false;
        currentSelectedOptionTag.selected = true;
        selectedWeekOptionTag = currentSelectedOptionTag;
    }
    
    function customizeSelectedTableCellsEvent() {
        [...$$('div#add-schedule-block table#subject-schedule tr.period-row td.schedule-item')].forEach(cell => {
            cell.addEventListener("click", e => {
                //--Selected cell is a schedule-cell which already has schedule or fully rent computer-room quantity.
                if (cell.classList.contains("unhover"))
                    return null;
    
                const selectedWeek = selectedWeekOptionTag.getAttribute("week");
                const day = cell.getAttribute("day");
                const period = cell.getAttribute("period");
    
                if (cell.classList.contains("selected")) {
                    cell.classList.remove("selected");
    
                    delete selectedCellList[selectedWeek][day][period];
    
                    if (Object.keys(selectedCellList[selectedWeek][day]).length == 0)
                        delete selectedCellList[selectedWeek][day];
                    
                    if (Object.keys(selectedCellList[selectedWeek]).length == 0)
                        delete selectedCellList[selectedWeek];
                } else {
                    cell.classList.add("selected");
    
                    if (selectedCellList[selectedWeek] == null) {
                        selectedCellList[selectedWeek] = {};
                    }
                    if (selectedCellList[selectedWeek][day] == null) {
                        selectedCellList[selectedWeek][day] = {};
                    }
                    //--This empty object is used to store available computer-room list.
                    selectedCellList[selectedWeek][day][period] = {};
                }
            });
        });
    }
    
    function customizeConvertingScheduleAction() {
        $('span#convert-btn').addEventListener("click", e => {
            //--Initialize availableRooms as Map(room:boolean) with all computer-rooms.
            for (var weekAsKey in selectedCellList) {
                for (var dayAsKey in selectedCellList[weekAsKey]) {
                    for (var periodAskey in selectedCellList[weekAsKey][dayAsKey]) {
                        allComputerRoom.forEach(room => {
                            selectedCellList[weekAsKey][dayAsKey][periodAskey][room] = true;
                        });
                    }
                }
            }
    
            //--Search all practiceSchedule and remove the available rooms which are in this schedule.
            allPracticeScheduleInSemester.forEach(schedule => {
                for (var week = schedule.startingWeek; week < schedule.startingWeek + schedule.totalWeek; week++) {
                    for (var period = schedule.startingPeriod; period <= schedule.lastPeriod; period++) {
                        selectedCellList[week][schedule.day][period][schedule.room] = false;
                    }
                }
            });

            createAdjustingScheduleTable();
        });
    }

    function createAdjustingScheduleTable() {
        const adjustScheduleTable = $('div#adjust-schedule-block table#ajdust-subject-schedule tbody');
    
        adjustScheduleTable.innerHTML = "";
        for (var weekAsKey in selectedCellList) {
            for (var dayAsKey in selectedCellList[weekAsKey]) {
                for (var periodAskey in selectedCellList[weekAsKey][dayAsKey]) {
                    const availableRoomsOptionTags = `<select class="available-room-options" name="roomId">${
                        allComputerRoom.reduce((accumulator, room) =>
                            accumulator + (selectedCellList[weekAsKey][dayAsKey][periodAskey][room] ? `<option value="${room}">${room}</option>` : "")
                        , "")
                    }</select>`;

                    adjustScheduleTable.innerHTML += `<tr>
                        <td class="firstWeek">${weekAsKey}</td>
                        <td class="totalWeek">1</td>
                        <td class="day">${dayAsKey}</td>
                        <td class="startingPeriod">${periodAskey}</td>
                        <td class="lastPeriod">${periodAskey}</td>
                        <td class="roomId">${availableRoomsOptionTags}</td>
                        <td class="delete table-row-btn">
                            <button id="${weekAsKey + "_" + dayAsKey + "_" + periodAskey}">
                                <i class="fa-regular fa-trash-can"></i>
                            </button>
                        </td>
                    </tr>`;
                }
            }
        }
        adjustScheduleTable.innerHTML += `<tr>
            <td style="font-size: 1.2rem;" class="firstWeek">...</td>
            <td style="font-size: 1.2rem;" class="totalWeek">...</td>
            <td style="font-size: 1.2rem;" class="day">...</td>
            <td style="font-size: 1.2rem;" class="startingPeriod">...</td>
            <td style="font-size: 1.2rem;" class="lastPeriod">...</td>
            <td style="font-size: 1.2rem;" class="roomId">...</td>
            <td style="font-size: 1.2rem;" class="delete">...</td>
        </tr>`;
    }
})();