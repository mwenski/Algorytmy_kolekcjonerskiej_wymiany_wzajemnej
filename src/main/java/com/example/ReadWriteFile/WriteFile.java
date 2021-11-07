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

    private static int rSheet1, rSheet2, rSheet3;

    public static void setRSheets(){
        rSheet1 = 0;
        rSheet2 = 0;
        rSheet3 = 0;
    }

    //TODO: W pętli for odczyt pierwszej linii nie działa. Dlaczego?
    public static void writeFirstSheet(String[] line){
        Row row = sheet1.createRow(rSheet1);
        System.out.println("p"+line[0]);
        for (int i = 0; i < 2 * numberOfObjects; i++){
            System.out.println(line[0]);
            System.out.println(line[i]);

            Cell cell = row.createCell(i);
            cell.setCellValue(line[i]);
        }
        rSheet1++;
    }

    //Funkcja służąca do przygotowania pliku zapisu
    public static void prepareFile(){
        int nS = 0;
        int nO = 0;

        Row row = sheet2.createRow(rSheet2);
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
        rSheet2++;

        row = sheet3.createRow(rSheet3);
        for (int i = 0; i < numberOfUsers + 1; i++){
            Cell cell = row.createCell(i);
            if (i == 0) {
                cell.setCellValue("Nr wymiany");
            } else {
                cell.setCellValue("Kolekcjoner " + (i - 1));
            }
        }
        rSheet3++;
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
