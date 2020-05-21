package com.banerdygadgets.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class RouteAlgoFeedbackTest {
    private RouteAlgoFeedback feedback;
    private static ObservableList<RouteAlgoFeedback> list = FXCollections.observableArrayList();
    @BeforeClass
    public static void beforeClass() {
       list.add(new RouteAlgoFeedback("Arnhem, Apeldoorn,Nijmegen,Deventer,Zutphen,Zwolle," +
               "Almelog","200","0.22","1.0","0.99292","Doorgaan met aangrenzende route"));
        list.add(new RouteAlgoFeedback("Zeewolde, Otterlo,Nijmegen,Deventer,Zutphen,Zwolle," +
                "Almelog","200","0.22","1.0","0.99292","Doorgaan met aangrenzende route"));

    }

    @Before
    public void setup() {
        System.out.println("Voeg plaatsen toe aan");
    }
    @Test
    public void getAlgoLijst() {
        System.out.println("Test failed get algolijst");
    }

    @Test
    public void ToString() {

    }
}