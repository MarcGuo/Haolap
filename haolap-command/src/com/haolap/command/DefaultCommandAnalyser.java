package com.haolap.command;

import java.util.List;

import com.haolap.command.parser.SyntaxAnalyser;
import com.haolap.command.parser.exception.GrammarCheckException;
import com.haolap.command.parser.exception.GrammarCreateException;
import com.haolap.command.scanner.LexicalAnalyser;
import com.haolap.command.scanner.exception.AnalyseException;
import com.haolap.command.util.ParserResult;

public class DefaultCommandAnalyser implements CommandAnalyser {
	private String sentence;
	private List<String> errors;
	private ParserResult result;
	private boolean isSuccess;

	public DefaultCommandAnalyser(String sentence) {
		this.sentence = sentence;
		this.isSuccess = false;
	}

	public void analyse() {
		LexicalAnalyser lexicalAnalyser = CommandAnalyseFactory
				.createLexicalAnalyser(this.sentence);
		try {
			lexicalAnalyser.analyse();
		} catch (AnalyseException e) {
		}
		if (!lexicalAnalyser.isComplete()) {
			this.errors = lexicalAnalyser.getErrorMsg();
			return;
		}
		SyntaxAnalyser syntaxAnalyser = CommandAnalyseFactory
				.createSyntaxAnalyser(lexicalAnalyser);
		try {
			syntaxAnalyser.analyse();
		} catch (GrammarCheckException e) {
		} catch (GrammarCreateException e) {
		}
		if (!syntaxAnalyser.isComplete()) {
			this.errors = syntaxAnalyser.getErrorMsg();
			return;
		}
		this.result = syntaxAnalyser.getParserResult();
		this.isSuccess = true;
	}

	@Override
	public boolean isSuccess() {
		return this.isSuccess;
	}

	@Override
	public ParserResult getResult() {
		return this.result;
	}

	@Override
	public List<String> getErrors() {
		return this.errors;
	}

}
