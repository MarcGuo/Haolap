package com.haolap.command.parser.impl;

import com.haolap.command.parser.GrammarTreeMonitor;
import com.haolap.command.parser.SyntaxAnalyser;
import com.haolap.command.parser.exception.GrammarCheckException;
import com.haolap.command.util.GrammarTree;
import com.haolap.command.util.TokenType;

public class SliceWordMonitor extends AbstractMonitor implements
		GrammarTreeMonitor {

	@Override
	public boolean checkGrammarTree(GrammarTree grammarTree,
			SyntaxAnalyser syntaxAnalyser) throws GrammarCheckException {
		syntaxAnalyser.setOperionName("slice");
		GrammarTree tree = grammarTree.getSubNode(0);

		if (tree == null
				|| (tree.getSiblingAmount() != 1 && tree.getSiblingAmount() != 3)) {
			GrammarCheckException exception = new GrammarCheckException(
					"Error: the 'slice' subsentence is not format");
			syntaxAnalyser.addErrorMsg(exception.getMessage());
			throw exception;
		}
		checkToken(tree, TokenType.IDENTIFIER, null, true, syntaxAnalyser);
		String dimensionName = tree.getValue().getToken();
		syntaxAnalyser.addDimensionLevelInfo(dimensionName, "");
		if (tree.hasNext()) {
			tree = tree.next();
			checkToken(tree, TokenType.KEYWARD, "as", true, syntaxAnalyser);
			tree = tree.next();
			checkToken(tree, TokenType.IDENTIFIER, null, true, syntaxAnalyser);
			String dimensionNickname = tree.getValue().getToken();
			syntaxAnalyser.addDimensionNickName(dimensionName, dimensionNickname);
		}else{
			
		}
		return true;
	}

}
