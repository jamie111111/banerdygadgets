package com.banerdygadgets.controllers.route;

import com.banerdygadgets.Main;
import com.banerdygadgets.controllers.bestellingen.BestellingenController;
import com.banerdygadgets.controllers.retouren.RetourenWindowController;
import com.banerdygadgets.helpers.AlertFactory;
import com.banerdygadgets.helpers.RouteHelpers;
import com.banerdygadgets.model.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class RouteWindowController  {
    private static ObservableList<Klant> verzendLijst = FXCollections.observableArrayList();
    private static PostcodeRange postcodeRange;
    private WebEngine engine;
    private SimmulatedAnnealing algo;

    @FXML private StackPane routeWindowPane;
    @FXML private TableView<Klant> verzendlijstTableView;
    @FXML private TableColumn<Klant,Integer> klantNrField;
    @FXML private TableColumn<Klant,String> naamField;
    @FXML private TableColumn<Klant,String> adresField;
    @FXML private TableColumn<Klant,String> huisnrField;
    @FXML private TableColumn<Klant,String> postcodeField;
    @FXML private TableColumn<Klant,String> woonplaatsField;
    @FXML private WebView webViewRoute;

    @FXML private void initialize() throws FileNotFoundException {
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
    //Tonen van een dialoogvenster om postcodegebied te bepalen voor de route
    @FXML private void getOrdersWithReadyStatus() {
        verzendLijst.clear();
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
            // Bepaal postcodegebied voor de verzendlijst
            postcodeRange = controller.getPostcodeRange();
            //Haal klanten op in het vastgestelde postcodegebied
            List<Klant> klantLijst =
                    Datahelpers.getSelectedKlantenWithPostcodeRange(postcodeRange.getPostcodeVan()
                            ,postcodeRange.getPostcodeTot());
            //Haal bestellingen op met verzendklaar status
            List<Bestelling> verzendKlaarlijstBestellingen =
                    BestellingenController.getInstance().getReadyBestellingen();
            //Haal klanten op uit de bestellingen met verzendklaar status
            RouteHelpers routeHelpers = new RouteHelpers();
            ObservableList<Klant> fetchedList;
             fetchedList = routeHelpers.getKlantBestellingWithVerzendKlaarStatus(klantLijst,
                    verzendKlaarlijstBestellingen);
             //loop door gefilterde lijst en voeg toe aan verzendlijkst
             for(Klant klant:fetchedList) {
                 verzendLijst.add(klant);
             }
            //Haal retourorders op met status aangemeld
            List<RetourOrder> retourOrdersAangemeld =
//                    RetourenWindowController.getReadyForDispatchRetourOrders();
              RetourenWindowController.getInstance().getReadyForDispatchRetourOrders();
             fetchedList = routeHelpers.getRetourOrderWithAangemeldStatus(klantLijst,
                     retourOrdersAangemeld);
            for(Klant klant:fetchedList) {
                verzendLijst.add(klant);
            }
            //Laad klanten in het scherm
            loadData();
            AlertFactory.showSimpleAlert("Klanten uit het gekozen postcodegebied zijn opgehaald",
                    postcodeRange.toString());
        }else {
            System.out.println("Er is iets fout gegaan");
        }
    }
    @FXML
    private void getGeoLocations() throws IOException {
        GoogleApi.geoLocaties.clear();
        if(!verzendLijst.isEmpty()) {
            GoogleApi.geoCodeApi();
        }else {
            AlertFactory.showSimpleErrorMessage("Waarschuwing","Geen verzendorders geselecteerd",
                    "Haal eerst verzendorders op om de geolocaties op te halen");
        }

    }
    //Een method om de gebruiker feedback te geven van de routeberekening
    public void getOptimalRoute() throws IOException, DocumentException {
        if(GoogleApi.geoLocaties.isEmpty()) {
            AlertFactory.showSimpleErrorMessage("Waarschuwing","Geen verzendorders","Haal eerst " +
                    "verzendorders en of geolocaties op");
        }else {
            Route route = new Route(GoogleApi.geoLocaties);
            //Print de header voor de uitkomsten van de berekening in console
            Printer.printHeading(route);
            //instantieer algo en vind de meest optimale route
            algo = new SimmulatedAnnealing();
            algo.findRoute(SimmulatedAnnealing.INITIAL_TEMPERATURE,
                    route);
            //Exporteer naar pdf
            //print informatie over berekening in console
            Printer.printInfo();
        }

    }
    //Methode om route uit te printen naar pdf, wordt nu opgeslagen in working directory
    public void exportAsPdf() throws IOException, DocumentException {
        if(GoogleApi.geoLocaties.isEmpty()) {
            AlertFactory.showSimpleErrorMessage("Waarschuwing","Geen verzendorders","Haal eerst " +
                    "verzendorders en of geolocaties en en dan bereken route");
        }else {
            StringBuilder url = new StringBuilder("https://www.google.nl/maps/dir");
            //Maak lijst voor de pdf
            com.itextpdf.text.List list = new com.itextpdf.text.List(true,
                    20);
            com.itextpdf.text.ListItem item;
            ObservableList<Geolocation> route = algo.getKorsteRoute().getCities();
            for (Geolocation locatie : route) {
                item = new com.itextpdf.text.ListItem(locatie.toStringPlaatsEnPostcode());
                list.add(item);
                url.append("/" + locatie.returnPostcode());
            }
            url.append("/" + route.get(0).returnPostcode());
            System.out.println("line 189 " + url);
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document,
                    new FileOutputStream("route.pdf"));
            document.open();
            document.add(new Paragraph("De meest optimale route voor de geselecteerde plaatsen: "));
            document.add(Chunk.NEWLINE);
            document.add(list);
            document.add(Chunk.NEWLINE);
            //Url naar google maps
            Chunk chunk = new Chunk("Link naar googlemaps route beschrijving");
            chunk.setAnchor(url.toString());
            //        document.add(new Paragraph(url.toString()));
            document.add(chunk);
            document.close();
            AlertFactory.showSimpleAlert("Route opgeslagen",
                    "De route is opgeslagen als pdf met een " +
                            "link naar googlemaps voor de koerier");
        }
    }

    public static ObservableList<Klant> getVerzendLijst() {
        return verzendLijst;
    }

    public void showRoute() { // Methode om route in google maps zien in de app
        if(GoogleApi.geoLocaties.isEmpty()) {
            AlertFactory.showSimpleErrorMessage("Waarschuwing","Geen verzendorders","Haal eerst " +
                    "verzendorders en of geolocaties en en dan bereken route");
        }else {
            ObservableList<Geolocation> route = algo.getKorsteRoute().getCities();
            System.out.println(route.toString());
            StringBuilder htmlString = RouteHelpers.getInstance().htmlStringBuilder(route);
            for (int i = 1; i < route.size(); i++) {
                if (i > 1) {
                    htmlString.append("|");
                }
                htmlString.append(route.get(i).returnPostcode());
            }
            htmlString.append("\" allowfullscreen></iframe>");
            htmlString.append("</body>");
            htmlString.append("</html>");
            loadRouteWebpage(htmlString.toString());
        }
    }


}
