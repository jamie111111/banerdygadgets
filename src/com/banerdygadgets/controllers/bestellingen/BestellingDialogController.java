package com.banerdygadgets.controllers.bestellingen;

import com.banerdygadgets.model.Bestelling;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class BestellingDialogController {
    private ObservableList<String> statusList = FXCollections.observableArrayList("Kies " +
                    "status","Verzendklaar",
            "In behandeling",
            "Backorder");
    @FXML private ChoiceBox statusChoiceBox;
    @FXML private DatePicker datePicker;
    @FXML private TextField klantnrField;

    @FXML private void initialize() {
        statusChoiceBox.setItems(statusList);
        statusChoiceBox.getSelectionModel().selectFirst();
    }

    public Bestelling getBestelling() {
        LocalDate datum = datePicker.getValue();
        String status = (String) statusChoiceBox.getValue();
        int klantNr = Integer.parseInt(klantnrField.getText().trim());
        Bestelling bestelling = new Bestelling(klantNr,datum,status);
        return bestelling;
    }
}
