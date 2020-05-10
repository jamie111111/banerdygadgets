package com.banerdygadgets.controllers.retouren;

import com.banerdygadgets.helpers.NumberTextField;
import com.banerdygadgets.model.RetourOrder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class RetourOrderUpdateController {
    private ObservableList<String> statusList = FXCollections.observableArrayList("Aangemeld","In behandeling",
            "Goedgekeurd",
            "Afgekeurd");
    @FXML private TextField retournummerField;
    @FXML private ChoiceBox statusChoiceBox;
    @FXML private TextField redenField;
    @FXML private NumberTextField bestelnummerField;
    @FXML private NumberTextField klantnrField;
    @FXML private DatePicker datePicker;
    private RetourOrder selectedOrder;

    @FXML private void initialize() {
        statusChoiceBox.setItems(statusList);

        if(RetourenWindowController.selectedRetourOrder != null) {
            selectedOrder = RetourenWindowController.selectedRetourOrder;
            setRetourOrderFields(selectedOrder.getRetourNummer(),
                    selectedOrder.getBestelNummer(),
                    selectedOrder.getDatumAanmelding(),
                    selectedOrder.getStatus(),
                    selectedOrder.getReden(),
                    selectedOrder.getKlantNummer());
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

    public void setRetourOrderFields(int retourNummer,int bestelnr ,LocalDate date,String status,
                                     String reden,int klantnr
                                     ) {
        retournummerField.setText(Integer.toString(retourNummer));
        bestelnummerField.setText(Integer.toString(bestelnr));
        datePicker.setValue(date);
        statusChoiceBox.setValue(status);
        redenField.setText(reden);
        klantnrField.setText(Integer.toString(klantnr));

    }

}
