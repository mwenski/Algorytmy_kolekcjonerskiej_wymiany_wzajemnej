package com.example.algorytmy_kolekcjonerskiej_wymiany_wzajemnej;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.*;
import java.util.*;

import com.opencsv.*;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Scene1 {
    @FXML
    private Button OKButton, InputButton, SaveDirectoryButton;

    @FXML
    private TextField Collectors, Things;

    @FXML
    private Label FileLabel, SaveDirectoryLabel;

    final FileChooser fileChooser = new FileChooser();
    final DirectoryChooser directoryChooser = new DirectoryChooser();
    public File file;
    final Stage stage = new Stage();
    CSVParser csvParser = new CSVParserBuilder().withSeparator(',').build();

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

    private static void configureDirectoryChooser(final DirectoryChooser directoryChooser){
        directoryChooser.setTitle("Wybierz folder zapisu");
        directoryChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
    }

    @FXML
    protected void chooseInputFile(){
        file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            FileLabel.setText(file.getAbsolutePath());
        }
    }

    @FXML
    protected void chooseDirectoryForSave(){
        final File selectedDirectory = directoryChooser.showDialog(stage);
        if (selectedDirectory != null) {
            SaveDirectoryLabel.setText(selectedDirectory.getAbsolutePath());
        }
    }

    @FXML
    protected void startComputing(){
        try(CSVReader reader = new CSVReaderBuilder(new FileReader(file))
                                                    .withCSVParser(csvParser)
                                                    .build()){
            String[] lineInArray;
            int intLine = 0;

            while ((lineInArray = reader.readNext()) != null) {
                System.out.println(lineInArray[0] +"|"+ lineInArray[1] +"|"+ lineInArray[2] +"|" +lineInArray[3]+"|" +lineInArray[8]);
                if(intLine == 1){

                }
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

    public void initialize(){
        configureFileChooser(fileChooser);
        configureDirectoryChooser(directoryChooser);
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
