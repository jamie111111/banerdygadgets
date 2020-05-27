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
