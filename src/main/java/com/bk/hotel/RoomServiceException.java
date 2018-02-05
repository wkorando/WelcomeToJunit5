package com.bk.hotel;

import java.util.ArrayList;
import java.util.List;

public class RoomServiceException extends RuntimeException {

	private static final long serialVersionUID = 4224097134365852621L;
	private List<String> errorMessages = new ArrayList<>();

	public RoomServiceException() {

	}

	public RoomServiceException(String message) {
		super(message);

	}

	public RoomServiceException(Throwable cause) {
		super(cause);

	}

	public RoomServiceException(String message, Throwable cause) {
		super(message, cause);

	}

	public RoomServiceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

	public List<String> getErrorMessages() {
		return errorMessages;
	}

	public void addErrorMessage(String errorMessage) {
		errorMessages.add(errorMessage);
	}

}
