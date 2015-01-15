package com.haolap.command.parser.impl;

import com.haolap.command.parser.GrammarTreeMonitor;
import com.haolap.command.parser.SyntaxAnalyser;
import com.haolap.command.parser.exception.GrammarCheckException;
import com.haolap.command.util.GrammarTree;
import com.haolap.command.util.TokenType;
import com.haolap.command.util.WordTable;

public class AggWordMonitor extends AbstractMonitor implements
		GrammarTreeMonitor {

	@Override
	public boolean checkGrammarTree(GrammarTree grammarTree,
			SyntaxAnalyser syntaxAnalyser) throws GrammarCheckException {
		GrammarTree tree = grammarTree.getSubNode(0);
		if (tree == null || tree.getSiblingAmount() != 1) {
			GrammarCheckException exception = new GrammarCheckException(
					"Error: the 'agg' subsentence is not format");
			syntaxAnalyser.addErrorMsg(exception.getMessage());
			throw exception;
		}
		checkToken(tree, TokenType.KEYWARD,
				WordTable.isAggregateFunction(tree.getValue().getToken()),
				true, syntaxAnalyser);
		syntaxAnalyser.setAggregateFunctionName(tree.getValue().getToken());
		return true;
	}

}
