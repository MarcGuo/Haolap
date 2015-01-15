package com.haolap.command.scanner.exception;

public class UnDefinedCharacterException extends Exception{
	private static final long serialVersionUID = 1L;
	public UnDefinedCharacterException() {
	}

	public UnDefinedCharacterException(String message) {
		super(message);
	}

	public UnDefinedCharacterException(Throwable cause) {
		super(cause);
	}

	public UnDefinedCharacterException(String message, Throwable cause) {
		super(message, cause);
	}
}
