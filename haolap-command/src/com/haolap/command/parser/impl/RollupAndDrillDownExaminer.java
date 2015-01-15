package com.haolap.command.parser.impl;

import java.util.HashMap;
import java.util.Map;

import com.haolap.command.parser.ParserResultExaminer;
import com.haolap.command.parser.SyntaxAnalyser;
import com.haolap.command.parser.exception.GrammarCheckException;
import com.haolap.command.util.ParserResult;

public class RollupAndDrillDownExaminer extends AbstractExaminer implements
		ParserResultExaminer {

	@Override
	public ParserResult specialCheckAndGetParserResult(ParserResult result,
			SyntaxAnalyser analyser) throws GrammarCheckException {
		boolean allConditionIsOk = result.getAggregateFunctionName() == null
				&& result.getConditions().size() == 0
				&& result.getDimensionLevelInfo().size() == 2
				&& result.getResultCube() == null;
		if (!allConditionIsOk) {
			this.throwException(result.getOperationName(), null, analyser);
		}
		
		return this.uniteDimensionInfo(result, analyser);

	}

	private ParserResult uniteDimensionInfo(ParserResult result,
			SyntaxAnalyser analyser) throws GrammarCheckException {
		Map<String, String> dimensionInfo = result.getDimensionLevelInfo();
		String value = dimensionInfo.get("");
		String key = result.getDimensionByLevel("");
		if (value == null || key == null) {
			this.throwException(result.getOperationName(), null, analyser);
		}
		Map<String, String> newDimensionInfo = new HashMap<String, String>();
		newDimensionInfo.put(key, value);
		result.setDimensionLevelInfo(newDimensionInfo);
		return result;

	}

}
