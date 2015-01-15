/**
 * 
 */
package com.haolap.command.parser.exception;

/**
 * @author mythboy
 * 
 */
public class GrammarCheckException extends Exception {
	private static final long serialVersionUID = 1L;

	public GrammarCheckException() {
	}

	public GrammarCheckException(String arg0) {
		super(arg0);
	}

	public GrammarCheckException(Throwable arg0) {
		super(arg0);
	}

	public GrammarCheckException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
