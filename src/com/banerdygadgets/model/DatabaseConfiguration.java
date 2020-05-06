package com.banerdygadgets.model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfiguration {

    private final String hostName = "jdbc:mysql://localhost:3306/";
    private final String database = "retouroders";
    private final String user = "root";
    private final String password = "";
    private final String url = hostName + database;

    public String getUrlandDatabase() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
