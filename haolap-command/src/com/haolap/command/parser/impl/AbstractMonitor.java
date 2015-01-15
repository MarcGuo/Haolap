package com.haolap.command.parser.impl;

import com.haolap.command.parser.SyntaxAnalyser;
import com.haolap.command.parser.exception.GrammarCheckException;
import com.haolap.command.util.GrammarTree;
import com.haolap.command.util.TokenType;

public abstract class AbstractMonitor {
	protected boolean checkToken(GrammarTree tree, TokenType type,
			String value, boolean throwException, SyntaxAnalyser syntaxAnalyser)
			throws GrammarCheckException {
		boolean condition = false;
		if (value != null) {
			condition = tree.getValue().isTheType(type)
					&& tree.getValue().getToken().toLowerCase().equals(value);
		} else {
			condition = tree.getValue().isTheType(type);
		}
		if (!condition && throwException) {
			GrammarCheckException exception = new GrammarCheckException(
					String.format(
							"The token token[%s] is writed at the wrong place",
							tree.getValue().toString()));
			syntaxAnalyser.addErrorMsg(exception.getMessage());
			throw exception;

		}
		return condition;
	}

	protected boolean checkToken(GrammarTree tree, TokenType type,
			boolean valueIsOk, boolean throwException,
			SyntaxAnalyser syntaxAnalyser) throws GrammarCheckException {
		boolean condition = false;
		condition = tree.getValue().isTheType(type) && valueIsOk;
		if (!condition && throwException) {
			GrammarCheckException exception = new GrammarCheckException(
					String.format(
							"The token token[%s] is writed at the wrong place",
							tree.getValue().toString()));
			syntaxAnalyser.addErrorMsg(exception.getMessage());
			throw exception;

		}
		return condition;
	}

	protected void throwExceptionAndAddErrorMsg(String wordName,
			SyntaxAnalyser analyser, String msg) throws GrammarCheckException {
		if (msg == null) {
			msg = "Error: the '" + wordName + "' subsentence is not format";
		} else {
			msg = wordName + msg;
		}
		GrammarCheckException exception = new GrammarCheckException(msg);
		analyser.addErrorMsg(exception.getMessage());
		throw exception;
	}

	protected GrammarTree checkDimensionAsSentence(GrammarTree tree,
			SyntaxAnalyser syntaxAnalyser, int nextTokenMinimum,
			String dimensionName) throws GrammarCheckException {
		checkToken(tree, TokenType.KEYWARD, "as", true, syntaxAnalyser);
		int nextAmount = tree.getNextAmount();
		String dimensionNickName = null;
		if (nextAmount >= nextTokenMinimum) {
			tree = tree.next();
			checkToken(tree, TokenType.IDENTIFIER, null, true, syntaxAnalyser);
			dimensionNickName = tree.getValue().getToken();
			tree = tree.next();
			checkToken(tree, TokenType.DELIMITER, ",", true, syntaxAnalyser);
		} else if (nextAmount == 1) {
			tree = tree.next();
			checkToken(tree, TokenType.IDENTIFIER, null, true, syntaxAnalyser);
			dimensionNickName = tree.getValue().getToken();
		} else
			this.throwExceptionAndAddErrorMsg("select", syntaxAnalyser, null);

		syntaxAnalyser.addDimensionNickName(dimensionName, dimensionNickName);
		return tree;
	}
}
