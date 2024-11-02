package com.example.cs213_project3;

import com.example.cs213_project3.util.CircularLinkedList;
import com.example.cs213_project3.util.List;
import com.example.cs213_project3.util.Location;
import com.example.cs213_project3.util.Date;
import com.example.cs213_project3.util.Specialty;
import com.example.cs213_project3.util.Profile;
import com.example.cs213_project3.util.Sort;
import com.example.cs213_project3.util.Visit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

//import javafx.scene.control.ComboBox;
//import javafx.scene.control.TableView;
//import javafx.scene.control.TextArea;
//import javafx.scene.control.TextField;
//import javafx.scene.control.DatePicker;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.Tab;
//import javafx.scene.control.TabPane;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.event.ActionEvent;
//import javafx.scene.control.RadioButton;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Scanner;
import java.util.ArrayList;


public class ClinicManagerController {

    private List<Appointment> appointments; 
    private List<Provider> providers;
    private CircularLinkedList<Technician> technicians;
    private List<Patient> patients;

    private static final int NUM_TOKENS_FOR_APPOINTMENT = 7;
    private static final int NUM_TOKENS_FOR_CANCELATION = 6;
    private static final int FORMAT_THRESHOLD = 1000;


    @FXML
    private TextArea output, appointmentsDisplay, billingStatementDisplay, expectedCreditAmountsDisplay;

    @FXML
    private TextField fname_schedule, lname_schedule,
            fname_reschedule, lname_reschedule,
            fname_cancel, lname_cancel;

    @FXML
    private Button loadedProviders;

    @FXML
    private ComboBox<String> scheduling_timeslot,
            old_timeslot, new_timeslot,
            canceling_timeslot,
            sort_by, appointment_type;
    @FXML
    private ComboBox<Radiology> imaging_service;

    @FXML
    private ComboBox <Provider> doctors_list;

    @FXML
    private DatePicker scheduling_date, scheduling_dob,
            rescheduling_date, rescheduling_dob,
            canceling_date, canceling_dob;

    @FXML
    private RadioButton selectOffice, selectImaging;

    @FXML
    private ToggleGroup appointmentType;

    @FXML
    private TabPane tabs;

    @FXML
    private Tab schedule_tab, reschedule_tab, cancel_tab, appointments_tab, billing_tab, credits_tab;

    /**
     * This method will be performed automatically after the fxml is loaded.
     * Write code to set the initial data for the GUI objects.
     * Typically, set a list of objects from the backend to the frontend objects,
     * such as ComboBox (dropdown), or ListView.
     */
    public void initialize() {
//        ObservableList<Location> locations =
//                FXCollections.observableArrayList(Location.values());
//        tbl_location.setItems(locations);
//        col_zip.setCellValueFactory(new PropertyValueFactory<>("zip"));
//        col_county.setCellValueFactory(new PropertyValueFactory<>("county"));

        this.appointments = new List<>();
        this.providers = new List<>();
        this.technicians = new CircularLinkedList<>();
        this.patients = new List<>();

        String[] slots = new String [Timeslot.getSlots().length];
        int i = 0;
        for (int[] pair: Timeslot.getSlots()) {
            slots[i] = new Timeslot(pair[0], pair[1]).toString();
            i++;
        }
        ObservableList<String> slots_list = FXCollections.observableArrayList(slots);
        scheduling_timeslot.setItems(slots_list);
        old_timeslot.setItems(slots_list);
        new_timeslot.setItems(slots_list);
        canceling_timeslot.setItems(slots_list);

        ObservableList<Radiology> imaging_services = FXCollections.observableArrayList(Radiology.values());
        this.imaging_service.setItems(imaging_services);

        String[] sorting_options_list = {"By Appointment", "By Location", "By Patient"};
        ObservableList<String> sorting_options = FXCollections.observableArrayList(sorting_options_list);
        sort_by.setItems(sorting_options);

        String[] appointment_type_list = {"All Appointments", "Only Office", "Only Imaging"};
        ObservableList<String> appointment_types = FXCollections.observableArrayList(appointment_type_list);
        appointment_type.setItems(appointment_types);

        sort_by.setDisable(true);
    }

    @FXML
    protected void clear() {
        output.clear();

        fname_schedule.clear();
        lname_schedule.clear();
        fname_reschedule.clear();
        lname_reschedule.clear();
        fname_cancel.clear();
        lname_cancel.clear();

        scheduling_date.setValue(null);
        scheduling_dob.setValue(null);
        rescheduling_date.setValue(null);
        rescheduling_dob.setValue(null);
        canceling_date.setValue(null);
        canceling_dob.setValue(null);

        scheduling_timeslot.setValue(null);
        old_timeslot.setValue(null);
        new_timeslot.setValue(null);
        canceling_timeslot.setValue(null);

        selectOffice.setSelected(false);
        selectImaging.setSelected(false);

        doctors_list.setValue(null);
        imaging_service.setValue(null);
    }

    protected String getSchedulingDob() {
        return scheduling_dob.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    }


    protected String getSchedulingDate() {
        return scheduling_date.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    }

    @FXML
    protected void getAppointmentType() {
        if (selectOffice.isSelected()) {
            selectImaging.setSelected(false);
            imaging_service.setDisable(true);
            doctors_list.setDisable(false);
        } else if (selectImaging.isSelected()) {
            selectOffice.setSelected(false);
            doctors_list.setDisable(true);
            imaging_service.setDisable(false);
        }
    }

    @FXML
    protected void toggleSortBy() {
        if (appointment_type.getValue().compareTo("Only Office")==0) {
            sort_by.setDisable(true);
        } else if (appointment_type.getValue().compareTo("Only Imaging")==0) {
            sort_by.setDisable(true);
        } else if (appointment_type.getValue().compareTo("All Appointments")==0) {
            sort_by.setDisable(false);
        }
    }

