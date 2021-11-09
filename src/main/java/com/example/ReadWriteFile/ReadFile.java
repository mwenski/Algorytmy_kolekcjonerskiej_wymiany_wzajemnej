/*******************************
 Klasa służąca do odczytu danych z pliku CSV
 *******************************/
package com.example.ReadWriteFile;

import com.example.Algorithms.Proposition;
import com.opencsv.CSVParser;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileReader;
import java.util.Objects;

import static com.example.ReadWriteFile.WriteFile.*;

public class ReadFile {
    public static int numberOfUsers;
    public static int numberOfSeries;
    public static int numberOfObjects;

    public static float Bonus;
    public static float[] Prices;

    public static Proposition[] Propositions;

    private static int[][] Offer;
    private static int[][] Need;
    private static int rowInProposition, userId;

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
    public static void setData(String[] data){
        numberOfUsers = Integer.parseInt(data[0]);
        numberOfSeries = Integer.parseInt(data[1]);
        numberOfObjects = Integer.parseInt(data[2]);
        Bonus = Float.parseFloat(data[3]);

        Prices = new float[numberOfObjects];
        Propositions = new Proposition[numberOfUsers];
        Offer = new int[numberOfSeries][numberOfObjects];
        Need = new int[numberOfSeries][numberOfObjects];
    }

    //Funkcja odczytująca część dotyczącą cen w pliku CSV i przypisująca wartości do tablicy cen
    public static void setPrices(String[] line){
        for (int i = 0; i < numberOfObjects; i++){
            Prices[i] = Float.parseFloat(line[i]);
        }
    }

    //Funkcja odczytująca część dotyczącą ofert i zapotrzebowań kolekcjonerów w pliku CSV i przypisująca wartości do poszczególnych tablic
    public static void setOfferAndNeed(String[] line){
        if (userId < numberOfUsers) {
            if (rowInProposition < numberOfSeries) {
                for (int i = 0; i < 2 * numberOfObjects; i++) {
                    if (i < numberOfObjects) {
                        Offer[rowInProposition][i] = Integer.parseInt(line[i]);
                    } else {
                        Need[rowInProposition][i - numberOfObjects] = Integer.parseInt(line[i]);
                    }
                }
                rowInProposition++;
            }
        }
    }

    //Funkcja przypisująca tablice ofert i potrzeb do poszczególnych kolekcjonerów
    public static void setPropositions(){
        int[][] offer = new int[numberOfSeries][numberOfObjects];
        int[][] need = new int[numberOfSeries][numberOfObjects];
        for (int i = 0; i < numberOfSeries; i++){
            for (int j = 0; j < numberOfObjects; j++){
                offer[i][j] = Offer[i][j];
                need[i][j] = Need[i][j];
            }
        }

        if (rowInProposition == numberOfSeries) {
            Propositions[userId] = new Proposition(offer, need);
            rowInProposition = 0;
            userId++;
        }
    }

}
