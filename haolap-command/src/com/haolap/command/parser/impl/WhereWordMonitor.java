package com.haolap.command.parser.impl;

import com.haolap.command.parser.GrammarTreeMonitor;
import com.haolap.command.parser.SyntaxAnalyser;
import com.haolap.command.parser.exception.GrammarCheckException;
import com.haolap.command.util.GrammarTree;
import com.haolap.command.util.Token;
import com.haolap.command.util.TokenType;

public class WhereWordMonitor extends AbstractMonitor implements
		GrammarTreeMonitor {

	@Override
	public boolean checkGrammarTree(GrammarTree grammarTree,
			SyntaxAnalyser syntaxAnalyser) throws GrammarCheckException {
		GrammarTree tree = grammarTree.getSubNode(0);
		// there are tree words need to be handled , at least
		if (tree == null || tree.getSiblingAmount() < 3)
			this.throwExceptionAndAddErrorMsg("where", syntaxAnalyser, null);
		while (true) {
			// first word
			if (tree.getNextAmount() + 1 < 3) {
				throwExceptionAndAddErrorMsg("where", syntaxAnalyser, null);
			}
			checkToken(tree, TokenType.IDENTIFIER, null, true, syntaxAnalyser);
			String dimensionName = tree.getValue().getToken();
			// second word
			tree = tree.next();
			boolean isEquals = checkToken(tree, TokenType.KEYWARD, "equals",
					false, syntaxAnalyser);
			if (isEquals) {
				tree = this.checkEqualSentence(tree, syntaxAnalyser,
						dimensionName);
			} else {
				tree = this.checkBetweenAndSentence(tree, syntaxAnalyser,
						dimensionName);
			}
			if (tree.hasNext())
				tree = tree.next();
			else
				break;
		}

		return true;
	}

	private GrammarTree checkEqualSentence(GrammarTree tree,
			SyntaxAnalyser syntaxAnalyser, String dimensionName)
			throws GrammarCheckException {
		checkToken(tree, TokenType.KEYWARD, "equals", true, syntaxAnalyser);
		String condition = null;
		int nextAmount = tree.getNextAmount();
		if (nextAmount > 3) {
			tree = tree.next();
			tree = checkConditionWord(tree, syntaxAnalyser);
			condition = tree.getValue().getToken();
			tree = tree.next();
			checkToken(tree, TokenType.DELIMITER, ",", true, syntaxAnalyser);
		} else if (nextAmount == 1) {
			tree = tree.next();
			tree = checkConditionWord(tree, syntaxAnalyser);
			condition = tree.getValue().getToken();
		} else
			this.throwExceptionAndAddErrorMsg("where", syntaxAnalyser, null);
		syntaxAnalyser.addCondition(dimensionName, condition, condition);
		return tree;
	}

	private GrammarTree checkBetweenAndSentence(GrammarTree tree,
			SyntaxAnalyser syntaxAnalyser, String dimensionName)
			throws GrammarCheckException {
		checkToken(tree, TokenType.KEYWARD, "between", true, syntaxAnalyser);
		String start = null;
		String end = null;
		int nextAmount = tree.getNextAmount();
		if (nextAmount > 4 || nextAmount == 3) {
			// first condition
			tree = tree.next();
			tree = checkConditionWord(tree, syntaxAnalyser);
			start = tree.getValue().getToken();
			// word : and
			tree = tree.next();
			checkToken(tree, TokenType.KEYWARD, "and", true, syntaxAnalyser);
			// second condition
			tree = tree.next();
			tree = checkConditionWord(tree, syntaxAnalyser);
			end = tree.getValue().getToken();
			if (nextAmount > 4) {
				// word : ,
				tree = tree.next();
				checkToken(tree, TokenType.DELIMITER, ",", true, syntaxAnalyser);
			}
		} else
			this.throwExceptionAndAddErrorMsg("where", syntaxAnalyser, null);
		syntaxAnalyser.addCondition(dimensionName, start, end);
		return tree;
	}

	private GrammarTree checkConditionWord(GrammarTree tree,
			SyntaxAnalyser syntaxAnalyser) throws GrammarCheckException {
		Token token = tree.getValue();
		if (!token.isTheType(TokenType.IDENTIFIER)
		 && !token.isTheType(TokenType.CONSTANT) ) {
			this.throwExceptionAndAddErrorMsg("where", syntaxAnalyser, null);
		}
		return tree;
	}
}
