package com.example.algorytmy_kolekcjonerskiej_wymiany_wzajemnej;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onHelloButtonClick2() {
        welcomeText.setText("Hajduszoboszlo!");
    }
}