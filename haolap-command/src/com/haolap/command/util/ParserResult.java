package com.haolap.command.util;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class ParserResult {
	private String operationName;
	private Map<String, String> dimensionLevelInfo;
	private Map<String, String> dimensionNicknames;
	private String targetCube;
	private String resultCube;
	private String aggregateFunctionName;
	private Map<String, String[]> conditions;

	public ParserResult() {
		this.dimensionLevelInfo = new LinkedHashMap<String, String>();

		this.dimensionNicknames = new LinkedHashMap<String, String>();
		this.conditions = new LinkedHashMap<String, String[]>();
	}

	public String getTargetCube() {
		return targetCube;
	}

	public void setTargetCube(String targetCube) {
		this.targetCube = targetCube;
	}

	public String getResultCube() {
		return resultCube;
	}

	public void setResultCube(String resultCube) {
		this.resultCube = resultCube;
	}

	public String getAggregateFunctionName() {
		return aggregateFunctionName;
	}

	public void setAggregateFunctionName(String aggregateFunctionName) {
		this.aggregateFunctionName = aggregateFunctionName;
	}

	public boolean addDimensionLevelInfo(String dimension, String level) {
		if (this.dimensionNicknames.containsValue(dimension)) {
			return false;
		}
		return dimensionLevelInfo.put(dimension, level) == null;
	}

	public boolean addDimensionNickName(String dimension, String nickName) {
		if (this.dimensionNicknames.containsKey(dimension)
				|| this.dimensionNicknames.containsValue(nickName)
				|| this.dimensionNicknames.containsKey(nickName))
			return false;
		return dimensionNicknames.put(dimension, nickName) == null;
	}

	public boolean addCondition(String value, String start, String end) {
		String point[] = new String[2];
		point[0] = start;
		point[1] = end;
		return this.conditions.put(value, point) == null;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("**************************").append("\n");
		builder.append("operationName = ").append(operationName).append("\n");
		if (this.dimensionLevelInfo.size() != 0) {
			builder.append("dimensionInfo = ").append(this.dimensionLevelInfo)
					.append("\n");
		}
		if (this.dimensionNicknames.size() != 0) {
			builder.append("dimensionNicknames = ")
					.append(this.dimensionNicknames).append("\n");
		}
		builder.append("target = ").append(targetCube).append("\n");
		if (this.resultCube != null) {
			builder.append("resultCube = ").append(resultCube).append("\n");
		}
		if (this.aggregateFunctionName != null) {
			builder.append("aggregateFunctionName = ")
					.append(aggregateFunctionName).append("\n");
		}
		if (this.conditions.size() > 0) {
			builder.append("conditions = ").append("{");
			for (Iterator<String> iterator = this.conditions.keySet()
					.iterator(); iterator.hasNext();) {
				String key = iterator.next();
				builder.append(key).append("=[")
						.append(this.conditions.get(key)[0]).append(",")
						.append(this.conditions.get(key)[1]).append("],");
			}
			builder.deleteCharAt(builder.length() - 1).append("}\n");
		}
		// StringBuilder builder = new StringBuilder();
		// builder.append("{");
		// Set<Entry<String, String[]>> entrySet = conditions.entrySet();
		// for (Entry<String, String[]> entry : entrySet) {
		// builder.append(entry.getKey());
		// builder.append("=[");
		// builder.append(entry.getValue()[0]);
		// builder.append(",");
		// builder.append(entry.getValue()[1]);
		// builder.append("],");
		// }
		// int index = builder.lastIndexOf(",");
		// if (index != -1) {
		// builder.deleteCharAt(index);
		// }
		// builder.append("}");
		// return
		// "ParserResult [operationName = "+operationName+" , dimensionLevelInfo="
		// + dimensionLevelInfo
		// + ", dimensionNicknames=" + dimensionNicknames
		// + ", targetCube=" + targetCube + ", resultCube=" + resultCube
		// + ", aggregateFunctionName=" + aggregateFunctionName
		// + ", aggregateFunctionParameters="
		// + builder.toString() + "]";
		builder.append("**************************").append("\n");
		return builder.toString();
	}

	public Map<String, String> getDimensionLevelInfo() {
		return dimensionLevelInfo;
	}

	public void setDimensionLevelInfo(Map<String, String> dimensionLevelInfo) {
		this.dimensionLevelInfo = dimensionLevelInfo;
	}

	public Map<String, String> getDimensionNicknames() {
		return dimensionNicknames;
	}

	public void setDimensionNicknames(Map<String, String> dimensionNicknames) {
		this.dimensionNicknames = dimensionNicknames;
	}

	public Map<String, String[]> getConditions() {
		return conditions;
	}

	public void setConditions(Map<String, String[]> conditions) {
		this.conditions = conditions;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public String getRealName(String nickName) {
		if (this.dimensionNicknames.containsValue(nickName)) {
			return this.getKeyByValue(dimensionNicknames, nickName);
		} else if (this.dimensionLevelInfo.containsKey(nickName)) {
			return nickName;
		} else
			return null;
	}

	public String getDimensionByLevel(String level) {
		return getKeyByValue(this.dimensionLevelInfo, level);
	}

	private String getKeyByValue(Map<String, String> map, String value) {
		Set<String> keySet = map.keySet();
		for (String tempKey : keySet) {
			if (map.get(tempKey).equals(value)) {
				return tempKey;
			}
		}
		return null;
	}

}
