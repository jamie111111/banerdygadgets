package com.banerdygadgets.controllers.route;

import com.banerdygadgets.Main;
import com.banerdygadgets.controllers.bestellingen.BestellingenController;
import com.banerdygadgets.controllers.retouren.RetourenWindowController;
import com.banerdygadgets.helpers.RouteHelpers;
import com.banerdygadgets.helpers.Verzendlijst;
import com.banerdygadgets.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class RouteWindowController {
    private static ObservableList<Klant> verzendLijst = FXCollections.observableArrayList();
    private static PostcodeRange postcodeRange;

    @FXML private StackPane routeWindowPane;
    @FXML private TableView<Klant> verzendlijstTableView;
    @FXML private TableColumn<Klant,Integer> klantNrField;
    @FXML private TableColumn<Klant,String> naamField;
    @FXML private TableColumn<Klant,String> adresField;
    @FXML private TableColumn<Klant,String> postcodeField;
    @FXML private TableColumn<Klant,String> woonplaatsField;



    @FXML private void initialize() {
        initCol();
       loadData();
    }
    private void initCol() {
        klantNrField.setCellValueFactory(new PropertyValueFactory<>("klantId"));
        naamField.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        adresField.setCellValueFactory(new PropertyValueFactory<>("adres"));
        postcodeField.setCellValueFactory(new PropertyValueFactory<>("postcode"));
        woonplaatsField.setCellValueFactory(new PropertyValueFactory<>("woonplaats"));
    }
    private void loadData() {
        initCol();
        if(verzendLijst != null) {
            verzendlijstTableView.setItems(verzendLijst);
        }else {
            return;
        }
    }


    @FXML private void getOrdersWithReadyStatus() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(routeWindowPane.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("views/routing/route_verzendlijst_dialog" +
                ".fxml"));
        try{
            dialog.getDialogPane().setContent(fxmlLoader.load());
        }catch (IOException e) {
            e.printStackTrace();
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK) {
            RouteWindowDialogController controller = fxmlLoader.getController();
            verzendLijst.clear();
            // Bepaal postcodegebied voor de verzendlijst
            postcodeRange = controller.getPostcodeRange();
            //Haal klanten op in het vastgestelde postcodegebied
            List<Klant> klantLijst =
                    Datahelpers.getSelectedKlantenWithPostcodeRange(postcodeRange.getPostcodeVan()
                            ,postcodeRange.getPostcodeTot());
            //Haal bestellingen op met verzendklaar status
            List<Bestelling> verzendKlaarlijstBestellingen =
                    BestellingenController.getReadyBestellingen();
            //Haal klanten op uit de bestellingen met verzendklaar status
            RouteHelpers routeHelpers = new RouteHelpers();
            ObservableList<Klant> fetchedList = FXCollections.observableArrayList();
             fetchedList = routeHelpers.getKlantBestellingWithVerzendKlaarStatus(klantLijst,
                    verzendKlaarlijstBestellingen);
             for(Klant klant:fetchedList) {
                 verzendLijst.add(klant);
             }
            //Haal retourorders op met status aangemeld
            List<RetourOrder> retourOrdersAangemeld =
                    RetourenWindowController.getReadyForDispatchRetourOrders();
             fetchedList = routeHelpers.getRetourOrderWithAangemeldStatus(klantLijst,
                     retourOrdersAangemeld);
            for(Klant klant:fetchedList) {
                verzendLijst.add(klant);
            }
            //Laad klanten in het scherm
            loadData();
        }else {
            System.out.println("Er is iets fout gegaan");
        }
    }
    @FXML
    public void createDispatchList() {
        Verzendlijst lijst = new Verzendlijst();
        lijst.writeList(verzendLijst);
    }

}