package com.banerdygadgets.helpers;

import com.banerdygadgets.model.Bestelling;
import com.banerdygadgets.model.Geolocation;
import com.banerdygadgets.model.Klant;
import com.banerdygadgets.model.RetourOrder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;



public class RouteHelpers {
    private static RouteHelpers instance;
    private StringBuilder html;

    public RouteHelpers() {
        instance = this;
    }
    public static RouteHelpers getInstance() {
        return instance;
    }
    //Een methode om klantorders te filteren die een verzendklaar status hebben
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
                }
            }
        }
        return verzendLijst;
    }
    //Methode om retour orders te filteren met een aangemeldstatus (=moet opgehaald worden)
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
                }
            }
        }
        return verzendLijst;
    }

    public StringBuilder htmlStringBuilder(ObservableList<Geolocation> route) {
        String src = "https://www.google.nl/maps/embed/v1/directions?key" +
                "=AIzaSyBcxeIz1lk8mFWHnhm466ZaUF1vQGWwFeQ";
        html = new StringBuilder();
        html.append("<!DOCTYPE html>");
        html.append("<html lang=\"en\">");
        html.append("<html>");
        html.append("<meta charset=\"UTF-8\">");
        html.append("<title>Routepagina</title>");
        html.append("</head>");
        html.append("<body>");
        html.append("<iframe width=\"800\" height=\"600\" frameborder=\"0\" style=\"border: 0;\" " +
                "src=\"" + src);
        html.append("&origin="+route.get(0).returnPostcode());
        html.append("&destination=" + route.get(0).returnPostcode());
        html.append("&waypoints=");
        return html;
    }


}
