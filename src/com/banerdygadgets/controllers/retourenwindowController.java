package com.banerdygadgets.controllers;

import com.banerdygadgets.Main;
import com.banerdygadgets.helpers.AlertFactory;
import com.banerdygadgets.model.DatabaseHandler;
import com.banerdygadgets.model.DatabaseStringQueries;
import com.banerdygadgets.model.RetourOrder;
import com.banerdygadgets.model.Datahelpers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class retourenwindowController implements Initializable {
    DatabaseHandler handler;
    @FXML
    StackPane retourStackPane;
    @FXML
    TextField retourNummerField;
    @FXML
    TextField statusField;
    @FXML
    TextField redenField;
    @FXML
    TextField bestelNummerField;
    @FXML
    DatePicker datePicker;

    @FXML
    private TableView<RetourOrder> tableviewRetouren;
    @FXML
    private TableColumn<RetourOrder, Integer> colRetourNr;
    @FXML
    private TableColumn<RetourOrder, Integer> colBestelNr;
    @FXML
    private TableColumn<RetourOrder, LocalDate> colDatum;
    @FXML
    private TableColumn<RetourOrder, String> colReden;
    @FXML
    private TableColumn<RetourOrder, String> colStatus;
    @FXML
    private ContextMenu tableViewContextMenu;

    @FXML
    private Button deleteBtnRetourOrder;

    private ObservableList<RetourOrder> retourData = FXCollections.observableArrayList();
    Datahelpers databaseHelper;

    public static RetourOrder selectedRetourOrder = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            //Haal database instantie op, maak query, haal resultsset op
            handler = DatabaseHandler.getInstance();
                String query = "SELECT * FROM retourorder";
                ResultSet rs = handler.executeQuery(query);
                while (rs.next()) {
                    String date = rs.getString("datumAanmelding");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate formattedDate = LocalDate.parse(date, formatter);

                    retourData.add(new RetourOrder(rs.getInt("id"), formattedDate
                            , rs.getString("reden"), rs.getString("status"),
                            rs.getInt("bestelId")));

                    tableViewContextMenu = new ContextMenu();
                    MenuItem updateMenuItem = new MenuItem("Wijzig gegevens");
                    updateMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            RetourOrder retourOrder = tableviewRetouren.getSelectionModel().getSelectedItem();
                        }
                    });

                    colRetourNr.setCellValueFactory(new PropertyValueFactory<>("retourNummer"));
                    colBestelNr.setCellValueFactory(new PropertyValueFactory<>("bestelNummer"));
                    colDatum.setCellValueFactory(new PropertyValueFactory<>("datumAanmelding"));
                    colReden.setCellValueFactory(new PropertyValueFactory<>("reden"));
                    colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
                    tableviewRetouren.setItems(retourData);
                }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }




    @FXML
    public void showRetourDialog() throws SQLException {
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
            RetourItemDialogController controller = fxmlLoader.getController();
            //Haal ingevoerde retourorder op van in invoervelden
            RetourOrder addedRetourOrder = controller.getRetourOrder();
            Datahelpers.addRetourItem(addedRetourOrder);
            System.out.println("item toegevoegd");
        } else {
            System.out.println("Cancel gedrukt");
        }
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
    private void onDeleteRetourOrder(ActionEvent event) {
        if(tableviewRetouren.getSelectionModel().isEmpty()) {
            AlertFactory.showSimpleAlert("Kies retourorder","Geen retourorder geselecteerd");
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Verwijderen retourorder");
            alert.setContentText("Weet je zeker dat je de retourorder wilt verwijderen?");
            Optional<ButtonType> resultalert = alert.showAndWait();
            if(resultalert.isPresent() && resultalert.get() == ButtonType.OK) {
                RetourOrder retourOrder = tableviewRetouren.getSelectionModel().getSelectedItem();
                databaseHelper = new Datahelpers();
                Boolean result = databaseHelper.deleteRetourItem(retourOrder);
                if (result) {
                    tableviewRetouren.refresh();
                    AlertFactory.showSimpleAlert("Retourorder verwijderd",
                            "Retourorder " +retourOrder.getRetourNummer() +
                                    " is " +
                                    "succesvol verwijderd");

                    System.out.println("retourorder verwijderd");
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
                update_retour_item_controller controller = fxmlLoader.getController();
                //Haal ingevoerde retourorder op van in invoervelden
                RetourOrder updatedRetourOrder = controller.getRetourOrder();
                Datahelpers.updateRetourItem(updatedRetourOrder);

            } else {
                System.out.println("Cancel gedrukt");
            }
        }


    }

    private  RetourOrder getSelectedRetourOrder() {
        if(tableviewRetouren.getSelectionModel().isEmpty()) {
            return null;
        }else {
            RetourOrder selectedRetourOrder = tableviewRetouren.getSelectionModel().getSelectedItem();
            return selectedRetourOrder;
        }
    }


}

//    public ObservableList<RetourOrder> getRetourOrders() {
//        ObservableList<RetourOrder> retourData = FXCollections.observableArrayList();
//        RetourOrder order1 = new RetourOrder(1, LocalDate.of(20, 4, 28), "Aangemeld", "Garantie", 10);
//        RetourOrder order2 = new RetourOrder(3, LocalDate.of(20, 4, 29), "Aangemeld", "Garantie", 20);
//        RetourOrder order3 = new RetourOrder(5, LocalDate.of(20, 4, 30), "Aangemeld", "Garantie", 30);
//        RetourOrder order4 = new RetourOrder(7, LocalDate.of(20, 5, 2), "Aangemeld", "Garantie", 40);
//        RetourOrder order5 = new RetourOrder(9, LocalDate.of(20, 5, 5), "Aangemeld", "Garantie", 20);
//
//        retourData.add(order1);
//        retourData.add(order2);
//        retourData.add(order3);
//        retourData.add(order4);
//        retourData.add(order5);
//        return retourData;
//    }