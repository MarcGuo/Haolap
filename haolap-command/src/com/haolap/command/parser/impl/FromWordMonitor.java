package com.haolap.command.parser.impl;

import com.haolap.command.parser.GrammarTreeMonitor;
import com.haolap.command.parser.SyntaxAnalyser;
import com.haolap.command.parser.exception.GrammarCheckException;
import com.haolap.command.util.GrammarTree;
import com.haolap.command.util.TokenType;

public class FromWordMonitor extends AbstractMonitor implements
		GrammarTreeMonitor {

	@Override
	public boolean checkGrammarTree(GrammarTree grammarTree,
			SyntaxAnalyser syntaxAnalyser) throws GrammarCheckException {
		GrammarTree tree = grammarTree.getSubNode(0);
		if (tree == null || tree.getSiblingAmount() != 1) {
			this.throwExceptionAndAddErrorMsg("from", syntaxAnalyser, null);
		}
		checkToken(tree, TokenType.IDENTIFIER, null, true, syntaxAnalyser);
		String targetCubeName = tree.getValue().getToken();
		syntaxAnalyser.setTargetCube(targetCubeName);
		return true;
	}

}
