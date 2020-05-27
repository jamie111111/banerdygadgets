package com.banerdygadgets.controllers.navigation;
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
        setWindow("views/bestellingen/bestellingen_window.fxml", event);
    }
    @FXML
    public void navigateLogin(ActionEvent event) {
        setWindow("views/login_window.fxml", event);
    }
    @FXML
    public void navigateToRoute(ActionEvent event) {
        setWindow("views/routing/route_window.fxml", event);
    }

    @FXML
    public void navigateToRetouren(ActionEvent event) {
        setWindow("views/retouren/retouren_window.fxml", event);
    }
    @FXML
    public void navigateToKlanten(ActionEvent event) {
        setWindow("views/klanten/klanten_view.fxml", event);
    }


    private void setWindow(String url, ActionEvent event) {
        try {
            Parent newView = FXMLLoader.load(Main.class.getResource(url));

            Scene newScene = new Scene(newView,1300,670);

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(newScene);
            window.show();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
