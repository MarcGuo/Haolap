/**
 * 
 */
package com.haolap.command.parser.impl;

import com.haolap.command.parser.GrammarTreeCreator;
import com.haolap.command.parser.GrammarTreeMonitor;
import com.haolap.command.parser.ParserResultExaminer;
import com.haolap.command.parser.SyntaxAnalyser;
import com.haolap.command.parser.exception.GrammarCheckException;
import com.haolap.command.parser.exception.GrammarCreateException;
import com.haolap.command.util.AnalyticResult;
import com.haolap.command.util.GrammarTree;

/**
 * @author mythboy
 * 
 */
public class DefaultSyntaxAnalyser extends AbstractSyntaxAnalyser implements
		SyntaxAnalyser {
	private GrammarTreeCreator grammarTreeCreator;
	private GrammarTreeMonitor grammarTreeMonitor;

	public DefaultSyntaxAnalyser(AnalyticResult result,
			GrammarTreeCreator creator, GrammarTreeMonitor monitor) {
		super(result);
		this.grammarTreeCreator = creator;
		this.grammarTreeMonitor = monitor;
	}

	@Override
	protected void analyseImpl() throws GrammarCheckException,
			GrammarCreateException {
		if (this.grammarTreeCreator == null || this.grammarTreeMonitor == null) {
			return;
		}
		GrammarTree grammarTree = grammarTreeCreator.createGrammarTree(this);
		boolean grammarTreeIsOk = grammarTreeMonitor
				.checkGrammarTree(grammarTree, this);
		if(grammarTreeIsOk){
			this.checkParserResult();
			this.isComplete = true;
		}
	}
	
	private void checkParserResult() throws GrammarCheckException{
		ParserResultExaminer examiner = ParserResultExaminerFactory.getExaminerByParserResult(this.result);
		if(examiner == null){
			this.throwExceptionAndAddErrorMsg("No suited examiner for ParserResult: " + this.result);
		}else{
			this.result  = examiner.checkAndGetParserResult(result,this);
		}
	}
}
