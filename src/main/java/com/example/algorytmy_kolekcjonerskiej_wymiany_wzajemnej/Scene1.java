package com.example.algorytmy_kolekcjonerskiej_wymiany_wzajemnej;

import java.io.File;
import java.lang.*;

import com.example.ReadWriteFile.*;
import com.opencsv.*;

import javafx.fxml.FXML;
import javafx.scene.control.*;
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

    public File file = new File(System.getProperty("user.dir") + "\\Test.csv");
    public File selectedDirectory = new File(System.getProperty("user.home")); //PLIK

    public String chosenFile = file.getAbsolutePath();
    public String chosenDirectory = selectedDirectory.getAbsolutePath(); //ŚCIEŻKA

    final FileChooser fileChooser = new FileChooser();
    final DirectoryChooser directoryChooser = new DirectoryChooser();
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
        selectedDirectory = directoryChooser.showDialog(stage);
        if (selectedDirectory != null) {
            SaveDirectoryLabel.setText(selectedDirectory.getAbsolutePath());
            chosenDirectory = selectedDirectory.getAbsolutePath();
        }
    }

    @FXML
    protected void startComputing() throws Exception {
        ReadWriteFile r = new ReadWriteFile();
        r.readCSVFile(file, csvParser);
        r.endWriting(chosenDirectory);
    }

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
