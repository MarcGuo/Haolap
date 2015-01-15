package com.haolap.command.scanner.impl;

import com.haolap.command.scanner.LexicalAnalyser;
import com.haolap.command.scanner.SubDFA;
import com.haolap.command.scanner.exception.AnalyseException;
import com.haolap.command.util.TokenType;
import com.haolap.command.util.WordTable;

public class DefaultNumericalDFA implements SubDFA {


	@Override
	public int analyse(LexicalAnalyser analyser, int beginIndex) throws AnalyseException {
		String content = analyser.getSentence();
		if (!WordTable.isDigit(content.charAt(beginIndex))) {
			throw new AnalyseException(String.format("Error Undefined String [%c] ", content.charAt(beginIndex)));
		}
		for (int index = beginIndex; index < content.length(); index++) {
			char c = content.charAt(index);
			if (!WordTable.isDigit(c)) {
				int endIndex = index;
				if (!WordTable.isDelimiter(c) &&/* !WordTable.isDelimiterPart(c) && */!WordTable.isOverableLetter(c)) {
					throw new AnalyseException(String.format("Error Undefined String [%s] ", content.substring(beginIndex, endIndex + 1)));
				}
				return addResultToAnalyserAndGetResult(analyser, beginIndex, endIndex);
			}
		}
		return addResultToAnalyserAndGetResult(analyser, beginIndex, content.length());
	}
	private int addResultToAnalyserAndGetResult(LexicalAnalyser analyser,int beginIndex,int endIndex){
		String content = analyser.getSentence();
		analyser.addTokenToResult(TokenType.CONSTANT, content.substring(beginIndex,endIndex));
		return endIndex;
	}
}
