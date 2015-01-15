package com.haolap.command.scanner.impl;

import java.util.Vector;

import com.haolap.command.scanner.LexicalAnalyser;
import com.haolap.command.scanner.exception.AnalyseException;
import com.haolap.command.util.AnalyticResult;
import com.haolap.command.util.Token;
import com.haolap.command.util.TokenType;

/**
 * author: Mart Guo time: 2012/5/31
 */
public abstract class AbstractLexicalAnalyser implements LexicalAnalyser {
	protected String sentence;
	protected boolean isComplete;
	private AnalyticResult result;
	private Vector<String> errorMsg;

	public AbstractLexicalAnalyser() {
		this.init();
	}

	public AbstractLexicalAnalyser(String sentence) {
		this.init();
		this.setSentence(sentence);
	}

	private void init() {
		this.isComplete = false;
		this.errorMsg = null;
		this.result = null;
	}

	@Override
	public void reset() {
		this.init();
	}

	@Override
	public void reset(String sentence) {
		this.init();
		this.setSentence(sentence);
	}

	@Override
	public String getSentence() {
		return this.sentence;
	}

	@Override
	public void setSentence(String sentence) {
		if (sentence != null && sentence.length() > 0) {
			sentence = sentence.trim().replace("\n", " ")
					.replace((char) 13, ' ').replace((char) 10, ' ')
					.replace('\t', ' ');
			sentence += '\n';
		}
		this.sentence = sentence;
	}

	@Override
	public boolean isComplete() {
		return this.isComplete;
	}

	/**
	 * define the analytic process
	 * 
	 */
	@Override
	public void analyse() throws AnalyseException {
		if (this.sentence != null && this.sentence.length() != 0) {
			this.analyseImpl();
			if (this.errorMsg == null || this.errorMsg.size() == 0) {
				this.isComplete = true;
			}
		} else {
			AnalyseException exception = new AnalyseException(
					"Error your sentence is enpty");
			this.addErrorMsg(exception.getMessage());
			throw exception;
		}
	}

	@Override
	public Vector<String> getErrorMsg() {
		return this.errorMsg == null ? new Vector<String>() : this.errorMsg;
	}

	@Override
	public AnalyticResult getAnalyticResult() {
		return this.result == null ? new AnalyticResult() : this.result;
	}

	@Override
	public void addErrorMsg(String msg) {
		if (this.errorMsg == null) {
			this.errorMsg = new Vector<String>();
		}
		this.errorMsg.add(msg);
	}

	@Override
	public void addTokenToResult(TokenType tokenType, String value) {
		if (this.result == null) {
			this.result = new AnalyticResult();
		}
		this.result.addToken(new Token(tokenType, value));
	}

	public abstract void analyseImpl() throws AnalyseException;
}
