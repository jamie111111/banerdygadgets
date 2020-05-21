package com.banerdygadgets.model;

public class Klant {
    private int klantId;
    private String fullName;
    private String adres;
    private String huisnr;
    private String postcode;
    private String woonplaats;


    public Klant(int klantId, String fullName, String adres, String huisnr,String postcode,
                 String woonplaats) {
        this.klantId = klantId;
        this.fullName = fullName;
        this.adres = adres;
        this.huisnr= huisnr;
        this.postcode = postcode;
        this.woonplaats = woonplaats;
    }

    public Klant(String fullName, String adres,String huisnr, String postcode, String woonplaats) {
        this.fullName = fullName;
        this.adres = adres;
        this.huisnr = huisnr;
        this.postcode = postcode;
        this.woonplaats = woonplaats;
    }

    public int getKlantId() {
        return klantId;
    }

    public void setKlantId(int klantId) {
        this.klantId = klantId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAdres() {
        String trimmed = adres.replaceAll("\\s+","");
        return trimmed;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getHuisnr() {
        return huisnr;
    }

    public void setHuisnr(String huisnr) {
        this.huisnr = huisnr;
    }

    public String getPostcode() {
        String trimmed = postcode.replaceAll("\\s+","");
        return trimmed;
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
        return klantId + ", " + fullName +", " + adres + " "+ huisnr +", "+ postcode +", " +
                woonplaats;
    }

}
