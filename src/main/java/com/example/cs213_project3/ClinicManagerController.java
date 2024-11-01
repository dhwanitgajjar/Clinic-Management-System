package com.example.cs213_project3;

import com.example.cs213_project3.util.Location;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ClinicManagerController {
    @FXML
    private Label welcomeText;

    @FXML
    private TextArea output;

    @FXML
    TableView tbl_location;

    @FXML
    private ChoiceBox<String> test, timeslots;

    @FXML
    private TableColumn<Location, String> col_zip, col_county;

    @FXML
    private TabPane tabs;

    @FXML
    private Tab tab1, tab2, tab3;

    /**
     * This method will be performed automatically after the fxml is loaded.
     * Write code to set the initial data for the GUI objects.
     * Typically, set a list of objects from the backend to the frontend objects,
     * such as ComboBox (dropdown), or ListView.
     */
    public void initialize() {
        ObservableList<Location> locations =
                FXCollections.observableArrayList(Location.values());
        tbl_location.setItems(locations);
        col_zip.setCellValueFactory(new PropertyValueFactory<>("zip"));
        col_county.setCellValueFactory(new PropertyValueFactory<>("county"));
        ObservableList<String> testList = FXCollections.observableArrayList("hello", "yep");
        test.setItems(testList);
        String[] slots = new String [Timeslot.getSlots().length];
        int i = 0;
        for (int[] pair: Timeslot.getSlots()) {
            slots[i] = new Timeslot(pair[0], pair[1]).toString();
            i++;
        }
        ObservableList<String> slots_list = FXCollections.observableArrayList(slots);
        timeslots.setItems(slots_list);
    }


    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("you are an idiot.");
    }

    @FXML
    protected void skibidy_toilet() {
        output.appendText("skibbidy toilet\n");
    }


}