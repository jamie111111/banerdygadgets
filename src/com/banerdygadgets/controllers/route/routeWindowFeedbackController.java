package com.banerdygadgets.controllers.route;

import com.banerdygadgets.model.RouteAlgoFeedback;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class routeWindowFeedbackController {
    @FXML TableView<RouteAlgoFeedback> algoFeedbackTable;
    @FXML TableColumn<RouteAlgoFeedback,String> routeField;
    @FXML TableColumn<RouteAlgoFeedback,String> afstandField;
    @FXML TableColumn<RouteAlgoFeedback,String> kansFunctieField;
    @FXML TableColumn<RouteAlgoFeedback,String> randomGetalField;
    @FXML TableColumn<RouteAlgoFeedback,String> besluitField;

    @FXML private void test(){

    }
}