    @FXML
    protected void printAppointmentsHandler() {
        if (appointment_type.getValue() == null) {
            output.appendText("Please select an appointment type.\n");
        } else if (appointment_type.getValue().compareTo("Only Office")==0) {
            displayOfficeAppointments();
        } else if (appointment_type.getValue().compareTo("Only Imaging")==0) {
            displayImagingAppointments();
        } else if (appointment_type.getValue().compareTo("All Appointments")==0) {
            if (sort_by.getValue() == null) {
                output.appendText("Please select a sorting option.\n");
            } else if (sort_by.getValue().compareTo("By Appointment")==0) {
                printByAppointment();
            } else if (sort_by.getValue().compareTo("By Location")==0) {
                printByLocation();
            } else if (sort_by.getValue().compareTo("By Patient")==0) {
                printByPatient();
            }
        }
    }

    @FXML
    protected void billingStatementHandler() {
        billingStatement();
    }

    @FXML
    protected void expectedCreditAmountsHandler() {
        displayExpectedCreditAmounts();
    }

    /**
     * Finds an appointment in the list that matches the given tokenized input.
     * 
     * @return the appointment object if found, null otherwise
     */
    private Appointment findForCancelation(){
        Date appointmentDate = null;
        Timeslot time = null;
        Date dateOfBirth = null;
        String fname = fname_cancel.getText().trim();
        String lname = lname_cancel.getText().trim();
        Person patient = null;
        
        appointmentDate = Date.createDateWithString(canceling_date.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));

        if (appointmentDate == null) {
            return null;
        }

        if (!Timeslot.isValid(Integer.toString(canceling_timeslot.getSelectionModel().getSelectedIndex()+1))) {
            return null;
        } else {
            time = new Timeslot(Integer.toString(canceling_timeslot.getSelectionModel().getSelectedIndex()+1));
        }

        dateOfBirth = Date.createDateWithString(canceling_dob.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        if (dateOfBirth == null) {
            return null;
        }
        
        Profile patientProfile = new Profile(fname, lname, dateOfBirth);
        patient = new Patient(patientProfile);

        Iterator<Appointment> iterator = this.appointments.iterator();
        Appointment curr = null;

        while (iterator.hasNext()) {
            curr = iterator.next();
            if (curr.getDate().equals(appointmentDate) &&
                curr.getTimeslot().equals(time) &&
                curr.getPatient().equals(patient)) {
                return curr;
            }
        }

        return null;
    }

    private Patient findPatientForCancelation(Profile patientProfile) {
        Iterator<Patient> iterator = patients.iterator();
        Patient curr = null;

        while (iterator.hasNext()) {
            curr = iterator.next();
            if (curr.getProfile().equals(patientProfile)) {
                return curr;
            }
        }

        return null;
    }
    
