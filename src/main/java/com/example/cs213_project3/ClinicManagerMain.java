package com.example.cs213_project3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main class for the Clinic Manager application.
 */
public class ClinicManagerMain extends Application {
    
    /**
     * This is the entry point for the JavaFX application. It is called
     * after the Application class is loaded. It loads the FXML file
     * for the GUI and displays the GUI.
     *
     * @param  stage  the primary stage for this application, onto which
     *                the application scene can be set.
     *                Applications may create other stages, if needed, but
     *                they should not set a different scene on the primary
     *                stage than the one that is set by the JavaFX
     *                application thread during its launch.
     *
     * @throws IOException if an error occurs loading the FXML file.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClinicManagerMain.class.getResource("clinic-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 810, 630); 
        stage.setTitle("RU Clinic Scheduler");
        stage.setScene(scene);
        stage.show();
    }

/**
 * The main method to launch the Clinic Manager application.
 *
 * @param args command-line arguments (not used)
 */
    public static void main(String[] args) {
        launch();
    }
}