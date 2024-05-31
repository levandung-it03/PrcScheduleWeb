const allUnavailableScheduleInThisSemester = [];
const allPracticeScheduleInSemester = [];
const computerRoomList = {};
const selectedCellList = {};
let selectedWeekOptionTag = null;

(function main() {
    customizeClosingNoticeMessageEvent();
    customizeSubmitFormAction('div#add-teacher-request-page > form');
    recoveryAllSelectTagDataInForm();
    removePathAttributes();
    buildHeader();
    /*---------------Own-methods------------------*/
    popAllHiddenDataFields();
    selectCurrentWeekOptionTagAndRenderTimeTable();
    continuouslyUpdateSelectedWeekOptionTag();

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
        });

        //--Color each cell that already has subject-schedule.
        allUnavailableScheduleInThisSemester.forEach((schedule, index) => {
            const selectedWeek = Number.parseInt(selectedWeekOptionTag.getAttribute("week"));

            //--This subject is having a schedule in this seleted_week.
            if ((schedule.startingWeek <= selectedWeek) && (selectedWeek <= (schedule.startingWeek + schedule.totalWeek - 1))) {
                //--Add the subject name into schedule-item. 
                const periodAsRowOfSubjectNameCell = Number.parseInt((schedule.lastPeriod + schedule.startingPeriod) / 2);
                $(`tr[id="${periodAsRowOfSubjectNameCell}"] td[day="${schedule.day}"] span`).innerText = schedule.subjectName;

                for (let period = schedule.startingPeriod; period <= schedule.lastPeriod; period++) {
                    //--Make every schedule cells this subject "un-hover" (change: background-color, cursor).
                    $(`tr[id="${period}"] td[day="${schedule.day}"]`).classList.add("un-hover");
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
        //--Then, we color all cells which dosen't have enough computer-room-quan to select.
        for (var periodRow = 1; periodRow <= 16; periodRow++) {
            for (var dayColumn = 2; dayColumn <= 8; dayColumn++) {
                //--If this cell is already colored, passing it.
                if ($(`tr[id="${periodRow}"] td[day="${dayColumn}"]`).classList.contains("un-hover"))
                    continue;

                if (rentRoomsQuantity[periodRow][dayColumn] >= computerRoomList.length) {
                    $(`tr[id="${periodRow}"] td[day="${dayColumn}"] span`).innerText = "Hết phòng";
                    $(`tr[id="${periodRow}"] td[day="${dayColumn}"]`).classList.add("un-hover");
                }
            }
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
})();
