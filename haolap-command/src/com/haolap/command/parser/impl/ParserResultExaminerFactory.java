package com.haolap.command.parser.impl;

import com.haolap.command.parser.ParserResultExaminer;
import com.haolap.command.util.ParserResult;

public class ParserResultExaminerFactory {
	private ParserResultExaminerFactory() {
	}

	public static ParserResultExaminer getExaminerByParserResult(
			ParserResult result) {
		String opertionName = result.getOperationName().toLowerCase();
		ParserResultExaminer examiner = null;
		if (opertionName.equals("select")) {
			examiner = new SelectExaminer();
		} else if (opertionName.equals("rollup")
				|| opertionName.equals("drilldown")) {
			examiner = new RollupAndDrillDownExaminer();
		} else if (opertionName.equals("slice") || opertionName.equals("dice")) {
			examiner = new SliceAndDiceExaminer();
		} else if (opertionName.equals("save") || opertionName.equals("delete")) {
			examiner = new SaveAndDeleteExaminer();
		} else if (opertionName.equals("show")) {
			examiner = new ShowExaminer();
		} else if (opertionName.equals("step")) {
			examiner = new StepExaminer();
		}
		return examiner;
	}
}
