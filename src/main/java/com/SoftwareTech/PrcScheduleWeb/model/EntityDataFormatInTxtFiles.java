package com.SoftwareTech.PrcScheduleWeb.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Data
@NoArgsConstructor
public class EntityDataFormatInTxtFiles {
    private MultipartFile file;
    private StringBuilder insertionQuery;
    public EntityDataFormatInTxtFiles(MultipartFile file) {
        this.file = file;
        this.insertionQuery = this.extractTxtFilesDataIntoInsertionQueryFormat(file);
    }
    public StringBuilder extractTxtFilesDataIntoInsertionQueryFormat() {
        return this.extractTxtFiles(this.file);
    }
    //--Overload
    public StringBuilder extractTxtFilesDataIntoInsertionQueryFormat(MultipartFile file) {
        return this.extractTxtFiles(file);
    }
    public StringBuilder extractTxtFiles(MultipartFile file) {
        try {
            String content = new String(file.getBytes(), StandardCharsets.UTF_8);

            String[] lines = content.split("\n");
            StringBuilder queryBuilder = new StringBuilder();

            queryBuilder.append("INSERT INTO ").append(lines[0].trim()).append(" ");
            queryBuilder.append(lines[1].trim()).append(" VALUES ");
            lines[0] = "";
            lines[1] = "";
            for (String line: lines) {
                queryBuilder.append(line.trim()).append(" ");
            }
            return queryBuilder;
        } catch (IOException e) {
            e.fillInStackTrace();
        }
        return null;
    }
}
