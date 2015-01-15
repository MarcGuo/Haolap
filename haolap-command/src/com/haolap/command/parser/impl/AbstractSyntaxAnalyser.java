/**
 * 
 */
package com.haolap.command.parser.impl;

import java.util.Collection;
import java.util.Vector;

import com.haolap.command.parser.SyntaxAnalyser;
import com.haolap.command.parser.exception.GrammarCheckException;
import com.haolap.command.parser.exception.GrammarCreateException;
import com.haolap.command.util.AnalyticResult;
import com.haolap.command.util.ParserResult;

/**
 * @author mythboy
 * 
 */
public abstract class AbstractSyntaxAnalyser implements SyntaxAnalyser {

	/**
	 * 
	 */
	private Vector<String> errorMsgs;
	private AnalyticResult analyticResult;
	protected boolean isComplete;
	protected ParserResult result;

	public AbstractSyntaxAnalyser() {
		this.isComplete = false;
	}

	public AbstractSyntaxAnalyser(AnalyticResult result) {
		this.setAnalyticResult(result);
	}

	@Override
	public void setAnalyticResult(AnalyticResult result) {
		this.analyticResult = result;
		this.isComplete = false;
		this.result = new ParserResult();
	}

	@Override
	public AnalyticResult getAnalyticResult() {
		return this.analyticResult;
	}

	@Override
	public void analyse() throws GrammarCheckException, GrammarCreateException {
		if (this.analyticResult != null && this.analyticResult.size() != 0
				&& this.isComplete == false) {
			this.analyseImpl();
		}
	}

	protected abstract void analyseImpl() throws GrammarCheckException,
			GrammarCreateException;

	private void initErrorMsg() {
		if (this.errorMsgs == null) {
			this.errorMsgs = new Vector<String>();
		}
	}

	public void addErrorMsg(String msg) {
		this.initErrorMsg();
		this.errorMsgs.add(msg);
	}

	protected void addErrorMsg(Collection<? extends String> msgs) {
		this.initErrorMsg();
		this.errorMsgs.addAll(msgs);
	}

	@Override
	public Vector<String> getErrorMsg() {
		return this.errorMsgs == null ? new Vector<String>() : this.errorMsgs;
	}

	@Override
	public boolean isComplete() {
		return this.isComplete;
	}

	@Override
	public ParserResult getParserResult() {
		return this.result;
	}

	@Override
	public void setTargetCube(String targetCube)throws GrammarCheckException {
		if(this.result.getTargetCube()!=null){
			this.throwExceptionAndAddErrorMsg("Error : multiple operable cube definition");
		}
		this.result.setTargetCube(targetCube);

	}

	@Override
	public void setResultCube(String resultCube) throws GrammarCheckException  {
		if(this.result.getResultCube() !=null){
			this.throwExceptionAndAddErrorMsg("Error: multiple rename for new cube ");
		}
		this.result.setResultCube(resultCube);
	}

	@Override
	public void setAggregateFunctionName(String aggregateFunctionName) throws GrammarCheckException {
		if(this.result.getAggregateFunctionName() != null){
			this.throwExceptionAndAddErrorMsg("Error : multiple aggregate function");
		}
		this.result.setAggregateFunctionName(aggregateFunctionName);
	}

	@Override
	public void addDimensionNickName(String dimension, String nickName)
			throws GrammarCheckException {
		boolean result = this.result.addDimensionNickName(dimension, nickName);
		if (!result) {
			this.throwExceptionAndAddErrorMsg("Error: multiple dimension nickname definition");
		}
	}

	@Override
	public void addCondition(String value, String start, String end)
			throws GrammarCheckException {
		boolean result = this.result.addCondition(value, start, end);
		if (!result) {
			this.throwExceptionAndAddErrorMsg("Error: multiple condition definition");
		}
	}

	@Override
	public void addDimensionLevelInfo(String dimension, String level)
			throws GrammarCheckException {
		boolean result = this.result.addDimensionLevelInfo(dimension, level);
		if (!result) {
			this.throwExceptionAndAddErrorMsg("Error: multiple dimension definend or your dimension's nickname is the same as a dimension name");
		}
	}

	protected void throwExceptionAndAddErrorMsg(String errorMsg)
			throws GrammarCheckException {
		GrammarCheckException exception = new GrammarCheckException(errorMsg);
		this.addErrorMsg(errorMsg);
		throw exception;
	}

	@Override
	public void setOperionName(String operation) throws GrammarCheckException {
		if (this.result.getOperationName() != null) {
			this.throwExceptionAndAddErrorMsg("mutilple operation");
		}
		this.result.setOperationName(operation);
	}
}
