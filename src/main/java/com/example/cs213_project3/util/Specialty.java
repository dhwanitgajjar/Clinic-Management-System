package com.example.cs213_project3.util;

/**
 * Enum representing medical specialties, each associated with a specific charge for services.
 * Provides functionality to retrieve the charge associated with each specialty.
 * @author Dhwanit Gajjar
 */
public enum Specialty {

    FAMILY(250), 
    PEDIATRICIAN(300), 
    ALLERGIST(350);

    private final int charge;

    /**
     * Constructs a Specialty with the specified charge amount.
     *
     * @param charge the charge associated with the specialty
     */
    Specialty(int charge) {
        this.charge = charge;
    }

    /**
     * Checks if this specialty is equal to another specialty.
     * Two specialties are considered equal if they have the same charge.
     *
     * @param specialty the specialty to compare
     * @return true if the specialties are equal, false otherwise
     */
    public boolean equals(Specialty specialty) {
        if (this == specialty) {
            return true;
        }
        if (specialty == null) {
            return false;
        }
        if (!(specialty instanceof Specialty)) {
            return false;
        }
        Specialty other = (Specialty) specialty;
        if (this.getCharge() != other.getCharge()) {
            return false;
        }
        return true;
    }

    /**
     * Returns the charge associated with the specialty.
     *
     * @return the charge amount
     */
    public int getCharge() {
        return this.charge;
    }

    /**
     * Main method to run the Specialty class.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        
    }
}
