package com.bk.hotel.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

public class JUnit5ParameterizedTest {

	@ParameterizedTest(name = "{0}")
	@CsvSource({// "Valid booking dates, 03/03/2020, 03/07/2020	",
			"Both dates null, null, null, Must provide a check-in date., Must provide a check-out date.]" })
	public void verifyDateValidation(ArgumentsAccessor arguments) {
		DateValidationBean dateValidation = new DateValidationBean(arguments.getString(0), arguments.getString(1),
				arguments.getString(2), arguments.get(3, String[].class));
		ReservationServiceImpl service = new ReservationServiceImpl();
		List<String> errorMsgs = service.verifyReservationDates(dateValidation.checkInDate,
				dateValidation.checkOutDate);
		assertThat(errorMsgs).containsExactlyInAnyOrder(dateValidation.errorMsgs);
	}

	static class DateValidationBean {
		final String testName;
		final String checkInDate;
		final String checkOutDate;
		final String errorMsgs[];

		private DateValidationBean(String testName, String checkInDate, String checkOutDate, String... errorMsgs) {
			this.testName = testName;
			this.checkInDate = checkInDate;

			this.checkOutDate = checkOutDate;
			this.errorMsgs = errorMsgs;
		}

		@Override
		public String toString() {
			return testName;
		}
	}
}