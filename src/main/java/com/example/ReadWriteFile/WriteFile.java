/*******************************
 Klasa służąca do zapisu danych w pliku XLSX (Microsoft Excel 2007)
 *******************************/
package com.example.ReadWriteFile;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.ReadWriteFile.ReadFile.*;

public class WriteFile {

    private final static XSSFWorkbook workbook = new XSSFWorkbook();
    private final static XSSFSheet sheet1 = workbook.createSheet("Dane wejściowe");
    private final static XSSFSheet sheet2 = workbook.createSheet("Dane wymiany");
    private final static XSSFSheet sheet3 = workbook.createSheet("Wartości zbiorów");

    //Tablica numerów wierszy w arkuszach
    private static int[] rSheet;

    //Funkcja zerująca numery wierszy
    public static void setRSheets(){
        rSheet = new int[3];
    }

    //TODO: W pętli for odczyt pierwszej linii nie działa. Dlaczego?
    public static void writeFirstSheet(String[] line){
        Row row = sheet1.createRow(rSheet[0]);
        for (int i = 0; i < line.length; i++){
            Cell cell = row.createCell(i);
            cell.setCellValue(line[i]);
        }
        rSheet[0]++;
    }

    //Funkcja służąca do przygotowania pliku zapisu
    public static void prepareFile(){
        int nS = 0;
        int nO = 0;
        int x = 0;
        for(int i = 0; i < numberOfSeries; i++) x += (numberOfObjects[i]);


        Row row = sheet2.createRow(rSheet[1]);
        for (int i = 0; i < x + 3; i++){
            Cell cell = row.createCell(i);
            switch (i) {
                case 0 -> cell.setCellValue("Nr wymiany");
                case 1 -> cell.setCellValue("ID oddajacego");
                case 2 -> cell.setCellValue("ID przyjmujacego");
                default -> {
                    cell.setCellValue("Seria: " + nS + "; Przedmiot: " + nO);
                    nO++;
                    if (nO == numberOfObjects[nS]) {
                        nS++;
                        nO = 0;
                    }
                    if (nS == numberOfSeries) nS = 0;
                }
            }
        }
        rSheet[1]++;

        row = sheet3.createRow(rSheet[2]);
        for (int i = 0; i < numberOfUsers + 1; i++){
            Cell cell = row.createCell(i);
            if (i == 0) {
                cell.setCellValue("Nr wymiany");
            } else {
                cell.setCellValue("Kolekcjoner " + (i - 1));
            }
        }
        rSheet[2]++;
    }

    //Funkcja zapisująca wartości do arkusza
    public static void writeValues(int nr, float[] Values){
        Row row = sheet3.createRow(rSheet[2]);
        for (int i = 0; i < Values.length + 1; i++){
            Cell cell = row.createCell(i);
            if (i == 0) {
                cell.setCellValue(nr);
            } else {
                cell.setCellValue(Values[i - 1]);
            }
        }
        rSheet[2]++;
    }

    //Funkcja zapisująca wymiany do arkusza
    public static void writeExchange(int nr, int p1, int p2, int[][] Object){
        int nS = 0;
        int nO = 0;
        int x = 0;
        for(int i = 0; i < numberOfSeries; i++) x += (numberOfObjects[i]);

        Row row = sheet2.createRow(rSheet[1]);
        for (int i = 0; i < x + 3; i++){
            Cell cell = row.createCell(i);
            switch (i) {
                case 0 -> cell.setCellValue(nr);
                case 1 -> cell.setCellValue(p1);
                case 2 -> cell.setCellValue(p2);
                default -> {
                    cell.setCellValue(Object[nS][nO]);
                    nO++;
                    if (nO == numberOfObjects[nS]) {
                        nS++;
                        nO = 0;
                    }
                    if (nS == numberOfSeries) nS = 0;
                }
            }
        }
        rSheet[1]++;
    }




    //Funkcja kończąca zapis pliku
    public static void endWriting(String chosenDirectory) throws Exception{
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy-HHmmss");
        String nameOfFile = "\\" + sdf.format(date) + "-Results.xlsx";
        FileOutputStream out = new FileOutputStream(chosenDirectory + nameOfFile);
        workbook.write(out);
        out.close();
    }

}
