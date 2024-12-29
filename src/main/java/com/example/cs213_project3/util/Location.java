package com.example.cs213_project3.util;

/**
 * Enum representing various locations with associated ZIP codes and counties.
 * Each location has a ZIP code and a county name associated with it.
 * @author Dhwanit Gajjar
 */
public enum Location {
    
    BRIDGEWATER("08807", "Somerset"), 
    EDISON("08817", "Middlesex"), 
    PISCATAWAY("08854", "Middlesex"), 
    PRINCETON("08542", "Mercer"), 
    MORRISTOWN("07960", "Morris"), 
    CLARK("07066", "Union");

    private final String county;
	private final String zip;

    /**
     * Constructs a Location with the specified ZIP and county.
     *
     * @param zip   the ZIP of the location
     * @param county the county of the location
     */
    private Location(String zip, String county) {
        this.zip = zip;
        this.county = county;
    }

    /**
     * Returns a string representation of the location, formatted as
     * "City, County ZIP".
     * @return a string representation of the location
     */
    @Override
    public String toString() {
        return this.name() + ", " + this.county + " " + this.zip;
    }

    /**
     * Returns the county associated with the location.
     * @return the county name
     */
    public String getCounty() {
        return this.county;
    }

    /**
     * Returns the ZIP code associated with the location.
     * @return the ZIP code
     */
    public String getZip() {
        return this.zip;
    }

    

    /**
     * Checks if this location is equal to another location.
     * Two locations are considered equal if they have the same county and ZIP code.
     * @param location2 the location to compare
     * @return true if the locations are equal, false otherwise
     */
    public boolean equals(Location location2) {
        return this.county.compareTo(location2.county) == 0 && this.zip.compareTo(location2.zip) == 0;
    }
}
