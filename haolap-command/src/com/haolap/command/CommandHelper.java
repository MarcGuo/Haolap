package com.haolap.command;

public class CommandHelper {
	private CommandHelper() {
	}

	public static CommandAnalyser createAnalyser(String sentence) {
		return new DefaultCommandAnalyser(sentence);
	}
}
