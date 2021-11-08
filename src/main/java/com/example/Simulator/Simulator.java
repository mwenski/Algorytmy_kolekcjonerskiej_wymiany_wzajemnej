/*******************************
 Główna klasa JavaFX,
 uruchamiająca cały symulator i aplikację okienkową zaprojektowaną w pliku FXML
 *******************************/
package com.example.Simulator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Simulator extends Application {

    //TODO: Funkcja wywołująca plik ze zdefiniowanym oknem symulatora (wymyśl lepszy opis)
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Simulator.class.getResource("scene.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Symulator");
        stage.setScene(scene);
        stage.show();
    }

    //Funkcja uruchamiająca aplikację
    public static void main(String[] args) {
        launch();
    }
}
