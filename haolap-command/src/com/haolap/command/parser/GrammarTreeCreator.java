package com.haolap.command.parser;

import com.haolap.command.parser.exception.GrammarCreateException;
import com.haolap.command.util.GrammarTree;


public interface GrammarTreeCreator {
	public GrammarTree createGrammarTree(SyntaxAnalyser analyser) throws GrammarCreateException;
}
