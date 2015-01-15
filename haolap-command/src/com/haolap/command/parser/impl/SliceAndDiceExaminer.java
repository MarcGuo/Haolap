package com.haolap.command.parser.impl;

import com.haolap.command.parser.ParserResultExaminer;
import com.haolap.command.parser.SyntaxAnalyser;
import com.haolap.command.parser.exception.GrammarCheckException;
import com.haolap.command.util.ParserResult;

public class SliceAndDiceExaminer extends AbstractExaminer implements
		ParserResultExaminer {

	@Override
	public ParserResult specialCheckAndGetParserResult(ParserResult result,
			SyntaxAnalyser analyser) throws GrammarCheckException {
		String operationName = result.getOperationName();
		if (operationName.equals("slice")) {
			result = this.limitConditionAmount(true, result, analyser);
		} else {
			result = this.limitConditionAmount(false, result, analyser);
		}
		return result;
	}

	private ParserResult limitConditionAmount(boolean limited,
			ParserResult result, SyntaxAnalyser analyser)
			throws GrammarCheckException {
		result = this.replaceNick(result, analyser);
		boolean conditionsIsOk = result.getConditions().size() == result
				.getDimensionLevelInfo().size();
		if (!conditionsIsOk) {
			this.throwException("where",
					"Conditions you gave for query didn't match the select sentence", analyser);
		}
		if (limited && result.getDimensionLevelInfo().size() != 1) {
			this.throwException("slice",
					" this operation just for one dimension partition problem",
					analyser);
		}
		if (result.getAggregateFunctionName() != null) {
			this.throwException(result.getOperationName(), null, analyser);
		}
		if (result.getResultCube() == null) {
			this.throwException(result.getOperationName(),
					"Your have to name the result cube", analyser);
		}
		return result;
	}

}
