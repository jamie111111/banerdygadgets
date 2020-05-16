package com.banerdygadgets.controllers.klanten;

import com.banerdygadgets.Main;
import com.banerdygadgets.helpers.AlertFactory;
import com.banerdygadgets.model.DatabaseHandler;
import com.banerdygadgets.model.Datahelpers;
import com.banerdygadgets.model.Klant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class KlantenViewController {
    private static ObservableList<Klant> klantLijst = FXCollections.observableArrayList();

    @FXML
    StackPane klantStackPane;

    @FXML
    TableView<Klant> tableViewKlant;
    @FXML
    TableColumn<Klant, Integer> colKlantNr;
    @FXML
    TableColumn<Klant, String> colVolledigeNaam;
    @FXML
    TableColumn<Klant, String> colAdres;
    @FXML
    TableColumn<Klant, String> colPostcode;
    @FXML
    TableColumn<Klant, String> colWoonplaats;

    public static Klant selectedklant = null;
    public static boolean selectedAddKlant = false;

    @FXML private void initialize() {
        initCol();
        loadData();
    }

    private void initCol() {
        colKlantNr.setCellValueFactory(new PropertyValueFactory<>("klantId"));
        colVolledigeNaam.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colAdres.setCellValueFactory(new PropertyValueFactory<>("adres"));
        colPostcode.setCellValueFactory(new PropertyValueFactory<>("postcode"));
        colWoonplaats.setCellValueFactory(new PropertyValueFactory<>("woonplaats"));
    }
    public static void loadDataOnStartUp() {
        klantLijst.clear();
        DatabaseHandler handler = DatabaseHandler.getInstance();
        String query = "SELECT * FROM klant";
        ResultSet results = handler.executeQuery(query);
        try{
            while (results.next()) {
                int klantId = results.getInt("klantId");
                String naam = results.getString("fullName");
                String adres = results.getString("adres");
                String postcode = results.getString("postcode");
                String woonplaats = results.getString("woonplaats");
                Klant klant = new Klant(klantId,naam,adres,postcode,woonplaats);
                klantLijst.add(klant);

            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    private void loadData() {
        klantLijst.clear();
        DatabaseHandler handler = DatabaseHandler.getInstance();
        String query = "SELECT * FROM klant";
        ResultSet results = handler.executeQuery(query);
        try{
            while (results.next()) {
                int klantId = results.getInt("klantId");
                String naam = results.getString("fullName");
                String adres = results.getString("adres");
                String postcode = results.getString("postcode");
                String woonplaats = results.getString("woonplaats");
                Klant klant = new Klant(klantId,naam,adres,postcode,woonplaats);
                klantLijst.add(klant);

            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        tableViewKlant.setItems(klantLijst);
    }
    public static List<Klant> getKlanten() {
        return klantLijst;
    }


    @FXML
    private void onUpdateKlant() {
        if (tableViewKlant.getSelectionModel().isEmpty()) {
            AlertFactory.showSimpleAlert("Kies klant", "Geen klant geselecteerd");
        } else {
            selectedklant = tableViewKlant.getSelectionModel().getSelectedItem();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.initOwner(klantStackPane.getScene().getWindow());
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("views/klanten" +
                    "/klanten_view_dialog.fxml"));
            try {
                dialog.getDialogPane().setContent(fxmlLoader.load());

            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                KlantViewDialogController controller = fxmlLoader.getController();
                Klant klant = controller.getUpdatedKlant();
                Boolean rs = Datahelpers.updateKlant(klant);
                if(rs) {
                    AlertFactory.showSimpleAlert("Klantgegevens aangepast",
                            "Klant " +klant.getKlantId() +
                                    " is " +
                                    "succesvol aangepast");
                    loadData();
                    System.out.println(rs);
                }

            } else {
                System.out.println("Cancel gedrukt");
            }
        }
    }
    @FXML
    private void onDeleteKlant() {
        if(tableViewKlant.getSelectionModel().isEmpty()) {
            AlertFactory.showSimpleAlert("Kies klant in de lijst","Geen klant geselecteerd");
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Verwijderen klant");
            alert.setContentText("Weet je zeker dat je deze klant wilt verwijderen?");
            Optional<ButtonType> resultalert = alert.showAndWait();
            if(resultalert.isPresent() && resultalert.get() == ButtonType.OK) {
                Klant klant = tableViewKlant.getSelectionModel().getSelectedItem();
                Boolean result = Datahelpers.deleteKlant(klant);
                if (result) {
                    AlertFactory.showSimpleAlert("Klant verwijderd",
                            "Klant " +klant.getKlantId() +
                                    " is " +
                                    "succesvol verwijderd");
                    loadData();
                } else {
                    System.out.println("Er is iets fouts gegaan");
                }
            }
        }
    }
    @FXML
    private void onAddKlant() {
            selectedAddKlant = true;
            Dialog<ButtonType> dialog = new Dialog<>();
//            dialog.setHeaderText("Toevoegen nieuwe klant");
            dialog.initOwner(klantStackPane.getScene().getWindow());
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("views/klanten" +
                    "/klanten_view_dialog.fxml"));
            try {
                dialog.getDialogPane().setContent(fxmlLoader.load());

            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                KlantViewDialogController controller = fxmlLoader.getController();
                Klant klant = controller.getKlant();
                Boolean rs = Datahelpers.addKlant(klant);
                if(rs) {
                    AlertFactory.showSimpleAlert("Nieuwe klant toegevoegd",
                            "Klant " +
                                    " is " +
                                    "succesvol toegevoegd");
                    selectedAddKlant = false;
                    loadData();
                }
            } else if(result.isPresent() && result.get() == ButtonType.CANCEL) {
                selectedAddKlant = false;
                System.out.println("Cancel gedrukt");
            }
        }

}
