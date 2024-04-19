
(function main() {
    const validatingBlocks = {
        gradeId: {
            tag: $('input[name=studentId]'),
            confirm: function (value) {
                this.isValid = (value.length != 0) && (value.search(" ")== -1);
                return this.isValid;
            },
            errorMessage: "Mã lớp không hợp lệ.",
            isValid: false,
        },
        department: {
             tag: $('input[name=grade]'),
             confirm: function (value) {
                  this.isValid = (value.length != 0) && (value.search(" ")== -1);
                  return this.isValid;
             },
             errorMessage: "Mã ngành không được để trống.",
             isValid: false,
        },
    };

       customizeClosingNoticeMessageEvent();
       createErrBlocksOfInputTags(validatingBlocks);
       customizeValidateEventInputTags(validatingBlocks);
       customizeSubmitFormAction(validatingBlocks);
       customizeAutoFormatStrongInputTextEvent();
       recoveryAllSelectTagDataInForm();
       removePathAttributes();
       mappingCategoryNameWithCurrentPage();
})();
