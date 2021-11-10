/*******************************
 Kontroler służący do sterowania logiką interfejsu symulatora
 *******************************/
package com.example.Simulator;

import java.io.File;
import java.lang.*;

import com.example.Algorithms.Algorithm;
import com.opencsv.*;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import static com.example.ReadWriteFile.ReadFile.*;
import static com.example.ReadWriteFile.WriteFile.*;

public class Scene {
    @FXML
    private Button StartButton, InputButton, SaveDirectoryButton;

    @FXML
    private Label FileLabel, SaveDirectoryLabel;

    @FXML
    private TextArea LogArea;

    public static TextArea _LogArea;

    public File file = new File(System.getProperty("user.dir") + "\\Test.csv");
    public File selectedDirectory = new File(System.getProperty("user.home")); //PLIK

    public String chosenFile = file.getAbsolutePath();
    public String chosenDirectory = selectedDirectory.getAbsolutePath(); //ŚCIEŻKA

    final FileChooser fileChooser = new FileChooser();
    final DirectoryChooser directoryChooser = new DirectoryChooser();
    final Stage stage = new Stage();
    CSVParser csvParser = new CSVParserBuilder().withSeparator(',').build();

    //Funkcja sterująca dostępnością przycisków w oknie symulatora
    public void setModeOfButtons(boolean b){
        StartButton.setDisable(b);
        InputButton.setDisable(b);
        SaveDirectoryButton.setDisable(b);
    }

    //Funkcja aktualizująca informacje wyświetlające się w oknie
    public static void updateLogArea(String log){
        _LogArea.appendText(log + "\n");
    }

    //Funkcja wywołująca okno dialogowe wyboru pliku odczytu
    @FXML
    protected void chooseInputFile(){
        file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            FileLabel.setText(file.getAbsolutePath());
        }
    }

    //Funkcja konfigurująca okno dialogowe wyboru pliku odczytu
    private static void configureFileChooser(final FileChooser fileChooser){
        fileChooser.setTitle("Wybierz plik wejściowy");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Plik CSV", "*.csv"),
                new FileChooser.ExtensionFilter("Wszystkie pliki", "*.*")
        );
    }

    //Funkcja wywołująca okno dialogowe wyboru folderu zapisu
    @FXML
    protected void chooseDirectoryForSave(){
        selectedDirectory = directoryChooser.showDialog(stage);
        if (selectedDirectory != null) {
            SaveDirectoryLabel.setText(selectedDirectory.getAbsolutePath());
            chosenDirectory = selectedDirectory.getAbsolutePath();
        }
    }

    //Funkcja konfigurująca okno dialogowe wyboru folderu zapisu
    private static void configureDirectoryChooser(final DirectoryChooser directoryChooser){
        directoryChooser.setTitle("Wybierz folder zapisu");
        directoryChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
    }

    //TODO: Funkcja rozpoczynająca analizę (Wymyśl coś lepszego)
    @FXML
    protected void startAnalyze() throws Exception {
        setModeOfButtons(true);
        updateLogArea("Analiza rozpoczęta");
        readCSVFile(file, csvParser);
        prepareFile();

        Algorithm a = new Algorithm(numberOfUsers, numberOfSeries, numberOfObjects);
        a.setPrices(Prices, Bonus);
        a.setPropositions(Propositions);
        a.completeAdjacency();
        a.StartAnalyzingGraph();

        endWriting(chosenDirectory);
        setModeOfButtons(false);
    }

    //Funkcja inicjalizująca, uruchamiana raz na początku
    public void initialize(){
        _LogArea = LogArea;
        configureFileChooser(fileChooser);
        configureDirectoryChooser(directoryChooser);
        FileLabel.setText(chosenFile);
        SaveDirectoryLabel.setText(chosenDirectory);
    }
}
