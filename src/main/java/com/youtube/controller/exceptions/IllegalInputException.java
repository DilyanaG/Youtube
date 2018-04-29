package com.youtube.controller.exceptions;

public class IllegalInputException extends Exception {

	private static final long serialVersionUID = 5012453207341859992L;

	public IllegalInputException() {
		super();
	}

	public IllegalInputException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public IllegalInputException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalInputException(String message) {
		super(message);
	}

	public IllegalInputException(Throwable cause) {
		super(cause);
	}

	
}
