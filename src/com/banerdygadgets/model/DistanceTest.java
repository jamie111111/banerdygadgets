//package com.banerdygadgets.model;
//
//import java.util.ArrayList;
//
//public class DistanceTest {
//    /**
//     * Create a matrix with distances between each points.
//     */
//    private void createDistanceMatrix() {
//        // Prepare destinations string for api call
//        String addresses = "";
//        for (LocationEntity location : this.locations) {
//            if (addresses.length() > 0) {
//                addresses += "|";
//            }
//            addresses += location.getPostalCode();
//        }
//        // Create new HTTPRequest to fetch distance matrix from Google Distance Matrix API.
//        HTTPRequest getDistancesRequest = new HTTPRequest(GoogleAPIConfig.distanceMatrixApiURI + "?origins=" + addresses + "&destinations=" + addresses + "&key=" + GoogleAPIConfig.apiKey);
//        getDistancesRequest.setMethod("GET");
//        getDistancesRequest.setRequestProperties(new String[][]{{"Content-Type", "application/json"}});
//        String res = getDistancesRequest.send();
//        Gson gson = new Gson();
//        JsonObject json = gson.fromJson(res, JsonObject.class);
//        JsonArray rows = json.get("rows").getAsJsonArray();
//        ArrayList<ArrayList<Integer>> distanceMatrix = new ArrayList<>();
//        for (JsonElement row : rows) {
//            ArrayList<Integer> elements = new ArrayList<>();
//            for (JsonElement element : row.getAsJsonObject().get("elements").getAsJsonArray()) {
//                elements.add(element.getAsJsonObject().get("distance").getAsJsonObject().get("value").getAsInt());
//            }
//            distanceMatrix.add(elements);
//        }
//        this.distanceMatrix = distanceMatrix;
//    }
//
//}
