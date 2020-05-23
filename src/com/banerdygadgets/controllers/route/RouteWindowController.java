package com.banerdygadgets.controllers.route;

import com.banerdygadgets.Main;
import com.banerdygadgets.controllers.bestellingen.BestellingenController;
import com.banerdygadgets.controllers.retouren.RetourenWindowController;
import com.banerdygadgets.helpers.RouteHelpers;
import com.banerdygadgets.model.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
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
    @FXML
    private ImageView imagePostcodes;

    @FXML private StackPane routeWindowPane;
    @FXML private TableView<Klant> verzendlijstTableView;
    @FXML private TableColumn<Klant,Integer> klantNrField;
    @FXML private TableColumn<Klant,String> naamField;
    @FXML private TableColumn<Klant,String> adresField;
    @FXML private TableColumn<Klant,String> huisnrField;
    @FXML private TableColumn<Klant,String> postcodeField;
    @FXML private TableColumn<Klant,String> woonplaatsField;
   SimmulatedAnnealing algo;
   public static String routelijst;




    @FXML private void initialize() throws FileNotFoundException {
        try{
            FileInputStream input = new FileInputStream("C:/BANerdyGadgets/src/resources" +
                    "/Postcodes_in_Nederland" +
                    ".gif");
            Image image = new Image(input);
            imagePostcodes.setImage(image);
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }


        initCol();
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
            stage.show();
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

        ObservableList<Geolocation> route = algo.getKorsteRoute().getCities();
        com.itextpdf.text.List list = new com.itextpdf.text.List(true,20);
        com.itextpdf.text.ListItem item;
        for(Geolocation locatie:route) {
            item = new com.itextpdf.text.ListItem(locatie.toStringPlaatsEnPostcode());
            list.add(item);
        }
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, new FileOutputStream("route_lijst.pdf"));

        document.open();
//        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
//        Chunk chunk = new Chunk(list, font);
        document.addTitle("");
        document.add(new Paragraph("De meest optimale route voor de geselecteerde plaatsen: "));
        document.add(new Paragraph("\n"));
        document.add(list);
        document.close();
    }

    public static ObservableList<Klant> getVerzendLijst() {
        return verzendLijst;
    }


}
