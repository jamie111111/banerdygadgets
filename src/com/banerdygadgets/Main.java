package com.banerdygadgets;

import com.banerdygadgets.controllers.bestellingen.BestellingenController;
import com.banerdygadgets.controllers.klanten.KlantenViewController;
import com.banerdygadgets.controllers.retouren.RetourenWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("views/main_window.fxml"));
        primaryStage.setTitle("Backoffice app");
        primaryStage.setScene(new Scene(root, 1300, 670));
        primaryStage.show();

    }
    public static void main(String[] args) {
        launch(args);
    }
}
