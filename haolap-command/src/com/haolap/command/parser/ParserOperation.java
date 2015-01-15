package com.haolap.command.parser;

import com.haolap.command.parser.exception.GrammarCheckException;

public interface ParserOperation {
	public void setTargetCube(String targetCube) throws GrammarCheckException;

	public void setResultCube(String resultCube) throws GrammarCheckException;

	public void setAggregateFunctionName(String aggregateFunctionName) throws GrammarCheckException;

	public void addDimensionNickName(String dimension, String nickName) throws GrammarCheckException;

	public void addCondition(String value, String start, String end) throws GrammarCheckException;

	public void addDimensionLevelInfo(String dimension, String level) throws GrammarCheckException;
	
	public void setOperionName(String operation) throws GrammarCheckException;

}
