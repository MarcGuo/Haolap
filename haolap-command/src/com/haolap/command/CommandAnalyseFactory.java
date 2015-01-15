package com.haolap.command;

import com.haolap.command.parser.SyntaxAnalyser;
import com.haolap.command.parser.impl.DefaultGrammarTreeCreator;
import com.haolap.command.parser.impl.DefaultGrammarTreeMonitor;
import com.haolap.command.parser.impl.DefaultSyntaxAnalyser;
import com.haolap.command.scanner.DFA;
import com.haolap.command.scanner.LexicalAnalyser;
import com.haolap.command.scanner.impl.DefaultDFA;
import com.haolap.command.scanner.impl.DefaultLexicalAnalyser;
import com.haolap.command.util.AnalyticResult;

public class CommandAnalyseFactory {
	public static LexicalAnalyser createLexicalAnalyser(String sentence) {
		DFA dfa = new DefaultDFA();
		LexicalAnalyser lexicalAnalyser = new DefaultLexicalAnalyser(sentence,
				dfa);
		return lexicalAnalyser;
	}

	public static SyntaxAnalyser createSyntaxAnalyser(
			LexicalAnalyser lexicalAnalyser) {
		return createSyntaxAnalyser(lexicalAnalyser.getAnalyticResult());
	}

	public static SyntaxAnalyser createSyntaxAnalyser(AnalyticResult result) {
		return new DefaultSyntaxAnalyser(result,
				new DefaultGrammarTreeCreator(),
				new DefaultGrammarTreeMonitor());
	}
}
