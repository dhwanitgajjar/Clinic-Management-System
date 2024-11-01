package com.example.cs213_project3;
import com.example.cs213_project3.util.Profile;
import com.example.cs213_project3.util.List;
import com.example.cs213_project3.util.Location;

/**
 * Abstract class for providers
 * @author Dhwanit Gajjar
 */
public abstract class Provider extends Person {
    
    private Location location;
    private List<Timeslot> availability; 

    public Provider(Profile profile, Location location) {
        super(profile);
        this.location = location;
        this.availability = new List<>();
    }

    /**
     * Get the rate of the provider
     * @return the rate of the provider
     */
    public abstract int rate();

    /**
     * Checks if the provider is available at a given timeslot.
     * @param timeslot the timeslot to check for availability
     * @return true if the provider is available, false if not
     */
    public boolean isAvailable(Timeslot timeslot) {
        if (availability.contains(timeslot)) {
            return false;
        }
        return true;

    }

    /**
     * Adds a timeslot to the provider's availability list.
     * @param timeslot the timeslot to add
     */
    public void addAppointment(Timeslot timeslot) {
        availability.add(timeslot);
    }

    /**
     * Removes a timeslot from the provider's availability list.
     * @param timeslot the timeslot to remove
     */
    public void removeAppointment(Timeslot timeslot) {
        availability.remove(timeslot);
    }

    /**
     * Returns the profile of the provider
     * @return the profile of the provider
     */
    public Profile getProfile() {
        return this.profile;
    }

    /**
     * Checks if this provider is equal to another object.
     * Two providers are considered equal if they have the same profile.
     * 
     * @param obj the object to compare
     * @return true if the providers are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        
        return this.profile.equals(((Provider) obj).getProfile());
        
    }

    /**
     * Returns a string representation of the provider
     * @return a string representation of the provider
     */
    public abstract String toString();

    /**
     * Returns the location of the provider
     * @return the location of the provider
     */
    public Location getLocation() {
        return location;
    }

}
