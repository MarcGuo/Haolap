package com.haolap.command.scanner.exception;

public class AnalyseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public AnalyseException() {
	}

	public AnalyseException(String message) {
		super(message);
	}

	public AnalyseException(Throwable cause) {
		super(cause);
	}

	public AnalyseException(String message, Throwable cause) {
		super(message, cause);
	}
}
