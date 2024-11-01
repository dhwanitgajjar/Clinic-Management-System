package com.example.cs213_project3;
import com.example.cs213_project3.util.Profile;

/**
 * Represents a person
 * @author Dhwanit Gajjar
 */
public class Person implements Comparable<Person> {
    protected Profile profile;

    /**
     * Creates a person
     * @param profile
     */
    public Person(Profile profile) {
        this.profile = profile;
    }

    /**
     * Returns the profile of the person
     * @return the profile of the person
     */
    public Profile getProfile() {
        return profile;
    } 

    /**
     * Returns a string representation of the person
     * @return a string representation of the person
     */
    @Override
    public String toString() {
        return profile.toString();
    }


    /**
     * Compares this person to another person based on their profile
     * @param other the person to compare
     * @return negative integer, zero, or a positive integer as this person
     *          is less than, equal to, or greater than the specified person
     */
    @Override
    public int compareTo(Person other) {
        return this.getProfile().compareTo(other.getProfile());
    }

    /**
     * Checks if this person is equal to another object.
     * Two persons are considered equal if they have the same profile.
     * 
     * @param other the object to compare
     * @return true if the persons are equal, false otherwise
     */
    @Override
    public boolean equals(Object other) {

        if (!(other instanceof Person)) {
            return false;
        }

        if (this == other) {
            return true;
        }

        Person otherPerson = (Person) other;
        return this.getProfile().equals(otherPerson.getProfile());
    }

    /**
     * Main method for testing the Person class
     * @param args not used
     */
    public static void main(String[] args) {
    }
}
