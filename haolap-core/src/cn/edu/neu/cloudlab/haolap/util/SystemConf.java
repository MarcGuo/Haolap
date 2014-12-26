package cn.edu.neu.cloudlab.haolap.util;

import cn.edu.neu.cloudlab.haolap.configuration.CubeConfiguration;
import org.apache.hadoop.conf.Configuration;

public class SystemConf {
    private static final String hdfsUriHeader;
    private static final String baseSchemaName = "timeAreaDepth";
    private static final String baseCubeName = "baseCube";
    private static final String hadoopConfDelimiter = "_";
    private static final String dimensionNameDelimiter = "_";
    private static final String pairInformationXmlKey = "PairInformation";
    private static final String cubeInformationXmlKey = "CubeInformation";
    private static final String axisInformationXmlKey = "AxisInformation";
    private static final String selectTaskXmlKey = "Select";
    private static final String rollupTaskXmlKey = "Rollup";
    private static final String drilldownTaskXmlKey = "Drilldown";
    private static final String sliceTaskXmlKey = "Slice";
    private static final String diceTaskXmlKey = "Dice";
    private static final String deleteTaskXmlKey = "Delete";
    private static final String fromCubeIdentifierForConfSetString = "fromCubeIdentifier";
    private static final String newCubeIdentifierForConfSetString = "newCubeIdentifier";
    private static final String fromSchemaForConfSetString = "fromSchema";
    private static final String newSchemaForConfSetString = "newSchema";
    private static final String fromDimensionLengthsForConfSetString = "fromDimensionLengths";
    private static final String newDimensionLengthsForConfSetString = "newDimensionLengths";
    private static final String aggregationTypeForConfSetString = "aggregationType";
    private static final String startPointForConfSetString = "startPoint";
    private static final String endPointForConfSetString = "endPoint";
    private static final String numberOfElementsInFromCubePerPageForConfSetString = "numberOfElementsInFromCubePerPage";
    private static final String schemaNameAndDimensionNameDelimiter = "_";
    private static final String socketMessageEndMark = "\3";
    private static final String nodeMeaningKeyDelimiter = "-";
    private static final String axisDelimiter = ",";

    static {
        Configuration conf = CubeConfiguration.getConfiguration();
        hdfsUriHeader = conf.get("fs.default.name");
    }

    public static String getHdfsUriHeader() {
        return hdfsUriHeader;
    }

    public static String getBaseSchemaName() {
        return baseSchemaName;
    }

    public static String getBaseCubeIdentifier() {
        return baseCubeName;
    }

    public static String getHadoopConfDelimiter() {
        return hadoopConfDelimiter;
    }

    public static String getDimensionNameDelimiter() {
        return dimensionNameDelimiter;
    }

    public static String getPairInformationXmlKey() {
        return pairInformationXmlKey;
    }

    public static String getCubeInformationXmlKey() {
        return cubeInformationXmlKey;
    }

    public static String getAxisInformationXmlKey() {
        return axisInformationXmlKey;
    }

    public static String getSelectTaskXmlKey() {
        return selectTaskXmlKey;
    }

    public static String getRollupTaskXmlKey() {
        return rollupTaskXmlKey;
    }

    public static String getDrilldownTaskXmlKey() {
        return drilldownTaskXmlKey;
    }

    public static String getSliceTaskXmlKey() {
        return sliceTaskXmlKey;
    }

    public static String getDiceTaskXmlKey() {
        return diceTaskXmlKey;
    }

    public static String getDeleteTaskXmlKey() {
        return deleteTaskXmlKey;
    }

    public static String getFromCubeIdentifierForConfSetString() {
        return fromCubeIdentifierForConfSetString;
    }

    public static String getNewCubeIdentifierForConfSetString() {
        return newCubeIdentifierForConfSetString;
    }

    public static String getFromSchemaForConfSetString() {
        return fromSchemaForConfSetString;
    }

    public static String getNewSchemaForConfSetString() {
        return newSchemaForConfSetString;
    }

    public static String getFromDimensionLengthsForConfSetString() {
        return fromDimensionLengthsForConfSetString;
    }

    public static String getNewDimensionLengthsForConfSetString() {
        return newDimensionLengthsForConfSetString;
    }

    public static String getAggregationTypeForConfSetString() {
        return aggregationTypeForConfSetString;
    }

    public static String getStartPointForConfSetString() {
        return startPointForConfSetString;
    }

    public static String getEndPointForConfSetString() {
        return endPointForConfSetString;
    }

    public static String getNumberOfElementsInFromCubePerPageForConfSetString() {
        return numberOfElementsInFromCubePerPageForConfSetString;
    }

    public static String getSchemaNameAndDimensionNameDelimiter() {
        return schemaNameAndDimensionNameDelimiter;
    }

    public static String getSocketMessageEndMark() {
        return socketMessageEndMark;
    }

    public static String getNodeMeaningKeyDelimiter() {
        return nodeMeaningKeyDelimiter;
    }

    public static String getAxisDelimiter() {
        return axisDelimiter;
    }
}
