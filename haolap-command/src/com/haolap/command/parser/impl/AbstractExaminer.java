package com.haolap.command.parser.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.haolap.command.parser.ParserResultExaminer;
import com.haolap.command.parser.SyntaxAnalyser;
import com.haolap.command.parser.exception.GrammarCheckException;
import com.haolap.command.util.ParserResult;

public abstract class AbstractExaminer implements ParserResultExaminer {

	protected void throwException(String name, String msg,
			SyntaxAnalyser analyser) throws GrammarCheckException {
		if (msg == null) {
			msg = " You gave some fallacious parameters";
		}
		GrammarCheckException exception = new GrammarCheckException(name
				+ " : " + msg);
		analyser.addErrorMsg(exception.getMessage());
		throw exception;
	}

	public ParserResult checkAndGetParserResult(ParserResult result,
			SyntaxAnalyser analyser) throws GrammarCheckException {
		if (result.getTargetCube() == null) {
			this.throwException(result.getOperationName(),
					"Your have to decide the fundamental cube", analyser);
		}
		if (result.getTargetCube().equals(result.getResultCube())) {
			this.throwException(result.getOperationName(),
					"The fundamental cube and the result cube have the same name."
							+ "Please rename the result cube", analyser);
		}
		return this.specialCheckAndGetParserResult(result, analyser);
	}

	protected abstract ParserResult specialCheckAndGetParserResult(
			ParserResult result, SyntaxAnalyser analyser)
			throws GrammarCheckException;

	protected ParserResult replaceNick(ParserResult result,
			SyntaxAnalyser analyser) throws GrammarCheckException {
		Map<String, String[]> conditions = result.getConditions();
		Map<String, String[]> newConditions = new HashMap<String, String[]>();
		Set<String> keySet = conditions.keySet();
		for (String key : keySet) {
			String[] value = conditions.get(key);
			String realName = result.getRealName(key);
			if (realName == null) {
				this.throwException("where ", " undefined nickname " + key,
						analyser);
			}
			newConditions.put(realName, value);
		}
		result.setConditions(newConditions);
		return result;
	}

	protected boolean checkCondtionAmount(ParserResult result) {
		return result.getConditions().size() <= result.getDimensionLevelInfo()
				.size();
	}

}
