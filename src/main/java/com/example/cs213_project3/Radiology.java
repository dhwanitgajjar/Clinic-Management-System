package com.example.cs213_project3;

/**
 * Radiology enum
 * @author Dhwanit Gajjar
 */
public enum Radiology {
    CATSCAN, 
    ULTRASOUND, 
    XRAY;

    /**
     * Returns the Radiology enum corresponding to the given string.
     * The input string is case-insensitive.
     * If the input string does not match any of the enum values, returns null.
     * 
     * @param radiologyString the string to check
     * @return the corresponding Radiology enum
     */ 
    public static Radiology getRadiology(String radiologyString) {

        String radiology = radiologyString.toUpperCase();

        switch (radiology) {
            case "CATSCAN": 
                return CATSCAN;
            case "ULTRASOUND": 
                return ULTRASOUND;
            case "XRAY": 
                return XRAY;
            default: 
                return null;
        } 

    }
    
}
