package com.banerdygadgets.controllers.retouren;

import com.banerdygadgets.Main;
import com.banerdygadgets.helpers.AlertFactory;
import com.banerdygadgets.model.DatabaseHandler;
import com.banerdygadgets.model.Datahelpers;
import com.banerdygadgets.model.RetourOrder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

public class RetourenWindowController  {
    private static ObservableList<RetourOrder> retourLijst = FXCollections.observableArrayList();
    private static RetourenWindowController instance;
    @FXML StackPane retourStackPane;
    @FXML private TableView<RetourOrder> tableviewRetouren;
    @FXML private TableColumn<RetourOrder, Integer> colRetourNr;
    @FXML private TableColumn<RetourOrder, Integer> colBestelNr;
    @FXML private TableColumn<RetourOrder, LocalDate> colDatum;
    @FXML private TableColumn<RetourOrder, String> colReden;
    @FXML private TableColumn<RetourOrder, String> colStatus;
    @FXML private TableColumn<RetourOrder, Integer> colKlantNr;
    @FXML private ContextMenu tableViewContextMenu;

    public static RetourOrder selectedRetourOrder = null;

    public RetourenWindowController() {

        instance = this;
    }
    public static RetourenWindowController getInstance() {
        if(instance == null) {
            instance = new RetourenWindowController();
        }
        return instance;
    }


    public void initialize() {
        initCol();
        loadData();

    }

    private void initCol() {
        colRetourNr.setCellValueFactory(new PropertyValueFactory<>("retourNummer"));
        colBestelNr.setCellValueFactory(new PropertyValueFactory<>("bestelNummer"));
        colDatum.setCellValueFactory(new PropertyValueFactory<>("datumAanmelding"));
        colReden.setCellValueFactory(new PropertyValueFactory<>("reden"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colKlantNr.setCellValueFactory(new PropertyValueFactory<>("klantNummer"));
    }
    public void loadRetourData() {
        retourLijst = Datahelpers.loadRetouren();
    }
    private void loadData() {
        retourLijst.clear();
        try {
            //Haal database instantie op, maak query, haal resultsset op
            DatabaseHandler handler = DatabaseHandler.getInstance();
            String query = "SELECT * FROM retourorder";
            ResultSet rs = handler.executeQuery(query);

            while (rs.next()) {
                String date = rs.getString("datumAanmelding");
                LocalDate formattedDate = Datahelpers.parseDate(date);
                int retourNr = rs.getInt("id");
                String reden = rs.getString("reden");
                String status = rs.getString("status");
                int bestelnr = rs.getInt("bestelId");
                int klantnr = rs.getInt("klantId");
                RetourOrder retourOrder = new RetourOrder(retourNr, formattedDate,status,reden,
                        bestelnr,klantnr);
               retourLijst.add(retourOrder);

                tableViewContextMenu = new ContextMenu();
                MenuItem updateMenuItem = new MenuItem("Wijzig gegevens");
                updateMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        RetourOrder retourOrder = tableviewRetouren.getSelectionModel().getSelectedItem();
                    }
                });
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        tableviewRetouren.setItems(retourLijst);
    }



    @FXML
    public void onAddRetourOrder() throws SQLException {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(retourStackPane.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("views/retouren/add_retour_item_dialog.fxml"));
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
            //Haal controller van Dialog op
            RetourOrderDialogController controller = fxmlLoader.getController();
            //Haal ingevoerde retourorder op van in invoervelden
            RetourOrder addedRetourOrder = controller.getRetourOrder();
            Datahelpers.addRetourItem(addedRetourOrder);
        } else {
            System.out.println("Er is iets fout gegaan");
        }
        loadData();
    }

    @FXML
    private void handleStatusOption(ActionEvent event) {
        //Haal geselecteerde rij op
        RetourOrder selectedForNewStatus = tableviewRetouren.getSelectionModel().getSelectedItem();
        //Fout afhandelen als er op een leegveld geselecteerd
        if (selectedForNewStatus == null) {
            AlertFactory.showSimpleAlert("Kies order", "Kies order");
        }
    }

    @FXML
    private void onDeleteRetourOrder() {
        if(tableviewRetouren.getSelectionModel().isEmpty()) {
            AlertFactory.showSimpleAlert("Kies retourorder","Geen retourorder geselecteerd");
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Verwijderen retourorder");
            alert.setContentText("Weet je zeker dat je de retourorder wilt verwijderen?");
            Optional<ButtonType> resultalert = alert.showAndWait();
            if(resultalert.isPresent() && resultalert.get() == ButtonType.OK) {
                RetourOrder retourOrder = tableviewRetouren.getSelectionModel().getSelectedItem();
                Boolean result = Datahelpers.deleteRetourItem(retourOrder);
                if (result) {
                    AlertFactory.showSimpleAlert("Retourorder verwijderd",
                            "Retourorder " +retourOrder.getRetourNummer() +
                                    " is " +
                                    "succesvol verwijderd");
                    System.out.println("retourorder verwijderd");
                    loadData();
                } else {
                    System.out.println("Er is iets fouts gegaan");
                }
            }
        }
    }

    @FXML
    public void updateRetourOrder() throws SQLException {
        if(tableviewRetouren.getSelectionModel().isEmpty()) {
            AlertFactory.showSimpleAlert("Kies retourorder", "Geen retourorder geselecteerd");
        }else {
            selectedRetourOrder = getSelectedRetourOrder();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.initOwner(retourStackPane.getScene().getWindow());
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("views/retouren" +
                    "/update_retour_item_dialog" +
                    ".fxml"));
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
                //Haal controller van Dialog op
                RetourOrderUpdateController controller = fxmlLoader.getController();
                //Haal ingevoerde retourorder op van in invoervelden
                RetourOrder updatedRetourOrder = controller.getRetourOrder();
                Boolean rs =Datahelpers.updateRetourItem(updatedRetourOrder);
                if(rs) {
                    AlertFactory.showSimpleAlert("Retourorder aangepast",
                            "Retourorder " +updatedRetourOrder.getRetourNummer() +
                                    " is " +
                                    "succesvol aangepast");
                }
                loadData();
            } else {
                System.out.println("Cancel gedrukt");
            }
        }
    }

    @FXML
    private void onRefresh() {
        loadData();
        System.out.println("Data loaded");
    }

    private  RetourOrder getSelectedRetourOrder() {
        if(tableviewRetouren.getSelectionModel().isEmpty()) {
            return null;
        }else {
            RetourOrder selectedRetourOrder = tableviewRetouren.getSelectionModel().getSelectedItem();
            return selectedRetourOrder;
        }
    }
    public List<RetourOrder> getReadyForDispatchRetourOrders() {
        List<RetourOrder> retourOrders = new ArrayList<>();
        for (RetourOrder retourOrder : retourLijst) {
            if(retourOrder.getStatus().equals("Aangemeld")) {
                retourOrders.add(retourOrder);
            }
        }
        return retourOrders;
    }
}




