package com.example.cs213_project3;

/**
 * Represents a time slot with an hour and minute.
 * @author Dhwanit Gajjar
 */
public class Timeslot implements Comparable<Timeslot> {
    private int hour;
    private int minute;

    private static final String AM = "AM";
    private static final String PM = "PM";

    private static final int[][] slots = {{9, 0},
                                        {9, 30},
                                        {10, 0},
                                        {10, 30},
                                        {11, 0},
                                        {11, 30},
                                        {14, 0},
                                        {14, 30},
                                        {15, 0},
                                        {15, 30},
                                        {16, 0},
                                        {16, 30}};

    /**
     * Constructs a Timeslot with the specified hour and minute.
     *
     * @param hour   the hour of the timeslot
     * @param minute the minute of the timeslot
     */
    public Timeslot(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    /**
     * Constructs a Timeslot with the specified hour and minute.
     * @param slotString
     */
    public Timeslot(String slotString) {

        int slot = -1;

        if (Timeslot.isValid(slotString)) {
            slot = Integer.parseInt(slotString) - 1;
        }

        this.hour = slots[slot][0];
        this.minute = slots[slot][1];
    }

    /**
     * Returns a string representation of the timeslot in the format "HH:MM AM/PM".
     *
     * @return a string representation of the timeslot
     */
    @Override
    public String toString() {
        String meridian = AM;

        int hour_time = this.hour;
        int minute_time = this.minute;

        String hourString;
        String minString;

        if (this.hour > 12) {
            meridian = PM;
            hour_time %= 12;
        }

        hourString = Integer.toString(hour_time);

        if (minute_time == 0) {
            minString = "0" + Integer.toString(minute_time);
        } else {
            minString = Integer.toString(minute_time);
        }

        return hourString + ":" + minString + " " + meridian;
    }

    /**
     * Compares this timeslot with another timeslot.
     *
     * @param other the other timeslot to compare against
     * @return 1 if this timeslot is after the other, -1 if before, 0 if the same
     */
    @Override
    public int compareTo(Timeslot other) {
        if (this.hour > other.hour) {
            return 1;
        } else if (this.hour == other.hour) {
            if (this.minute > other.minute) {
                return 1; 
            } else if (this.minute == other.minute) {
                return 0;
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }

    /**
     * Compares this timeslot with another timeslot.
     *
     * @param other the other timeslot to compare against
     * @return true if the timeslots are equal, false if not equal
     */
    public boolean equals(Timeslot other) {
        if (this.hour == other.hour && this.minute == other.minute) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Determines if the provided string represents a valid timeslot number.
     *
     * @param slotString the string representing the timeslot number
     * @return true if valid, false otherwise
     */
    public static boolean isValid(String slotString) {
        try {
            int slot = Integer.parseInt(slotString);

            if (slot < 1 || slot > 12) {
                return false;
            }
            return true;
        } catch ( NumberFormatException e) {
            return false;
        }
    }

    public static int[][] getSlots() {
        return slots;
    }
}
