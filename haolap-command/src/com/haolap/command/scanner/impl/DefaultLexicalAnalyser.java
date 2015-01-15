package com.haolap.command.scanner.impl;

import com.haolap.command.scanner.DFA;
import com.haolap.command.scanner.LexicalAnalyser;
import com.haolap.command.scanner.exception.AnalyseException;

public class DefaultLexicalAnalyser extends AbstractLexicalAnalyser implements
		LexicalAnalyser {
	private DFA dfa;

	public DefaultLexicalAnalyser(DFA dfa) {
		super();
		this.dfa = dfa;
	}

	public DefaultLexicalAnalyser(String sentence, DFA dfa) {
		super(sentence);
		this.dfa = dfa;
	}

	@Override
	public void analyseImpl() throws AnalyseException {
		try {
			this.dfa.analyse(this);
		} catch (AnalyseException e) {
			this.addErrorMsg(e.getMessage());
			throw e;
		}
	}

}
