package com.youtube.controller.exceptions;

public class DataBaseException extends Exception {

	private static final long serialVersionUID = -3938162429397772071L;

	public DataBaseException() {

	}

	public DataBaseException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public DataBaseException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public DataBaseException(String arg0) {
		super(arg0);
	}

	public DataBaseException(Throwable arg0) {
		super(arg0);
	}

}
