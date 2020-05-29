package com.banerdygadgets.model;

import com.banerdygadgets.controllers.route.RouteWindowController;
import com.banerdygadgets.helpers.AlertFactory;
import com.banerdygadgets.helpers.GeoCodingApiHelper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Testapi {
    private static ObservableList<Klant> verzendLijst;
    public static ObservableList<Geolocation> geoLocaties = FXCollections.observableArrayList();

    public static void geoCodeApi() {
        verzendLijst = RouteWindowController.
                getVerzendLijst();

        for (int counter = 0; counter <= verzendLijst.size() - 1; counter++) {
                Klant klant = verzendLijst.get(counter);
            String adres = klant.getAdres()+"+"+klant.getHuisnr()+"+"+klant.getPostcode()+
                    "+"+klant.getWoonplaats();
            try {
                URL url = new URL(
                        GeoCodingApiHelper.apiUri + "?address=" + adres + "&key=" + GeoCodingApiHelper.key);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json");
                // Controleer of de status van de response (reactie van server) 200 is (OK)
                if (connection.getResponseCode() == 200) {
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
                    String inputLine;
                    StringBuilder content = new StringBuilder();
                    // We willen deze buffer nu omzetten naar een Java String (leesbare tekst)
                    while ((inputLine = in.readLine()) != null) {
                        content.append(inputLine);
                    }
                    in.close();
                    String response = content.toString();
                    Gson gson = new Gson();
                    JsonObject json = gson.fromJson(response, JsonObject.class);
                    if (json.get("status").getAsString().equals("OK")) {
                        JsonArray jsonArray = json.get("results").getAsJsonArray();
                        JsonObject item = jsonArray.get(0).getAsJsonObject();
                        JsonObject geometryLocation = item.get("geometry").getAsJsonObject().get("location").getAsJsonObject();
                        float latitude = geometryLocation.get("lat").getAsFloat();
                        float longitude = geometryLocation.get("lng").getAsFloat();
                        Geolocation location = new Geolocation(klant.getWoonplaats(),klant.getPostcode(),
                                latitude,
                                longitude);
                        geoLocaties.add(location);
                        System.out.println("Address: " + klant.getWoonplaats() + " " + klant.getPostcode() + ", " +
                                "Latitude: " + latitude + ", " +
                                "Longitude: " + longitude);
                    }

                }
            } catch(Exception e) {
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
            }

        }
        AlertFactory.showSimpleAlert("De geolocaties zijn opgehaald", "Geolocaties " +
                "succesvol opgehaald");
    }
}
