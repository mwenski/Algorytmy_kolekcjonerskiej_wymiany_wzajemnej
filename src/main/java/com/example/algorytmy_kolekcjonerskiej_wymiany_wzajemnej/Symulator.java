package com.example.algorytmy_kolekcjonerskiej_wymiany_wzajemnej;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class Symulator extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Symulator obliczeniowy");

        BorderPane borderPane = new BorderPane();
        HBox topHBox = topHBox();
        VBox leftVBox = leftVBox();
        VBox rightVBox = rightVBox();
        HBox bottomHBox = bottomHBox();
        borderPane.setTop(topHBox);
        borderPane.setLeft(leftVBox);
        borderPane.setRight(rightVBox);
        borderPane.setBottom(bottomHBox);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public HBox topHBox(){
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(15, 12, 15, 12));
        hBox.setSpacing(10);

        Button buttonNew = new Button("Nowy");
        buttonNew.setPrefSize(100, 20);

        Button buttonReport = new Button("Wygeneruj raport");
        buttonReport.setPrefSize(200, 20);

        hBox.getChildren().addAll(buttonNew, buttonReport);

        return hBox;
    }

    public VBox leftVBox(){
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(15, 12, 15, 12));
        vBox.setSpacing(10);

        GridPane dataTable = new GridPane();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                if (i == 0 && j==0){
                    Label label = new Label("Premia: ");
                    dataTable.add(label, 0, 0);
                }else if(i == 0){
                    Label label = new Label("Cena przedmiotu #" + String.format("%d", j-1) + ": ");
                    dataTable.add(label, 0, j);
                }else {
                    TextField inputDataTableCell = new TextField();
                    inputDataTableCell.setPrefSize(40, 20);
                    dataTable.add(inputDataTableCell, i, j);
                }
            }
        }

        GridPane inputTable = new GridPane();
        GridPane widthPane = new GridPane();
        GridPane heightPane = new GridPane();
        GridPane centralPane = new GridPane();

        Label labelWidth = new Label("Ilość przedmiotów");
        widthPane.add(labelWidth, 0, 0, 3, 1);
        for (int i = 0; i < 3; i++){
            Label label = new Label("#" + String.format("%d", i));
            widthPane.add(label, i, 1, 1, 1);
        }
        inputTable.add(widthPane, 2, 0, 3, 2);

        Label labelHeight = new Label("Użytkownicy");
        heightPane.add(labelHeight, 0, 0, 1, 2);
        for (int i = 0; i < 2; i++){
            Label label = new Label("#" + String.format("%d", i));
            heightPane.add(label, 1, i, 1, 1);
        }
        inputTable.add(heightPane, 0, 2, 2, 2);

        for (int i = 0; i < 2; i++){
            for (int j = 0; j < 3; j++){
                TextField inputTableCell = new TextField();
                //inputTableCell.setPrefSize(40, 20);
                centralPane.add(inputTableCell, j, i, 1, 1);
            }
        }
        inputTable.add(centralPane, 3, 3, 3, 2);

        //inputTable.setMinSize(400,400);
        //inputTable.setMaxSize(400,400);


        int rowCount = 4;
        int columnCount = 5;

        RowConstraints rc = new RowConstraints();
        rc.setPercentHeight(500d / rowCount);

        for (int i = 0; i < rowCount; i++) {
            inputTable.getRowConstraints().add(rc);
        }

        ColumnConstraints cc = new ColumnConstraints();
        cc.setPercentWidth(3000d / columnCount);

        for (int i = 0; i < columnCount; i++) {
            inputTable.getColumnConstraints().add(cc);
        }


        for (int i = 3; i < 6; i++){
            Label label1 = new Label("#" + String.format("%d", i));
            inputTable.add(label1, i, 1, 1, 1);
        }
        for (int i = 3; i < 5; i++){
            Label label2 = new Label("#" + String.format("%d", i));
            inputTable.add(label2, 1, i, 1, 1);
        }

        for (int i = 3; i < 5; i++){
            for (int j = 3; j < 6; j++){
                TextField inputTableCell = new TextField();
                //inputTableCell.setPrefSize(40, 20);
                inputTable.add(inputTableCell, 3, 3, 1, 1);
            }
        }



        vBox.getChildren().addAll(dataTable, inputTable);

        return vBox;
    }

    public VBox rightVBox(){
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(15, 12, 15, 12));
        vBox.setSpacing(10);

        GridPane firstTable = new GridPane();
        for (int i = 0; i < 2; i++){
            for (int j = 0; j < 3; j++){
                TextField inputFirstCell = new TextField();
                inputFirstCell.setPrefSize(40, 20);
                firstTable.add(inputFirstCell, j, i);
            }
        }

        GridPane lastTable = new GridPane();
        for (int i = 0; i < 2; i++){
            for (int j = 0; j < 3; j++){
                TextField inputLastCell = new TextField();
                inputLastCell.setPrefSize(40, 20);
                lastTable.add(inputLastCell, j, i);
            }
        }

        vBox.getChildren().addAll(firstTable, lastTable);

        return vBox;
    }

    public HBox bottomHBox(){
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(15, 12, 15, 12));
        hBox.setSpacing(10);

        Button buttonNew = new Button("Start");
        buttonNew.setPrefSize(100, 20);

        Button buttonReport = new Button("Wygeneruj raport");
        buttonReport.setPrefSize(200, 20);

        hBox.getChildren().addAll(buttonNew, buttonReport);

        return hBox;
    }
}
