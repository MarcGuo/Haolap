package com.haolap.command.scanner;

import java.lang.String;
import java.util.Vector;

import com.haolap.command.scanner.exception.AnalyseException;
import com.haolap.command.util.AnalyticResult;
import com.haolap.command.util.TokenType;

public interface LexicalAnalyser {
	public String getSentence();
	public void setSentence(String sentence);
	public boolean isComplete();
	public void analyse() throws AnalyseException;
	public Vector<String> getErrorMsg();
	public AnalyticResult getAnalyticResult();
	public void reset();
	public void reset(String sentence);
	void addErrorMsg(String msg);
    void addTokenToResult(TokenType tokenType, String value);
}
