package com.banerdygadgets.model;

public class Geolocation {
    private static final double EARTH_EQUATORIAL_RADIUS = 6378.1370D;
    private static final double CONVERT_DEGREES_TO_RADIANS = Math.PI/180D;
    private double longitude;
    private double latitude;
    private String plaats;
    private String postcode;

    public Geolocation(String plaats, String postcode,double latitude,
                       double longitude
                      ) {
        this.longitude = longitude * CONVERT_DEGREES_TO_RADIANS;
        this.latitude = latitude * CONVERT_DEGREES_TO_RADIANS;
        this.plaats    = plaats;
        this.postcode  = postcode;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getName() {
        return plaats;
    }
    public double getLatitude(){
        return this.latitude;
    }
    public double getLongitude(){
        return this.longitude;
    }
    //Methode om de afstand te berekenen met de distance formule
    public double measureDistance(Geolocation city) {
        double deltaLongitude = (city.getLongitude() - this.getLongitude());
        double deltaLatitude = (city.getLatitude() - this.getLatitude());
        double a = Math.pow(Math.sin(deltaLatitude / 2D), 2D) +
                Math.cos(this.getLatitude()) * Math.cos(city.getLatitude()) * Math.pow(Math.sin(deltaLongitude / 2D), 2D);
        return EARTH_EQUATORIAL_RADIUS * 2D * Math.atan2(Math.sqrt(a), Math.sqrt(1D - a));
    }
    public String toString() {
        return this.plaats;
    }
    public String returnPostcode() {
        return postcode;
    }
    public String toStringPlaatsEnPostcode() {
        return this.plaats + this.postcode;
    }
}
