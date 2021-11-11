module com.example.algorytmy_kolekcjonerskiej_wymiany_wzajemnej {
    requires javafx.controls;
    requires javafx.fxml;
    requires opencsv;
    requires poi.ooxml;
    requires poi;
    requires poi.ooxml.schemas;

    exports com.example.Simulator;
    exports com.example.ReadWriteFile;
    exports com.example.Algorithms;
    opens com.example.Simulator to javafx.fxml;
    opens com.example.ReadWriteFile to javafx.fxml;
    opens com.example.Algorithms to javafx.fxml;
}