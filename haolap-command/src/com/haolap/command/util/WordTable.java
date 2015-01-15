package com.haolap.command.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class WordTable {
	private static Map<String, Integer> keyWordTable;
	private static Vector<String> delimiterTable;
	private static Map<String, Integer> aggregateFunctionTable;
	static {
		keyWordTable = new HashMap<String, Integer>();
		aggregateFunctionTable = new HashMap<String, Integer>();
		delimiterTable = new Vector<String>();
		initKeyWordAndFunctionTable();
		initDelimiterTable();
	}

	private static void initKeyWordAndFunctionTable() {
		keyWordTable.put("select", 1);
		keyWordTable.put("step", 1);
		keyWordTable.put("show", 1);
		keyWordTable.put("as", 3);
		keyWordTable.put("from", 2);
		keyWordTable.put("where", 2);
		keyWordTable.put("rollup", 1);
		keyWordTable.put("to", 2);
		keyWordTable.put("drilldown", 1);
		keyWordTable.put("slice", 1);
		keyWordTable.put("dice", 1);
		keyWordTable.put("save", 1);
		keyWordTable.put("delete", 1);
		keyWordTable.put("on", 3);
		keyWordTable.put("name", 2);
		keyWordTable.put("agg", 2);
		keyWordTable.put("between", 3);
		keyWordTable.put("and", 3);
		keyWordTable.put("equals", 3);
		aggregateFunctionTable.put("avg", 3);
		aggregateFunctionTable.put("max", 3);
		aggregateFunctionTable.put("min", 3);
		aggregateFunctionTable.put("sum", 3);
		keyWordTable.putAll(aggregateFunctionTable);
	}

	private static void initDelimiterTable() {
		// delimiterTable.add("(");
		// delimiterTable.add(")");
		delimiterTable.add(",");
		//delimiterTable.add("+");
		//delimiterTable.add("-");
	}

	public static boolean isKeyWord(String key) {
		return keyWordTable.containsKey(key);
	}

	public static int getKeyRank(String key) {
		String temp = key.toLowerCase();
		Integer result = keyWordTable.get(temp);
		return result == null ? 0 : result;
	}

	public static boolean isLetter(char c) {
		return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_'
				|| c == '\'';
	}

	public static boolean isDigit(char c) {
		return c >= '0' && c <= '9';
	}

	public static boolean isDelimiter(char c) {
		return delimiterTable.contains(String.valueOf(c));
	}

	// public static boolean isDelimiterPart(char c) {
	// return c == '|' || c == '&';
	// }

	public static boolean isDelimiter(String str) {
		return delimiterTable.contains(str);
	}

	public static boolean isOverableLetter(char c) {
		return c == ' ' || c == '\n' || c == (char) 13 || c == '\t';
	}

	public static boolean isAggregateFunction(String value) {
		String temp = value.toLowerCase();
		return aggregateFunctionTable.containsKey(temp);
	}
}
