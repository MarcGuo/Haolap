package com.haolap.command;


import java.util.List;

import com.haolap.command.util.ParserResult;

public interface CommandAnalyser {
	void analyse();
	boolean isSuccess();
	ParserResult getResult();
	List<String> getErrors();
}
