package com.banerdygadgets.model;

public class Plaats {
    private String adres;
    private String postcode;
    private String woonplaats;


    public Plaats(String adres, String postcode, String woonplaats) {
        this.adres = adres;
        this.postcode = postcode;
        this.woonplaats = woonplaats;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }

    @Override
    public String toString() {
        return "Plaats{" +
                "adres='" + adres + '\'' +
                ", postcode='" + postcode + '\'' +
                ", woonplaats='" + woonplaats + '\'' +
                '}';
    }
}
