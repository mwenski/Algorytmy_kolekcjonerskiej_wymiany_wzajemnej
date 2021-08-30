module com.example.algorytmy_kolekcjonerskiej_wymiany_wzajemnej {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.algorytmy_kolekcjonerskiej_wymiany_wzajemnej to javafx.fxml;
    exports com.example.algorytmy_kolekcjonerskiej_wymiany_wzajemnej;
}