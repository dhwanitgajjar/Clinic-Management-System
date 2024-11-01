package com.example.cs213_project3;
import com.example.cs213_project3.util.Date;

/**
 * Represents an imaging appointment.
 * @author Dhwanit Gajjar
 */
public class Imaging extends Appointment {
    private Radiology room;
    /**
     * Creates a new Imaging appointment.
     * @param date
     * @param timeslot
     * @param patient
     * @param provider
     * @param room
     */
    public Imaging(Date date, Timeslot timeslot, Person patient, Person provider, Radiology room) {
        super(date, timeslot, patient, provider);
        this.room = room;
    }

    /**
     * Returns the imaging room for this appointment.
     *
     * @return the imaging room
     */
    public Radiology getRoom() {
        return room;
    }

    /**
	 * Returns a string representation of the appointment, including date, timeslot, patient, and provider.
	 *
	 * @return a string representation of the appointment
	 */
	@Override
	public String toString() {
		return this.date.toString() + " " + this.timeslot.toString() + " "
			+ this.patient.toString() + " " + this.provider.toString();
	}
}
