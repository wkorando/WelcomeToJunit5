package com.bk.hotel.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.platform.commons.util.PreconditionViolationException;

import com.bk.hotel.test.utils.ToDataValidationBean;

public class ArgumentsAccessorParamterizedTest {

	@ParameterizedTest
	@CsvSource({ "Valid booking dates, 03/03/2020, 03/07/2020",
			"Null check-in date, , 11/27/2020, Must provide a check-in date.",
			"Both dates null, , , 'Must provide a check-in date., Must provide a check-out date.'",
			"Invalid check-in date, 02/30/2020, 03/07/2020, check-in date of: 02/30/2020 is not a valid date or does not match date format of: MM/DD/YYYY" })
	public void verifyDateValidationUsingArgumentAccessor(ArgumentsAccessor arguments) {
		DateValidationBean dateValidation;
		try {
			dateValidation = new DateValidationBean(arguments.getString(0), arguments.getString(1),
					arguments.getString(2), StringArrayConverter.convert(arguments.get(3)));
		} catch (PreconditionViolationException exception) {
			// Used when scenario has no messages being returned
			dateValidation = new DateValidationBean(arguments.getString(0), arguments.getString(1),
					arguments.getString(2));
		}
		ReservationServiceImpl service = new ReservationServiceImpl();
		List<String> errorMsgs = service.verifyReservationDates(dateValidation.checkInDate,
				dateValidation.checkOutDate);
		assertThat(errorMsgs).containsExactlyInAnyOrder(dateValidation.errorMsgs);
	}

	@ParameterizedTest
	@CsvSource({ "Valid booking dates, 03/03/2020, 03/07/2020",
			"Null check-in date, , 11/27/2020, Must provide a check-in date.",
			"Both dates null, , , 'Must provide a check-in date., Must provide a check-out date.'",
			"Invalid check-in date, 02/30/2020, 03/07/2020, check-in date of: 02/30/2020 is not a valid date or does not match date format of: MM/DD/YYYY" })
	public void verifyDateValidationUsingConverter(
			@AggregateWith(DateValidationBeanAggregator.class) DateValidationBean dateValidation) {

		ReservationServiceImpl service = new ReservationServiceImpl();
		List<String> errorMsgs = service.verifyReservationDates(dateValidation.checkInDate,
				dateValidation.checkOutDate);
		assertThat(errorMsgs).containsExactlyInAnyOrder(dateValidation.errorMsgs);
	}

	@ParameterizedTest
	@CsvSource({ "Valid booking dates, 03/03/2020, 03/07/2020",
			"Null check-in date, , 11/27/2020, Must provide a check-in date.",
			"Both dates null, , , 'Must provide a check-in date., Must provide a check-out date.'",
			"Invalid check-in date, 02/30/2020, 03/07/2020, check-in date of: 02/30/2020 is not a valid date or does not match date format of: MM/DD/YYYY" })
	public void verifyDateValidationUsingAnnotation(@ToDataValidationBean DateValidationBean dateValidation) {
		ReservationServiceImpl service = new ReservationServiceImpl();
		List<String> errorMsgs = service.verifyReservationDates(dateValidation.checkInDate,
				dateValidation.checkOutDate);
		assertThat(errorMsgs).containsExactlyInAnyOrder(dateValidation.errorMsgs);
	}
	
	@ParameterizedTest
	@CsvFileSource(resources="/DatesSource.csv")
	public void verifyDateValidationUsingCsvFile(@ToDataValidationBean DateValidationBean dateValidation) {
		ReservationServiceImpl service = new ReservationServiceImpl();
		List<String> errorMsgs = service.verifyReservationDates(dateValidation.checkInDate,
				dateValidation.checkOutDate);
		assertThat(errorMsgs).containsExactlyInAnyOrder(dateValidation.errorMsgs);
	}
}
