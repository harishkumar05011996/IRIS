package com.IRIS.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.IRIS.locatorUpdates.LocatorsReader;
import com.IRIS.validations.TestngValidation;

public class CompareTwoExcelFiles {
	
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CompareTwoExcelFiles.class);
    static String ResultString1 = null;
    static String ResultString2 = null;
    static int rowCount=0;
    private static TestngValidation testngValidation;

    public void verifyIfExcelFilesHaveSameNumberAndNameOfSheets(SoftAssert assertion, Workbook workbook1, Workbook workbook2) {
        int sheetsInWorkbook1 = workbook1.getNumberOfSheets();
        int sheetsInWorkbook2 = workbook2.getNumberOfSheets();
        assertion.assertEquals(sheetsInWorkbook1, sheetsInWorkbook2,
                "Excel work books have different number of sheets. \n " + "Sheets in work book 1 : " + sheetsInWorkbook1
                        + "\n " + "Number of sheets in work book 2 : " + sheetsInWorkbook2);
        List<String> sheetsNameOfWb1 = new ArrayList<>();
        List<String> sheetsNameOfWb2 = new ArrayList<>();
        for (int i = 0; i < sheetsInWorkbook1; i++) {
            sheetsNameOfWb1.add(workbook1.getSheetName(i));
            sheetsNameOfWb2.add(workbook2.getSheetName(i));
        }
        Collections.sort(sheetsNameOfWb1);
        Collections.sort(sheetsNameOfWb2);
        assertion.assertEquals(sheetsNameOfWb1, sheetsNameOfWb2, "Provided excel work books have different name of sheets.");
    }

    public void verifySheetsInExcelFilesHaveSameRowsAndColumns(SoftAssert assertion, Workbook workbook1, Workbook workbook2) {
        int sheetCounts = workbook1.getNumberOfSheets();
        for (int i = 0; i < sheetCounts; i++) {
            Sheet s1 = workbook1.getSheetAt(i);
            Sheet s2 = workbook2.getSheetAt(i);
            int rowsInSheet1 = s1.getPhysicalNumberOfRows();
            int rowsInSheet2 = s2.getPhysicalNumberOfRows();
            assertion.assertEquals(rowsInSheet1, rowsInSheet2, "Sheets have different count of rows..");
            Iterator<Row> rowInSheet1 = s1.rowIterator();
            Iterator<Row> rowInSheet2 = s2.rowIterator();
            while (rowInSheet1.hasNext()) {
                int cellCounts1 = rowInSheet1.next().getPhysicalNumberOfCells();
                int cellCounts2 = rowInSheet2.next().getPhysicalNumberOfCells();
                assertion.assertEquals(cellCounts1, cellCounts2, "Sheets have different count of columns..");
            }
        }
    }

    public void verifyDataInExcelBookAllSheets(SoftAssert assertion, Workbook workbook1, Workbook workbook2, String sheetName1, String sheetName2 ) {
        int sheetCounts = workbook1.getNumberOfSheets();

        for (int i = 0; i < sheetCounts; i++) {
            Sheet s1 = workbook1.getSheetAt(i);
            Sheet s2 = workbook2.getSheetAt(i);
            int rowCounts = s1.getPhysicalNumberOfRows();
            for (int j = 0; j < rowCounts; j++) {
                rowCount++;
                int cellCounts = s1.getRow(j).getPhysicalNumberOfCells();
                for (int k = 0; k < cellCounts; k++) {
                    Cell c1 = s1.getRow(j).getCell(k);
                    Cell c2 = s2.getRow(j).getCell(k);
                    if (c1.getCellType().equals(c2.getCellType())) {
                        if (c1.getCellType() == CellType.STRING) {
                            String v1 = c1.getStringCellValue();
                            String v2 = c2.getStringCellValue();
                            LOGGER.debug("Row Number = {}, First Sheet Value = {}, Second Sheet value = {}, First sheet cell Type = {}, Second Sheet cell Type = {}", rowCount, v1, v2, c1.getCellType(), c2.getCellType());
                            assertion.assertEquals(v1, v2,  "Row number "+ rowCount +  " Sheet Cell " + c1 + " data is not matching");
                        }
                        if (c1.getCellType() == CellType.NUMERIC) {
                            if (DateUtil.isCellDateFormatted(c1) | DateUtil.isCellDateFormatted(c2)) {
                                DataFormatter df = new DataFormatter();
                                String v1 = df.formatCellValue(c1);
                                String v2 = df.formatCellValue(c2);
                                LOGGER.debug("Row Number = {}, First Sheet Value = {}, Second Sheet value = {}, First sheet cell Type = {}, Second Sheet cell Type = {}", rowCount, v1, v2, c1.getCellType(), c2.getCellType());
                                assertion.assertEquals(v1, v2, "Row number "+ rowCount +  " Sheet Cell " + c1 + " data is not matching");
                            } else {
                                double v1 = c1.getNumericCellValue();
                                double v2 = c2.getNumericCellValue();
                                assertion.assertEquals(v1, v2, "Row number "+ rowCount +  " Sheet Cell " + c1 + " data is not matching");
                            }
                        }
                        if (c1.getCellType() == CellType.BOOLEAN) {
                            boolean v1 = c1.getBooleanCellValue();
                            boolean v2 = c2.getBooleanCellValue();
                            LOGGER.debug("Row Number = {}, First Sheet Value = {}, Second Sheet value = {}, First sheet cell Type = {}, Second Sheet cell Type = {}", rowCount, v1, v2, c1.getCellType(), c2.getCellType());
                            assertion.assertEquals(v1, v2, "Row number "+ rowCount +  " Sheet Cell " + c1 + " data is not matching");
                        }
                    }
                    if (c1.getCellType() != (c2.getCellType())) {
                        LOGGER.debug("Row Number = {}, First sheet cell Type = {}, Second Sheet cell Type", rowCount, c1.getCellType(), c2.getCellType());
                        assertion.assertTrue(false, "Row number "+ rowCount +" and cell number " + c1 +" type is not mached");
                    }
                }
            }
        }
    }
    
    public static void ExcelMatch(SoftAssert assertion, String firstFileName, String SecondFileName) throws EncryptedDocumentException, IOException {
        String basepathOfexcelFiles = System.getProperty("user.dir")+ "\\src\\main\\resources\\ExcelSheets\\";
        String file1 = basepathOfexcelFiles + firstFileName  + ".xlsx";
        String file2 = basepathOfexcelFiles + SecondFileName + ".xlsx";
        Pattern regex1 = Pattern.compile("([^\\\\/:*?\"<>|\r\n]+$)");
        Matcher regexMatcher1 = regex1.matcher(file1);
        if (regexMatcher1.find()) {
            ResultString1 = regexMatcher1.group(1);
        }
        Pattern regex2 = Pattern.compile("([^\\\\/:*?\"<>|\r\n]+$)");
        Matcher regexMatcher2 = regex2.matcher(file2);
        if (regexMatcher2.find()) {
            ResultString2 = regexMatcher2.group(1);
        }
        Workbook wb1 = WorkbookFactory.create(new File(file1));
        Workbook wb2 = WorkbookFactory.create(new File(file2));
        CompareTwoExcelFiles mse_CompareExcelFiles = new CompareTwoExcelFiles();
        mse_CompareExcelFiles.verifyIfExcelFilesHaveSameNumberAndNameOfSheets(assertion, wb1, wb2);
        mse_CompareExcelFiles.verifySheetsInExcelFilesHaveSameRowsAndColumns(assertion, wb1, wb2);
        mse_CompareExcelFiles.verifyDataInExcelBookAllSheets(assertion,wb1, wb2, ResultString1, ResultString2);
    }
    
    public static void CompareTwoExcelSheets(SoftAssert assertion,String firstSheetName, String secondSheetName) {
        try {
            ExcelMatch(assertion, firstSheetName, secondSheetName);
        } catch (EncryptedDocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}