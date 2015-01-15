package com.haolap.command.parser.impl;

import java.util.HashMap;
import java.util.Map;

import com.haolap.command.parser.GrammarTreeMonitor;

public class GrammarTreeMonitorFactory {
	public static Map<String, GrammarTreeMonitor> monitors = new HashMap<String, GrammarTreeMonitor>();
	private static String packageName = "com.haolap.command.parser.impl";

	public static GrammarTreeMonitor getGrammarTreeBySentenceKeyWord(
			String value) {
		if (value == null || value.length() < 1) {
			return null;
		}
		String monitorName = value.toLowerCase();
		monitorName = packageName + "."
				+ monitorName.substring(0, 1).toUpperCase()
				+ monitorName.substring(1) + "WordMonitor";
		if (monitors.containsKey(monitorName)) {
			return monitors.get(monitorName);
		} else {
			GrammarTreeMonitor monitor = null;
			try {
				@SuppressWarnings("unchecked")
				Class<GrammarTreeMonitor> c = (Class<GrammarTreeMonitor>) Class
						.forName(monitorName);
				monitor = c.newInstance();
				monitors.put(monitorName, monitor);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			return monitor;
		}
	}
}
