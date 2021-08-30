package com.example.algorytmy_kolekcjonerskiej_wymiany_wzajemnej;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class Symulator extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Symulator obliczeniowy");

        GridPane inputTable = new GridPane();
        GridPane dataTable = new GridPane();

        for (int i = 0; i < 2; i++){
            for (int j = 0; j < 3; j++){
                TextField inputTableCell = new TextField();
                inputTable.add(inputTableCell, j, i);
            }
        }

        for (int j = 0; j < 3; j++){
            TextField inputDataTableCell = new TextField();
            dataTable.add(inputDataTableCell, 0, j);
        }
        /*
        Button button = new Button("Click to get text");

        button.setOnAction(action -> {
            System.out.println(textField.getText());
        });

        HBox hbox = new HBox(textField, button);
         */

        Scene scene = new Scene(inputTable, 240, 100);
        //TODO: Pokombinować z tymi gridami
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
