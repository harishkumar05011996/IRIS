package com.IRIS.utils;

import static com.IRIS.constants.constants.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.*;

public class GetTestData {
    
    public static Map<String, Map<String, String>> GetTestDataFromExcel() {
        List<Map<String, String>> data = readExcelFile(TEST_DATA_EXCEL_PATH);
        for (Map<String, String> row : data) {
            String key = "";
            Map<String, String> valuesMap = new HashMap<>();
            for (Map.Entry<String, String> entry : row.entrySet()) {
                if (entry.getKey().equals("TestCaseId")) {
                    key = entry.getValue();
                } else {
                    valuesMap.put(entry.getKey(), entry.getValue());
                }
            }
            rowMap.put(key, valuesMap);
        }

        return rowMap;
    }

    private static List<Map<String, String>> readExcelFile(String filename) {
        List<Map<String, String>> data = new ArrayList<>();
        try (Workbook workbook = WorkbookFactory.create(new FileInputStream(new File(filename)))) {
            Sheet sheet = workbook.getSheetAt(0);
            Row headerRow = sheet.getRow(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue; // skip the header row
                }
                Map<String, String> rowMap = new HashMap<>();
                for (Cell cell : row) {
                    String columnName = headerRow.getCell(cell.getColumnIndex()).getStringCellValue();
                    String columnValue = cell.toString();
                    System.out.println(columnName + " | " + columnValue);
                    rowMap.put(columnName, columnValue);
                }
                System.out.println("\n");
                data.add(rowMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
    
    public static void main(String[] args) {
    	GetTestDataFromExcel();
	}
}
