package com.example.cs213_project3.util;

/**
 * Represents a profile containing a patient's first name, last name, and date of birth.
 * Implements the Comparable interface to allow comparison between profiles.
 * @author Harb Chauhan
 */
public class Profile implements Comparable<Profile> {
    private String fname;
	private String lname;
	private Date dob;

    /**
     * Constructs a Profile with the specified first name, last name, and date of birth.
     *
     * @param fname the first name of the patient
     * @param lname the last name of the patient
     * @param dob   the date of birth of the patient
     */
    public Profile(String fname, String lname, Date dob) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }

    /**
     * Compares this profile with another profile.
     * Comparison is performed based on the corresponding
     * ASCII value of a character.
     *
     * @param profile2 the profile to compare against
     * @return an integer result of the comparison
     */
    @Override
    public int compareTo(Profile profile2) {
        if (this.lname.compareTo(profile2.lname) > 0) {
            return 1;
        } else if (this.lname.compareTo(profile2.lname) < 0) {
            return -1;
        } else {
            if (this.fname.compareTo(profile2.fname) > 0) {
                return 1;
            } else if (this.fname.compareTo(profile2.fname) < 0) {
                return -1;
            } else {
                if (this.dob.compareTo(profile2.dob) > 0) {
                    return 1;
                } else if (this.dob.compareTo(profile2.dob) < 0) {
                    return -1;
                }
            }
        }
        return 0;
    }

    /**
     * Returns the first name of the profile.
     *
     * @return a string of the profile's first name attribute
     */
    public String getFname() {
        return this.fname;
    }

    /**
     * Returns the last name of the profile.
     *
     * @return a string of the profile's last name attribute
     */
    public String getLname() {
        return this.lname;
    }

    /**
     * Returns the date of birth of the profile.
     *
     * @return a Date object of the profile's date of birth attribute
     */
    public Date getDateOfBirth() {
        return this.dob;
    }

    /**
     * Returns a string representation of the profile, including first name, last name, and date of birth.
     *
     * @return a string representation of the profile
     */
    @Override
    public String toString() {
        return fname + " " + lname + " " + this.dob.toString();
    }

    /**
     * Checks if this profile is equal to another object.
     * Two profiles are considered equal if they have the same first name, last name, and date of birth.
     *
     * @param obj the object to compare
     * @return true if the profiles are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(this instanceof Profile)) {
            return false;
        }

        Profile profile = (Profile) obj;

        return profile.fname.toUpperCase().compareTo(this.fname.toUpperCase()) == 0 && 
        profile.lname.toUpperCase().compareTo(this.lname.toUpperCase()) == 0 &&
        profile.dob.equals(this.dob);
    }

    
    /**
     * Tests if the result of a method matches the expected output.
     *
     * @param profile1      the first profile being tested
     * @param profile2      the second profile being tested
     * @param expectedOutput the expected output of the method
     * @param actualOutput   the actual output of the method
     * @return a string containing the test results
     */
	public static String testResult(Profile profile1, Profile profile2, int expectedOutput, int actualOutput) {
		return "Testing the following profile: " + profile1.toString() + " and " + profile2.toString() + 
		"\n Expected output: " + Integer.toString(expectedOutput) + 
		"\n Actual output: " + Integer.toString(actualOutput) + "\n";
	}

	/**
	 * Test if the Date class works as intended.
	 * Validates the number of days in February for a non-leap year.
	 */
	private static String greaterLastName() {
        Profile profile1 = new Profile("Dr.", "Tomato", new Date(1, 1, 1990));
        Profile profile2 = new Profile("Dr.", "Doofenshmirtz", new Date(1, 1,1990));
        int expectedOutput = 1;
        int actualOutput = profile1.compareTo(profile2);
        return testResult(profile1, profile2, expectedOutput, actualOutput);
	}

    
    /**
     * Test if the compareTo() method correctly compares two profiles with the same last name and different first names.
     * The profile with the greater first name should come first.
     */
    private static String sameLastName_GreaterFirstName() {
        Profile profile1 = new Profile("Jonny", "Beef", new Date(1, 1,1990));
        Profile profile2 = new Profile("Angus", "Beef", new Date(1, 1,1990));
        int expectedOutput = 1;
        int actualOutput = profile1.compareTo(profile2);
        return testResult(profile1, profile2, expectedOutput, actualOutput);
	}

    /**
     * Test if the compareTo() method correctly compares two profiles with the same last name, first name, and later date of birth.
     * The profile with the later date of birth should come first.
     */
    private static String sameLastName_SameFirstName_LaterDOB() {
        Profile profile1 = new Profile("John", "Doe", new Date(1, 1,2090));
        Profile profile2 = new Profile("John", "Doe", new Date(1, 1,1990));
        int expectedOutput = 1;
        int actualOutput = profile1.compareTo(profile2);
        return testResult(profile1, profile2, expectedOutput, actualOutput);
	}

    /**
     * Test if the compareTo() method correctly compares two profiles with the same attributes.
     * The expected output is 0, indicating that the two profiles are equal.
     */
    private static String sameAttributes() {
        Profile profile1 = new Profile("John", "Doe", new Date(1, 1, 1990));
        Profile profile2 = new Profile("John", "Doe", new Date(1, 1, 1990));
        int expectedOutput = 0;
        int actualOutput = profile1.compareTo(profile2);
        return testResult(profile1, profile2, expectedOutput, actualOutput);
	}

    /**
     * Runs all the test methods for the compareTo() method to verify that it works as expected.
     * The expected output of each test should be printed to the console.
     * @param args the command line arguments. Not used in this program.
     */
    public static void main(String[] args) {
        // System.out.println("Testing greaterLastName()");
        // System.out.println(greaterLastName());

        // System.out.println("Testing sameLastName_GreaterFirstName()");
        // System.out.println(sameLastName_GreaterFirstName());
        
        // System.out.println("Testing sameLastName_SameFirstName_LaterDOB()");
        // System.out.println(sameLastName_SameFirstName_LaterDOB());
        
        // System.out.println("Testing sameAttributes()");
        // System.out.println(sameAttributes());
        
    }
}
