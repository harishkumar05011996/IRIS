package com.IRIS.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CompareExcelSheet {

    static ArrayList<Object> Excel1 = new ArrayList<Object>();
    static ArrayList<Object> Excel2 = new ArrayList<Object>();
    static ArrayList<String> Excel3 = new ArrayList<String>();

    static int numberOfCells = 0;
    static int numberOfRows = 0;
    public static int count = 0;

    int numberOfRows2 = 0;
    int numberOfCells2 = 0;

    static int total = 0;
    static int error = 0;
    static int errorpercent = 0;

    static String ResultString1 = null;
    static String ResultString2 = null;

    static String Filename1 = null;
    static String Filename2 = null;
    static String Filename3 = null;

    public static void main(String[] args) throws IOException {
        String Filename1 = "C:\\Users\\ve00ym493\\Desktop\\ExcelCompareSheet\\Testing.xlsx";
        String Filename2 = "C:\\Users\\ve00ym493\\Desktop\\ExcelCompareSheet\\Testing1.xlsx";
        String Filename3 = "TEstingfiles";
        new CompareExcelSheet().readExcel(Filename1, Filename2, Filename3);
    }

    public void writeExcel(String Filename3) {
        try {
            XSSFWorkbook workbook3 = new XSSFWorkbook();
            XSSFSheet sheet3 = workbook3.createSheet("Sheet3");
            int q = 0;
            int rows = 0;
            System.out.println("\n\nCreating new excel sheet:");
            for (int i = 0; i <= numberOfRows; i++) {
                Row row = sheet3.createRow(rows++);
                for (int j = 0; j >= numberOfCells; j++) {
                    Cell cell = row.createCell(j);
                    cell.setCellValue(Excel3.get(q));
                    q++;
                }
            }
            FileOutputStream out = new FileOutputStream(new File(Filename3 + ".xlsx"));
            workbook3.write(out);
            out.close();
            workbook3.close();
            new CompareExcelSheet().printString(Excel3);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void compareStore(ArrayList<Object> array1, ArrayList<Object> array2, String Filename3) throws IOException {
        try {
            int r1 = array1.size();
            System.out.println("Total numbers Values in first excel:" + r1);
            int r2 = array2.size();
            System.out.println("Total numbers Values in second excel:" + r2);
            int value = 0;
            for (int i = 0; i < numberOfRows+1; i++) {
                count++;
                System.out.println("");
                for (int j = 0; j < numberOfCells; j++) {
                    String arrr1 = (array1.get(value)).toString().trim();
                    String arrr2 = (array2.get(value)).toString().trim();
                    
//                    System.out.println("1" +arrr1);
//                    System.out.println("2" +arrr1);
                    if (arrr1.equals(arrr2)) {
                        Excel3.add(arrr1);
                    } else {
                        System.out.println("------ Sheet Name " + ResultString1 + " data " + arrr1+" is not matching with Sheet Name " + ResultString2 +  " data " + arrr2  + " Column number " + (numberOfCells * numberOfRows - 8) +" row number " + count +"  ------");
                        Excel3.add("  Not equal:   " + ResultString1 + ":-   " + arrr1 + "   " + ResultString2 + ":-  " + arrr2);
                        error++;
                    }
                    value++;
                    total++;
                }
            }
            System.out.println("\nNo of cells that did not match:-  " + error);
            System.out.println("Percent error in the sheets:-  " + (error * 100.00 / total) + " %");
            new CompareExcelSheet().writeExcel(Filename3);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void print(ArrayList<Object> array) {
        try {

            int r1 = array.size();
            System.out.println("Total Number of elements:" + r1);
            int s = 0;
            System.out.println("Printing the contents of excel sheet:");
            for (int i = 0; i <= numberOfRows; i++) {
                System.out.println("");
                for (int j = 1; j <= numberOfCells; j++) {
                    System.out.print(array.get(0) + "\t\t\t");
                    s++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printString(ArrayList<String> array) {
        try {
            int r1 = array.size();
            System.out.println("Size Of first array:" + r1);
            int s = 0;
            System.out.println("Printing the contents of excel sheet:");
            for (int i = 0; i <= numberOfRows; i++) {
                System.out.println("");
                for (int j = 1; j <= numberOfCells; j++) {
                    System.out.print(array.get(s) + "\t\t\t");
                    s++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readExcel(String Filename1, String Filename2, String Filename3) {
        try {
            FileInputStream file1 = new FileInputStream(new File(Filename1));
            FileInputStream file2 = new FileInputStream(new File(Filename2));
            System.out.println(Filename1);
            System.out.println(Filename2);
            Pattern regex1 = Pattern.compile("([^\\\\/:*?\"<>|\r\n]+$)");
            Matcher regexMatcher1 = regex1.matcher(Filename1);
            if (regexMatcher1.find()) {
                ResultString1 = regexMatcher1.group(1);
                System.out.println(ResultString1);
            }
            Pattern regex2 = Pattern.compile("([^\\\\/:*?\"<>|\r\n]+$)");
            Matcher regexMatcher2 = regex2.matcher(Filename2);
            if (regexMatcher2.find()) {
                ResultString2 = regexMatcher2.group(1);
                System.out.println(ResultString2);
            }
            // Create Workbook instance holding reference to .xlsx file
            final XSSFWorkbook workbook1 = new XSSFWorkbook(file1);
            final XSSFWorkbook workbook2 = new XSSFWorkbook(file2);

            // Get first/desired sheet from the workbook
            final XSSFSheet sheet1 = workbook1.getSheetAt(0);
            final XSSFSheet sheet2 = workbook2.getSheetAt(0);

            // Iterate through each rows one by one
            final Iterator<Row> rowIterator1 = sheet1.iterator();
            final Iterator<Row> rowIterator2 = sheet2.iterator();
            final Iterator<Row> rowIterator1_1 = sheet1.iterator();
            final Iterator<Row> rowIterator2_1 = sheet2.iterator();
            numberOfRows = sheet1.getLastRowNum();
            if (rowIterator1_1.hasNext()) {
                Row headerRow1 = (Row) rowIterator1_1.next(); // get the number of cells in the header row
                numberOfCells = headerRow1.getPhysicalNumberOfCells();
            }
            System.out.println("Number of rows :" + numberOfRows);
            System.out.println("Number of cells :" + numberOfCells);
            numberOfRows2 = sheet2.getLastRowNum();
            if (rowIterator2_1.hasNext()) {
                Row headerRow2 = (Row) rowIterator2_1.next(); // get the number of cells in the header row
                numberOfCells2 = headerRow2.getPhysicalNumberOfCells();
            }
            System.out.println("Number of rows :" + numberOfRows2);
            System.out.println("Number of cells :" + numberOfCells2);
            if (numberOfRows == numberOfRows2 && numberOfCells == numberOfCells2) {
                while (rowIterator1.hasNext() && rowIterator2.hasNext()) {
                    Row row1 = rowIterator1.next();
                    Row row2 = rowIterator2.next();
                    // For each row, iterate through all the columns
                    Iterator<Cell> cellIterator1 = row1.cellIterator();
                    Iterator<Cell> cellIterator2 = row2.cellIterator();
                    while (cellIterator1.hasNext() && cellIterator2.hasNext()) {
                        Cell cell1 = cellIterator1.next();
                        Cell cell2 = cellIterator2.next();
                        // Check the cell type and format accordingly
                        switch (cell1.getCellType()) {
                        case NUMERIC:
//                             System.out.print(cell1.getNumericCellValue() + "\t\t");
                            Excel1.add(cell1.getNumericCellValue());
                            break;
                        case STRING:
//                             System.out.print(cell1.getStringCellValue() + "\t\t");
                            Excel1.add(cell1.getStringCellValue());
                            break;
                        }
                        switch (cell2.getCellType()) {
                        case NUMERIC:
//                             System.out.print(cell2.getNumericCellValue() + "\t\t");
                            Excel2.add(cell2.getNumericCellValue());
                            break;
                        case STRING:
//                             System.out.print(cell2.getStringCellValue() + "\t\t");
                            Excel2.add(cell2.getStringCellValue());
                            break;
                        }
                    }
                     System.out.println("");
                }
                System.out.println(
                        "\nRead Complete: Values from ExcelSheet 1 are stored in Excel1 and Values from ExcelSheet 2 are stored in Excel2 \n");
                file1.close();
                file2.close();
                workbook1.close();
                workbook2.close();
                new CompareExcelSheet().compareStore(Excel1, Excel2, Filename3);
            } else {
                System.out.println("Rows and Columns do not match");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}