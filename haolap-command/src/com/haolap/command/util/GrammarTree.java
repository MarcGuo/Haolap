package com.haolap.command.util;


public class GrammarTree extends LinkedNode<Token> {

	public GrammarTree(Token value) {
		super(value);
	}

	public GrammarTree next() {
		return (GrammarTree) super.next();
	}

	public GrammarTree getSubNode(int index) {
		return (GrammarTree) super.getSubNode(index);
	}
}
