module com.example.chattutorial {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml.crypto;


    opens com.example.chattutorial.client to javafx.graphics;
    exports com.example.chattutorial.client;
}