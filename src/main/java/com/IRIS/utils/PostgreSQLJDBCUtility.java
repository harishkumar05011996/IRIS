package com.IRIS.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.slf4j.LoggerFactory;

import com.IRIS.locatorUpdates.LocatorsReader;

public class PostgreSQLJDBCUtility {
	
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PostgreSQLJDBCUtility.class);

    public static List<String> headres = new ArrayList<>();
    public static List<String> style = new ArrayList<>();
    public static List<Integer> column = new ArrayList<>();
    public static List<Object[]> excelData = new ArrayList<>();

    public static void dataBaseConnection(String Query) {
    	System.out.println(">>>>>>>>>>"+Query);
        Connection c = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://10.167.10.34:5432/pymidol12062022", "postgres", "Pymidol@123");
            c.setAutoCommit(false);
            LOGGER.debug(" Database connection has been Established ");
            stmt = c.createStatement();
            rs = stmt.executeQuery(Query);
            ResultSetMetaData md = rs.getMetaData();
            for (int i = 1; i <= md.getColumnCount(); i++) {
                headres.add(md.getColumnName(i));
                column.add(20);
                style.add("");
            }
            int columns = md.getColumnCount();
            Object[] rowss = null;
            int count = 0;
            while (rs.next()) {
                count = 0;
                rowss = new Object[columns];
                for (int i = 1; i <= columns; i++) {
                    rowss[count++] = rs.getObject(i);
                }
                excelData.add(rowss);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        LOGGER.debug(" Database connection has been Closed ");
    }
    
    public static void writeDatatoExcel(String sqlQuery, String sheetName) throws Exception {
         dataBaseConnection(sqlQuery);
         LOGGER.debug(" Data writting process is  In-progress ");
         ByteArrayInputStream totaldata = ExcelDownloadUtil.toExcel("data", headres, column, excelData, style);
         File filePath = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\ExcelSheets\\" + sheetName +".xlsx");
         FileUtils.copyInputStreamToFile(totaldata, filePath);
         LOGGER.debug(" Data has been written in the excel Sheet ");
    }

}