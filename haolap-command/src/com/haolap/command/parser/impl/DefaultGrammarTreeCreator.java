/**
 * 
 */
package com.haolap.command.parser.impl;

import com.haolap.command.parser.GrammarTreeCreator;
import com.haolap.command.parser.SyntaxAnalyser;
import com.haolap.command.parser.exception.GrammarCreateException;
import com.haolap.command.util.AnalyticResult;
import com.haolap.command.util.GrammarTree;
import com.haolap.command.util.Token;
import com.haolap.command.util.TokenType;
import com.haolap.command.util.WordTable;

/**
 * @author mythboy
 * 
 */
public class DefaultGrammarTreeCreator implements GrammarTreeCreator {

	/**
	 * 
	 */

	public DefaultGrammarTreeCreator() {
	}

	@Override
	public GrammarTree createGrammarTree(SyntaxAnalyser analyser)
			throws GrammarCreateException {
		GrammarTree grammarTree = new GrammarTree(new Token(TokenType.KEYWARD,
				"all"));
		AnalyticResult tokens = analyser.getAnalyticResult();
		Token tempToken = tokens.front();
		if (tempToken == null) {
			GrammarCreateException exception = new GrammarCreateException(
					"Error token list is empty");
			analyser.addErrorMsg(exception.getMessage());
			throw exception;
		}

		// System.out.println(WordTable.getKeyRank(tempToken.getToken()));
		if (WordTable.getKeyRank(tempToken.getToken()) != 1) {
			GrammarCreateException exception = new GrammarCreateException(
					"Error: the sentence should start from key-word");
			analyser.addErrorMsg(exception.getMessage());
			throw exception;
		}
		GrammarTree currentNode = null;
		while (tokens.hasNext()) {
			Token token = tokens.next();
			GrammarTree node = new GrammarTree(token);
			if (token.getTokenType() == TokenType.KEYWARD) {
				int rank = WordTable.getKeyRank(token.getToken());
				if (rank == 1 || rank == 2) {
					currentNode = node;
					String value = node.getValue().getToken().toLowerCase();
					node.getValue().setToken(value);
					grammarTree.addSubNode(node);
				} else {
					currentNode.addSubNode(node);
				}
			} else {
				currentNode.addSubNode(node);
			}
		}

		return grammarTree;
	}
}
