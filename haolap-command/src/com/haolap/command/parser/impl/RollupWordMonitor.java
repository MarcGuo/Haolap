package com.haolap.command.parser.impl;

import com.haolap.command.parser.GrammarTreeMonitor;
import com.haolap.command.parser.SyntaxAnalyser;
import com.haolap.command.parser.exception.GrammarCheckException;
import com.haolap.command.util.GrammarTree;
import com.haolap.command.util.TokenType;

public class RollupWordMonitor extends AbstractMonitor implements GrammarTreeMonitor {


	@Override
	public boolean checkGrammarTree(GrammarTree grammarTree,
			SyntaxAnalyser syntaxAnalyser) throws GrammarCheckException {
		syntaxAnalyser.setOperionName("rollup");
		GrammarTree tree = grammarTree.getSubNode(0);
		if (tree==null || tree.getSiblingAmount() != 1) {
			GrammarCheckException exception = new GrammarCheckException(
					"Error: the 'rollup' subsentence is not format");
			syntaxAnalyser.addErrorMsg(exception.getMessage());
			throw exception;
		}
		checkToken(tree, TokenType.IDENTIFIER, null, true, syntaxAnalyser);
		String dimensionName  =  tree.getValue().getToken();
		syntaxAnalyser.addDimensionLevelInfo(dimensionName, "");
		return true;
	}

}
