package com.IRIS.utils;

import static com.IRIS.constants.constants.TEST_DATA_EXCEL_PATH;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

import org.apache.poi.ss.usermodel.*;

public class GetTestData {

    // Cache: TestCaseId -> (ColumnName -> Value)
    private static final Map<String, Map<String, String>> testDataCache = new HashMap<>();
    private static boolean loaded = false;

    /**
     * Loads Excel into cache once and returns whole map.
     */
    public static Map<String, Map<String, String>> getTestDataFromExcel() {
        if (!loaded) {
            List<Map<String, String>> data = readExcelFile(TEST_DATA_EXCEL_PATH);

            for (Map<String, String> row : data) {
                String testCaseId = getIgnoreCase(row, "TestCaseId");
                if (testCaseId == null || testCaseId.trim().isEmpty()) {
                    continue; // skip rows without TestCaseId
                }

                // Copy all columns except TestCaseId
                Map<String, String> valuesMap = new HashMap<>();
                for (Map.Entry<String, String> entry : row.entrySet()) {
                    if (!entry.getKey().equalsIgnoreCase("TestCaseId")) {
                        valuesMap.put(entry.getKey(), entry.getValue());
                    }
                }

                testDataCache.put(testCaseId.trim(), valuesMap);
            }

            loaded = true;
        }

        return testDataCache;
    }

    /**
     * ✅ Get full row data based on TestCaseId
     */
    public static Map<String, String> getRow(String testCaseId) {
        Map<String, Map<String, String>> all = getTestDataFromExcel();
        Map<String, String> row = all.get(testCaseId);

        if (row == null) {
            throw new RuntimeException("No test data found for TestCaseId: " + testCaseId);
        }
        return row;
    }

    /**
     * ✅ Get single column value based on TestCaseId and ColumnName
     */
    public static String getValue(String testCaseId, String columnName) {
        Map<String, String> row = getRow(testCaseId);

        // direct match
        if (row.containsKey(columnName)) {
            return row.get(columnName);
        }

        // fallback: case-insensitive match
        for (Map.Entry<String, String> e : row.entrySet()) {
            if (e.getKey().equalsIgnoreCase(columnName)) {
                return e.getValue();
            }
        }

        throw new RuntimeException("Column '" + columnName + "' not found for TestCaseId: " + testCaseId
                + ". Available columns: " + row.keySet());
    }

    /**
     * Reads Excel and returns list of rows (ColumnName -> Value)
     * ✅ Handles blank cells (cell iterator skips blanks)
     * ✅ Uses DataFormatter to preserve formats exactly like Excel
     */
    private static List<Map<String, String>> readExcelFile(String filename) {
        List<Map<String, String>> data = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(new FileInputStream(new File(filename)))) {
            Sheet sheet = workbook.getSheetAt(0);

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new RuntimeException("Header row is missing in excel: " + filename);
            }

            DataFormatter formatter = new DataFormatter();
            int lastColumn = headerRow.getLastCellNum(); // total columns based on header

            for (int r = 1; r <= sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);
                if (row == null) continue;

                Map<String, String> rowMap = new HashMap<>();

                for (int c = 0; c < lastColumn; c++) {
                    Cell headerCell = headerRow.getCell(c, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                    if (headerCell == null) continue;

                    String columnName = formatter.formatCellValue(headerCell).trim();
                    if (columnName.isEmpty()) continue;

                    Cell cell = row.getCell(c, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                    String columnValue = (cell == null) ? "" : formatter.formatCellValue(cell);

                    rowMap.put(columnName, columnValue);
                }

                // only add non-empty rows (optional)
                if (!rowMap.isEmpty()) {
                    data.add(rowMap);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Error reading excel file: " + filename, e);
        }

        return data;
    }

    private static String getIgnoreCase(Map<String, String> map, String key) {
        for (Map.Entry<String, String> e : map.entrySet()) {
            if (e.getKey().equalsIgnoreCase(key)) {
                return e.getValue();
            }
        }
        return null;
    }

    // Example
    public static void main(String[] args) {
        String tc = "Retrieve_01";
        System.out.println("Username: " + getValue(tc, "username"));
        System.out.println("Password: " + getValue(tc, "password"));
        System.out.println("Row Data: " + getRow(tc));
    }
}