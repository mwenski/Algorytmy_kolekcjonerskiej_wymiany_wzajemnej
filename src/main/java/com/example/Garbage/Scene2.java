package com.example.Garbage;

import java.lang.*;
import java.util.*;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Scene2 {
    public int collectors, things;
    Random random = new Random();

    @FXML
    private GridPane PricesInput, ThingsInput, ThingsOutput;

    private GridPane ThingsInputPane = new GridPane();
    private GridPane ThingsOutputPane = new GridPane();

    @FXML
    private TableView ExchangeTable, ValuesTable;

    @FXML
    private Button NewButton, StartButton, ReportButton, RandomButton;

    public void getData(int i, int j){
        this.collectors = i;
        this.things = j;
    }

    public void setWidthAndHeightOfTheTableCells(int rowCount, int columnCount, GridPane gridPane, double prefWidth, double prefHeight){
        RowConstraints rc = new RowConstraints();
        rc.setPrefHeight(prefHeight);

        for (int i = 0; i < rowCount; i++) {
            gridPane.getRowConstraints().add(rc);
        }

        ColumnConstraints cc = new ColumnConstraints();
        cc.setPrefWidth(prefWidth);

        for (int i = 0; i < columnCount; i++) {
            gridPane.getColumnConstraints().add(cc);
        }
    }

    public void setPricesInput(){
        setWidthAndHeightOfTheTableCells(things, 2, PricesInput, 120d, 50d);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < things + 1; j++) {
                if (i == 0 && j==0){
                    Label label = new Label("Premia: ");
                    PricesInput.add(label, 0, 0);
                }else if(i == 0){
                    Label label = new Label("Cena przedmiotu #" + String.format("%d", j-1) + ": ");
                    PricesInput.add(label, 0, j);
                }else {
                    TextField inputDataTableCell = new TextField();
                    inputDataTableCell.setPrefSize(40, 20);
                    PricesInput.add(inputDataTableCell, i, j);
                }
            }
        }
    }

    public void setVPane(GridPane gridPane){
        GridPane VPane = new GridPane();
        Label VLabel = new Label("Kolekcjonerzy");
        VLabel.setStyle("vertical");
        VPane.add(VLabel, 0, 0, 1, collectors);
        for (int i = 0; i < collectors; i++){
            Label label = new Label("#" + String.format("%d", i));
            VPane.add(label, 1, i, 1, 1);
        }
        setWidthAndHeightOfTheTableCells(collectors, 2, VPane, 50d, 30d);
        gridPane.add(VPane, 0, 2, 2, 2);
    }

    public void setHPane(GridPane gridPane){
        GridPane HPane = new GridPane();
        Label HLabel = new Label("Ilość przedmiotów");
        HPane.add(HLabel, 0, 0, things, 1);
        for (int i = 0; i < things; i++){
            Label label = new Label("#" + String.format("%d", i));
            HPane.add(label, i, 1, 1, 1);
        }
        setWidthAndHeightOfTheTableCells(2, things, HPane, 50d, 30d);
        gridPane.add(HPane, 2, 0, things, 2);
    }

    public void setThingsInput(){
        setVPane(ThingsInput);
        setHPane(ThingsInput);

        for (int i = 0; i < collectors; i++){
            for (int j = 0; j < things; j++){
                TextField inputTableCell = new TextField();
                //inputTableCell.setPrefSize(10, 5);
                ThingsInputPane.add(inputTableCell, j, i, 1, 1);
            }
        }
        setWidthAndHeightOfTheTableCells(collectors, things, ThingsInputPane, 50d, 30d);
        ThingsInput.add(ThingsInputPane, 3, 3, things, collectors);
    }

    public void setThingsOutput(){
        setVPane(ThingsOutput);
        setHPane(ThingsOutput);

        for (int i = 0; i < collectors; i++){
            for (int j = 0; j < things; j++){
                Label label = new Label("0");
                //inputTableCell.setPrefSize(40, 20);
                ThingsOutputPane.add(label, j, i, 1, 1);
            }
        }
        setWidthAndHeightOfTheTableCells(collectors, things, ThingsOutputPane, 50d, 30d);
        ThingsOutput.add(ThingsOutputPane, 3, 3, things, collectors);
    }

    public void setExchangeTableColumns(){
        for (int i = 0; i < things; i++){
            TableColumn iThings = new TableColumn("#"+ String.format("%d", i));
            ExchangeTable.getColumns().add(iThings);
        }
            TableColumn giver = new TableColumn("ID przekazującego");
            ExchangeTable.getColumns().add(giver);
            TableColumn getter = new TableColumn("ID obdarowanego");
            ExchangeTable.getColumns().add(getter);

    }

    public void setValuesTableColumns(){
        for (int i = 0; i < collectors; i++){
            TableColumn iCollectors = new TableColumn("#"+ String.format("%d", i));
            ValuesTable.getColumns().add(iCollectors);
        }
    }

    @FXML
    protected void newCase(ActionEvent event) throws Exception{
        /*
        Stage stage;
        Parent root;

        stage = (Stage) NewButton.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("scene.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();*/
        FXMLLoader loader = new FXMLLoader(getClass().getResource("scene.fxml"));
        Parent root = (Parent) loader.load();

        Stage stage = new Stage();
       // stage.setScene(new Scene(root));
        stage.setTitle("Symulator");
        stage.show();

        Stage thisStage = (Stage) NewButton.getScene().getWindow();
        thisStage.close();
    }

    public TextField getTextFieldByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        TextField result = null;
        for(Node node : gridPane.getChildren()) {
            if(gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                result = (TextField) node;
                break;
            }
        }
        return result;
    }

    public Label getLabelByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        Label result = null;
        for(Node node : gridPane.getChildren()) {
            if(gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                result = (Label) node;
                break;
            }
        }
        return result;
    }

    @FXML
    private void setRandomValues(){
        for (int i = 0; i < collectors; i++) {
            for (int j = 0; j < things; j++) {
                getTextFieldByRowColumnIndex(i, j, ThingsInputPane).setText(String.format("%d", random.nextInt(20)));
            }
        }
        for (int i = 0; i < things+1; i++) {
            getTextFieldByRowColumnIndex(i, 1, PricesInput).setText(String.format("%f", random.nextDouble()*10));
        }
    }

    @FXML
    private void generateReport(){

    }

    @FXML
    private void startComputing(){
        String[][] actualThings = new String[collectors][things];
        for (int i = 0; i < collectors; i++) {
            for (int j = 0; j < things; j++) {
                actualThings[i][j] = getTextFieldByRowColumnIndex(i, j, ThingsInputPane).getText();
            }
        }
        for (int i = 0; i < collectors; i++){
            for (int j = 0; j < things; j++){
                getLabelByRowColumnIndex(i, j, ThingsOutputPane).setText(actualThings[i][j]);
                //inputTableCell.setPrefSize(40, 20);
            }
        }
    }

    public void initialize(){
        Platform.runLater(() -> {
                    setPricesInput();
                    setThingsInput();
                    setThingsOutput();
                    setExchangeTableColumns();
                    setValuesTableColumns();
                }
        );
    }
}
