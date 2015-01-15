package com.haolap.command.parser.impl;

import com.haolap.command.parser.ParserResultExaminer;
import com.haolap.command.parser.SyntaxAnalyser;
import com.haolap.command.parser.exception.GrammarCheckException;
import com.haolap.command.util.ParserResult;

public class SaveAndDeleteExaminer extends AbstractExaminer implements
		ParserResultExaminer {

	@Override
	public ParserResult specialCheckAndGetParserResult(ParserResult result,
			SyntaxAnalyser analyser) throws GrammarCheckException {
		boolean theOtherInfoIsEmpty = result.getAggregateFunctionName() == null
				&& result.getConditions().size() == 0
				&& result.getDimensionLevelInfo().size() == 0
				&& result.getDimensionNicknames().size() == 0
				&& result.getResultCube() == null;
		if (!theOtherInfoIsEmpty) {
			this.throwException(result.getOperationName(), null, analyser);
		}
		return result;
	}
}
