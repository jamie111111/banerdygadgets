package com.banerdygadgets.controllers.route;

import com.banerdygadgets.Main;
import com.banerdygadgets.controllers.bestellingen.BestellingenController;
import com.banerdygadgets.controllers.retouren.RetourenWindowController;
import com.banerdygadgets.helpers.RouteHelpers;
import com.banerdygadgets.model.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 *
 */
public class RouteWindowController  {
    private static ObservableList<Klant> verzendLijst = FXCollections.observableArrayList();
    private static PostcodeRange postcodeRange;
    public static routeWindowFeedbackController ctrl;
//    @FXML
//    private ImageView imagePostcodes;

    @FXML private StackPane routeWindowPane;
    @FXML private TableView<Klant> verzendlijstTableView;
    @FXML private TableColumn<Klant,Integer> klantNrField;
    @FXML private TableColumn<Klant,String> naamField;
    @FXML private TableColumn<Klant,String> adresField;
    @FXML private TableColumn<Klant,String> huisnrField;
    @FXML private TableColumn<Klant,String> postcodeField;
    @FXML private TableColumn<Klant,String> woonplaatsField;
    @FXML private WebView webViewRoute;
    private WebEngine engine;
   SimmulatedAnnealing algo;
   public static String routelijst;
    StringBuilder html;




    @FXML private void initialize() throws FileNotFoundException {
//        engine = webViewRoute.getEngine();

        loadData();
    }
    private void initCol() {
        klantNrField.setCellValueFactory(new PropertyValueFactory<>("klantId"));
        naamField.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        adresField.setCellValueFactory(new PropertyValueFactory<>("adres"));
        huisnrField.setCellValueFactory(new PropertyValueFactory<>("huisnr"));
        postcodeField.setCellValueFactory(new PropertyValueFactory<>("postcode"));
        woonplaatsField.setCellValueFactory(new PropertyValueFactory<>("woonplaats"));
    }
    private void loadData() {
        initCol();
        klantNrField.setStyle( "-fx-alignment: CENTER;");
        huisnrField.setStyle( "-fx-alignment: CENTER;");
        postcodeField.setStyle( "-fx-alignment: CENTER;");
        if(verzendLijst != null) {
            verzendlijstTableView.setItems(verzendLijst);
        }else {
            return;
        }
    }
    private void loadRouteWebpage(String url) {
        engine = webViewRoute.getEngine();
        engine.loadContent(url);

    }


    @FXML private void getOrdersWithReadyStatus() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(routeWindowPane.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("views/routing/route_verzendlijst_dialog" +
                ".fxml"));
        try{
            dialog.getDialogPane().setContent(fxmlLoader.load());
        }catch (IOException e) {
            e.printStackTrace();
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK) {
            RouteWindowDialogController controller = fxmlLoader.getController();

            verzendLijst.clear();
            // Bepaal postcodegebied voor de verzendlijst
            postcodeRange = controller.getPostcodeRange();
            //Haal klanten op in het vastgestelde postcodegebied
            List<Klant> klantLijst =
                    Datahelpers.getSelectedKlantenWithPostcodeRange(postcodeRange.getPostcodeVan()
                            ,postcodeRange.getPostcodeTot());
            //Haal bestellingen op met verzendklaar status
            List<Bestelling> verzendKlaarlijstBestellingen =
                    BestellingenController.getReadyBestellingen();
            //Haal klanten op uit de bestellingen met verzendklaar status
            RouteHelpers routeHelpers = new RouteHelpers();
            ObservableList<Klant> fetchedList = FXCollections.observableArrayList();
             fetchedList = routeHelpers.getKlantBestellingWithVerzendKlaarStatus(klantLijst,
                    verzendKlaarlijstBestellingen);
             for(Klant klant:fetchedList) {
                 verzendLijst.add(klant);
             }
            //Haal retourorders op met status aangemeld
            List<RetourOrder> retourOrdersAangemeld =
                    RetourenWindowController.getReadyForDispatchRetourOrders();
             fetchedList = routeHelpers.getRetourOrderWithAangemeldStatus(klantLijst,
                     retourOrdersAangemeld);
            for(Klant klant:fetchedList) {
                verzendLijst.add(klant);
            }
            //Laad klanten in het scherm
            loadData();
        }else {
            System.out.println("Er is iets fout gegaan");
        }
    }
    @FXML
    public void createDispatchList() throws IOException {
    Testapi.geoCodeApi();


    }
    public void getOptimalRoute() throws IOException, DocumentException {
        try {
            Parent algoView = FXMLLoader.load(Main.class.getResource("views/routing" +
                    "/route_algo_feedback.fxml"));

            Scene algoScene = new Scene(algoView,1100, 600);
            Stage stage = new Stage();
            stage.setScene(algoScene);
            stage.setTitle("Algoritme calculaties");
//            stage.show();
        }catch (Exception e) {
            System.out.println("Can't load window " + e.getMessage());
        }

        Route route = new Route(Testapi.geoLocaties);
        FileDriver.printHeading(route);
        algo = new SimmulatedAnnealing();
        algo.findRoute(SimmulatedAnnealing.INITIAL_TEMPERATURE, route);
        exportAsPdf();
        FileDriver.printInfo();
    }
    public void exportAsPdf() throws IOException, DocumentException {
        String printData = "De meeste optimale route op volgorde:  \n" + "RouteWindowController " +
                "ln 171";
        StringBuilder builder = new StringBuilder(printData);
        StringBuilder url = new StringBuilder("https://www.google.nl/maps/dir");

        com.itextpdf.text.List list = new com.itextpdf.text.List(true,20);
        com.itextpdf.text.ListItem item;
        ObservableList<Geolocation> route = algo.getKorsteRoute().getCities();
        for(Geolocation locatie:route) {
            item = new com.itextpdf.text.ListItem(locatie.toStringPlaatsEnPostcode());
            list.add(item);
            url.append("/" + locatie.returnPostcode());
        }
        url.append("/"+ route.get(0).returnPostcode());

        System.out.println("line 189" + url);
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("pdf",
//                        "*.pdf"));
        Document document = new Document(PageSize.A4);
        PdfWriter pdf= PdfWriter.getInstance(document,
                new FileOutputStream("route.pdf"));
        document.open();
        document.addTitle("");
        document.add(new Paragraph("De meest optimale route voor de geselecteerde plaatsen: "));
        Rectangle rect = new Rectangle(0,0);
        document.close();
        try {



        }catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }

    }

    public static ObservableList<Klant> getVerzendLijst() {
        return verzendLijst;
    }
    public void testbutton() {
        ObservableList<Geolocation> route = algo.getKorsteRoute().getCities();
        System.out.println(route.toString());
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

        for(int i = 1;i<route.size();i++ ) {
            if (i > 1) {html.append("|");
            }
            html.append(route.get(i).returnPostcode());
        }
        html.append("\" allowfullscreen></iframe>");
        html.append("</body>");
        html.append("</html>");
        loadRouteWebpage(html.toString());
    }


}
