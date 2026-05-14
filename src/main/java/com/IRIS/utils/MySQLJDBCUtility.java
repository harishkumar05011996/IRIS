package com.IRIS.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.LoggerFactory;

public class MySQLJDBCUtility {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MySQLJDBCUtility.class);

    public static List<String> headres   = new ArrayList<>();
    public static List<String> style     = new ArrayList<>();
    public static List<Integer> column   = new ArrayList<>();
    public static List<Object[]> excelData = new ArrayList<>();

    /**
     * Executes SELECT query and stores results in headres/column/style/excelData
     */
    public static void dataBaseConnection(String query) {
        // ✅ Clear previous run data (important!)
        headres.clear();
        style.clear();
        column.clear();
        excelData.clear();

        // ✅ MySQL JDBC URL format: jdbc:mysql://host:port/database?properties [2](https://marmo.dev/lombok-java)
        // Replace localhost/user/pass with your environment values
        String url  = "jdbc:mysql://localhost:3306/iris?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String pass = "root";

        // Optional: explicit driver load (usually not required in JDBC 4+, but safe) [1](https://stackoverflow.com/questions/45119595/how-to-add-maven-to-the-path-variable)[3](https://bing.com/search?q=Jenkins+configure+Maven+tool+installation+Manage+Jenkins+Tools+Maven+installations)
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection con = DriverManager.getConnection(url, user, pass);
                 Statement stmt = con.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {

                LOGGER.debug("✅ MySQL Database connection established");

                ResultSetMetaData md = rs.getMetaData();
                int columnsCount = md.getColumnCount();

                // Headers
                for (int i = 1; i <= columnsCount; i++) {
                    headres.add(md.getColumnLabel(i));
                    column.add(20);
                    style.add("");
                }

                // Data rows
                while (rs.next()) {
                    Object[] row = new Object[columnsCount];
                    for (int i = 1; i <= columnsCount; i++) {
                        row[i - 1] = rs.getObject(i);
                    }
                    excelData.add(row);
                }

                LOGGER.debug("✅ Data fetched successfully. Rows: {}", excelData.size());
            }

        } catch (Exception e) {
            LOGGER.error("❌ DB Error while executing query: {}", query, e);
            // Do NOT System.exit here; just throw runtime or handle gracefully
            throw new RuntimeException("Database operation failed: " + e.getMessage(), e);
        }

        LOGGER.debug("✅ MySQL Database connection closed");
    }

    /**
     * Runs query and writes results to Excel
     */
    public static void writeDatatoExcel(String sqlQuery, String sheetName) throws Exception {
        dataBaseConnection(sqlQuery);

        LOGGER.debug("📄 Excel writing process started");
        ByteArrayInputStream totaldata =
                ExcelDownloadUtil.toExcel("data", headres, column, excelData, style);

        File filePath = new File(System.getProperty("user.dir")
                + "\\src\\main\\resources\\ExcelSheets\\"
                + sheetName + ".xlsx");

        FileUtils.copyInputStreamToFile(totaldata, filePath);
        LOGGER.debug("✅ Data written to Excel: {}", filePath.getAbsolutePath());
    }
}