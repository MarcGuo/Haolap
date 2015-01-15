package com.haolap.command.parser.impl;

import com.haolap.command.parser.GrammarTreeMonitor;
import com.haolap.command.parser.SyntaxAnalyser;
import com.haolap.command.parser.exception.GrammarCheckException;
import com.haolap.command.util.GrammarTree;
import com.haolap.command.util.TokenType;

public class SaveWordMonitor extends AbstractMonitor implements
		GrammarTreeMonitor {

	@Override
	public boolean checkGrammarTree(GrammarTree grammarTree,
			SyntaxAnalyser syntaxAnalyser) throws GrammarCheckException {
		syntaxAnalyser.setOperionName("save");
		GrammarTree tree = grammarTree.getSubNode(0);
		if (tree==null || tree.getSiblingAmount() != 1) {
			throwExceptionAndAddErrorMsg("save", syntaxAnalyser, null);
		}
		checkToken(tree, TokenType.IDENTIFIER, null, true, syntaxAnalyser);
		String cubeName = tree.getValue().getToken();
		syntaxAnalyser.setTargetCube(cubeName);
		return true;
	}

}
