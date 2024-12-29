package com.example.cs213_project3;
import com.example.cs213_project3.util.Profile;
import com.example.cs213_project3.util.Location;
import com.example.cs213_project3.util.List;

/**
 * Class for technicians
 * @author Dhwanit Gajjar
 */
public class Technician extends Provider {

    private int ratePerVisit;
    private String data;
    private Technician next;
    private List<Timeslot> availability; 


    /**
     * Constructor for technicians
     * @param ratePerVisit
     * @param profile
     * @param location
     */
    public Technician(int ratePerVisit, Profile profile, Location location) {
        super(profile, location);
        this.ratePerVisit = ratePerVisit;
        this.data = profile.getFname() + " " + profile.getLname() + " (" + location.toString() + ")";
        this.next = null;
        this.availability = new List<>();
    }

    /**
     * Returns the location of the technician
     * @return the location of the technician
     */
    public Location getLocation() {
        return super.getLocation();
    }


    /**
     * Checks if the technician is available at a given timeslot.
     * @param timeslot the timeslot to check for availability
     * @return true if the technician is available, false if not
     */
    public boolean isAvailable(Timeslot timeslot) {
        if (availability.contains(timeslot)) {
            return false;
        }
        return true;

    }

    /**
     * Adds a timeslot to the technician's availability list.
     * @param timeslot the timeslot to add
     */
    public void addAppointment(Timeslot timeslot) {
        availability.add(timeslot);
    }

    /**
     * Removes a timeslot from the technician's availability list.
     * @param timeslot the timeslot to remove
     */
    public void removeAppointment(Timeslot timeslot) {
        availability.remove(timeslot);
    }

    /**
     * Returns a string representation of the technician, including the profile, location, and rate.
     * @return a string representation of the technician
     */
    @Override
    public String toString() {
        return "[" + super.getProfile().toString() + ", " + 
        super.getLocation().toString() + "] [rate: $" + 
        this.ratePerVisit + "]";
    }

    /**
     * Returns the rate per visit of the technician.
     * @return the rate per visit of the technician
     */
    @Override
    public int rate() {
        return ratePerVisit;
    }

    /**
     * Returns a string representation of the technician for use in a GUI.
     * This method is used internally to populate the GUI with a list of technicians.
     * The string representation includes the first name, last name, and location of the technician.
     * @return a string representation of the technician
     */
    public String getData() {
        return data;
    }

    /**
     * Returns the next technician in the circular linked list of technicians.
     * @return the next technician in the circular linked list of technicians
     */
    public Technician getNext() {
        return next;
    }

    /**
     * Sets the next technician in the circular linked list of technicians to the given technician.
     * @param next the technician to set as the next technician in the circular linked list
     */
    public void setNext(Technician next) {
        this.next = next;
    }
}
