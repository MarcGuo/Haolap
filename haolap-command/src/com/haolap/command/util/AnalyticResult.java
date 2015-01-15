package com.haolap.command.util;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class AnalyticResult implements Iterable<Token> {
	private Queue<Token> tokens;

	public AnalyticResult() {
		this.tokens = new LinkedList<Token>();
	}

	public void addToken(Token token) {
		tokens.offer(token);
	}

	public boolean hasNext() {
		return !tokens.isEmpty();
	}

	public Token next() {
		return tokens.poll();
	}

	@Override
	public Iterator<Token> iterator() {
		return tokens.iterator();
	}

	public int size() {
		return tokens.size();
	}

	public Token front() {
		return tokens.peek();
	}
}
