package com.banerdygadgets.controllers.retouren;

import com.banerdygadgets.model.RetourOrder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.time.LocalDate;


public class RetourOrderDialogController {
    private ObservableList<String> statusList = FXCollections.observableArrayList("Aangemeld","In behandeling",
            "Goedgekeurd",
            "Afgekeurd");
    @FXML private ChoiceBox statusChoiceBox;
    @FXML private TextField redenField;
    @FXML private TextField bestelnummerField;
    @FXML private DatePicker datePicker;
    @FXML private TextField klantNummerField;



    @FXML private void initialize() throws IOException {
            statusChoiceBox.setItems(statusList);
            statusChoiceBox.getSelectionModel().selectFirst();

    }

    public RetourOrder getRetourOrder() {
        LocalDate datum = datePicker.getValue();
        String status = (String) statusChoiceBox.getValue();
        String reden = redenField.getText().trim();
        int bestelId = Integer.parseInt(bestelnummerField.getText().trim());
        int klantNr = Integer.parseInt(klantNummerField.getText().trim());


        RetourOrder retourOrder = new RetourOrder(datum,status,reden,bestelId,klantNr);

        return retourOrder;
    }


}
