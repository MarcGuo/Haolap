/**
 * 
 */
package com.haolap.command.parser;

import java.util.Vector;

import com.haolap.command.parser.exception.GrammarCheckException;
import com.haolap.command.parser.exception.GrammarCreateException;
import com.haolap.command.util.AnalyticResult;
import com.haolap.command.util.ParserResult;

/**
 * @author "Mark Guo"
 * 
 */
public interface SyntaxAnalyser extends ParserOperation{

	public void setAnalyticResult(AnalyticResult result);

	public AnalyticResult getAnalyticResult();

	public void analyse() throws GrammarCheckException, GrammarCreateException;

	public Vector<String> getErrorMsg();

	public boolean isComplete();
	
	public ParserResult getParserResult();
	
	public void addErrorMsg(String value);
	

	
}