    /**
     * Cancels an appointment given the tokenized input.
     * 
     */
    @FXML
    private void cancelAppointment() {

        if (fname_cancel.getText().trim().compareTo("") == 0) {
            output.appendText("Missing patient first name.\n");
            return;
        } else if (lname_cancel.getText().trim().compareTo("") == 0) {
            output.appendText("Missing patient last name.\n");
            return;
        } else if (canceling_dob.getValue()==null) {
            output.appendText("Missing patient date of birth.\n");
            return;
        } else if (canceling_date.getValue()==null) {
            output.appendText("Missing appointment date.\n");
            return;
        } else if (canceling_timeslot.getValue()==null) {
            output.appendText("Missing appointment time.\n");
            return;
        }

        Appointment appointment = findForCancelation();

        if (appointment != null && this.appointments.contains(appointment)) {
            this.appointments.remove(appointment);
            output.appendText(appointment.getDate().toString() + 
            " " + appointment.getTimeslot().toString() + 
            " " + appointment.getPatient().toString() + " - appointment has been canceled.\n");

            Patient patient = findPatientForCancelation(appointment.getPatient().getProfile());
            patient.removeVisit(appointment);
        } else {
            output.appendText(canceling_date.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) +
            " " + canceling_timeslot.getValue() +
            " " + fname_cancel + " " + lname_cancel + " " +
            canceling_dob.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) + " - appointment does not exist.\n");
        }

    }

    
    /**
     * Finds an appointment given the tokenized input for rescheduling.
     *
     * @return the matching Appointment object or null if no match is found
     */
    public Appointment findForRescheduling() {

        Date date = Date.createDateWithString(rescheduling_date.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        Timeslot time = new Timeslot(Integer.toString(old_timeslot.getSelectionModel().getSelectedIndex()+1));
        Date dob = Date.createDateWithString(rescheduling_dob.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        Profile patientProfile = new Profile(fname_reschedule.getText().trim(), lname_reschedule.getText().trim(), dob);
        Person patient = new Person(patientProfile);

        Iterator iterator = this.appointments.iterator();
        Appointment curr = null;

        while (iterator.hasNext()) {
            curr = (Appointment) iterator.next();
            if (curr.getDate().equals(date) &&
            curr.getTimeslot().equals(time) && 
            curr.getPatient().compareTo(patient)==0) {
                return curr;
            }

        }

        return null;
    } 

    /**
     * Finds a doctor given the profile.
     * 
     * @param profile the profile of the doctor to find
     * @return the matching Doctor object or null if no match is found
     */
    public Doctor getDoctorWithProfile(Profile profile) {

        Iterator iterator = this.providers.iterator();
        Provider curr = null;

        while (iterator.hasNext()) {
            curr = (Provider) iterator.next();
            if (curr instanceof Doctor) {
                if (curr.getProfile().compareTo(profile)==0) {
                    return (Doctor) curr;
                }
            }
        }
        return null;
    }

    /**
     * Finds a technician given the profile.
     * 
     * @param profile the profile of the technician to find
     * @return the matching Technician object or null if no match is found
     */
    public Technician getTechnicianWithProfile(Profile profile) {

        Iterator iterator = this.technicians.iterator();
        Provider curr = null;

        while (iterator.hasNext()) {
            curr = (Provider) iterator.next();
            if (curr instanceof Technician) {
                if (((Technician) curr).getProfile().equals(profile)) {
                    return (Technician) curr;
                }
            }
        }
        return null;
    }


    /**
     * Prints the technicians in the rotation list to the console.
     * Each technician is separated by " --> ".
     * If the list is empty, prints a message saying so.
     */
    public void getTechnician() {

        if (technicians.getSize() == 0) {
            output.appendText("Rotation list is empty.\n");
            return;
        }
        output.appendText("Rotation list for the technicians.\n");

        Iterator<Technician> iterator = technicians.iterator();

        output.appendText(technicians.getHead().getData());

        while (iterator.hasNext()) {
            output.appendText(" --> ");
            output.appendText(iterator.next().getData());
        }
        output.appendText("\n");
    }

    /**
     * Checks if a provider is available for an appointment at a given time slot.
     * This method is used to check if a provider is available for a given time slot before scheduling an appointment.
     * It checks if the provider is scheduled for another appointment at the same time slot.
     * If the provider is scheduled, it returns false.
     * If the provider is available, it returns true.
     * @param slot the time slot to check for availability
     * @return true if the provider is available, false otherwise
     */
    private boolean providerIsAvailable(Profile patientProfile, Provider doctor, Date date, Timeslot slot) {
        Iterator<Appointment> iterator = this.appointments.iterator();
        Appointment curr = null;
        Patient patient = new Patient(patientProfile);

        while (iterator.hasNext()) {
            curr = iterator.next();

            if (curr.getProvider() instanceof Doctor) {

                Doctor provider = getDoctorWithProfile(curr.getProvider().getProfile());

                if (provider.compareTo(doctor) == 0 &&
                    curr.getTimeslot().compareTo(slot) == 0 &&
                    curr.getDate().compareTo(date)==0 &&
                    !curr.getPatient().equals(patient)) { 
                    return false;
                }
            }
            
        }
        return true;
    }

    /**
     * Checks if an imaging room is available at a given time slot and date.
     * This method is used to check if an imaging room is available for a given time slot and date before scheduling an imaging appointment.
     * It checks if the imaging room is scheduled for another appointment at the same time slot and date.
     * If the imaging room is scheduled, it returns false.
     * If the imaging room is available, it returns true.
     * @param location the location of the imaging room to check for availability
     * @param slot the time slot to check for availability
     * @return true if the imaging room is available, false otherwise
     */
    private boolean imagingRoomIsAvailable(Location location, Timeslot slot) {

        Iterator iterator = this.appointments.iterator();
        Appointment curr = null;

        while (iterator.hasNext()) {
            curr = (Appointment) iterator.next();

            if (curr instanceof Imaging) {
                    if (curr.getDate().toString().compareTo(getSchedulingDate()) == 0 &&
                    curr.getTimeslot().compareTo(slot) == 0 && 
                    ((Provider) curr.getProvider()).getLocation().equals(location) &&
                    ((Imaging) curr).getRoom().name().compareTo(imaging_service.getValue().name())==0) {
                        return false;
                }
            }
        }
        return true;
    }


    /**
     * Checks if a technician is available at a given time slot.
     * This method is used to check if a technician is available for a given time slot before scheduling an imaging appointment.
     * It checks if the technician is scheduled for another appointment at the same time slot.
     * If the technician is scheduled, it returns false.
     * If the technician is available, it returns true.
     * @param profile the profile of the technician to check for availability
     * @param slot the time slot to check for availability
     * @return true if the technician is available, false otherwise
     */
    private boolean techIsAvailable(Profile profile, Timeslot slot) {
        Iterator<Appointment> iterator2 = appointments.iterator();
        Appointment curr2 = null;

        while (iterator2.hasNext()) {
            curr2 = (Appointment) iterator2.next();
            if (curr2.getProvider().getProfile().equals(profile) && curr2.getTimeslot().compareTo(slot) == 0) {
                return false;
            }
        }

        return true;
    }


    /**
     * Assigns a technician to an imaging appointment given the tokenized input for the appointment details.
     * It checks if the technician is available at the given time slot and if the imaging room is available.
     * If both conditions are met, it assigns the technician to the appointment and returns the technician.
     * If no technician can be assigned, it returns null.
se     * @param slot the time slot of the appointment
     * @return the assigned technician or null if no technician can be assigned
     */
    private Technician assignTechnician(Timeslot slot) {
        Iterator<Technician> iterator = technicians.iterator();
        Technician curr = null;

        Technician head = technicians.getCurrent();
        if (techIsAvailable(head.getProfile(), slot)) { 
            if (imagingRoomIsAvailable(head.getLocation(), slot)) { return head; }
        }

        while (iterator.hasNext()) {
            curr = (Technician) iterator.next();
            Profile profile = curr.getProfile();

            boolean currIsAvailable = techIsAvailable(profile, slot);

            if (currIsAvailable) {
                if (imagingRoomIsAvailable(curr.getLocation(), slot)) {
                    technicians.setCurrent(curr.getNext());
                    return curr;
                }
            }

        }
        return null;
    }

    /**
     * Checks if an appointment exists at a given date and time slot.
     * The patient must also be the same.
     * @param appointment the appointment to check for
     * @param newTime the time slot to check for
     * @return true if the appointment exists, false otherwise
     */
    public boolean appointmentExists(Appointment appointment, Timeslot newTime) {

        Iterator iterator = this.appointments.iterator();
        Appointment curr = null;

        while (iterator.hasNext()) {
            curr = (Appointment) iterator.next();

            if (curr.getDate().equals(appointment.getDate()) &&
            curr.getTimeslot().equals(newTime) && 
            curr.getPatient().equals(appointment.getPatient())) {
                return true;
            }
        }

        return false;
    }


    /**
     * Updates the time slot of an appointment in the list.
     * This method is used to reschedule an appointment.
     * It creates a new appointment with the same date, patient, and provider
     * as the old appointment, but with the new time slot.
     * It then replaces the old appointment with the new one in the list.
     * @param old the appointment to update
     * @param newTime the new time slot to update the appointment with
     */
    private void updateTimeslot(Appointment old, Timeslot newTime) {

        Appointment newApp = new Appointment(old.getDate(), newTime, old.getPatient(), old.getProvider());

        int index = this.appointments.indexOf(old);
        this.appointments.set(index, newApp);
    }

    
    /**
     * Reschedules an appointment given the tokenized input for the appointment details.
     * It finds the appointment in the list by matching the date, timeslot, first name, last name, and date of birth.
     * If the appointment exists, it checks if the new time slot is valid and if the appointment is not already
     * scheduled at the same time.
     * If the appointment is not already scheduled at the same time, it updates the time slot of the appointment
     * in the list.
     * If the appointment is already scheduled at the same time, it prints an error message.
     * If the appointment does not exist, it prints an error message.
     */
    @FXML
    private void rescheduleAppointment() {

        if (fname_reschedule.getText().trim().compareTo("") == 0) {
            output.appendText("Missing patient first name.\n");
            return;
        } else if (lname_reschedule.getText().trim().compareTo("") == 0) {
            output.appendText("Missing patient last name.\n");
            return;
        } else if (rescheduling_dob.getValue()==null) {
            output.appendText("Missing patient date of birth.\n");
            return;
        } else if (rescheduling_date.getValue()==null) {
            output.appendText("Missing appointment date.\n");
            return;
        } else if (old_timeslot.getValue()==null) {
            output.appendText("Missing old appointment time.\n");
            return;
        } else if (new_timeslot.getValue()==null) {
            output.appendText("Missing new appointment time.\n");
            return;
        }

        Appointment appointment = findForRescheduling();
        Timeslot newTime = null;

        if (appointment != null) {
            try {
                if (!Timeslot.isValid(Integer.toString(new_timeslot.getSelectionModel().getSelectedIndex()+1))) {
                    output.appendText(Integer.toString(new_timeslot.getSelectionModel().getSelectedIndex()+1) +" is not a valid time slot.\n");
                    return;
                } else {
                    newTime = new Timeslot(Integer.toString(new_timeslot.getSelectionModel().getSelectedIndex()+1));

                    if (appointmentExists(appointment, newTime)) {
                        output.appendText(fname_reschedule.getText().trim() + " " + lname_reschedule.getText().trim() +
                        " has an existing appointment at " + rescheduling_date.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) +
                        " " + (new Timeslot(Integer.toString(new_timeslot.getSelectionModel().getSelectedIndex()+1))).toString() + ".\n");
                    } else {
                        updateTimeslot(appointment, newTime);
                        output.appendText("Rescheduled to " + rescheduling_date.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) +" "+ newTime.toString()+ " "+
                        appointment.getPatient().toString() +" "+appointment.getProvider().toString()+"\n");
                    }
                }
            } catch (Exception e) {
                output.appendText(e.getMessage() + "\n");
            }
        } else {
            output.appendText(rescheduling_date.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))+" "+ old_timeslot.getValue() +
            " "+fname_reschedule.getText().trim()+" "+lname_reschedule.getText().trim()+" "+rescheduling_dob.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))+" does not exist.\n");
        }
    }

    
    /**
     * Prints a list of all appointments in the list, one per line.
     * Prints a message if the list is empty.
     */
    private void printAppointments() {
        Iterator<Appointment> iterator = appointments.iterator();
        Appointment curr = null;

        while (iterator.hasNext()) {
            curr = iterator.next();
            appointmentsDisplay.appendText(curr.toString() + "\n");
        }
    }

    /**
     * Prints a message indicating the end of the list.
     */
    private void endOfList() {
        output.appendText("** end of list **\n\n");
    } 

    
    /**
     * Prints a list of all office appointments in the list, one per line.
     * Prints a message if the list is empty.
     * Display list of office appointments by county/date/time
     * For sorting, can just use the L key (sorts by county/date/time)
     * then only display appointments of type office
     */ 
    private void displayOfficeAppointments() {

        if (appointments.size() > 0) { 

            appointmentsDisplay.appendText("** List of office appointments ordered by county/date/time. **\n");

            Iterator<Appointment> iterator = appointments.iterator();
            Appointment curr = null;
            Sort.appointment(appointments, 'L');

            while (iterator.hasNext()) {
                curr = iterator.next();
                if (!(curr instanceof Imaging)) {
                    appointmentsDisplay.appendText(curr.toString()+"\n");
                }
            }

            endOfList();
        } else {
            appointmentsDisplay.appendText("The schedule calendar is empty.\n");
        }

    }

    
    /**
     * Prints a list of all imaging appointments in the list, one per line.
     * Prints a message if the list is empty.
     * Display list of imaging appointments by county/date/time
     * For sorting, can just use the L key (sorts by county/date/time)
     * then only display appointments of type imaging
     */ 
    private void displayImagingAppointments() {

        if (appointments.size() > 0) {

            appointmentsDisplay.appendText("** List of radiology appointments ordered by county/date/time.");
         
            Iterator<Appointment> iterator = appointments.iterator();
            Appointment curr = null;
            Sort.appointment(appointments, 'L');

            while (iterator.hasNext()) {
                curr = iterator.next();
                if (curr instanceof Imaging) {
                    appointmentsDisplay.appendText(curr.toString()+"\n");
                }
            }
            endOfList();
        } else {
            appointmentsDisplay.appendText("The schedule calendar is empty.\n");
        }
    }

    /**
     * Prints a list of providers and their expected credit amounts for seeing patients.
     * The list is sorted by provider profile.
     * Prints a message if the list is empty.
     * 
     * Display expected credit amounts for providers by provider profile
     */
    private void displayExpectedCreditAmounts() {
        
        if (appointments.size() > 0) {    
            Sort.provider(providers);

            Iterator<Provider> iterator = providers.iterator();
            Provider currProvider = null;

            int providerCount = 1;

            while (iterator.hasNext()) {
                currProvider = iterator.next();
                int currCount = 0;
                
                Iterator<Appointment> iterator2 = appointments.iterator();
                Appointment currAppointment = null;
                while (iterator2.hasNext()) {
                    currAppointment = iterator2.next();
                    Provider appointmentProvider = ((Provider) currAppointment.getProvider());
                    if (appointmentProvider.equals(currProvider)) {
                        currCount++;
                    }
                }

                String fname = currProvider.getProfile().getFname();
                String lname = currProvider.getProfile().getLname();
                Date dob = currProvider.getProfile().getDateOfBirth();
                String amountString = formatTotalAmount(currCount*currProvider.rate());

                expectedCreditAmountsDisplay.appendText("("+ providerCount +") " +
                fname + " " + lname + " " + dob + " [credit amount: $" + amountString + ".00]\n");
                providerCount++;
            }
        } else {
            expectedCreditAmountsDisplay.appendText("The schedule calendar is empty.\n");
        }
    }

    

    /**
     * Clears all appointments from the list.
     * After clearing, the list will be empty.
     */
    private void clearAppointments() {

        for (int i = this.appointments.size(); i >-1; i--) {
            appointments.set(i, null);
        }
    }

    /**
     * Formats a total amount in dollars to a string. If the total is under 1000, it is
     * returned as a string. If the total is 1000 or greater, it is returned as a string
     * in the format "X,---" where X is the number of thousands and --- represents the
     * hundreds.
     * 
     * @param amount the total amount in dollars to format
     * @return the formatted string
     */
    public static String formatTotalAmount(int amount) {

        if (amount < FORMAT_THRESHOLD) {
            return Integer.toString(amount);
        } else if (amount == FORMAT_THRESHOLD) {
            return "1,000";
        }

        int thousands = (int) (amount/FORMAT_THRESHOLD);
        int hundreds = (int) (amount%FORMAT_THRESHOLD);

        String hundredsString = Integer.toString(hundreds);
        if (hundreds < 100) {
            hundredsString = "0" + hundredsString;
        }

        return Integer.toString(thousands) + "," + hundredsString;
    }

    /**
     * Prints a billing statement ordered by patient name.
     * Prints each patient's name, date of birth, and total amount due.
     * Prints a message if no patients are in the MedicalRecord.
     */
    private void billingStatement() {
        if (patients.size() > 0) {

            billingStatementDisplay.appendText("** Billing statement ordered by patient **\n");

            Sort.patient(patients);
            int patientCount = 1;
            
            Iterator<Patient> iterator = patients.iterator();
            Patient patient = null;

            while (iterator.hasNext()) {
                patient = (Patient) iterator.next();
                

                String fname = patient.getProfile().getFname();
                String lname = patient.getProfile().getLname();
                Date dob = patient.getProfile().getDateOfBirth();
                int amount = patient.getCharge();
                String amountString = formatTotalAmount(amount);

                billingStatementDisplay.appendText("("+ patientCount +") " +
                fname + " " + lname + " " + dob + " [due: $" + amountString + ".00]\n");

                patientCount++;
            }

            clearAppointments();

            billingStatementDisplay.appendText("** end of list **\n");

        } else {
            billingStatementDisplay.appendText("The schedule calendar is empty.\n");
        }
    }

    /**
     * Prints all appointments in the list, sorted by patient name, date and time.
     * Prints a message if the list is empty.
     */
    private void printByPatient() {
        if (appointments.size() > 0) {
  
            appointmentsDisplay.appendText("** Appointments ordered by patient/date/time **\n");

            Sort.appointment(appointments, 'P');;
            printAppointments();

            endOfList();
            
        } else {
            appointmentsDisplay.appendText("The schedule calendar is empty.\n");
        }
    }

    
    /**
     * Prints all appointments in the list, sorted by county, date and time.
     * Prints a message if the list is empty.
     */
    private void printByLocation() {
        if (appointments.size() > 0) {
  
            appointmentsDisplay.appendText("** Appointments ordered by county/date/time **\n");

            Sort.appointment(appointments, 'L');
            printAppointments();
            

            endOfList();

        } else {
            appointmentsDisplay.appendText("The schedule calendar is empty.\n");
        }
    }

    
    /**
     * Prints all appointments in the list, sorted by date, time, and provider.
     * Prints a message if the list is empty.
     */
    private void printByAppointment() {
        if (appointments.size() > 0) {

            appointmentsDisplay.appendText("** Appointments ordered by date/time/provider **\n");

            Sort.appointment(appointments, 'A');
            printAppointments();

            endOfList();

        } else {
            appointmentsDisplay.appendText("The schedule calendar is empty.\n");
        }
    }

    /**
     * Checks if the current date is a valid appointment date.
     *
     * @param date the date to validate
     * @return true if the date is a valid appointment date, false otherwise
     */
    private boolean validateAppointmentDate(Date date) {
        // THIS SHOULD ALSO CHECK FOR INVALID DAY IN NON-LEAP YEAR
        if (!date.isValid()) { 
			output.appendText("Appointment date: " + date.toString() + " is not a valid calendar date.\n");
			return false; 
		}

		Calendar appointment = Calendar.getInstance();
		appointment.set(date.getYear(), date.getMonth()-1, date.getDay()); 

		if (date.onOrBeforeToday()) {
			output.appendText("Appointment date: " + date.toString() + " is today or a date before today.\n");
			return false;
		} else if (!date.withinSixMonths()) {
			output.appendText("Appointment date: " + date.toString() + " is not within six months.\n");
			return false;
		} else if (appointment.get(Calendar.DAY_OF_WEEK)==Date.SUNDAY || appointment.get(Calendar.DAY_OF_WEEK)==Date.SATURDAY) {
			output.appendText("Appointment date: " + date.toString() + " is Saturday or Sunday.\n");
			return false;
		}

        return true;
    }

    /**
     * Checks if the current date is a valid date of birth.
     *
     * @param date the date to validate
     * @return true if the date is a valid date of birth, false otherwise
     */
    private boolean validateDateOfBirth(Date date) {

		if (!date.isValid()) {
			output.appendText("Patient dob: " + date.toString() + " is not a valid calendar date.\n");
			return false;
		}

        if (!date.notOnOrAfterToday()) {
            output.appendText("Patient dob: " + date.toString() + " is today or a date after today.\n");
            return false;
        }

        return true;
    }

    /**
     * Finds a doctor given the NPI.
     * 
     * @param npi the NPI of the doctor to find
     * @return the matching Doctor object or null if no match is found
     */
    private Doctor doctorSearchWithNPI(String npi) {

        Provider curr = null;

        Iterator<Provider> iterator = providers.iterator();
        while (iterator.hasNext()) {
            curr = iterator.next();
            if (curr instanceof Doctor) {
                if (((Doctor) curr).getNPI().equals(npi)) {
                    return (Doctor) curr;
                }
            }
        }
        return null;
    }

    /**
     * Checks if the given imaging service is provided by the clinic.
     * 
     * @param service the imaging service to check
     * @return true if the service is provided, false otherwise
     */
    private boolean imagingServiceProvided(String service) {
        String serviceName = service.toUpperCase();
        for (Radiology element: Radiology.values()) {
            if (serviceName.compareTo(element.name()) == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if there is a conflicting appointment for the given patient at the given date and time slot.
     * The conflicting appointment is an appointment that is scheduled for the same patient at the same time slot and date.
     * @param patient the patient to check for a conflicting appointment
     * @param date the date to check for a conflicting appointment
     * @param time the time slot to check for a conflicting appointment
     * @return true if there is a conflicting appointment, false otherwise
     */
    private boolean conflictingAppointment(Person patient, Date date, Timeslot time) {

        Iterator<Appointment> iterator = appointments.iterator();
        while (iterator.hasNext()) {
            Appointment curr = iterator.next();
            if (curr.getPatient().equals((Person) patient) && curr.getTimeslot().equals(time) && curr.getDate().equals(date)) {
                return true;
            }
        }
        return false;

    }

    
    /**
     * Creates an appointment object from the given tokenized input string.
     * 
     * @return the created Appointment object or null if the input is invalid
     */
    private Appointment createAppointmentObject() {
        Date appointmentDate = null;
        Timeslot time = null;
        Date dateOfBirth = null;
        String fname = fname_schedule.getText().trim();
        String lname = lname_schedule.getText().trim();
        Person patient = null;
        Person provider = null;
        
        try {

            appointmentDate = Date.createDateWithString(getSchedulingDate());

            if (appointmentDate != null) {
                if (!validateAppointmentDate(appointmentDate)) {
                    return null;
                }
            }

        } catch (Exception e) {
            output.appendText(e.getMessage()+"\n");
            return null;
        }

        try {
            int slot_int = scheduling_timeslot.getSelectionModel().getSelectedIndex()+1;
            if (!Timeslot.isValid(Integer.toString(slot_int))) {
                output.appendText(slot_int +" is not a valid time slot.\n");
                return null;
            } else {
                time = new Timeslot(Integer.toString(slot_int));
            }
        } catch (Exception e) {
            
        }

        try {
            dateOfBirth = Date.createDateWithString(getSchedulingDob());
            if (dateOfBirth != null) {
                if (!validateDateOfBirth(dateOfBirth)) {
                    return null;
                }
            }
        } catch (Exception e) {
            
        }
        Profile patientProfile = new Profile(fname, lname, dateOfBirth);
        patient = new Patient(patientProfile);

        if (conflictingAppointment(patient, appointmentDate, time)) {
            output.appendText(patient.toString()+ " has an existing appointment at the same time slot.\n");
            return null;
        }

        if (selectOffice.isSelected()) {
            if (providerIsAvailable(patientProfile, doctors_list.getValue(), appointmentDate, new Timeslot(Integer.toString(scheduling_timeslot.getSelectionModel().getSelectedIndex()+1)))) {
                Provider doctor = doctors_list.getValue();
                if (doctor != null) {
                    Profile doctorProfile = doctor.getProfile();
                    provider = getDoctorWithProfile(doctorProfile);
                } else {
                    output.appendText( " - provider doesn't exist.\n");
                    return null;
                }
            } else {
                output.appendText(doctors_list.getValue().toString() +
                " is not available at slot " + Integer.toString(scheduling_timeslot.getSelectionModel().getSelectedIndex()+1) + ".\n");
                return null;
            }
        } else if (selectImaging.isSelected()) {
            if (imagingServiceProvided(imaging_service.getValue().name())) {
                Technician assignedTechnician = assignTechnician(time);
                if (assignedTechnician==null) {
                    output.appendText("Cannot find an available technician at all locations for " + 
                                imaging_service.getValue().name().toUpperCase() + " at slot " + Integer.toString(scheduling_timeslot.getSelectionModel().getSelectedIndex()+1) + ".\n");
                    return null;
                } else {
                    Radiology radiology = imaging_service.getValue();
                    return new Imaging(appointmentDate, time, patient, assignedTechnician, radiology);
                }
            } else {
                output.appendText(" - imaging service not provided.\n");
                return null;
            }
        }

        return new Appointment(appointmentDate, time, patient, provider);
    }

    /**
     * Finds a patient in the list that matches the given person.
     * The comparison is done by comparing the profiles of the two persons.
     * If the patient exists, it returns the patient.
     * If the patient does not exist, it returns null.
     * @param person the person to find in the list of patients
     * @return the matching patient or null if no match is found
     */
    private Patient findPatient(Person person) {
        Iterator<Patient> iterator = patients.iterator();
        Patient curr = null;

        while (iterator.hasNext()) {
            curr = iterator.next();
            if (curr.getProfile().equals(person.getProfile())) {
                return curr;
            }
        }
        return null;
    }

    @FXML
    public void scheduleAppointment() {
        if (fname_schedule.getText().trim().compareTo("") == 0) {
            output.appendText("Missing patient first name.\n");
            return;
        } else if (lname_schedule.getText().trim().compareTo("") == 0) {
            output.appendText("Missing patient last name.\n");
            return;
        } else if (scheduling_dob.getValue()==null) {
            output.appendText("Missing patient date of birth.\n");
            return;
        } else if (scheduling_date.getValue()==null) {
            output.appendText("Missing appointment date.\n");
            return;
        } else if (scheduling_timeslot.getValue()==null) {
            output.appendText("Missing appointment time.\n");
            return;
        } else if (appointmentType.getSelectedToggle()==null) {
            output.appendText("Please select an appointment type.\n");
            return;
        }

        if (selectOffice.isSelected()) {
            if (doctors_list.getValue() == null) {
                output.appendText("Please select a provider.\n");
                return;
            } else {
                scheduleOfficeAppointment();
            }
        } else if (selectImaging.isSelected()) {
            if (imaging_service.getValue() == null) {
                output.appendText("Please select an imaging service.\n");
                return;
            } else {
                scheduleImagingAppointment();
            }
        }

    }

    /**
     * Schedules an office appointment given the tokenized input for the appointment details.
     * It validates the appointment date, timeslot, first name, last name, and date of birth.
     * It creates an appointment object and adds it to the list of appointments.
     * It also adds the appointment to the patient's list of visits and to the provider's list of appointments.
     * If the patient does not exist, it adds the patient to the list of patients.
     * If the appointment is invalid, it prints an error message.
     */
    @FXML
    public void scheduleOfficeAppointment() {

        Appointment appointment = createAppointmentObject();
        if (appointment != null) {
            appointments.add(appointment);
            Person patientPerson = appointment.getPatient();
            Patient patient = (Patient) patientPerson;

            if (!patients.contains(patient)) {
                patients.add(patient);
            } else {
                patient = findPatient(patientPerson);
            }
            patient.addVisit(new Visit(appointment, null));

            Provider provider = (Provider) appointment.getProvider();
            provider.addAppointment(appointment.getTimeslot());
            output.appendText(appointment.toString() + " booked.\n");
        }
    }

    /**
     * Schedules an imaging appointment given the tokenized input for the appointment details.
     * It validates the appointment date, timeslot, first name, last name, and date of birth.
     * It creates an imaging appointment object and adds it to the list of appointments.
     * It also adds the appointment to the patient's list of visits and to the provider's list of appointments.
     * If the patient does not exist, it adds the patient to the list of patients.
     * If the appointment is invalid, it prints an error message.
     */
    public void scheduleImagingAppointment() {
        Appointment appointment = createAppointmentObject();

        if (appointment != null) {
            
            appointments.add(appointment);
            Patient patientPerson = (Patient) appointment.getPatient();
            Patient patient = (Patient) patientPerson;

            if (!patients.contains(patient)) {
                patients.add(patient);
            } else {
                patient = findPatient(patientPerson);
            }
            patient.addVisit(new Visit(appointment, null));

            Provider provider = (Provider) appointment.getProvider();
            provider.addAppointment(appointment.getTimeslot());
            output.appendText(appointment.toString() + "[" + imaging_service.getValue().name() + "]" + " booked.\n");
        }
    }

    /**
     * Checks if the given array of strings has the correct number of tokens for cancelation.
     * The correct number of tokens is 6: date, timeslot, first name, last name, date of birth, and provider.
     * @param tokenized an array of strings representing the appointment details
     * @return true if the number of tokens is valid, false otherwise
     */
    public boolean validNumberOfTokensForCancelation(String[] tokenized) {

        return tokenized.length == NUM_TOKENS_FOR_CANCELATION;
    }

    /**
     * Checks if the given array of strings has the correct number of tokens for an appointment.
     * The correct number of tokens is 6: date, timeslot, first name, last name, date of birth, provider.
     * @param tokenized an array of strings representing the appointment details
     * @return true if the number of tokens is valid, false otherwise
     */
    public boolean validNumberOfTokensForAppointment(String[] tokenized) {
        return tokenized.length == NUM_TOKENS_FOR_APPOINTMENT;
    }

    /**
     * Prints the technicians in the rotation list to the console.
     * Each technician is separated by " --> ".
     * If the list is empty, prints a message saying so.
     */
    public void printCircularLinkedList() {

        if (technicians.getSize() == 0) {
            output.appendText("Rotation list is empty.\n");
            return;
        }
        output.appendText("Rotation list for the technicians.\n");

        Iterator<Technician> iterator = technicians.iterator();

        output.appendText(technicians.getCurrent().getData());

        while (iterator.hasNext()) {
            output.appendText(" --> ");
            output.appendText(iterator.next().getData());
        }
        output.appendText("\n");
    }

    /**
     * Prints all providers in the list, one per line.
     * Sorts the list by provider profile before printing.
     * Prints a newline after the last provider is printed.
     */
    public void printProviders() {
        Sort.provider(providers);
        Iterator<Provider> iterator = providers.iterator();
        while (iterator.hasNext()) {
            output.appendText(iterator.next().toString() + "\n");
        }
        output.appendText("\n");
    }

    
    
    /**
     * Loads the providers from a file named "providers.txt" in the same package into the list.
     * The file should have the format:
     * D|T [first name] [last name] [date of birth] [location] [specialty|rate] [NPI]
     * For example:
     * D John Doe 1990-01-01 New York Cardiology 1234567890
     * T Jane Doe 1990-01-01 New York 10
     * The date of birth should be in the format "yyyy-mm-dd".
     * The location should be one of the following: New York, New Jersey, Florida, California.
     * The specialty should be one of the following: Cardiology, Neurology, Orthopedics, General Surgery.
     * The rate should be an integer.
     * The NPI should be a 10-digit string.
     * If the file is not found, prints an error message.
     * If the file is found, prints a success message and prints the list of providers and the circular linked list.
     */
    @FXML
    public void loadProviders() {

        try {
            File file = new File(getClass().getResource("providers.txt").getFile());
            Scanner scanner = new Scanner(file);
            ObservableList<Provider> provider_names = FXCollections.observableArrayList();
            
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] tokenized = line.split("\\s+");

                String fName = tokenized[1];
                String LName = tokenized[2];
                Date DOB = Date.createDateWithString(tokenized[3]);

                Profile profile = new Profile(fName, LName, DOB);

                Location location = null;
                
                for (Location element: Location.values()) {
                    if (tokenized[4].equals(element.name())) { 
                        location = element;
                    }
                }

                if (tokenized[0].equals("D")) {

                    Specialty specialty = null;
                    String NPI = tokenized[6];

                    for (Specialty element: Specialty.values()) {
                        if (tokenized[5].equals(element.name())) {
                            specialty = element;
                        }
                    }

                    Doctor newDoc = new Doctor(specialty, NPI, profile, location);
                    this.providers.add(newDoc);
                    provider_names.add(newDoc);

    
                } else if (tokenized[0].equals("T")) {
                    int rate = Integer.parseInt(tokenized[5]);
                    Technician technician = new Technician(rate, profile, location);
                    this.providers.add(technician);
                    this.technicians.add(technician);
                }
            }

            this.doctors_list.setItems(provider_names);
            loadedProviders.setDisable(true);

            scanner.close();

            output.appendText("Providers loaded to the list.\n");
            printProviders();
            printCircularLinkedList();

        } catch (FileNotFoundException e) {
            output.appendText("File not found: " + e.getMessage()+"\n");
        }
    }

    
    /**
     * The primary method for running the ClinicManager, which handles user input
     * and commands to schedule, cancel, reschedule, and display appointments.
     * 
     * This method will loop and continue to prompt the user for input until the
     * user enters the "Q" command.
     */
    public void run() {

        loadProviders();

        output.appendText("Clinic Manager is running...\n");

        boolean run = true;
        Scanner scanner = new Scanner(System.in);

        while (run) {
            String[] tokenized = scanner.nextLine().split("\\,");
            if (tokenized[0].isEmpty()) continue;

            switch(tokenized[0]) {
                case "D":
                    if (!validNumberOfTokensForAppointment(tokenized)) {
                        output.appendText("Missing data tokens.");
                        break;
                    } 
                    scheduleOfficeAppointment();
                    break;
                case "T":
                    if (!validNumberOfTokensForAppointment(tokenized)) {
                        output.appendText("Missing data tokens.");
                        break;
                    } 
                    scheduleImagingAppointment();
                    break;
                case "C":
                    if (!validNumberOfTokensForCancelation(tokenized)) {
                        output.appendText("Missing data tokens.");
                        break;
                    }
                    cancelAppointment();
                    break;
                case "R":
                    if (!validNumberOfTokensForAppointment(tokenized)) {
                        output.appendText("Missing data tokens.");
                        break;
                    } 
                    rescheduleAppointment();
                    break;
                case "PA":
                    printByAppointment();
                    break;
                case "PP":
                    printByPatient();
                    break;
                case "PL":
                    printByLocation();
                    break;
                case "PS":
                    billingStatement();
                    break;
                case "Q":
                    scanner.close();
                    output.appendText("Clinic Manager terminated.");
                    run = false;
                    break;
                case "PO":
                    displayOfficeAppointments();
                    break;
                case "PI":
                    displayImagingAppointments();
                    break;
                case "PC":
                    displayExpectedCreditAmounts();
                    break;
                default:
                    output.appendText("Invalid command!");
            }
        }
    }
}