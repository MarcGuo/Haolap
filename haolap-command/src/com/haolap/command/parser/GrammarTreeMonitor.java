package com.haolap.command.parser;



import com.haolap.command.parser.exception.GrammarCheckException;
import com.haolap.command.util.GrammarTree;
public interface GrammarTreeMonitor {
	public boolean checkGrammarTree(GrammarTree grammarTree, SyntaxAnalyser syntaxAnalyser) throws GrammarCheckException;
}
