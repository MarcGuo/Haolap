package com.haolap.command.parser.impl;

import com.haolap.command.parser.GrammarTreeMonitor;
import com.haolap.command.parser.SyntaxAnalyser;
import com.haolap.command.parser.exception.GrammarCheckException;
import com.haolap.command.util.GrammarTree;
import com.haolap.command.util.TokenType;

public class DrilldownWordMonitor extends AbstractMonitor implements
		GrammarTreeMonitor {

	@Override
	public boolean checkGrammarTree(GrammarTree grammarTree,
			SyntaxAnalyser syntaxAnalyser) throws GrammarCheckException {
		syntaxAnalyser.setOperionName("drilldown");
		GrammarTree tree = grammarTree.getSubNode(0);
		if (tree == null || tree.getSiblingAmount() != 1) {
			this.throwExceptionAndAddErrorMsg("drilldown", syntaxAnalyser, null);
		}
		checkToken(tree, TokenType.IDENTIFIER, null, true, syntaxAnalyser);
		String dimensionName = tree.getValue().getToken();
		syntaxAnalyser.addDimensionLevelInfo(dimensionName, "");
		return true;
	}

}
