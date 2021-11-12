/*******************************
 Klasa służąca do odczytu danych z pliku CSV
 *******************************/
package com.example.ReadWriteFile;

import com.example.Algorithms.Proposition;
import com.opencsv.CSVParser;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static com.example.ReadWriteFile.WriteFile.*;

public class ReadFile {
    //Zmienne stałe
    public static int numberOfUsers;
    public static int numberOfSeries;
    public static int[] numberOfObjects;

    //Ceny
    public static float[][] Prices;
    public static float[] Bonus;

    //Propozycje
    public static Proposition[] Propositions;

    //Zmienne lokalne
    private static int[][] Offer;
    private static int[][] Need;
    private static int rowInProposition, userId;
    private static int rowInPrices;

    //Funkcja odczytująca plik CSV i przypisująca wartości do poszczególnych treści
    public static void readCSVFile(File file, CSVParser csvParser){
        try(CSVReader reader = new CSVReaderBuilder(new FileReader(file))
                .withCSVParser(csvParser)
                .build()){
            setRSheets();
            String[] line;
            int mode = 0;
            rowInProposition = 0;
            userId = 0;

            while ((line = reader.readNext()) != null) {
                if (Objects.equals(line[0], "Liczba uczestnikow") || Objects.equals(line[0], "Cena 0") || Objects.equals(line[0], "Oferuje 0")) mode++;
                if (!Objects.equals(line[0], "") && !Objects.equals(line[0], "Liczba uczestnikow") && !Objects.equals(line[0], "Cena 0") && !Objects.equals(line[0], "Oferuje 0")){
                    switch (mode) {
                        case 1 -> setData(line);
                        case 2 -> setPrices(line);
                        case 3 -> {
                            setOfferAndNeed(line);
                            setPropositions();
                        }
                    }
                }
                writeFirstSheet(line);
            }

        }catch (Exception e){
            e.printStackTrace();
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Błąd");
            error.setHeaderText(null);
            error.setContentText("Błąd odczytu pliku");
            error.show();
        }
    }

    //Funkcja odczytująca pierwsze linie z pliku CSV i przypisująca wartości do zmiennych globalnych
    public static void setData(String[] line){
        numberOfUsers = Integer.parseInt(line[0]);
        numberOfSeries = Integer.parseInt(line[1]);

        numberOfObjects = new int[numberOfSeries];
        for(int i = 2; i < numberOfSeries + 2; i++) numberOfObjects[(i - 2)] = Integer.parseInt(line[i]);

        Prices = new float[numberOfSeries][];
        Bonus = new float[numberOfSeries];
        Propositions = new Proposition[numberOfUsers];
        Offer = new int[numberOfSeries][];
        Need = new int[numberOfSeries][];
    }

    //Funkcja odczytująca część dotyczącą cen w pliku CSV i przypisująca wartości do tablicy cen
    public static void setPrices(String[] line){
        int x = 0;
        Prices[rowInPrices] = new float[numberOfObjects[rowInPrices]];
        for (int i = 0; i < line.length; i++){
            if(!Objects.equals(line[i], "")){
                if (x < numberOfObjects[rowInPrices]){
                    Prices[rowInPrices][i] = Float.parseFloat(line[i]);
                    x++;
                }else if(x == numberOfObjects[rowInPrices]){
                    Bonus[rowInPrices] = Float.parseFloat(line[i]);
                }
            }
        }
        rowInPrices++;
    }

    //Funkcja odczytująca część dotyczącą ofert i zapotrzebowań kolekcjonerów w pliku CSV i przypisująca wartości do poszczególnych tablic
    public static void setOfferAndNeed(String[] line){
        Offer[rowInProposition] = new int[numberOfObjects[rowInProposition]];
        Need[rowInProposition] = new int[numberOfObjects[rowInProposition]];

        if (userId < numberOfUsers) {
            if (rowInProposition < numberOfSeries){
                int j = 0;
                for (String s : line) {
                    if (!Objects.equals(s, "")) {
                        if (j < numberOfObjects[rowInProposition]) {
                            Offer[rowInProposition][j] = Integer.parseInt(s);
                        } else if (j < 2 * numberOfObjects[rowInProposition]) {
                            Need[rowInProposition][j - numberOfObjects[rowInProposition]] = Integer.parseInt(s);
                        }
                        j++;
                    }
                }
                rowInProposition++;
            }
        }


            /*
        if (userId < numberOfUsers) {
            if (rowInProposition < numberOfSeries) {
                for (int i = 0; i < numberOfObjects[rowInProposition]; i++) {
                    Offer[rowInProposition][i] = Integer.parseInt(line[i]);
                }
                for (int i = 4; i < numberOfObjects[rowInProposition] + 4; i++) {
                    Need[rowInProposition][i - 4] = Integer.parseInt(line[i]);
                }
                rowInProposition++;
            }
        }

             */
    }

    //Funkcja przypisująca tablice ofert i potrzeb do poszczególnych kolekcjonerów
    public static void setPropositions(){
        /*
        int[][] offer = new int[numberOfSeries][numberOfObjects[rowInProposition]];
        int[][] need = new int[numberOfSeries][numberOfObjects[rowInProposition]];
        for (int i = 0; i < numberOfSeries; i++){
            for (int j = 0; j < numberOfObjects[i]; j++){ //PĘTLA
                offer[i][j] = Offer[i][j];
                need[i][j] = Need[i][j];
            }
        }

         */

        if (rowInProposition == numberOfSeries) {
            Propositions[userId] = new Proposition(Offer, Need);
            rowInProposition = 0;
            userId++;
        }
    }

}
