package com.example.cs213_project3;
import com.example.cs213_project3.util.Profile;
import com.example.cs213_project3.util.Visit;

/**
 * This class represents a patient.
 * @author Dhwanit Gajjar
 */
public class Patient extends Person{

    private Visit visits;

    /**
     * Constructor for a patient.
     * @param profile
     */
    public Patient(Profile profile) {
        super(profile);
        this.visits = null;
    }

    /**
     * Calculates the total charge of all completed appointments for the patient.
     *
     * @return the total charge of all completed appointments
     */
    public int getCharge() {

        int total_charge = 0;
        Visit curr = this.visits;

        if (curr == null) {
            return 0;
        }

        while (curr != null) {
            total_charge += curr.getAppointment().getCharge();
            curr = curr.getNext();
        }
        return total_charge;
    }

    /**
     * Adds a visit to the end of the linked list of visits for the patient.
     *
     * @param visit the visit to add
     */
    public void addVisit(Visit visit) {
        if (this.visits == null) {
            this.visits = visit;
            return;
        } 

        if (this.visits.getNext() == null) {
            this.visits.setNext(visit);
            return;
        }

        Visit curr = this.visits;

        while (curr.getNext() != null) { 
            curr = curr.getNext();
        }

        curr.setNext(visit);
    }

    /**
     * Returns the number of visits in the linked list of visits for the patient.
     *
     * @return the number of visits
     */
    public int sizeOfVisits() {
        if (this.visits == null) {
            return 0;
        }

        Visit curr = this.visits;
        int count = 0;

        while (curr != null) {
            count++;
            curr = curr.getNext();
        }

        return count;
    }

    /**
     * Removes a visit from the linked list of visits for the patient.
     *
     * If the visit is not found, the method does nothing.
     *
     * @param appointment the visit to remove
     */
    public void removeVisit(Appointment appointment) {

        if (this.visits == null) {
            return;
        }

        if (this.visits.getNext() == null) {
            this.visits = null;
            return;
        }

        Visit prev = null;
        Visit curr = this.visits;

        if (this.visits.getAppointment().equals(appointment)) {
            this.visits = this.visits.getNext();
            return;
        }

        while (!(curr.getAppointment().equals(appointment)) && curr != null) {
            prev = curr;
            curr = curr.getNext();
        }

        if (curr == null) {
            return;
        }

        prev.setNext(curr.getNext());
        curr = null;
    }

    /**
     * Returns the profile of the patient.
     *
     * @return the profile of the patient
     */
    public Profile getProfile() {
        return this.profile;
    }

    
    /**
     * Returns a string representation of the patient.
     *
     * @return a string representation of the patient
     */
    @Override
    public String toString() {
        return this.profile.toString();
    }

    /**
     * Checks if this patient is equal to another object.
     * Two patients are considered equal if they have the same profile.
     *
     * @param obj the object to compare
     * @return true if the patients are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(this instanceof Patient)) {
            return false;
        }
        
        Patient patient = (Patient) obj;
        return this.profile.equals(patient.getProfile());
    }

    /**
     * Compares this patient to another patient based on their profile.
     *
     * @param patient2 the patient to compare
     * @return negative integer, zero, or a positive integer as this patient
     *          is less than, equal to, or greater than the specified patient
     */
    public int compareTo(Patient patient2) {
        return this.getProfile().compareTo(patient2.getProfile());
    }
}
