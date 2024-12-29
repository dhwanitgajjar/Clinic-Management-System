package com.example.cs213_project3.util;
import com.example.cs213_project3.Appointment;;

/**
 * Represents a visit, which is linked to an appointment and potentially to other visits
 * in a singly linked list structure. Each Visit object holds a reference to an appointment
 * and the next visit in the list.
 * 
 * @author Dhwanit Gajjar
 */
public class Visit {
	private Appointment appointment; 
	private Visit next; 

    /**
     * Constructs a Visit with the specified appointment and reference to the next visit.
     *
     * @param appointment the appointment associated with this visit
     * @param next        the next visit in the linked list; can be null if this is the last visit
     */
    public Visit(Appointment appointment, Visit next) {
        this.appointment = appointment;
        this.next = next;
    }

    /**
     * Returns the next visit in the linked list of visits, or null if this is the last
     * visit in the list.
     *
     * @return the next visit in the linked list of visits, or null if this is the last
     *         visit
     */
    public Visit getNext() {
        return this.next;
    }

    /**
     * Sets the next visit in the linked list of visits to the specified visit.
     *
     * @param visit the visit to set as the next visit in the linked list
     */
    public void setNext(Visit visit) {
        this.next = visit;
    }

    
    /**
     * Returns the appointment associated with this visit.
     *
     * @return the appointment associated with this visit
     */
    public Appointment getAppointment() {
        return this.appointment;
    }

    /**
     * The main method to run the Visit class.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        
    }
}