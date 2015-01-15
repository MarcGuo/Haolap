package com.haolap.command.parser;

import com.haolap.command.parser.exception.GrammarCheckException;
import com.haolap.command.util.ParserResult;

public interface ParserResultExaminer {
	public ParserResult checkAndGetParserResult(ParserResult result, SyntaxAnalyser analyser) throws GrammarCheckException;
}
