package com.banerdygadgets.controllers.klanten;

import com.banerdygadgets.model.Klant;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class KlantViewDialogController {

    @FXML TextField klantnrField;
    @FXML TextField fullNameField;
    @FXML TextField adresField;
    @FXML TextField postcodeField;
    @FXML TextField woonPlaatsField;
    @FXML Label klantnrLabel;
    @FXML Label headerTitel;
    @FXML
    GridPane gridPane;



    @FXML private void initialize() {
        if(KlantenViewController.selectedklant != null && KlantenViewController.selectedAddKlant
                == false) {
            Klant klant = KlantenViewController.selectedklant;
            setKlantenFields(klant.getKlantId(),klant.getFullName(),klant.getAdres(),
                    klant.getPostcode(),klant.getWoonplaats());
        setUpdateHeaderTitel();
        }else {
            setAddHeaderTitel();
        }


    }

    private void setKlantenFields(int klantnr,String fullName,String adres,String postcode,
                             String woonplaats) {
        klantnrField.setText(Integer.toString(klantnr));
        fullNameField.setText(fullName);
        adresField.setText(adres);
        postcodeField.setText(postcode);
        woonPlaatsField.setText(woonplaats);
    }

    public Klant getKlant() {
        String naam = fullNameField.getText();
        String adres = adresField.getText();
        String postcode = postcodeField.getText().trim();
        String woonplaats = woonPlaatsField.getText();

        Klant klant = new Klant(naam,adres,postcode,woonplaats);
        return klant;
    }
    public Klant getUpdatedKlant() {
        int klantnr = Integer.parseInt(klantnrField.getText());
        String naam = fullNameField.getText();
        String adres = adresField.getText();
        String postcode = postcodeField.getText().trim();
        String woonplaats = woonPlaatsField.getText();

        Klant klant = new Klant(klantnr,naam,adres,postcode,woonplaats);
        return klant;
    }


    private void setAddHeaderTitel() {
        headerTitel.setText("Voeg een nieuwe klant toe");
        klantnrField.setStyle("visibility:hidden");
        klantnrLabel.setStyle("visibility:hidden");

    }

    private void setUpdateHeaderTitel() {
        headerTitel.setText("Aanpassen klantgegevens");
        klantnrField.setStyle("visibility:visible");
        klantnrLabel.setStyle("visibility:visible");
        klantnrField.setDisable(true);
    }
}
