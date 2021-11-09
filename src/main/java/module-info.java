module com.example.algorytmy_kolekcjonerskiej_wymiany_wzajemnej {
    requires javafx.controls;
    requires javafx.fxml;
    requires opencsv;
    requires poi.ooxml;
    requires poi;
    requires poi.ooxml.schemas;

    opens com.example.Simulator to javafx.fxml;
    exports com.example.Simulator;
    exports com.example.ReadWriteFile;
    opens com.example.ReadWriteFile to javafx.fxml;
    exports com.example.Algorithms;
    opens com.example.Algorithms to javafx.fxml;
}