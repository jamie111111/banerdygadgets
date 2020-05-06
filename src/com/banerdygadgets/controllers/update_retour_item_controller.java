package com.banerdygadgets.controllers;

import com.banerdygadgets.helpers.NumberTextField;
import com.banerdygadgets.model.RetourOrder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class update_retour_item_controller {
    private ObservableList<String> statusList = FXCollections.observableArrayList("Aangemeld","In behandeling",
            "Goedgekeurd",
            "Afgekeurd");
    @FXML private TextField retournummerField;
    @FXML private ChoiceBox statusChoiceBox;
    @FXML private TextField redenField;
    @FXML private NumberTextField bestelnummerField;
    @FXML private DatePicker datePicker;
    private RetourOrder selectedOrder;

    @FXML private void initialize() {
        statusChoiceBox.setItems(statusList);

        if(retourenwindowController.selectedRetourOrder != null) {
            selectedOrder = retourenwindowController.selectedRetourOrder;
            setRetourOrderFields(selectedOrder.getStringRetourNummer(),selectedOrder.getDatumAanmelding(),
                    selectedOrder.getStatus(),
                    selectedOrder.getReden(),selectedOrder.getBestelNummer());
        }
    }
    public RetourOrder getRetourOrder() {
        LocalDate datum = datePicker.getValue();
        String status = (String) statusChoiceBox.getValue();
        String reden = redenField.getText().trim();
        int bestelId = Integer.parseInt(bestelnummerField.getText().trim());

        RetourOrder retourOrder = new RetourOrder(selectedOrder.getRetourNummer(),datum,status,reden,bestelId);
               return retourOrder;

    }

    public void setRetourOrderFields(String retourNummer ,LocalDate date,String reden,String status,
                                     int bestelnr) {
        retournummerField.setText(retourNummer);
        datePicker.setValue(date);
        redenField.setText(reden);
        statusChoiceBox.setValue(status);
        bestelnummerField.setText(Integer.toString(bestelnr));
    }

}
