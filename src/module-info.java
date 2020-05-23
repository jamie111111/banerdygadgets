module BANerdyGadgets {
    requires junit;
    requires javafx.controls;
    requires java.sql;
    requires javafx.fxml;
    requires gson;
    requires org.apache.pdfbox;
    requires itextpdf;
    opens com.banerdygadgets;

    opens com.banerdygadgets.model;
    opens com.banerdygadgets.model.algofeedback;
    opens com.banerdygadgets.controllers;
    opens com.banerdygadgets.controllers.navigation;
    opens com.banerdygadgets.controllers.retouren;
    opens com.banerdygadgets.controllers.bestellingen;
    opens com.banerdygadgets.controllers.klanten;
    opens com.banerdygadgets.controllers.route;


    opens com.banerdygadgets.views;
    opens com.banerdygadgets.views.bestellingen;
    opens com.banerdygadgets.views.navigation;
    opens com.banerdygadgets.views.retouren;
    opens com.banerdygadgets.views.routing;
    opens com.banerdygadgets.views.klanten;


    opens com.banerdygadgets.helpers;



}