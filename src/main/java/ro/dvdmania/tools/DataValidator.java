package ro.dvdmania.tools;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import ro.dvdmania.entities.Account;

public class DataValidator {

	public static LocalDate checkDate(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDate parsedDate = null;

		try {
			parsedDate = LocalDate.parse(date, formatter);
		} catch (DateTimeParseException e) {
			e.printStackTrace();
		}

		return parsedDate;
	}
}
