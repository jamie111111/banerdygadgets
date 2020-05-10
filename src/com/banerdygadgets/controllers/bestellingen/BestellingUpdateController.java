package com.banerdygadgets.controllers.bestellingen;

import com.banerdygadgets.model.Bestelling;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class BestellingUpdateController {
    private ObservableList<String> statusList = FXCollections.observableArrayList("In behandeling",
            "Verzendklaar",
            "Backorder");
    @FXML private TextField bestelnrField;
    @FXML private ChoiceBox statusChoiceBox;
    @FXML private TextField klantnrField;
    @FXML private DatePicker datePicker;
    private Bestelling selectedBestelling;

    @FXML private void initialize() {
        statusChoiceBox.setItems(statusList);

        if(BestellingenController.selectedBestelling != null) {
            selectedBestelling = BestellingenController.selectedBestelling;
            setBestellingFields(selectedBestelling.getStringBestellingId(),
                    selectedBestelling.getStringKlantId(),
                    selectedBestelling.getDatum(),
                    selectedBestelling.getStatus());
        }
    }
    public Bestelling getBestelling() {
            int bestelnr = Integer.parseInt(bestelnrField.getText().trim());
            int klantNr = Integer.parseInt(klantnrField.getText().trim());
            LocalDate datum = datePicker.getValue();
            String status = (String) statusChoiceBox.getValue();

            Bestelling bestelling = new Bestelling(bestelnr,klantNr,datum,status);

            return bestelling;
    }

    public void setBestellingFields(String bestelNummer,String klantNr ,LocalDate date,
                                    String status) {
        bestelnrField.setText(bestelNummer);
        datePicker.setValue(date);

        statusChoiceBox.setValue(status);
        klantnrField.setText(klantNr);
    }

}
