package com.banerdygadgets.model;

import java.sql.*;

public class Datahelpers {
    Connection conn = DatabaseHandler.getInstance().getConnection();

    public static boolean addRetourItem(RetourOrder retourOrder) throws SQLException {

        try {
            PreparedStatement statement =
                    DatabaseHandler.getInstance().getConnection().prepareStatement(DatabaseStringQueries.INSERT_RETOURORDER);
            statement.setString(1,retourOrder.getStringDatumAanmelding());
            statement.setString(2,retourOrder.getStatus());
            statement.setString(3,retourOrder.getReden());
            statement.setInt(4,retourOrder.getBestelNummer());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    public boolean deleteRetourItem(RetourOrder retourOrder) {
        try {
            PreparedStatement statement = conn.prepareStatement(DatabaseStringQueries.DELETE_RETOURORDER);
            statement.setInt(1,retourOrder.getRetourNummer());
            int result = statement.executeUpdate();
            if(result == 1) {
                return true;
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(DatabaseStringQueries.DELETE_RETOURORDER);
        }
        return false;
    }
    public static boolean updateRetourItem(RetourOrder retourOrder) {
        try {
            PreparedStatement statement =
                    DatabaseHandler.getInstance().getConnection().prepareStatement(DatabaseStringQueries.UPDATE_STATUS_RETOURORDER);
            statement.setString(1,retourOrder.getStatus());
            statement.setInt(2,retourOrder.getRetourNummer());
            int result = statement.executeUpdate();
            if(result == 1) {
                return true;
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(DatabaseStringQueries.UPDATE_STATUS_RETOURORDER);
        }
        return false;
    }
    public static void closeStatement(Statement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
                System.out.println("Statement gesloten");
            }
        } catch (SQLException e) {
            System.out.println("Kan verbinding niet sluiten " + e.getMessage());
        }
    }
}
