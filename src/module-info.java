module BANerdyGadgets {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;
    opens   com.banerdygadgets;
    opens com.banerdygadgets.model;
    opens  com.banerdygadgets.controllers;
    opens com.banerdygadgets.views;
    opens com.banerdygadgets.helpers;



}