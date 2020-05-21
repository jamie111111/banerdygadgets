package com.banerdygadgets.model;

public class RouteAlgoFeedback {
    private String plaatsen;
    private String afstand;
    private String temp;
    private String kansFunctie;
    private String randomGetal;
    private String besluit;

    public RouteAlgoFeedback(String plaatsen,
                             String afstand,
                             String temp,
                             String kansFunctie,
                             String randomGetal,
                             String besluit) {
        this.plaatsen = plaatsen;
        this.afstand     = afstand;
        this.temp = temp;
        this.kansFunctie = kansFunctie;
        this.randomGetal = randomGetal;
        this.besluit     = besluit;
    }

    public String getPlaatsen() {
        return plaatsen;
    }

    public void setPlaatsen(String plaatsen) {
        this.plaatsen = plaatsen;
    }

    public String getAfstand() {
        return afstand;
    }

    public void setAfstand(String afstand) {
        this.afstand = afstand;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getKansFunctie() {
        return kansFunctie;
    }

    public void setKansFunctie(String kansFunctie) {
        this.kansFunctie = kansFunctie;
    }

    public String getRandomGetal() {
        return randomGetal;
    }

    public void setRandomGetal(String randomGetal) {
        this.randomGetal = randomGetal;
    }

    public String getBesluit() {
        return besluit;
    }

    public void setBesluit(String besluit) {
        this.besluit = besluit;
    }

    @Override
    public String toString() {
        return "RouteAlgoFeedback{" +
                "plaatsen='" + plaatsen + '\'' +
                ", afstand='" + afstand + '\'' +
                ", temp='" + temp + '\'' +
                ", kansFunctie='" + kansFunctie + '\'' +
                ", randomGetal='" + randomGetal + '\'' +
                ", besluit='" + besluit + '\'' +
                '}';
    }
}
