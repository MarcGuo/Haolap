package com.haolap.command.parser.impl;

import com.haolap.command.parser.GrammarTreeMonitor;
import com.haolap.command.parser.SyntaxAnalyser;
import com.haolap.command.parser.exception.GrammarCheckException;
import com.haolap.command.util.GrammarTree;
import com.haolap.command.util.Token;
import com.haolap.command.util.TokenType;

public class DiceWordMonitor extends AbstractMonitor implements
		GrammarTreeMonitor {

	@Override
	public boolean checkGrammarTree(GrammarTree grammarTree,
			SyntaxAnalyser syntaxAnalyser) throws GrammarCheckException {
		syntaxAnalyser.setOperionName("dice");
		int commaAmount = grammarTree.getSubNode(
				new Token(TokenType.DELIMITER, ",")).size();
		if (commaAmount == 0) {
			this.throwExceptionAndAddErrorMsg("dice", syntaxAnalyser,
					"Dice should be used to deal with mutiple dimenstion partation");
		}
		GrammarTree tree = grammarTree.getSubNode(0);

		if (tree == null || tree.getSiblingAmount() == 0) {
			this.throwExceptionAndAddErrorMsg("dice", syntaxAnalyser, null);
		}

		while (true) {
			checkToken(tree, TokenType.IDENTIFIER, null, true, syntaxAnalyser);
			String dimensionName = tree.getValue().getToken();
			syntaxAnalyser.addDimensionLevelInfo(dimensionName, "");
			int nextAmount = tree.getNextAmount();
			if (nextAmount >= 2) {
				tree = tree.next();
				boolean hasAsSentence = checkToken(tree, TokenType.KEYWARD,
						"as", false, syntaxAnalyser);
				if (hasAsSentence) {
					tree = this.checkDimensionAsSentence(tree, syntaxAnalyser,
							3, dimensionName);
				} else {
					checkToken(tree, TokenType.DELIMITER, ",", true,
							syntaxAnalyser);
				}
			} else if (nextAmount == 1) {
				this.throwExceptionAndAddErrorMsg("dice", syntaxAnalyser, null);
			}
			if (tree.hasNext())
				tree = tree.next();
			else
				break;
		}

		return true;
	}
}
