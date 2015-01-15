package com.haolap.command.parser.impl;

import com.haolap.command.parser.GrammarTreeMonitor;
import com.haolap.command.parser.SyntaxAnalyser;
import com.haolap.command.parser.exception.GrammarCheckException;
import com.haolap.command.util.GrammarTree;
import com.haolap.command.util.TokenType;

public class ShowWordMonitor extends AbstractMonitor implements
		GrammarTreeMonitor {

	@Override
	public boolean checkGrammarTree(GrammarTree grammarTree,
			SyntaxAnalyser syntaxAnalyser) throws GrammarCheckException {
		GrammarTree tree = grammarTree.getSubNode(0);
		if (tree == null || tree.getSiblingAmount() != 3) {
			GrammarCheckException exception = new GrammarCheckException(
					"Error: the 'show' subsentence is not format");
			syntaxAnalyser.addErrorMsg(exception.getMessage());
			throw exception;
		}
		syntaxAnalyser.setOperionName("show");
		checkToken(tree, TokenType.IDENTIFIER, null, true, syntaxAnalyser);
		syntaxAnalyser.setTargetCube(tree.getValue().getToken());
		tree = tree.next();
		checkToken(tree, TokenType.IDENTIFIER, null, true, syntaxAnalyser);
		syntaxAnalyser.addDimensionLevelInfo(tree.getValue().getToken(), null);
		tree = tree.next();
		checkToken(tree, TokenType.IDENTIFIER, null, true, syntaxAnalyser);
		syntaxAnalyser.addDimensionLevelInfo(tree.getValue().getToken(), null);
		return true;
	}

}
