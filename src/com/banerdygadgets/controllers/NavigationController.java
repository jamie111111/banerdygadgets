package com.banerdygadgets.controllers;
import com.banerdygadgets.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@SuppressWarnings("SpellCheckingInspection")
public class NavigationController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    public void navigateToBestellingen(ActionEvent event) {
        setWindow("views/bestellingenwindow.fxml", event);
    }
    @FXML
    public void navigateToHome(ActionEvent event) {
        setWindow("views/homewindow.fxml", event);
    }
    @FXML
    public void navigateToRoute(ActionEvent event) {
        setWindow("views/routewindow.fxml", event);
    }

    @FXML
    public void navigateToRetouren(ActionEvent event) {
        setWindow("views/retourenwindow.fxml", event);
    }


    private void setWindow(String url, ActionEvent event) {
        try {
            Parent newView = FXMLLoader.load(Main.class.getResource(url));

            Scene newScene = new Scene(newView,900,600);

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(newScene);
            window.show();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
