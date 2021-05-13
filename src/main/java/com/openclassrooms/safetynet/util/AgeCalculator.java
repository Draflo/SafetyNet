package com.openclassrooms.safetynet.util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class AgeCalculator {

	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	/**
	 * Calculate age from a Date object.
	 *
	 * @param birthDate the birth date
	 * @return the age
	 */

	public int calculateAge(String Birthdate) {

		LocalDate birthdate = LocalDate.parse(Birthdate, formatter);
		long dateInterval;
		dateInterval = birthdate.until(LocalDate.now(), ChronoUnit.YEARS);
		return (int) dateInterval;

	}
}
