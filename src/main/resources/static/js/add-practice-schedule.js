const allUnavailableScheduleInThisSemester = [];
const allPracticeScheduleInSemester = [];
const computerRoomList = {};
const selectedCellList = {};
let selectedWeekOptionTag = null;
let updatedPracticeSchedule = {};

(function main() {
    customizeClosingNoticeMessageEvent();
    removePathAttributes();
    buildHeader();
    /*---------------Own-methods------------------*/
    popAllHiddenDataFields();
    selectCurrentWeekOptionTagAndRenderTimeTable();
    continuouslyUpdateSelectedWeekOptionTag();
    customizeSelectedTableCellsEvent();
    customizeConvertingScheduleAction();
    customizeSubmitFormAction("div#adjust-schedule-block > form");
    /*---------Update-subject-schedule------------*/
    mappingUpdatedSubjectSchedule();
    /*--------------------------------------------*/

    function popAllHiddenDataFields() {
        [...$$("div#hidden-blocks div#all-unavailable-subject-schedule div.subject-schedule-hidden-block")].forEach((block, index) => {
            allUnavailableScheduleInThisSemester[index] = {};
            block.querySelectorAll("span.data-field").forEach(tagField => {
                if (tagField.getAttribute("type") == "text") {
                    allUnavailableScheduleInThisSemester[index][tagField.getAttribute("name")] = tagField.innerText;
                } else if (tagField.getAttribute("type") == "number") {
                    allUnavailableScheduleInThisSemester[index][tagField.getAttribute("name")] = Number.parseInt(tagField.innerText);
                }
            });
        });

        [...$$("div#hidden-blocks div#all-practice-schedule-in-this-semester div.subject-schedule-hidden-block")].forEach((block, index) => {
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
            computerRoomList[tag.innerText] = true;
        });

        [...$$('div#hidden-blocks div#updated-subject-schedule-block span.data-field')].forEach(tagField => {
            if (tagField.getAttribute("type") == "text") {
                updatedPracticeSchedule[tagField.getAttribute("name")] = tagField.innerText;
            } else if (tagField.getAttribute("type") == "number") {
                updatedPracticeSchedule[tagField.getAttribute("name")] = Number.parseInt(tagField.innerText);
            }
        });

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

    function continuouslyUpdateSelectedWeekOptionTag() {
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
            cell.classList.remove("un-hover");
            cell.classList.remove("selected");
            cell.classList.remove("cant-choose");
        });

        //--Color each cell that already has subject-schedule.
        allUnavailableScheduleInThisSemester.forEach((schedule, index) => {
            const selectedWeek = Number.parseInt(selectedWeekOptionTag.getAttribute("week"));
            //--This subject is having a schedule in this selected-week.
            if ((schedule.startingWeek <= selectedWeek) && (selectedWeek <= (schedule.startingWeek + schedule.totalWeek - 1))) {
                //--Add the subject name into schedule-item. 
                const periodAsRowOfSubjectNameCell = Number.parseInt((schedule.lastPeriod + schedule.startingPeriod) / 2);
                $(`tr[id="${periodAsRowOfSubjectNameCell}"] td[day="${schedule.day}"] span`).innerText = schedule.subjectName;

                for (let period = schedule.startingPeriod; period <= schedule.lastPeriod; period++) {
                    //--Make every schedule cells this subject "un-hover" (change: background-color, cursor).
                    $(`tr[id="${period}"] td[day="${schedule.day}"]`).classList.add("un-hover");
                    $(`tr[id="${period}"] td[day="${schedule.day}"]`).classList.add("cant-choose");
                }
            }
        });

        //--Fill the MarkingArray 'rentRoomsQuantity' with rent computer-room quantity
        let rentRoomsQuantity = Array.from({ length: 17 }, () => Array(8).fill(0));
        allPracticeScheduleInSemester.forEach(schedule => {
            //--Step to next schedule if this subject-schedule is not in this selected-week.
            if ((selectedWeekOptionTag.getAttribute("week") < schedule.startingWeek)
                || ((schedule.startingWeek + schedule.totalWeek - 1) < selectedWeekOptionTag.getAttribute("week")))
                return;

            for (var period = schedule.startingPeriod; period <= schedule.lastPeriod; period++)
                rentRoomsQuantity[period][schedule.day]++;

        });
        //--Then, we color all cells which don't have enough computer-room-quan to select.
        let startingDateOfSelectedWeek = getDateObjFromCommonFormat(selectedWeekOptionTag.getAttribute("startingDate"));
        for (var dayColumn = 2; dayColumn <= 8; dayColumn++) {
            for (var periodRow = 1; periodRow <= 16; periodRow++) {
                let tempCell = $(`tr[id="${periodRow}"] td[day="${dayColumn}"]`);
                //--Check if this cell is already filled by a subject-schedule, pass it!
                if (!tempCell.classList.contains("un-hover")) {
                    //--Check if this cell doesn't has enough prc-rooms-quantity to be selected.
                    if (rentRoomsQuantity[periodRow][dayColumn] >= Object.keys(computerRoomList).length) {
                        tempCell.querySelector('span').innerText = "Hết phòng";
                        tempCell.classList.add("un-hover");
                        tempCell.classList.add("cant-choose");
                    }
                }
                //--Make all cells which are in the past can't be selected.
                if (startingDateOfSelectedWeek <= new Date())
                    tempCell.classList.add("un-hover");
            }
            startingDateOfSelectedWeek.setDate(startingDateOfSelectedWeek.getDate() + 1);
        }

        //--Color all cells that was selected before.
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
                if (cell.classList.contains("un-hover"))
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
            createAdjustingScheduleTable();
            customizeDeleteAdjustingSchedule();
        });
    }

    function createAdjustingScheduleTable() {
        //--Initialize selectedCellList[weekAsKey][dayAsKey][periodAskey] = {<room>: true, ...};
        for (var weekAsKey in selectedCellList)
            for (var dayAsKey in selectedCellList[weekAsKey])
                for (var periodAskey in selectedCellList[weekAsKey][dayAsKey])
                    Object.assign(selectedCellList[weekAsKey][dayAsKey][periodAskey], computerRoomList);

        /**-Loop through all practice-schedule in this current semester, and change computer-room status.
         *--Object syntax:
         *  selectedCellList[weekAsKey][dayAsKey][periodAskey] = {
         *      <rent_room>: false,
         *      <room>: true,
         *      ...
         *  };
        */
        allPracticeScheduleInSemester.forEach(schedule => {
            for (var week = schedule.startingWeek; week < schedule.startingWeek + schedule.totalWeek; week++) {
                for (var period = schedule.startingPeriod; period <= schedule.lastPeriod; period++) {
                    try {
                        selectedCellList[week][schedule.day][period][schedule.room] = false;
                    } catch(err) { continue; }
                }
            }
        });

        //--Get adjust-table as DOM element to create its t-body with selected-schedule-rows-data.
        const adjustScheduleTable = $('div#adjust-schedule-block table#ajdust-subject-schedule tbody');
        adjustScheduleTable.innerHTML = "";

        //--Loop through selected-cell-list to map it into t-body as select-schedule-rows-data.
        for (var weekAsKey in selectedCellList) {
            for (var dayAsKey in selectedCellList[weekAsKey]) {
                //--To seperate the background-color of even-week and odd-week.
                const backgroundColor = (weekAsKey % 2 == 0) ? "#e4e3e3" : "white";

                for (var periodAskey in selectedCellList[weekAsKey][dayAsKey]) {
                    let availableRoomsOptionTags = "";
                    //--Map the available-computer-rooms as option-tags into select-tag of each select-schedule-rows-data.
                    for (var roomKey in computerRoomList)
                        //--If computer-room is available, or its value is "true" in 'selectedCellList'.
                        if (selectedCellList[weekAsKey][dayAsKey][periodAskey][roomKey])
                            availableRoomsOptionTags += `<option value="${roomKey}">${roomKey}</option>`;

                    //--Create select-schedule-rows-data.
                    adjustScheduleTable.innerHTML += `<tr style="background-color: ${backgroundColor}">
                        <td class="week">${weekAsKey}</td>
                        <td class="day">${dayAsKey}</td>
                        <td class="period">${periodAskey}</td>
                        <td class="roomId">
                            <select style="background-color: ${backgroundColor}" class="available-room-options" name="roomId">
                                ${availableRoomsOptionTags}
                            </select>
                        </td>
                        <td class="delete table-row-btn">
                            <span id="${weekAsKey + "_" + dayAsKey + "_" + periodAskey}">
                                <i class="fa-regular fa-trash-can"></i>
                            </span>
                        </td>
                    </tr>`;
                }
            }
        }
    }

    function customizeDeleteAdjustingSchedule() {
        [...$$('div#adjust-schedule-block table tbody td.delete span')].forEach(deleteBtn => {
            deleteBtn.addEventListener("click", e => {
                const deleteBtnId = deleteBtn.id.split("_");
                const schedule = {
                    week: Number.parseInt(deleteBtnId[0]),
                    day: Number.parseInt(deleteBtnId[1]),
                    period: Number.parseInt(deleteBtnId[2])
                };

                delete selectedCellList[schedule.week][schedule.day][schedule.period];

                if (Object.keys(selectedCellList[schedule.week][schedule.day]).length == 0)
                    delete selectedCellList[schedule.week][schedule.day];

                if (Object.keys(selectedCellList[schedule.week]).length == 0)
                    delete selectedCellList[schedule.week];

                deleteBtn.parentElement.parentElement.outerHTML = "";
                renderTimeTable();
            });
        });
    }

    function customizeSubmitFormAction() {
        $('div#adjust-schedule-block form span#submit').addEventListener("click", e => {
            const practiceScheduleList = [...$$('div#adjust-schedule-block table tbody tr')].map(row =>
                `week:${row.querySelector('td.week').innerText.trim()
                }_day:${row.querySelector('td.day').innerText.trim()
                }_period:${row.querySelector('td.period').innerText.trim()
                }_roomId:${row.querySelector('td.roomId select option:checked').innerText.trim()}`
            );
            $('div#adjust-schedule-block form input[name=practiceScheduleListAsString]').value = practiceScheduleList.join(", ");

            if (confirm("Bạn chắc chắn muốn thực hiện thao tác?") == true) {
                $('div#adjust-schedule-block form').submit();
            }
        });
    }

    function mappingUpdatedSubjectSchedule() {
        if (Object.keys(updatedPracticeSchedule).length != 0) {
            (function removeUpdatedScheduleFromScheduleList() {
                for (var index = 0; index < allUnavailableScheduleInThisSemester.length; index++) {
                    if (updatedPracticeSchedule.subjectScheduleId === allUnavailableScheduleInThisSemester[index].subjectScheduleId) {
                        delete allUnavailableScheduleInThisSemester[index];
                        break;
                    }
                }
            })();

            //--Remove it out of practice-schedule-list to make the computer-room of this updated-practice-schedule free,
            //--and not be counted by 'rentRoomsQuantity' when 'renderTimetable()'.
            (function removeUpdatedScheduleFromPracticeScheduleList() {
                for (var index = 0; index < allPracticeScheduleInSemester.length; index++) {
                    if (updatedPracticeSchedule.subjectScheduleId === allPracticeScheduleInSemester[index].subjectScheduleId) {
                        delete allPracticeScheduleInSemester[index];
                        break;
                    }
                }
            })();

            (function addUpdatedScheduleIntoSelectedCellListOfTimeTable() {
                //--Map the updated-practice-schedule into 'selectedCellList' elements.
                const lastWeek = updatedPracticeSchedule.startingWeek + updatedPracticeSchedule.totalWeek - 1;
                for (var week = updatedPracticeSchedule.startingWeek; week <= lastWeek; week++) {
                    for (var period = updatedPracticeSchedule.startingPeriod; period <= updatedPracticeSchedule.lastPeriod; period++) {
                        if (selectedCellList[week] == null) {
                            selectedCellList[week] = {};
                        }
                        if (selectedCellList[week][updatedPracticeSchedule.day] == null) {
                            selectedCellList[week][updatedPracticeSchedule.day] = {};
                        }
                        //--This empty object is used to store available computer-room list.
                        selectedCellList[week][updatedPracticeSchedule.day][period] = {};
                    }
                }
                /**-Render time-table with the updated-components:
                 *      'allUnavailableScheduleInThisSemester' from 'removeUpdatedScheduleFromScheduleList()'
                 *      'allPracticeScheduleInSemester' from 'removeUpdatedScheduleFromPracticeScheduleList()'
                 *      'computerRoomList'
                 *      'selectedCellList' above.
                */
                renderTimeTable();
                //--Use the created 'selectedCellList' to generate t-body data of adjust-schedule-table.
                createAdjustingScheduleTable();
            })();
        }
    }
})();