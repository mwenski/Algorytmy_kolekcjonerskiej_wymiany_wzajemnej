/*******************************
 Kontroler służący do sterowania logiką interfejsu symulatora
 *******************************/
package com.example.algorytmy_kolekcjonerskiej_wymiany_wzajemnej;

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
    private Button OKButton, InputButton, SaveDirectoryButton;

    @FXML
    private Label FileLabel, SaveDirectoryLabel;

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
        OKButton.setDisable(b);
        InputButton.setDisable(b);
        SaveDirectoryButton.setDisable(b);
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

    //Funkcja konfigurująca okno dialogowe wyboru folderu zapisu
    private static void configureDirectoryChooser(final DirectoryChooser directoryChooser){
        directoryChooser.setTitle("Wybierz folder zapisu");
        directoryChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
    }

    //Funkcja wywołująca okno dialogowe wyboru pliku odczytu
    @FXML
    protected void chooseInputFile(){
        file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            FileLabel.setText(file.getAbsolutePath());
        }
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

    @FXML
    protected void startComputing() throws Exception {
        setModeOfButtons(true);
        /*
        ReadWriteFile rw = new ReadWriteFile();
        rw.readCSVFile(file, csvParser);
        rw.prepareFile();

        Algorithm a = new Algorithm(rw.numberOfUsers, rw.numberOfSeries, rw.numberOfObjects);
        a.setPrices(rw.Prices, rw.Bonus);
        a.setPropositions(rw.Propositions);
        a.completeAdjacency();

        rw.endWriting(chosenDirectory);
         */
        readCSVFile(file, csvParser);
        prepareFile();


        Algorithm a = new Algorithm(numberOfUsers, numberOfSeries, numberOfObjects);
        a.setPrices(Prices, Bonus);
        a.setPropositions(Propositions);
        a.completeAdjacency();


        endWriting(chosenDirectory);

        setModeOfButtons(false);
    }

    //Funkcja inicjalizująca, uruchamiana raz na początku
    public void initialize(){
        configureFileChooser(fileChooser);
        configureDirectoryChooser(directoryChooser);
        FileLabel.setText(chosenFile);
        SaveDirectoryLabel.setText(chosenDirectory);
    }

/*
    @FXML
    protected void sendData(ActionEvent event) throws Exception{
        try {
            int collectors = Integer.parseInt(this.Collectors.getText());
            int things = Integer.parseInt(this.Things.getText());

            FXMLLoader loader = new FXMLLoader(getClass().getResource("scene2.fxml"));
            Parent root = (Parent) loader.load();

            Scene2 scene2 = loader.<Scene2>getController();
            scene2.getData(collectors, things);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Symulator");
            stage.show();

            Stage thisStage = (Stage) OKButton.getScene().getWindow();
            thisStage.close();
        }catch (Exception e){
            e.printStackTrace();
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Błąd");
            error.setHeaderText(null);
            error.setContentText("Wprowadzono nieprawidłowe dane");
            error.show();
        }
    }

 */
}
