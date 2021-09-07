package com.example.algorytmy_kolekcjonerskiej_wymiany_wzajemnej;

import java.lang.*;
import java.util.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Scene2 {
    public int collectors, things;

    @FXML
    private GridPane PricesInput, ThingsInput, ThingsOutput;

    @FXML
    private TableView ExchangeTable, ValuesTable;

    @FXML
    private Button NewButton, StartButton, ReportButton;

    public void getData(int i, int j){
        this.collectors = i;
        this.things = j;
        System.out.println(collectors);
    }

    public void setPricesInput(){
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
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

    public void setThingsInput(){
        GridPane widthPane = new GridPane();
        GridPane heightPane = new GridPane();
        GridPane centralPane = new GridPane();

        Label labelWidth = new Label("Ilość przedmiotów");
        widthPane.add(labelWidth, 0, 0, 3, 1);
        for (int i = 0; i < 3; i++){
            Label label = new Label("#" + String.format("%d", i));
            widthPane.add(label, i, 1, 1, 1);
        }
        ThingsInput.add(widthPane, 2, 0, 3, 2);

        Label labelHeight = new Label("Użytkownicy");
        heightPane.add(labelHeight, 0, 0, 1, 2);
        for (int i = 0; i < 2; i++){
            Label label = new Label("#" + String.format("%d", i));
            heightPane.add(label, 1, i, 1, 1);
        }
        ThingsInput.add(heightPane, 0, 2, 2, 2);

        for (int i = 0; i < 2; i++){
            for (int j = 0; j < 3; j++){
                TextField inputTableCell = new TextField();
                //inputTableCell.setPrefSize(40, 20);
                centralPane.add(inputTableCell, j, i, 1, 1);
            }
        }
        ThingsInput.add(centralPane, 3, 3, 3, 2);
    }

    public void setThingsOutput(){
        GridPane widthPane = new GridPane();
        GridPane heightPane = new GridPane();
        GridPane centralPane = new GridPane();

        Label labelWidth = new Label("Ilość przedmiotów");
        widthPane.add(labelWidth, 0, 0, 3, 1);
        for (int i = 0; i < 3; i++){
            Label label = new Label("#" + String.format("%d", i));
            widthPane.add(label, i, 1, 1, 1);
        }
        ThingsOutput.add(widthPane, 2, 0, 3, 2);

        Label labelHeight = new Label("Użytkownicy");
        heightPane.add(labelHeight, 0, 0, 1, 2);
        for (int i = 0; i < 2; i++){
            Label label = new Label("#" + String.format("%d", i));
            heightPane.add(label, 1, i, 1, 1);
        }
        ThingsOutput.add(heightPane, 0, 2, 2, 2);

        for (int i = 0; i < 2; i++){
            for (int j = 0; j < 3; j++){
                Label label = new Label("0");
                //inputTableCell.setPrefSize(40, 20);
                centralPane.add(label, j, i, 1, 1);
            }
        }
        ThingsOutput.add(centralPane, 3, 3, 3, 2);
    }

    @FXML
    protected void newCase(ActionEvent event) throws Exception{
        Stage stage;
        Parent root;

        stage = (Stage) NewButton.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("scene1.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void initialize(){
        setPricesInput();
        setThingsInput();
        setThingsOutput();
        System.out.println(collectors);
        System.out.println(things);
    }



}
