package com.haolap.command.parser.impl;

import com.haolap.command.parser.GrammarTreeMonitor;
import com.haolap.command.parser.SyntaxAnalyser;
import com.haolap.command.parser.exception.GrammarCheckException;
import com.haolap.command.util.GrammarTree;
import com.haolap.command.util.TokenType;

public class DeleteWordMonitor extends AbstractMonitor implements
		GrammarTreeMonitor {

	@Override
	public boolean checkGrammarTree(GrammarTree grammarTree,
			SyntaxAnalyser syntaxAnalyser) throws GrammarCheckException {
		syntaxAnalyser.setOperionName("delete");
		GrammarTree tree = grammarTree.getSubNode(0);
		if (tree == null || tree.getSiblingAmount() != 1) {
			throwExceptionAndAddErrorMsg("delete", syntaxAnalyser, null);
		}
		checkToken(tree, TokenType.IDENTIFIER, null, true, syntaxAnalyser);
		syntaxAnalyser.setTargetCube(tree.getValue().getToken());
		return true;
	}

}
