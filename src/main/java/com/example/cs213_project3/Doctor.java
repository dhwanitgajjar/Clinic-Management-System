package com.example.cs213_project3;
import com.example.cs213_project3.util.Specialty;
import com.example.cs213_project3.util.Profile;
import com.example.cs213_project3.util.Location;

/**
 * Class for doctors
 * @author Dhwanit Gajjar
 */
public class Doctor extends Provider {
    
    private Specialty specialty;//encapsulate the rate per visit based on specialty
    private String npi; //National Provider Identification unique to the doctor

    /**
     * Constructor for a doctor
     * @param specialty
     * @param npi
     * @param profile
     * @param location
     */
    public Doctor(Specialty specialty, String npi, Profile profile, Location location) {
        super(profile, location);
        this.specialty = specialty;
        this.npi = npi;
    }

    /**
     * Returns the doctor's NPI (National Provider Identification)
     * @return the doctor's NPI
     */
    public String getNPI() {
        return npi;
    }

    /**
     * Checks if this doctor is equal to another object.
     * Two doctors are considered equal if they have the same profile, specialty, and NPI.
     * 
     * @param obj the object to compare
     * @return true if the doctors are equal; false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && 
            this.specialty.equals(((Doctor) obj).specialty) && 
            this.npi.equals(((Doctor) obj).npi);
    }

    /**
     * Returns the location of the doctor.
     * 
     * @return the location of the doctor
     */
    public Location getLocation() {
        return super.getLocation();
    }

    /**
     * Returns the rate of the doctor.
     * 
     * @return the rate as an int
     */
    public int rate() {
        return this.specialty.getCharge();
    }

    /**
     * Returns a string representation of the doctor, including the profile, location, specialty, and NPI.
     * The format of the string is "[profile, location] [specialty, #npi]"
     * 
     * @return a string representation of the doctor
     */
    @Override
    public String toString() {
        return "[" + super.getProfile().toString() + ", " + 
            super.getLocation().toString() + 
            "] [" + this.specialty + ", #" + 
            this.npi + "]";
    }
}
