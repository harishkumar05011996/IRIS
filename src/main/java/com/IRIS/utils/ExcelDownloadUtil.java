package com.IRIS.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFFont;


public class ExcelDownloadUtil {

    public static ByteArrayInputStream toExcel(String sheetName, List<String> headerArr, List<Integer> columnwidth,
            List<Object[]> excelData, List<String> styleArray) throws Exception {
        SXSSFWorkbook workbook = new SXSSFWorkbook(50);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        SXSSFSheet sheet = workbook.createSheet(sheetName);
        CellStyle headerStyle = workbook.createCellStyle();
        XSSFFont headerFont = (XSSFFont) workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontName("Arial");
        headerFont.setFontHeight(11);

        headerStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setFont(headerFont);
        headerStyle.setWrapText(true);

        CellStyle rowStyle = workbook.createCellStyle();
        XSSFFont rowFont = (XSSFFont) workbook.createFont();
        rowFont.setFontName("Verdana");
        rowFont.setFontHeight(10);
        rowStyle.setBorderBottom(BorderStyle.THIN);
        rowStyle.setBorderRight(BorderStyle.THIN);
        rowStyle.setBorderTop(BorderStyle.THIN);
        rowStyle.setBorderLeft(BorderStyle.THIN);
        rowStyle.setFont(rowFont);

        CellStyle numberStyle = workbook.createCellStyle();
        numberStyle.setAlignment(HorizontalAlignment.RIGHT);
        numberStyle.setWrapText(true);
        numberStyle.setFont(rowFont);
        numberStyle.setBorderBottom(BorderStyle.THIN);
        numberStyle.setBorderRight(BorderStyle.THIN);
        numberStyle.setBorderTop(BorderStyle.THIN);
        numberStyle.setBorderLeft(BorderStyle.THIN);

        CellStyle centerStyle = workbook.createCellStyle();
        centerStyle.setAlignment(HorizontalAlignment.CENTER);
        centerStyle.setWrapText(true);
        centerStyle.setFont(rowFont);
        centerStyle.setBorderBottom(BorderStyle.THIN);
        centerStyle.setBorderRight(BorderStyle.THIN);
        centerStyle.setBorderTop(BorderStyle.THIN);
        centerStyle.setBorderLeft(BorderStyle.THIN);
        
        CellStyle decimalStyle = workbook.createCellStyle();
        decimalStyle.setAlignment(HorizontalAlignment.RIGHT);
        decimalStyle.setWrapText(true);
        decimalStyle.setFont(rowFont);
        decimalStyle.setBorderBottom(BorderStyle.THIN);
        decimalStyle.setBorderRight(BorderStyle.THIN);
        decimalStyle.setBorderTop(BorderStyle.THIN);
        decimalStyle.setBorderLeft(BorderStyle.THIN);
        decimalStyle.setDataFormat(workbook.createDataFormat().getFormat("0.00"));

        Row row = sheet.createRow(0);
        row.setHeightInPoints(40.0f);
        int count = 0;
        for (String header : headerArr) {
            createCell(row, count++, header, headerStyle);
            sheet.setColumnWidth(count - 1, columnwidth.get(count - 1) * 256);
        }

        int rowCount = 1;
        for (Object[] data : excelData) {
            Row dataRow = sheet.createRow(rowCount++);
            dataRow.setHeightInPoints(12.75f);
            for (int i = 0; i < data.length; i++) {
                if (i > headerArr.size()-1) {
                    break;
                }
                if (styleArray.get(i) == "numberStyle") {
                    //numberStyle.setDataFormat(workbook.createDataFormat().getFormat("0.00"));
                    createCell(dataRow, i, data[i], numberStyle);
                } else if (styleArray.get(i) == "centerStyle") {
                    createCell(dataRow, i, data[i], centerStyle);
                } else if (styleArray.get(i) == "decimalStyle") {
                    createCell(dataRow, i, data[i], decimalStyle);
                }
                else {
                    createCell(dataRow, i, data[i], rowStyle);
                }

            }
        }
        workbook.write(out);
        workbook.close();
        return new ByteArrayInputStream(out.toByteArray());
    }

    private static void createCell(Row row, int columnCount, Object value, CellStyle style) {
        Cell cell = row.createCell(columnCount);
        if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else if (value instanceof BigInteger) {
            cell.setCellValue(value.toString());
        } else if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof BigDecimal) {
            cell.setCellValue(((BigDecimal) value).doubleValue());
        } else {
            if(value == null) {
                cell.setCellValue("");
            } else {
                cell.setCellValue(value.toString());
            }
        }
        cell.setCellStyle(style);
    }

}
