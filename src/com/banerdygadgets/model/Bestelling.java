package com.banerdygadgets.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Bestelling {
    private int bestellingId;
    private int klantId;
    private LocalDate datum;
    private String status;

    public Bestelling(int bestellingId, int klantId, LocalDate datum, String status) {
        this.bestellingId = bestellingId;
        this.klantId = klantId;
        this.datum = datum;
        this.status = status;
    }
    public Bestelling(int klantId, LocalDate datum, String status) {
        this.klantId = klantId;
        this.datum = datum;
        this.status = status;
    }

    public int getBestellingId() {
        return bestellingId;
    }
    public String getStringBestellingId() {
        String bestelnr = Integer.toString(bestellingId);
        return bestelnr;
    }

    public void setBestellingId(int bestellingId) {
        this.bestellingId = bestellingId;
    }

    public int getKlantId() {
        return klantId;
    }
    public String getStringKlantId() {
        String klantnr = Integer.toString(klantId);
        return  klantnr;
    }

    public void setKlantId(int klantId) {
        this.klantId = klantId;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public String getStringDatum() {
        LocalDate date = datum;
        String formattedDate =  date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return  formattedDate;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Bestelling{" +
                "status='" + status + '\'' +
                '}';
    }
}
