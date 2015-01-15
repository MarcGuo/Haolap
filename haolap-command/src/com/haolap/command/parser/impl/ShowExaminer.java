package com.haolap.command.parser.impl;

import java.util.Iterator;
import java.util.Map;

import com.haolap.command.parser.ParserResultExaminer;
import com.haolap.command.parser.SyntaxAnalyser;
import com.haolap.command.parser.exception.GrammarCheckException;
import com.haolap.command.util.ParserResult;

public class ShowExaminer extends AbstractExaminer implements
		ParserResultExaminer {

	@Override
	public ParserResult specialCheckAndGetParserResult(ParserResult result,
			SyntaxAnalyser analyser) throws GrammarCheckException {
		Map<String, String> dimensionLevelInfo = result.getDimensionLevelInfo();
		boolean conditionIsOk = dimensionLevelInfo.size() == 2;
		for (Iterator<String> iterator = dimensionLevelInfo.keySet().iterator(); iterator
				.hasNext();) {
			String key = (String) iterator.next();
			conditionIsOk = conditionIsOk
					&& (dimensionLevelInfo.get(key) == null);
		}
		boolean theOtherConditionIsOk = result.getAggregateFunctionName() == null
				&& result.getConditions().size() == 0
				&& result.getDimensionNicknames().size() == 0
				&& result.getResultCube() == null;
		if (!(conditionIsOk && theOtherConditionIsOk)) {
			this.throwException(result.getOperationName(), null, analyser);
		}
		return result;
	}

}
