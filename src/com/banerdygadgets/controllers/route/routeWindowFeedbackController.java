package com.banerdygadgets.controllers.route;

import com.banerdygadgets.model.RouteAlgoFeedback;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class routeWindowFeedbackController {
    private  ObservableList<RouteAlgoFeedback> feedbackList =
            FXCollections.observableArrayList();

    @FXML
    private TableView<RouteAlgoFeedback> algoFeedbackTable;
    private  @FXML TableColumn<RouteAlgoFeedback, String> routeField;
    private  @FXML TableColumn<RouteAlgoFeedback, String> afstandField;
    private  @FXML TableColumn<RouteAlgoFeedback, String> tempField;
    private  @FXML TableColumn<RouteAlgoFeedback, String> kansFunctieField;
    private  @FXML TableColumn<RouteAlgoFeedback, String> randomGetalField;
    private  @FXML TableColumn<RouteAlgoFeedback, String> besluitField;
    private static routeWindowFeedbackController instance;

    @FXML private void initialize() {
    }
    public routeWindowFeedbackController() {
        instance = this;
    }
    public static routeWindowFeedbackController getInstance(){
        return instance;
    }
    public ObservableList<RouteAlgoFeedback> getFeedbackList() {
        return this.feedbackList;
    }
    private  void initCol() {
        routeField.setCellValueFactory(new PropertyValueFactory<>("plaatsen"));
        afstandField.setCellValueFactory(new PropertyValueFactory<>("afstand"));
        tempField.setCellValueFactory(new PropertyValueFactory<>("temp"));
        kansFunctieField.setCellValueFactory(new PropertyValueFactory<>("kansFunctie"));
        randomGetalField.setCellValueFactory(new PropertyValueFactory<>("randomGetal"));
        besluitField.setCellValueFactory(new PropertyValueFactory<>("besluit"));
    }
    public  void loadData(){
        initCol();
        algoFeedbackTable.setItems(feedbackList);

    }


    @FXML private void test(){
        System.out.println(RouteWindowController.routelijst);
    }
}

