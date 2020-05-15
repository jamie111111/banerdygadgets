package com.banerdygadgets.helpers;

import com.banerdygadgets.model.Klant;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class Verzendlijst {
    private String filename = "klanten.txt";
    String line;

    public void writeList(List<Klant> klanten) {
        try{
            FileWriter fw = new FileWriter(filename);
            Writer output = new BufferedWriter(fw);
            int size = klanten.size();
            for (int i=0;i <size;i++) {
                output.write(klanten.get(i).toString() + "\n");
            }
            output.close();;
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}