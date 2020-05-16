package com.banerdygadgets.helpers;

import com.banerdygadgets.model.Bestelling;
import com.banerdygadgets.model.Klant;
import com.banerdygadgets.model.RetourOrder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class RouteHelpers {
    public ObservableList<Klant> getKlantBestellingWithVerzendKlaarStatus(
                                                                List<Klant> klantLijst,
                                                                List<Bestelling> verzendKlaarLijst) {
        int klantLijstId;
        int klantVerzendLijstId;
        ObservableList<Klant> verzendLijst = FXCollections.observableArrayList();
        for(Bestelling bestelling:verzendKlaarLijst) {
            klantVerzendLijstId = bestelling.getKlantId();
            for(Klant klant:klantLijst) {
                klantLijstId = klant.getKlantId();
                if(klantLijstId == klantVerzendLijstId) {
                    verzendLijst.add(klant);
                    System.out.println("verzendLijst: " + verzendLijst);
                }
            }
        }
        return verzendLijst;
    }
    public ObservableList<Klant> getRetourOrderWithAangemeldStatus(
            List<Klant> klantLijst,
            List<RetourOrder> retourOrderAangemeldLijst) {
        int klantLijstId;
        int klantVerzendLijstId;
        ObservableList<Klant> verzendLijst = FXCollections.observableArrayList();
        for(RetourOrder retourOrder : retourOrderAangemeldLijst) {
            klantVerzendLijstId = retourOrder.getKlantNummer();
            for(Klant klant:klantLijst) {
                klantLijstId = klant.getKlantId();
                if(klantLijstId == klantVerzendLijstId) {
                    verzendLijst.add(klant);
                    System.out.println("RetourLijst: " + verzendLijst);
                }
            }
        }
        return verzendLijst;
    }


}
