package com.haolap.command.scanner;

import com.haolap.command.scanner.exception.AnalyseException;

public interface SubDFA {
	public int analyse(LexicalAnalyser analyser, int beginIndex)
			throws AnalyseException;
}
