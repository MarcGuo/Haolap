package com.haolap.command.parser.impl;

import com.haolap.command.parser.ParserResultExaminer;
import com.haolap.command.parser.SyntaxAnalyser;
import com.haolap.command.parser.exception.GrammarCheckException;
import com.haolap.command.util.ParserResult;

public class SelectExaminer extends AbstractExaminer implements
		ParserResultExaminer {

	@Override
	public ParserResult specialCheckAndGetParserResult(ParserResult result,
			SyntaxAnalyser analyser) throws GrammarCheckException {
		result = this.replaceNick(result, analyser);
		boolean conditionIsOk = this.checkCondtionAmount(result);
		if (!conditionIsOk) {
			this.throwException(result.getOperationName(), null, analyser);
		}
		if (result.getAggregateFunctionName() == null) {
			this.throwException(result.getOperationName(),
					"Please add an aggregative function for calculation",
					analyser);
		}
		if (result.getDimensionLevelInfo().size() == 0) {
			this.throwException(result.getOperationName(),
					"Please add some dimensions that you want to search",
					analyser);
		}
		if (result.getResultCube() == null) {
			this.throwException(result.getOperationName(),
					"Your have to name the result cube", analyser);
		}
		return result;
	}
}
