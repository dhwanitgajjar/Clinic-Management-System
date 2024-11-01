package com.example.cs213_project3;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class ClinicManagerController {
    @FXML
    private Label welcomeText;

    @FXML
    private TextArea output;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("you are an idiot.");
    }

    @FXML
    protected void skibidy_toilet() {
        output.appendText("skibbidy toilet\n");
    }
}