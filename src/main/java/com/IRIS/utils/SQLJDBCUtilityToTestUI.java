package com.IRIS.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLJDBCUtilityToTestUI {

    public static List<Map<String, Object>> rows = new ArrayList<>();

    public static List<Map<String, Object>> dataBaseConnection(String query) {

        // ✅ Important: clear old data so it doesn't append each time
        rows.clear();

        // ✅ MySQL URL format: jdbc:mysql://host:port/database?properties [2](https://marmo.dev/lombok-java)
        // Replace host/user/pass as per your environment
        String url  = "jdbc:mysql://localhost:3306/iris?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String pass = "root";

        try {
            // Optional explicit driver load (safe in Jenkins environments) [1](https://stackoverflow.com/questions/45119595/how-to-add-maven-to-the-path-variable)[3](https://bing.com/search?q=Jenkins+configure+Maven+tool+installation+Manage+Jenkins+Tools+Maven+installations)
            Class.forName("com.mysql.cj.jdbc.Driver");

            System.out.println("---------------- MySQL Database connection is starting ----------------------");

            // ✅ try-with-resources ensures everything closes properly
            try (Connection c = DriverManager.getConnection(url, user, pass);
                 Statement stmt = c.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {

                System.out.println("---------------- Database connection has been Established ----------------------");

                ResultSetMetaData md = rs.getMetaData();
                int columns = md.getColumnCount();

                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>(columns);
                    for (int i = 1; i <= columns; i++) {
                        // ColumnLabel works better with aliases; ColumnName is also okay
                        row.put(md.getColumnLabel(i), rs.getObject(i));
                    }
                    rows.add(row);
                }
            }

        } catch (Exception e) {
            // ✅ Do NOT System.exit(0) in test utilities
            System.err.println("DB ERROR: " + e.getClass().getName() + ": " + e.getMessage());
            throw new RuntimeException("Database operation failed: " + e.getMessage(), e);
        }

        System.out.println("-----------------Database connection has been Closed---------------------------");
        System.out.println();
        return rows;
    }

    /**
     * Print all the data for a query
     * @param query SQL query to fetch the data
     * @param tablePrint if you want to print the table pass true else false
     */
    public static List<Map<String, Object>> getAllTableData(String query) {
        List<Map<String, Object>> resultSetData = dataBaseConnection(query);

        return resultSetData;
    }

    public static void main(String[] args) {
        // ✅ Example for MySQL (no "public." schema in MySQL)
        // If your table is "username" inside iris DB:
        List<Map<String, Object>> allData =
                getAllTableData("SELECT * FROM username");
       List<String> vsdvsd = getColumnValuesAsString(allData, "user_id");
    }
    
    public static List<String> getColumnValuesAsString(List<Map<String, Object>> data, String columnName) {
        List<String> values = new ArrayList<>();
        for (Map<String, Object> row : data) {
            Object val = row.get(columnName);
            if (val != null) {
                values.add(String.valueOf(val));
            }
        }
        Collections.sort(values);
        return values;
    }
}