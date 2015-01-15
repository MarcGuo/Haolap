package com.haolap.command.scanner;

import com.haolap.command.scanner.exception.AnalyseException;


public interface DFA {
	public void analyse(LexicalAnalyser analyser) throws AnalyseException;
}
