/**
 * 
 */
package com.haolap.command.parser.impl;

import java.util.HashSet;
import java.util.Set;

import com.haolap.command.parser.GrammarTreeMonitor;
import com.haolap.command.parser.SyntaxAnalyser;
import com.haolap.command.parser.exception.GrammarCheckException;
import com.haolap.command.util.GrammarTree;
import com.haolap.command.util.Token;
import com.haolap.command.util.TokenType;
import com.haolap.command.util.WordTable;

/**
 * @author mythboy
 * 
 */
public class DefaultGrammarTreeMonitor implements GrammarTreeMonitor {

	@Override
	public boolean checkGrammarTree(GrammarTree grammarTree,
			SyntaxAnalyser syntaxAnalyser) throws GrammarCheckException {
		boolean firstLevelIsOk = this.checkFirstLevel(grammarTree);
		if (!firstLevelIsOk) {
			GrammarCheckException exception = new GrammarCheckException(
					"Error: the sentence has mutiple key-words");
			syntaxAnalyser.addErrorMsg(exception.getMessage());
			throw exception;
		}
		int subNodesAmount = grammarTree.getSubNodesAmount();
		for (int i = 0; i < subNodesAmount; i++) {
			GrammarTree tree = grammarTree.getSubNode(i);
			Token token = tree.getValue();
			if (!token.isTheType(TokenType.KEYWARD)
					|| (WordTable.getKeyRank(token.getToken()) != 1 && WordTable
							.getKeyRank(token.getToken()) != 2)) {
				throw new GrammarCheckException(String.format(
						"The token([%s]) should be a key-word",
						token.toString()));
			}
			GrammarTreeMonitor monitor = GrammarTreeMonitorFactory
					.getGrammarTreeBySentenceKeyWord(token.getToken());
			if (monitor == null) {
				GrammarCheckException exception = new GrammarCheckException(
						"There no suited monitor for the grammar tree. Current token is ["
								+ token.toString() + "]");
				syntaxAnalyser.addErrorMsg(exception.getMessage());
				throw exception;
			}
			boolean checkResult = monitor
					.checkGrammarTree(tree, syntaxAnalyser);
			if (!checkResult) {
				return false;
			}
		}
		return true;

	}

	private boolean checkFirstLevel(GrammarTree tree) {
		int subNodesAmount = tree.getSubNodesAmount();
		Set<String> tempSet = new HashSet<String>();
		for (int i = 0; i < subNodesAmount; i++) {
			boolean result = tempSet.add(tree.getSubNode(i).getValue()
					.getToken());
			if (!result) {
				tempSet = null;
				return false;
			}
		}
		tempSet = null;
		return true;
	}
}
