package com.banerdygadgets.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RetourOrder {
    private int retourNummer;
    private LocalDate datumAanmelding;
    private String status;
    private String reden;
    private int bestelNummer;

    public RetourOrder(int retourNummer, LocalDate datumAanmelding, String status, String reden, int bestelNummer) {
        this.retourNummer = retourNummer;
        this.datumAanmelding = datumAanmelding;
        this.status = status;
        this.reden = reden;
        this.bestelNummer = bestelNummer;
    }
    public RetourOrder(LocalDate datumAanmelding, String status, String reden, int bestelNummer) {
        this.datumAanmelding = datumAanmelding;
        this.status = status;
        this.reden = reden;
        this.bestelNummer = bestelNummer;
    }

    public int getRetourNummer() {
        return retourNummer;
    }
    public String getStringRetourNummer() {
        int retournr = this.retourNummer;
        String nr = Integer.toString(retournr);
        return nr;
    }

    public void setRetourNummer(int retourNummer) {
        this.retourNummer = retourNummer;
    }

    public LocalDate getDatumAanmelding() {
        return datumAanmelding;
    }
    public String getStringDatumAanmelding() {
        LocalDate date = this.datumAanmelding;
        String formattedDate =  date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return  formattedDate;
    }


    public void setDatumAanmelding(LocalDate datumAanmelding) {
        this.datumAanmelding = datumAanmelding;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReden() {
        return reden;
    }

    public void setReden(String reden) {
        this.reden = reden;
    }

    public int getBestelNummer() {
        return bestelNummer;
    }

    public void setBestelNummer(int bestelNummer) {
        this.bestelNummer = bestelNummer;
    }

    @Override
    public String toString() {
        return "RetourOrder{" +
                "retourNummer=" + retourNummer +
                ", datumAanmelding=" + datumAanmelding +
                ", status='" + status + '\'' +
                ", reden='" + reden + '\'' +
                ", bestelNummer='" + bestelNummer + '\'' +
                '}';
    }
}
