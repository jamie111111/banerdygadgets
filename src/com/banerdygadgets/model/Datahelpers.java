package com.banerdygadgets.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Datahelpers {
    public static boolean addRetourItem(RetourOrder retourOrder) throws SQLException {

        try {
            PreparedStatement statement =
                    DatabaseHandler.getInstance().getConnection().prepareStatement("INSERT INTO " +
                            "retourorder (datumAanmelding,status,reden,bestelId,klantId) VALUES" +
                            "(?,?,?,?,?)");
            statement.setString(1,retourOrder.getStringDatumAanmelding());
            statement.setString(2,retourOrder.getStatus());
            statement.setString(3,retourOrder.getReden());
            statement.setInt(4,retourOrder.getBestelNummer());
            statement.setInt(5,retourOrder.getKlantNummer());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    public static boolean deleteRetourItem(RetourOrder retourOrder) {
        try {
            PreparedStatement statement =
                    DatabaseHandler.getInstance().getConnection().prepareStatement("DELETE FROM " +
                            "retourorder WHERE id=?");
            statement.setInt(1,retourOrder.getRetourNummer());
            int result = statement.executeUpdate();
            if(result == 1) {
                return true;
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    public static boolean updateRetourItem(RetourOrder retourOrder) {
        try {
            PreparedStatement statement =
                    DatabaseHandler.getInstance().getConnection().prepareStatement("" +
                            "UPDATE retourorder SET status=? WHERE id=?");
            statement.setString(1,retourOrder.getStatus());
            statement.setInt(2,retourOrder.getRetourNummer());
            int result = statement.executeUpdate();
            if(result == 1) {
                return true;
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    public static boolean addBestelling(Bestelling bestelling) {
        try {
            PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement(
                    "INSERT INTO bestelling(klantId,datum,status) VALUES(?,?,?)"
            );
            statement.setInt(1,bestelling.getKlantId());
            statement.setString(2,bestelling.getStringDatum());
            statement.setString(3,bestelling.getStatus());
            return statement.executeUpdate() > 0;
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    public static boolean deleteBestelling(Bestelling bestelling) {
        try {
            PreparedStatement statement =
                    DatabaseHandler.getInstance().getConnection().prepareStatement(
                            "DELETE FROM bestelling WHERE bestellingId=?"
                    );
            statement.setInt(1,bestelling.getBestellingId());
            int result = statement.executeUpdate();
            if(result == 1) {
                return true;
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    public static boolean updateBestelling(Bestelling bestelling) {
        try {
            PreparedStatement statement =
                    DatabaseHandler.getInstance().getConnection().prepareStatement("" +
                            "UPDATE bestelling SET status=?,klantId=? WHERE bestellingId=?");
            statement.setString(1,bestelling.getStatus());
            statement.setString(2,bestelling.getStringKlantId());
            statement.setInt(3,bestelling.getBestellingId());
            int result = statement.executeUpdate();
            if(result == 1) {
                return true;
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static boolean updateKlant(Klant klant) {
        try {
            PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement(
                    "" +
                    "UPDATE klant set fullName=?,adres=?,postcode=?,huisnr=?,woonplaats=? WHERE " +
                    "klantId=?");
            statement.setString(1,klant.getFullName());
            statement.setString(2,klant.getAdres());
            statement.setString(4,klant.getHuisnr());
            statement.setString(3,klant.getPostcode());
            statement.setString(5,klant.getWoonplaats());
            statement.setInt(6,klant.getKlantId());
            int result = statement.executeUpdate();
            if(result == 1 ) {
                return  true;
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static boolean addKlant(Klant klant) {
        try {
            PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement(
               "INSERT INTO klant(fullName,adres,huisnr,postcode,woonplaats) VALUES(?,?,?,?,?)");
            statement.setString(1,klant.getFullName());
            statement.setString(2,klant.getAdres());
            statement.setString(3,klant.getHuisnr());
            statement.setString(4,klant.getPostcode());
            statement.setString(5,klant.getWoonplaats());
            return statement.executeUpdate() > 0;
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    public static boolean deleteKlant(Klant klant) {
        try {
            PreparedStatement statement =
                    DatabaseHandler.getInstance().getConnection().prepareStatement(
                            "DELETE FROM klant WHERE klantId=?"
                    );
            statement.setInt(1,klant.getKlantId());
            int result = statement.executeUpdate();
            if(result == 1) {
                return true;
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    //Method om klanten op te halen uit de database in een postcode gebied
    public static List<Klant> getSelectedKlantenWithPostcodeRange(int postcodeVan,
                                                                  int postcodeTot) {
        List<Klant> selectedKlantenInPostcodeRange = new ArrayList<>();
        try {
            PreparedStatement statement =
                    DatabaseHandler.getInstance().getConnection().prepareStatement("Select * FROM" +
                            " klant WHERE postcode BETWEEN ? AND ?");
            statement.setInt(1,postcodeVan);
            statement.setInt(2,postcodeTot);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int klantId = resultSet.getInt("klantId");
                String naam = resultSet.getString("fullName");
                String adres = resultSet.getString("adres");
                String huisnr = resultSet.getString("huisnr");
                String postcode = resultSet.getString("postcode");
                String woonplaats = resultSet.getString("woonplaats");
                Klant klant = new Klant(klantId,naam,adres,huisnr,postcode,woonplaats);
                selectedKlantenInPostcodeRange.add(klant);
            }
            System.out.println("line 183 Datahelpers" + selectedKlantenInPostcodeRange);
            return selectedKlantenInPostcodeRange;
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
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

    public static LocalDate parseDate(String datum) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate formattedDate = LocalDate.parse(datum,formatter);
        return formattedDate;
    }
    public static ObservableList<Bestelling> loadBestellingen() {
        ObservableList<Bestelling> bestelLijst = FXCollections.observableArrayList();
        DatabaseHandler handler = DatabaseHandler.getInstance();
        String query = "SELECT * FROM BESTELLING";
        ResultSet results = handler.executeQuery(query);
        try{
            while (results.next()) {
                int bestellingId = results.getInt("bestellingId");
                int klantId = results.getInt("klantId");
                String datum = results.getString("datum");
                String status = results.getString("status");
                LocalDate formattedDatum = Datahelpers.parseDate(datum);
                Bestelling bestelling = new Bestelling(bestellingId,klantId,formattedDatum,status);
                bestelLijst.add(bestelling);
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return bestelLijst;
    }
    public static ObservableList<RetourOrder> loadRetouren()  {
        ObservableList<RetourOrder> retourLijst = FXCollections.observableArrayList();
        DatabaseHandler handler = DatabaseHandler.getInstance();
        String query = "SELECT * FROM retourorder";
        ResultSet rs = handler.executeQuery(query);
        try {
            while (rs.next()) {
                String date = rs.getString("datumAanmelding");
                LocalDate formattedDate = Datahelpers.parseDate(date);
                int retourNr = rs.getInt("id");
                String reden = rs.getString("reden");
                String status = rs.getString("status");
                int bestelnr = rs.getInt("bestelId");
                int klantnr = rs.getInt("klantId");
                RetourOrder retourOrder = new RetourOrder(retourNr,
                        formattedDate,
                        status,
                        reden,
                        bestelnr,
                        klantnr);
                retourLijst.add(retourOrder);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            }
        return retourLijst;
     }
    }
