package com.IRIS.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostgreSQLJDBCUtilityToTestUI {

    public static List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();

    public static List<Map<String, Object>> dataBaseConnection(String Query) {
        Connection c = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://yna-test.ymi.com:5432/TCGEN", "postgres", "test");
            c.setAutoCommit(false);
            System.out.println("---------------- Database connection has been Established ----------------------");
            stmt = c.createStatement();
            rs = stmt.executeQuery(Query);
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();
            while (rs.next()) {
                Map<String, Object> row = new HashMap<String, Object>(columns);
                for (int i = 1; i <= columns; ++i) {
                    row.put(md.getColumnName(i), rs.getObject(i));
                }
                rows.add(row);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("-----------------Database connection has been Closed---------------------------");
        System.out.println("\r\n");
        return rows;
    }

    /** This method is to print all the data for a table
     * @param query SQl query to fetch the data
     * @param tablePrint if you want to print the table than pass true else false
     * @return
     */
    public static List<Map<String, Object>> getAllTableData(String query, boolean tablePrint) {
        List<Map<String, Object>> resultSetData = dataBaseConnection(query);
        if(tablePrint) {
        	 for (Map<String, Object> row : resultSetData) {
                 for (Map.Entry<String, Object> rowEntry : row.entrySet()) {
                     System.out.print(rowEntry.getKey() + " = " + rowEntry.getValue() + " | ");
                 }
                 System.out.println("\r\n");
             }
        }
        return resultSetData;
    }
    
    /** indexing is used for Rows of  table
     * @param args
     * @throws SQLException
     */
    public static void main(String[] args) throws SQLException {
        List<Map<String, Object>> allData = getAllTableData("SELECT * FROM public.locators limit 20", true);
    }
}
