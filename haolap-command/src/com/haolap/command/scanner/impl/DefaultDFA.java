package com.haolap.command.scanner.impl;

import com.haolap.command.scanner.DFA;
import com.haolap.command.scanner.LexicalAnalyser;
import com.haolap.command.scanner.SubDFA;
import com.haolap.command.scanner.exception.AnalyseException;
import com.haolap.command.scanner.exception.UnDefinedCharacterException;

public class DefaultDFA implements DFA {

	@Override
	public void analyse(LexicalAnalyser analyser) throws AnalyseException {
		String content = analyser.getSentence();
		if (content == null) {
			throw new AnalyseException("Error: the sentence is null");
		}
		for (int index = 0; index < content.length(); index++) {
			char current = content.charAt(index);
			SubDFA subDFA = null;
			try {
				subDFA = SubDFAFactory.getSubDFAbyCharacter(current);
			} catch (UnDefinedCharacterException e) {
				analyser.addErrorMsg(e.getMessage());
				return;
			}
			if (subDFA == null) {
				continue;
			} else {
				index = subDFA.analyse(analyser, index);
				index--;
			}
		}
	}

}
