package com.haolap.command.parser.impl;

import com.haolap.command.parser.GrammarTreeMonitor;
import com.haolap.command.parser.SyntaxAnalyser;
import com.haolap.command.parser.exception.GrammarCheckException;
import com.haolap.command.util.GrammarTree;
import com.haolap.command.util.TokenType;

public class SelectWordMonitor extends AbstractMonitor implements
		GrammarTreeMonitor {
	@Override
	public boolean checkGrammarTree(GrammarTree grammarTree,
			SyntaxAnalyser syntaxAnalyser) throws GrammarCheckException {
		syntaxAnalyser.setOperionName("select");
		GrammarTree tree = grammarTree.getSubNode(0);

		if (tree == null || tree.getSiblingAmount() < 3)
			this.throwExceptionAndAddErrorMsg("select", syntaxAnalyser, null);

		while (true) {
			// first word
			checkToken(tree, TokenType.IDENTIFIER, null, true, syntaxAnalyser);
			String levelName = tree.getValue().getToken();
			// second word
			tree = tree.next();
			checkToken(tree, TokenType.KEYWARD, "on", true, syntaxAnalyser);
			// third word
			tree = tree.next();
			checkToken(tree, TokenType.IDENTIFIER, null, true, syntaxAnalyser);
			String dimensionName = tree.getValue().getToken();
			// check the rest tokens amount
			syntaxAnalyser.addDimensionLevelInfo(dimensionName, levelName);
			int nextAmount = tree.getNextAmount();
			if (nextAmount > 1) {
				tree = tree.next();
				boolean hasAsSentence = checkToken(tree, TokenType.KEYWARD,
						"as", false, syntaxAnalyser);
				if (hasAsSentence) {
					tree = this.checkDimensionAsSentence(tree, syntaxAnalyser,
							5, dimensionName);
				} else {
					checkToken(tree, TokenType.DELIMITER, ",", true,
							syntaxAnalyser);
				}
			} else if (nextAmount == 1)
				this.throwExceptionAndAddErrorMsg("select", syntaxAnalyser,
						null);
			boolean hasNext = tree.hasNext();
			if (hasNext)
				tree = tree.next();
			else
				break;
		}

		return true;
	}
}
