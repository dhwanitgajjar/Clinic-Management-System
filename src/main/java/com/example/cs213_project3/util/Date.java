package com.example.cs213_project3.util;
import java.util.Calendar;

/**
 * Represents a calendar date with day, month, and year attributes.
 * Implements the Comparable interface to allow comparison of Date objects.
 * Provides utility methods to validate dates, handle leap years, and compare dates.
 * @author Harb Chauhan
 */
public class Date implements Comparable<Date> {

	private int year;
	private int month;
	private int day;

	public static final int SIX_MONTHS = 6;
	public static final int MONTHS_IN_YEAR = 12;

	public static final int SUNDAY = 1;
	public static final int SATURDAY = 7;

	/**
	 * Constructs a Date object with the specified day, month, and year.
	 *
	 * @param month the month of the date (1-12)
	 * @param day   the day of the date (1-31 depending on the month)
	 * @param year  the year of the date
	 */
	public Date(int month, int day, int year) {
		this.year = year;
		this.month = month;
		this.day = day;
	}

	/**
	 * Default constructor, creates a Date object.
	 */
	public Date() {

	}

	/**
	 * Creates a Date object from a string in the format "MM/DD/YYYY".
	 *
	 * @param dateString the date string in MM/DD/YYYY format
	 * @return a new Date object
	 */
	public static Date createDateWithString(String dateString) {

		try {
			String[] tokenizedDate = dateString.split("\\/");
		
			int monthToken = Integer.parseInt(tokenizedDate[0]);
			int dayToken = Integer.parseInt(tokenizedDate[1]);
			int yearToken = Integer.parseInt(tokenizedDate[2]);

			return new Date(monthToken, dayToken, yearToken);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Checks if this date is a valid calendar date.
	 *
	 * @return true if the date is valid, false otherwise
	 */
	public boolean isValid() {

		Calendar c = Calendar.getInstance();
		c.set(this.year, this.month-1, 1);
		
		boolean validYear = this.year > 0;
		boolean validMonth = this.month >= 1 && this.month <= 12;
		boolean validDay = this.day > 0 && this.day <= c.getActualMaximum(Calendar.DAY_OF_MONTH);

		if (validYear && validMonth && validDay) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Checks if the current date is within six months from today.
	 *
	 * @return true if the date is within six months, false otherwise
	 */
	public boolean withinSixMonths() {

		Calendar today = Calendar.getInstance();

		int difference_in_months = (this.year*12 + this.month-1) - (today.get(Calendar.YEAR)*12 + today.get(Calendar.MONTH)); 
		int difference_in_days = this.day - today.get(Calendar.DAY_OF_MONTH);

		if ( difference_in_months > SIX_MONTHS) {
			return false;
		} else if ( difference_in_months == SIX_MONTHS && difference_in_days > 0) {
			return false;
		}
		return true;

	}

	/**
	 * Checks if the appointment date is today or on a date before today.
	 *
	 * @return true if the date is on or before today, false otherwise
	 */
	public boolean onOrBeforeToday() {
		Calendar today = Calendar.getInstance();

		if (this.year < today.get(Calendar.YEAR)) {
			return true;
		} else if (this.year == today.get(Calendar.YEAR)) {
			if (this.month-1 < today.get(Calendar.MONTH)) {
				return true;
			} else if (this.month-1 == today.get(Calendar.YEAR)) {
				if (this.day < today.get(Calendar.DAY_OF_MONTH)) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Returns the year of this date.
	 * 
	 * @return the year
	 */
	public int getYear() {
		return this.year;
	}

	/**
	 * Returns the month of this date.
	 * 
	 * @return the month (1-12)
	 */
	public int getMonth() {
		return this.month;
	}

	/**
	 * Returns the day of this date.
	 * 
	 * @return the day of the month (1-31)
	 */
	public int getDay() {
		return this.day;
	}

	/**
	 * Checks if this date is not on or after today.
	 * 
	 * @return true if the date is not on or after today, false otherwise
	 */
	public boolean notOnOrAfterToday() {

		Calendar today = Calendar.getInstance();
		boolean valid = true;

		if (this.year < today.get(Calendar.YEAR)) {
			return valid;
		} else if (this.year == today.get(Calendar.YEAR)) {
			if (this.month-1 < today.get(Calendar.MONTH)) {
				return valid;
			} else if (this.month-1 == today.get(Calendar.YEAR)) {
				if (this.day < today.get(Calendar.DAY_OF_MONTH)) {
					return valid;
				} else {
					valid = false;
				}
			} else {
				valid = false;
			}
		} else {
			valid = false;
		}

		return valid;
	}

	/**
	 * Compares this Date to another Date object.
	 *
	 * @param date2 the date to compare to
	 * @return a negative integer if this date is less than the specified date,
	 *         zero if they are equal, or a positive integer if this date is greater
	 */
	@Override
	public int compareTo(Date date2) {
		if (this.year > date2.year) {
			return 1;
		} else if (this.year == date2.year) {
			if (this.month > date2.month) {
				return 1;
			} else if (this.month == date2.month) {
				if (this.day > date2.day) {
					return 1;
				} else if (this.day < date2.day) {
					return -1;
				} else {
					return 0;
				}
			} else {
				return -1;
			}
		} else {
			return -1;
		}
	}

	/**
	 * Returns a string representation of the date in "MM/DD/YYYY" format.
	 *
	 * @return the string representation of the date
	 */
	@Override
	public String toString() {
		return this.month + "/" + this.day + "/" + this.year;
	}

	/**
	 * Checks if this Date is equal to another object.
	 *
	 * @param date2 the object to compare
	 * @return true if the two dates are equal, false otherwise
	 */
	@Override
	public boolean equals(Object date2) {
		if (date2 == this) {
			return true;
		}
		if (!(date2 instanceof Date)) {
			return false;
		}
		Date other = (Date) date2;

		return this.year == other.year && this.month == other.month && this.day == other.day;
	}

	/**
	 * Tests if the result of a method matches the expected output.
	 *
	 * @param date           the date being tested
	 * @param expectedOutput the expected output of the method
	 * @param actualOutput   the actual output of the method
	 */
	public static String testResult(Date date, boolean expectedOutput, boolean actualOutput) {
		return "Testing the following date: " + date.toString() + 
		"\n Expected output: " + Boolean.toString(expectedOutput) + 
		"\n Actual output: " + Boolean.toString(actualOutput) + "\n";
	}

	
	/**
	 * Test if the Date class works as intended.
	 * Validates the number of days in February for a non-leap year.
	 */
	private static String testDaysInFeb_Nonleap() {
        Date date = new Date(2, 29, 2011);
        boolean expectedOutput = false;
        boolean actualOutput = date.isValid();
        return testResult(date, expectedOutput, actualOutput);

	}

	/**
	 * Tests if the Date class works as intended.
	 * Validates the number of days in a month for an invalid month.
	 */
	private static String testValidMonth() {
        Date date = new Date(13, 29, 2011);
        boolean expectedOutput = false;
        boolean actualOutput = date.isValid();
        return testResult(date, expectedOutput, actualOutput);
	}

	/**
	 * Tests if the Date class works as intended.
	 * Validates the number of days in a month for an invalid day.
	 */
	private static String testValidDay() {
        Date date = new Date(5, 76, 2011);
        boolean expectedOutput = false;
        boolean actualOutput = date.isValid();
        return testResult(date, expectedOutput, actualOutput);
	}

	/**
	 * Tests if the Date class works as intended.
	 * Validates the number of months within six months.
	 */
	private static String testWithinSixMonths() {
        Date date = new Date(8, 16, 2025);
        boolean expectedOutput = false;
        boolean actualOutput = date.withinSixMonths();
        return testResult(date, expectedOutput, actualOutput);

	}

	/**
	 * Tests if the Date class works as intended.
	 * Validates the number of days since today to be within the past.
	 */
	private static String testOnOrBeforeToday() {
        Date date = new Date(7, 30, 2011);
        boolean expectedOutput = true;
        boolean actualOutput = date.onOrBeforeToday();
        return testResult(date, expectedOutput, actualOutput);
	}

	/**
	 * Tests if the Date class works as intended.
	 * Validates the number of days since today to be within the future.
	 */
	private static String testTodayOrAfterToday() {
        Date date = new Date(8, 16, 2025);
        boolean expectedOutput = false;
        boolean actualOutput = date.notOnOrAfterToday();
        return testResult(date, expectedOutput, actualOutput);

	}

	/**
	 * The main method to run tests and experiments on the Date class.
	 *
	 * @param args the command-line arguments
	 */
    public static void main(String[] args) {
		
		// System.out.println("Testing leap year");
		// System.out.println(testDaysInFeb_Nonleap());
		
		// System.out.println("Testing invalid month");
		// System.out.println(testValidMonth());
		
		// System.out.println("Testing invalid day");
		// System.out.println(testValidDay());
		
		// System.out.println("Testing within six months");
		// System.out.println(testWithinSixMonths());
		
		// System.out.println("Testing on or before today");
		// System.out.println(testOnOrBeforeToday());
		
		// System.out.println("Testing today or after today");
		// System.out.println(testTodayOrAfterToday());
    }
}