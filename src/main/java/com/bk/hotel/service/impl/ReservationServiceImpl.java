package com.bk.hotel.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bk.hotel.service.ReservationService;

@Service
public class ReservationServiceImpl implements ReservationService {
	private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

	public ReservationServiceImpl() {
		dateFormat.setLenient(false);
	}

	@Override
	public List<String> verifyReservationDates(String checkInDate, String checkOutDate) {
		List<String> errors = new ArrayList<>();
		Date checkIn = null;
		Date checkOut = null;

		errors.addAll(validateDate(checkInDate, "check-in"));
		errors.addAll(validateDate(checkOutDate, "check-out"));
		if (errors.isEmpty()) {
			try {
				checkIn = dateFormat.parse(checkInDate);
				checkOut = dateFormat.parse(checkOutDate);
				if (checkOut.before(checkIn) || checkOut.equals(checkIn)) {
					errors.add("Check-out date must occur after check-in date");
				}
			} catch (ParseException e) {
			}
		}

		return errors;
	}

	private List<String> validateDate(String formattedDate, String dateType) {
		List<String> errors = new ArrayList<>();
		if (formattedDate == null || formattedDate.trim().isEmpty()) {
			errors.add("Must provide a " + dateType + " date.");
		} else {
			Date date;
			try {
				date = dateFormat.parse(formattedDate);
				if (date.getTime() < System.currentTimeMillis()) {
					errors.add(dateType + ": " + formattedDate + " is in the past.");
				}
			} catch (ParseException e) {
				errors.add(dateType + " date of: " + formattedDate
						+ " is not a valid date or does not match date format of: MM/DD/YYYY");
			}
		}
		return errors;
	}
}
