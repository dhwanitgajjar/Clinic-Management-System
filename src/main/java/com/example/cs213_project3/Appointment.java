package com.example.cs213_project3;
import com.example.cs213_project3.util.Date;
import com.example.cs213_project3.util.Profile;

/**
 * Represents an appointment.
 * @author Harb Chauhan
 */
public class Appointment implements Comparable <Appointment> {
    protected Date date;
	protected Timeslot timeslot;
    // note these are both Person class
    // Person has Profile (Project 1) instance variable
	protected Person patient;
	protected Person provider;

    /**
     * Creates an appointment.
     * @param date
     * @param timeslot
     * @param patient
     * @param provider
     */
    public Appointment(Date date, Timeslot timeslot, Person patient, Person provider) {
        this.date = date;
        this.timeslot = timeslot;
        this.patient = patient;
        this.provider = provider;
    }

    /**
     * Returns the timeslot of the appointment.
     * @return the timeslot of the appointment
     */
    public Timeslot getTimeslot() {
        return this.timeslot;
    }

    /**
     * Returns the charge of the appointment.
     * @return the charge of the appointment
     */
    public int getCharge() {
        return ((Provider) provider).rate();
    }

    /**
     * Returns the date of the appointment.
     * @return the date of the appointment
     */
    public Date getDate() {
        return this.date;
    }

    /**
     * Returns the patient of the appointment.
     * @return the patient of the appointment
     */
    public Person getPatient() {
        return this.patient;
    }
    

    /**
     * Returns the provider of the appointment.
     * @return the provider of the appointment
     */
    public Person getProvider() {
        return this.provider;
    }

    /**
	 * Checks if this appointment is equal to another object.
	 * Appointments are considered equal if they have the same date, timeslot, patient, and provider.
	 *
	 * @param obj the object to compare
	 * @return true if the appointments are equal; false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Appointment)) {
			return false;
		}
		Appointment a = (Appointment) obj;
		return a.date.equals(this.date) && 
		a.timeslot.equals(this.timeslot) && 
		a.patient.equals(this.patient) && 
		a.provider.equals(this.provider);
	}

    /**
	 * Compares this appointment to another appointment based on date and timeslot.
	 *
	 * @param appointment2 the object to be compared.
	 * @return negative integer, zero, or a positive integer as this appointment
	 *          is less than, equal to, or greater than the specified appointment
	 */
	@Override
	public int compareTo(Appointment appointment2) {
		if (this.date.compareTo(appointment2.date) > 0) {
			return 1;
		} else if (this.date.compareTo(appointment2.date) < 0) {
			return -1;
		} else {
			if (this.timeslot.compareTo(appointment2.timeslot) > 0) {
				return 1;
			} else if (this.timeslot.compareTo(appointment2.timeslot) < 0) {
				return -1;
			} else {
				if (this.provider.compareTo(appointment2.provider) > 0) {
					return 1;
				} else if (this.provider.compareTo(appointment2.provider) < 0) {
					return -1;
				} else {
					return 0;
				}
			}
		}
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
