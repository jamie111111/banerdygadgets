package com.banerdygadgets;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.*;

public class Main extends Application {
    private static PreparedStatement insertRetour;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("views/mainwindow.fxml"));
        primaryStage.setTitle("Backoffice app");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();


    }


    public static void main(String[] args) {

        launch(args);


    }
}
