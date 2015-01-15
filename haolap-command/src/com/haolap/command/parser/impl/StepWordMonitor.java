package com.haolap.command.parser.impl;

import com.haolap.command.parser.GrammarTreeMonitor;
import com.haolap.command.parser.SyntaxAnalyser;
import com.haolap.command.parser.exception.GrammarCheckException;
import com.haolap.command.util.GrammarTree;
import com.haolap.command.util.TokenType;

public class StepWordMonitor extends AbstractMonitor implements
		GrammarTreeMonitor {

	@Override
	public boolean checkGrammarTree(GrammarTree grammarTree,
			SyntaxAnalyser syntaxAnalyser) throws GrammarCheckException {
		GrammarTree tree = grammarTree.getSubNode(0);
		if (tree == null || tree.getSiblingAmount() < 5) {
			this.throwExceptionAndAddErrorMsg("step", syntaxAnalyser, null);
		}
		syntaxAnalyser.setOperionName("step");
		// determine the target cube name
		checkToken(tree, TokenType.IDENTIFIER, null, true, syntaxAnalyser);
		syntaxAnalyser.setTargetCube(tree.getValue().getToken());
		// determine the first mutative dimension
		tree = tree.next();
		checkToken(tree, TokenType.IDENTIFIER, null, true, syntaxAnalyser);
		syntaxAnalyser.addDimensionLevelInfo(tree.getValue().getToken(), null);
		// determine the second mutative dimension
		tree = tree.next();
		checkToken(tree, TokenType.IDENTIFIER, null, true, syntaxAnalyser);
		syntaxAnalyser.addDimensionLevelInfo(tree.getValue().getToken(), null);
		// determine the dimension condition operator
		int amount = tree.getNextAmount();

		if (amount % 2 != 0) {
			this.throwExceptionAndAddErrorMsg("step", syntaxAnalyser, null);
		}
		for (int i = 0; i < amount; i += 2) {
			tree = tree.next();
			checkToken(tree, TokenType.IDENTIFIER, null, true, syntaxAnalyser);
			String operatianalDimmensionName = tree.getValue().getToken();

			tree = tree.next();
			checkToken(tree, TokenType.CONSTANT, null, true, syntaxAnalyser);

			syntaxAnalyser.addDimensionLevelInfo(operatianalDimmensionName,
					tree.getValue().getToken());
		}

		return true;
	}

}
