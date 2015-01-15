package com.haolap.command.scanner.impl;

import com.haolap.command.scanner.LexicalAnalyser;
import com.haolap.command.scanner.SubDFA;
import com.haolap.command.scanner.exception.AnalyseException;
import com.haolap.command.util.TokenType;
import com.haolap.command.util.WordTable;

public class DefaultStringSubDFA implements SubDFA {

	@Override
	public int analyse(LexicalAnalyser analyser, int beginIndex)
			throws AnalyseException {
		String content = analyser.getSentence();
		if (!WordTable.isLetter(content.charAt(beginIndex))) {
			throw new AnalyseException(String.format(
					"Error: [%c] is not a letter", content.charAt(beginIndex)));
		}

		char beginChar = content.charAt(beginIndex);
		if (beginChar == '\'') {
			return handleString(analyser, beginIndex);
		} else {
			return handleWord(analyser, beginIndex);
		}
	}

	private int handleString(LexicalAnalyser analyser, int beginIndex)
			throws AnalyseException {
		String content = analyser.getSentence();
		for (int index = beginIndex + 1; index < content.length(); index++) {
			char c = content.charAt(index);
			if (c == '\'') {
				int endIndex = index;
				if (index != content.length() - 1) {
					char nextC = content.charAt(index + 1);
					if (!WordTable.isDelimiter(nextC)
							&& !WordTable.isOverableLetter(nextC)
					/* && !WordTable.isDelimiterPart(nextC) */) {
						throw new AnalyseException(String.format(
								"Error: Undefined word [%s]",
								content.substring(beginIndex, endIndex + 2)));
					}
				}
				return this.addResultToAnalyserAndGetResult(analyser,
						beginIndex + 1, endIndex) + 1;
			}
		}
		throw new AnalyseException(String.format("Error: [%s] is not a string",
				content.substring(beginIndex)));
	}

	private int handleWord(LexicalAnalyser analyser, int beginIndex)
			throws AnalyseException {
		String content = analyser.getSentence();
		for (int index = beginIndex; index < content.length(); index++) {
			char c = content.charAt(index);
			if (!WordTable.isDigit(c) && !WordTable.isLetter(c)) {
				int endIndex = index;
				if (!WordTable.isDelimiter(c) && !WordTable.isOverableLetter(c)
				/* && !WordTable.isDelimiterPart(c) */) {
					throw new AnalyseException(String.format(
							"Error: Undefined word [%s]",
							content.substring(beginIndex, endIndex + 1)));
				} else {
					return addResultToAnalyserAndGetResult(analyser,
							beginIndex, endIndex);
				}

			}
		}
		return addResultToAnalyserAndGetResult(analyser, beginIndex,
				content.length());
	}

	public TokenType checkTokenType(String token) {
		String temp = token.toLowerCase();
		if (WordTable.isKeyWord(temp)) {
			return TokenType.KEYWARD;
		}
		return TokenType.IDENTIFIER;
	}

	private int addResultToAnalyserAndGetResult(LexicalAnalyser analyser,
			int beginIndex, int endIndex) throws AnalyseException {
		String value = analyser.getSentence().substring(beginIndex, endIndex);
		if (value.length() == 0) {
			throw new AnalyseException("you writed an empty string");
		}
		analyser.addTokenToResult(checkTokenType(value), value);
		return endIndex;
	}

}
