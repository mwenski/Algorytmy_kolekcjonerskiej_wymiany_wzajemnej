module com.example.algorytmy_kolekcjonerskiej_wymiany_wzajemnej {
    requires javafx.controls;
    requires javafx.fxml;
    requires opencsv;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;

    opens com.example.algorytmy_kolekcjonerskiej_wymiany_wzajemnej to javafx.fxml;
    exports com.example.algorytmy_kolekcjonerskiej_wymiany_wzajemnej;
}