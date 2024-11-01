package com.example.cs213_project3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClinicManagerMain extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //load fxml file
        FXMLLoader fxmlLoader = new FXMLLoader(ClinicManagerMain.class.getResource("clinic-view.fxml"));
        //create a scene object
        //fxmlLoader.load() returns a Parent node (root node of the Scene-Graph)
        Scene scene = new Scene(fxmlLoader.load(), 810, 630); //width, height
        //configure and display scene
        stage.setTitle("RU Clinic Scheduler");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}