package cn.edu.neu.cloudlab.experiment.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RangeTrasnlator {
    private final static Pattern rangePattern = Pattern
            .compile("\\[-?\\d+\\.?\\d*,\\s*-?\\d+\\.?\\d*\\]");
    private final static Pattern numberPattern = Pattern.compile("-?\\d+\\.?\\d*");


    public static String rangeToString(double[][] range) {
        String[] rangeStrs = new String[range.length];
        for (int i = 0; i < rangeStrs.length; i++) {
            rangeStrs[i] = Arrays.toString(range[i]);
        }
        return Arrays.toString(rangeStrs);
    }

    public static double[][] stringToRange(String rangeStr) {

        Matcher rangeMatcher = rangePattern.matcher(rangeStr);
        List<String> rangeStrs = new ArrayList<String>();
        while (rangeMatcher.find()) {
            rangeStrs.add(rangeMatcher.group());
        }
        if (rangeStrs.size() == 0) {
            return new double[0][];
        }
        double[][] rangs = new double[rangeStrs.size()][];
        List<String> tempRangeList = new ArrayList<String>();
        for (int i = 0; i < rangeStrs.size(); i++) {
            tempRangeList.clear();
            String range = rangeStrs.get(i);
            Matcher numberMatcher = numberPattern.matcher(range);
            while (numberMatcher.find()) {
                tempRangeList.add(numberMatcher.group());
            }
            rangs[i] = new double[tempRangeList.size()];
            for (int j = 0; j < tempRangeList.size(); j++) {
                rangs[i][j] = Double.parseDouble(tempRangeList.get(j));
            }
        }
        return rangs;
    }
}
