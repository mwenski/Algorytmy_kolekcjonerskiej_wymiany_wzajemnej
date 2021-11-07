/*******************************
 Klasa służąca do odczytu danych z pliku CSV
 oraz zapisu danych w pliku XLSX (Microsoft Excel 2007)
 *******************************/
package com.example.Garbage;

import com.opencsv.CSVParser;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import javafx.scene.control.Alert;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import com.example.Algorithms.Proposition;

public class ReadWriteFile {
    public int numberOfUsers;
    public int numberOfSeries;
    public int numberOfObjects;

    public float Bonus;
    public float[] Prices;

    public Proposition[] Propositions;

    private int[][] Offer;
    private int[][] Need;
    private int rowInProposition = 0;
    private int userId = 0;

    public XSSFWorkbook workbook = new XSSFWorkbook();
    public XSSFSheet sheet1 = workbook.createSheet("Dane wejściowe");
    public XSSFSheet sheet2 = workbook.createSheet("Dane wymiany");
    public XSSFSheet sheet3 = workbook.createSheet("Wartości zbiorów");

    //Funkcja kończąca zapis pliku
    public void endWriting(String chosenDirectory) throws Exception{
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy-HHmmss");
        String nameOfFile = "\\" + sdf.format(date) + "-Results.xlsx";
        FileOutputStream out = new FileOutputStream(chosenDirectory + nameOfFile);
        workbook.write(out);
        out.close();
    }

    //Funkcja odczytująca plik CSV i przypisująca wartości do poszczególnych treści
    public void readCSVFile(File file, CSVParser csvParser){
        try(CSVReader reader = new CSVReaderBuilder(new FileReader(file))
                                                    .withCSVParser(csvParser)
                                                    .build()){
            String[] line;
            int rowId = 0;
            int mode = 0;

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
                Row row = sheet1.createRow(rowId);
                for (int i = 0; i < 2 * numberOfObjects; i++){
                    Cell cell = row.createCell(i);
                    cell.setCellValue(line[i]);
                }
                rowId++;
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

    //Funkcja służąca do przygotowania pliku zapisu
    public void prepareFile(){
        int nS = 0;
        int nO = 0;

        Row row = sheet2.createRow(0);
        for (int i = 0; i < numberOfSeries * numberOfObjects + 3; i++){
            Cell cell = row.createCell(i);
            switch (i) {
                case 0 -> cell.setCellValue("Nr wymiany");
                case 1 -> cell.setCellValue("ID oddajacego");
                case 2 -> cell.setCellValue("ID przyjmujacego");
                default -> {
                    cell.setCellValue("Seria: " + nS + "; Przedmiot: " + nO);
                    nO++;
                    if (nO == numberOfObjects) {
                        nS++;
                        nO = 0;
                    }
                    if (nS == numberOfSeries) nS = 0;
                }
            }
        }

        row = sheet3.createRow(0);
        for (int i = 0; i < numberOfUsers + 1; i++){
            Cell cell = row.createCell(i);
            if (i == 0) {
                cell.setCellValue("Nr wymiany");
            } else {
                cell.setCellValue("Kolekcjoner " + (i - 1));
            }
        }
    }

    //Funkcja odczytująca pierwsze linie z pliku CSV i przypisująca wartości do zmiennych globalnych
    public void setData(String[] data){
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
    public void setPrices(String[] data){
        for (int i = 0; i < numberOfObjects; i++){
            Prices[i] = Float.parseFloat(data[i]);
        }
    }

    //Funkcja odczytująca część dotyczącą ofert i zapotrzebowań kolekcjonerów w pliku CSV i przypisująca wartości do poszczególnych tablic
    public void setOfferAndNeed(String[] data){
        if (userId < numberOfUsers) {
            if (rowInProposition < numberOfSeries) {
                for (int i = 0; i < 2 * numberOfObjects; i++) {
                    if (i < numberOfObjects) {
                        Offer[rowInProposition][i] = Integer.parseInt(data[i]);
                    } else {
                        Need[rowInProposition][i - numberOfObjects] = Integer.parseInt(data[i]);
                    }
                }
                rowInProposition++;
            }
        }
    }

    //Funkcja przypisująca tablice ofert i potrzeb do poszczególnych kolekcjonerów
    public void setPropositions(){
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
