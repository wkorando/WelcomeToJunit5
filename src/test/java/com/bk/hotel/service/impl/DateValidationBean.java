package com.bk.hotel.service.impl;

class DateValidationBean {
	final String testName;
	final String checkInDate;
	final String checkOutDate;
	final String errorMsgs[];

	DateValidationBean(String testName, String checkInDate, String checkOutDate, String... errorMsgs) {
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