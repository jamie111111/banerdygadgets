package com.banerdygadgets.helpers;

import com.banerdygadgets.model.Geolocation;
import javafx.collections.ObservableList;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class Verzendlijst {
    private String filename = "klanten.txt";
    String line;

    public void writeList(ObservableList<Geolocation> plaatsten) {
        try{
            FileWriter fw = new FileWriter(filename);
            Writer output = new BufferedWriter(fw);
            int size = plaatsten.size();
            for (int i=0;i <size;i++) {
                output.write(plaatsten.get(i).toString() + "\n");
            }
            output.close();;

        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


}