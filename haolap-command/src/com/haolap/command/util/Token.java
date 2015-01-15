package com.haolap.command.util;

public class Token {
	private TokenType tokenType;
	private String token;

	public TokenType getTokenType() {
		return tokenType;
	}

	public void setTokenType(TokenType tokenType) {
		this.tokenType = tokenType;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Token(TokenType tokenType, String token) {
		super();
		this.tokenType = tokenType;
		this.token = token;
	}

	public boolean isTheType(TokenType tokenType) {
		return this.tokenType.equals(tokenType);
	}

	@Override
	public String toString() {
		/*
		 * for test return String.format("[%s]", token);
		 */
		return String.format(
				"{Token} [tokenType]:(%-10s) [tokenValue]:(%-10s)",
				tokenType.toString(), token);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((token == null) ? 0 : token.hashCode());
		result = prime * result
				+ ((tokenType == null) ? 0 : tokenType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Token other = (Token) obj;
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
			return false;
		if (tokenType != other.tokenType)
			return false;
		return true;
	}

}
