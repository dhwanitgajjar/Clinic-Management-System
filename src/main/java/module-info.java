module com.example.cs213_project3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cs213_project3 to javafx.fxml;
    exports com.example.cs213_project3;
}