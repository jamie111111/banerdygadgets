package com.banerdygadgets.model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GeoApiRequest {

    public static void geoCodeApi() {
        try {
           // String address = "1600+Amphitheatre+Parkway,+Mountain+View,+CA";
            String address = "Nieuwstraat+29,+7311HZ+Apeldoorn";
            String key = "AIzaSyBcxeIz1lk8mFWHnhm466ZaUF1vQGWwFeQ";
            String apiUri = "https://maps.googleapis.com/maps/api/geocode/json";
            // Maak een valid URL instantie aan (de url waarnaar je de request wilt uitvoeren)
            URL url = new URL(apiUri + "?address=" + address + "&key=" + key);
            // Maak een connectie met de url.
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // Configureer timeouts, hoe lang een request mag doen over uitlezen en hoe lang de connectie mag duren.
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            // Welke HTTP methode wil je gebruiken? In ons geval GET.
            connection.setRequestMethod("GET");
            // Headers om mee te sturen met de GET request. In dit geval willen we de Content-Type header meesturen,
            // om google te laten weten dat we graag een JSON response terug willen.
            connection.setRequestProperty("Content-Type", "application/json");
            // Controleer of de status van de response (reactie van server) 200 is (OK)
            if (connection.getResponseCode() == 200) {
                // Nu gaan we de response uitlezen, dit is een buffer (soort van lijstje met kleine chunks van data)
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                // We willen deze buffer nu omzetten naar een Java String (leesbare tekst)
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                // De stringbuilder welke we gebruikten om een buffer om te zetten naar een leesbare string,
                // gaan we nu omzetten naar een normale String.
                String response = content.toString();
                // Nu gaan we met GSON de JSON String omzetten naar Java Objecten
                Gson gson = new Gson();
                JsonObject json = gson.fromJson(response, JsonObject.class);
                // Controleer of de status OK is
                if (json.get("status").getAsString().equals("OK")) {
                    // Je krijgt het resultaat als array... weet niet waarom... want het is altijd maar 1 adres??
                    JsonArray jsonArray = json.get("results").getAsJsonArray();
                    // Vraag dus het 1ste item uit de array op... want er is er maar 1!
                    JsonObject item = jsonArray.get(0).getAsJsonObject();
                    // Nu willen we uit de "geometry" key en daar weer uit de "location" at en lng halen.
                    JsonObject geometryLocation = item.get("geometry").getAsJsonObject().get("location").getAsJsonObject();
                    float latitude = geometryLocation.get("lat").getAsFloat();
                    float longitude = geometryLocation.get("lng").getAsFloat();
                    System.out.println("Address: " + address + ", Latitude: " + latitude + ", Longitude: " + longitude);
                }
                System.out.println("Party!!!");
            }
        } catch(Exception e) { // shit hits the fan
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}