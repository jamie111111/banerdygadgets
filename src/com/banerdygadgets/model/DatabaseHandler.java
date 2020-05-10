package com.banerdygadgets.model;

import com.banerdygadgets.helpers.AlertFactory;

import java.sql.*;


public final class DatabaseHandler {
    private static DatabaseHandler dHandler = null;
    private static DatabaseConfiguration dconfig;
    private static Connection conn = null;
    private static Statement stmt = null;
    private static final String hostName = "jdbc:mysql://localhost:3306/";
    private static final String database = "nerdygadgets";
    private static final String user = "root";
    private static final String password = "";
    private static final String url = hostName + database;



    private DatabaseHandler() {
        createConnection();
    }

    public static DatabaseHandler getInstance() {
        if (dHandler == null) {
            dHandler = new DatabaseHandler();
        }
        return dHandler;
    }

    private static void createConnection() {
        try {
            conn = DriverManager.getConnection(url,user,password);

        }catch (SQLException e){
            AlertFactory.showSimpleErrorMessage("Foutmelding","Geen connectie","Er zijn verbindingsproblemen met de " +
                    "database");
            System.out.println(e.getMessage());
        }
    }

    public ResultSet executeQuery(String query) {
        ResultSet result;
        try {
            stmt = conn.createStatement();
            result = stmt.executeQuery(query);
            return result;
        }
        catch (SQLException e) {
            System.out.println("Foutmelding bij executeQuery:dataHandle" + e.getMessage());
            return null;
        }

    }

    public static Connection getConnection() {
        return conn;
    }
}
