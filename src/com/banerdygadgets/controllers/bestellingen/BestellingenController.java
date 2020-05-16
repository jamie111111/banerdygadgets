package com.banerdygadgets.controllers.bestellingen;

import com.banerdygadgets.Main;
import com.banerdygadgets.helpers.AlertFactory;
import com.banerdygadgets.model.Bestelling;
import com.banerdygadgets.model.DatabaseHandler;
import com.banerdygadgets.model.Datahelpers;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class BestellingenController {
    private static ObservableList<Bestelling> bestelLijst = FXCollections.observableArrayList();

    @FXML
    StackPane bestellingStackpane;

    @FXML
    TableView<Bestelling> tableViewBestelling;
    @FXML
    TableColumn<Bestelling, Integer> colBestelNr;
    @FXML
    TableColumn<Bestelling, Integer> colKlantNr;
    @FXML
    TableColumn<Bestelling, String> colDatum;
    @FXML
    TableColumn<Bestelling, String> colStatus;

    public static Bestelling selectedBestelling = null;

    public static List<Bestelling> getReadyBestellingen() {
        List<Bestelling> verzendklaarLijst = new ArrayList<>();
        for (Bestelling bestelling:bestelLijst) {
            if(bestelling.getStatus().equals("Verzendklaar")) {
                verzendklaarLijst.add(bestelling);
            }
        }
        return verzendklaarLijst;
    }

    @FXML private void initialize() {
        initCol();
        loadData();
    }

    private void initCol() {
        colBestelNr.setCellValueFactory(new PropertyValueFactory<>("bestellingId"));
        colKlantNr.setCellValueFactory(new PropertyValueFactory<>("klantId"));
        colDatum.setCellValueFactory(new PropertyValueFactory<>("datum"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }
    private void loadData() {
        bestelLijst.clear();
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
        tableViewBestelling.setItems(bestelLijst);
    }

    @FXML private void onAddBestelling() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(bestellingStackpane.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("views/bestellingen" +
                "/add_bestelling_item_dialog.fxml"));
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
            BestellingDialogController controller = fxmlLoader.getController();
            Bestelling bestelling = controller.getBestelling();
            Boolean rs = Datahelpers.addBestelling(bestelling);
            if(rs) {
                AlertFactory.showSimpleAlert("Bestelling toegevoegd",
                        "Nieuwer bestelling "  +
                                " is " +
                                "succesvol toegevoegd");
                loadData();
            }

        } else {
            System.out.println("Cancel gedrukt");
        }
    }
    @FXML private void onDeleteBestelling() {
        if(tableViewBestelling.getSelectionModel().isEmpty()) {
            AlertFactory.showSimpleAlert("Kies bestelling","Geen bestelling geselecteerd");
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Verwijderen bestelling");
            alert.setContentText("Weet je zeker dat je de bestelling wilt verwijderen?");
            Optional<ButtonType> resultalert = alert.showAndWait();
            if(resultalert.isPresent() && resultalert.get() == ButtonType.OK) {
                Bestelling bestelling = tableViewBestelling.getSelectionModel().getSelectedItem();
                Boolean result = Datahelpers.deleteBestelling(bestelling);
                if (result) {
                    AlertFactory.showSimpleAlert("Bestelling verwijderd",
                            "Bestelling " +bestelling.getBestellingId() +
                                    " is " +
                                    "succesvol verwijderd");
                    loadData();
                } else {
                    System.out.println("Er is iets fouts gegaan");
                }
            }
        }
    }
    @FXML private void onRefreshBestellingen() {
        loadData();
    }
    @FXML private void onUpdateBestelling() {
        if (tableViewBestelling.getSelectionModel().isEmpty()) {
            AlertFactory.showSimpleAlert("Kies bestelling", "Geen bestelling geselecteerd");
        } else {
            selectedBestelling = getSelectedBestelling();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.initOwner(bestellingStackpane.getScene().getWindow());
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("views/bestellingen" +
                    "/update_bestelling_item_dialog.fxml"));
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
                BestellingUpdateController controller = fxmlLoader.getController();
                Bestelling bestelling = controller.getBestelling();
                bestelling.toString();
               Boolean rs = Datahelpers.updateBestelling(bestelling);
               if(rs) {
                   AlertFactory.showSimpleAlert("Bestelling aangepast",
                           "Bestelling " +bestelling.getBestellingId() +
                                   " is " +
                                   "succesvol aangepast");
                   loadData();
               }

            } else {
                System.out.println("Cancel gedrukt");
            }
        }

    }
    private  Bestelling getSelectedBestelling() {
        if(tableViewBestelling.getSelectionModel().isEmpty()) {
            return null;
        }else {
            Bestelling selectedBestelling =
                    tableViewBestelling.getSelectionModel().getSelectedItem();
            return selectedBestelling;
        }
    }

}
