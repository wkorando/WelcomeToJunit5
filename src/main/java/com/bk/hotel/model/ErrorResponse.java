package com.bk.hotel.model;

import java.util.List;

public class ErrorResponse {

	private List<String> errorMessages;

	public ErrorResponse(List<String> errorMessages) {
		this.errorMessages = errorMessages;
	}

	public List<String> getErrorMessages() {
		return errorMessages;
	}

}
