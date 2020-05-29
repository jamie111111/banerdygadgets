package com.banerdygadgets.model;

public class PostcodeRange {
    private int postcodeVan;
    private int postcodeTot;

    public PostcodeRange(int postcodeVan, int postcodeTot) {
        this.postcodeVan = postcodeVan;
        this.postcodeTot = postcodeTot;
    }

    public int getPostcodeVan() {
        return postcodeVan;
    }

    public int getPostcodeTot() {
        return postcodeTot;
    }

    @Override
    public String toString() {
        return "Postcode gebied: " + postcodeVan + " tot " + postcodeTot;
    }
}
