package com.haolap.command.parser.impl;

import com.haolap.command.parser.GrammarTreeMonitor;
import com.haolap.command.parser.SyntaxAnalyser;
import com.haolap.command.parser.exception.GrammarCheckException;
import com.haolap.command.util.GrammarTree;
import com.haolap.command.util.TokenType;

public class ToWordMonitor extends AbstractMonitor implements GrammarTreeMonitor {

	public boolean checkGrammarTree(GrammarTree grammarTree,
			SyntaxAnalyser syntaxAnalyser) throws GrammarCheckException {
		GrammarTree tree = grammarTree.getSubNode(0);
		if (tree==null || tree.getSiblingAmount() != 1) {
			GrammarCheckException exception = new GrammarCheckException(
					"Error: the 'to' subsentence is not format");
			syntaxAnalyser.addErrorMsg(exception.getMessage());
			throw exception;
		}
		checkToken(tree, TokenType.IDENTIFIER, null, true, syntaxAnalyser);
		String levelName = tree.getValue().getToken();
		syntaxAnalyser.addDimensionLevelInfo("", levelName);
		return true;
	}

}
