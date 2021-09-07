package com.example.algorytmy_kolekcjonerskiej_wymiany_wzajemnej;

import java.io.IOException;
import java.lang.*;
import java.util.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Scene1 {
    @FXML
    private Button OKButton;

    @FXML
    private TextField Collectors, Things;

    @FXML
    protected void sendData(ActionEvent event) throws Exception{
        /*
        Stage stage;
        Parent root;

        stage = (Stage) OKButton.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("scene2.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

         */
        int collectors = Integer.parseInt(this.Collectors.getText());
        int things = Integer.parseInt(this.Things.getText());

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("scene2.fxml"));
            Parent root = (Parent) loader.load();

            Scene2 scene2 = loader.<Scene2>getController();
            scene2.getData(collectors, things);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Symulator");
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
