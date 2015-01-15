package com.haolap.command.scanner.impl;

import com.haolap.command.scanner.LexicalAnalyser;
import com.haolap.command.scanner.SubDFA;
import com.haolap.command.scanner.exception.AnalyseException;
import com.haolap.command.util.TokenType;
import com.haolap.command.util.WordTable;

public class DefaultDelimiterSubDFA implements SubDFA {

	@Override
	public int analyse(LexicalAnalyser analyser, int beginIndex)
			throws AnalyseException {
		String content = analyser.getSentence();
		char character = content.charAt(beginIndex);
		char characterNext = beginIndex + 1 == content.length() ? ' ' : content
				.charAt(beginIndex + 1);
		// if (!WordTable.isDelimiter(character) &&
		// !WordTable.isDelimiterPart(character)) {
		// throw new AnalyseException(String.format(
		// "Error: Undefined character [%c]", character));
		// }
		// if (WordTable.isDelimiterPart(character)
		// || (WordTable.isDelimiter(characterNext) || WordTable
		// .isDelimiterPart(characterNext))) {
		// String delimiter = String.valueOf(character)
		// + String.valueOf(characterNext);
		// if (WordTable.isDelimiter(delimiter)) {
		// return addResultToAnalyerAndGetResult(analyser, beginIndex,
		// beginIndex + 2);
		// } else {
		// throw new AnalyseException(String.format(
		// "Error: Undefined character [%c]", character));
		// }
		// }
		if (WordTable.isDelimiter(character)
				&& (WordTable.isDigit(characterNext)
						|| WordTable.isOverableLetter(characterNext) || WordTable
							.isLetter(characterNext))) {
			return addResultToAnalyerAndGetResult(analyser, beginIndex,
					beginIndex + 1);
		}
		throw new AnalyseException(String.format(
				"Error: Undefined character [%c]", character));

	}

	private int addResultToAnalyerAndGetResult(LexicalAnalyser analyser,
			int beginIndex, int endIndex) {
		String content = analyser.getSentence();
		analyser.addTokenToResult(TokenType.DELIMITER,
				content.substring(beginIndex, endIndex));
		return endIndex;
	}

}
