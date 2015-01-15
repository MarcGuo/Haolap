package com.haolap.command.scanner.impl;

import com.haolap.command.scanner.SubDFA;
import com.haolap.command.scanner.exception.UnDefinedCharacterException;
import com.haolap.command.util.WordTable;

public class SubDFAFactory {
	private SubDFAFactory() {
	}

	public static SubDFA getSubDFAbyCharacter(char c)
			throws UnDefinedCharacterException {
		if (WordTable.isLetter(c)) {
			return new DefaultStringSubDFA();
		} else if (WordTable.isDigit(c)) {
			return new DefaultNumericalDFA();
		} else if (WordTable.isDelimiter(c) /*|| WordTable.isDelimiterPart(c)*/) {
			return new DefaultDelimiterSubDFA();
		} else if (WordTable.isOverableLetter(c)) {
			return null;
		} else {
			throw new UnDefinedCharacterException("Error: The character [" + c
					+ "] ,which ascii code is [" + (int) c + "] , is unacceptable");
		}
	}

}
