# Classes
## ClinicManagerMain
```java
package com.example.cs213_project3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClinicManagerMain extends Application {
	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(ClinicManagerMain.class.getResource("clinic-view.fxml"));
		Scene scene = new Scene(fxmlLoader.load(), 320, 240);
		stage.setTitle("RU Clinic Scheduler");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch();
	}
}
```
- `main()`
- `launch()` to run project
## ClinicManagerController
```java
package com.example.cs213_project3;  
  
import javafx.fxml.FXML;  
import javafx.scene.control.Label;  
  
public class ClinicManagerController {  
    @FXML  
    private Label welcomeText;  
  
    @FXML  
    protected void onHelloButtonClick() {  
        welcomeText.setText("you are an idiot.");  
    }  
}
```
- event handlers
# JavaFX
## clinic-view.fxml
```xml
<?xml version="1.0" encoding="UTF-8"?>  
  
<?import javafx.geometry.Insets?>  
<?import javafx.scene.control.Label?>  
<?import javafx.scene.layout.VBox?>  
  
<?import javafx.scene.control.Button?>  
<VBox alignment="CENTER" spacing="20.0" xmlns:fx="http://javafx.com/fxml"  
      fx:controller="com.example.cs213_project3.ClinicManagerController">  
    <padding>
	    <Insets bottom="20.0" left="20.0" right="20.0" top="20.0"/>
    </padding>  
    
    <Label fx:id="welcomeText"/>  
    <Button text="definitely don't click this button..." onAction="#onHelloButtonClick"/>  
</VBox>
```
# Notes and Checklist
## Notes
1. Always cs213_project3 as a separate Project rather than opening the CS213_soft_meth directory in IntelliJ
	1. the super directory won't be able to detect the openjfx SDK
2. Switched from openjfx SDK version ~25 to ~23 for LTS
	1. clinic-view.fxml was created with non-LTS version, so I created a new project to avoid conflicts
3. source code is included in com.example.cs213_project3 (refer to comboBox)
4. **ClinicManagerMain.java** is only used to launch the application, no functionality beyond that AFAIK so far
5. according to HelloController.java in comboBox, a ComboBox object is set as instance variable and used to display the list of providers
6. can add a **logo** (past image in src, set icon in application.java)
7. event-driven: **xml element** is linked to a **handler** -> handler controls a **UI component** -> UI component is linked to **xml element**
8. the GUI for RU Clinic Scheduler will replace ClinicManager.java
	1. each private method in ClinicManager was effectively an event handler, except a handler takes an Event object and responds to it
## General Checklist
- [ ] All constants are `static final`
- [ ] No magic numbers
- [ ] Included class comments
- [ ] Included constructor comments
- [ ] Created "doc" directory
- [ ] Created Javadoc documentation html files
- [ ] `.equals()`, `.compareTo()`, and `.toString()` are all overridden in non-enum classes
- [ ] Accessibility of each method and variable is appropriate to the necessary function?
- [ ] Methods are at-most 40 lines
- [ ] Created *two* packages and their names are lowercase
- [ ] Not importing * from any packages
- [ ] You CANNOT use sysout `System.in` or `System.out` statements in ALL classes, EXCEPT the user interface class **ClinicManager.java** or the testbed `main()` methods. -2 points for each violation, with a maximum of -10 points.
	- REMOVE SYSOUT FROM LIST CLASS
- [ ] You must always add the `@Override` tag for overriding methods or -2 points for each violation.
- [ ] Polymorphism is required, i.e., dynamic binding for all the `equals()`, `compareTo()`, `toString()`, and the `rate()` method, and the methods in the generic `List<E>` class, or you will lose 10 points.
- [ ] You MUST implement or refactor the Java classes below. -5 points for each class missing or NOT used. You should define necessary constant names in UPPERCASE letters and DO NOT use MAGIC NUMBERs, or you will lose points listed in the Coding Standard.
- [ ] The UML Class diagram is generated and included in the project directory
	- [ ] For each rectangle (class), include ONLY the instance variables and public methods.
- [ ] Not using sysout or sysin anywhere in any of the classes (All read and write is done on the GUI).
- [ ] Must use TextField, Button, RadioButton, TextArea (), TableView (listing appointments), TabPane, and GridPane
## Debugging
- [ ] Failed to open the file in the Scene Builder
	- installed Scene Builder from Gluon site
	- "C:\Program Files\JetBrains\IntelliJ IDEA 2024.2.1\plugins\javaFX\lib\rt\sceneBuilderBridge.jar"
	- C:\Users\Harb\AppData\Local\SceneBuilder\SceneBuilder.exe (correct path to executable)
	- pointed to correct path and was able to open SceneBuilder, but only externally
- [ ] billing statement error
- [ ] this.test, this.col_county, this.col_zip is null
	- added fx:id in fxml
	- RESOLVED
# GUI Design Overview
## based on prof demo
1. use tabs instead of switching views
## Process
1. user is presented with list of commands
	1. radio button is the most appropriate for this since user is **picking only one option**, but prof said we should use radio button if there are max 3-4 options
	2. maybe use list view or combobox
```
select an option: 
1. schedule an appointment (office or imaging)
	1. office
	2. imaging
2. reschedule or cancel an existing appointment
	1. reschedule
	2. cancel
3. print a list of appointments
	1. only office
	2. only imaging
	3. all (ordered by appointment)
	4. all (ordered by patient)
	5. all (ordered by location)
4. print a list of credit amounts for each provider
5. print a billing statement
6. quit
```
2. scheduling:
	1. select appointment type (two options)
	2. select
		1. date with datepicker
		2. time using an observable list that iterates through the timeslots array
	3. enter patient profile
		1. first name (text field)
		2. last name (text field)
		3. DOB (datepicker)
	4. depending on appointment type:
		1. display list of providers with listview - get corresponding NPI
		2. display list of imaging services with listview
3. rescheduling or canceling
	1. select reschedule or cancel
	2. select date and time
		1. date with datepicker
		2. time using an observable list that iterates through the timeslots array
	3. enter patient profile
		1. first name (text field)
		2. last name (text field)
		3. DOB (datepicker)
	4. depending on appointment type:
		1. cancel if cancel
		2. if reschedule, select new timeslot using observable list
4. print a list of appointments
	1. select the variable by which appointments should be ordered (5 options)
	2. sort the lists accordingly and display using the list view UI control or just text field **tableview**
5. billing statement
	1. display the billing statement in  **tableview**
6. quit
	1. exit application
### Note - Appointment Type
- Office: display list of providers with ListView
- Imaging: display list of imaging services with ListView
# Test Specification
1. Design test cases to test the functionality through the GUI. Test the software as a black box, focusing on the I/O behaviors.
	1. this specification should follow the same format as the specification from Project 1 (See Below)
2. You MUST create a test specification and design at least 15 test cases for testing, each focusing on a specific testing objective. The Test Specification is worth 15 points. You MUST use the table template in the Coding Standard to organize the test cases, or you will get 0 points for this part.
3. Use the test cases in Project2TestCases.txt as a reference to design your test cases and run the tests.
4. The GUI shall reject any invalid data. **Proper error messages must be displayed on the GUI**. You will lose 2 points for each invalid condition not rejected, or each error message not properly displayed.
5. You are responsible for thoroughly testing your software **beyond the 15 test cases** required. Your software must always run in a sane state and should not crash in any circumstances. The grader will try to produce exceptions while using the GUI. You must handle all exceptions. The software shall continue to run until the grader stops the execution or closes the GUI window. You will lose 2 points for each exception not caught.