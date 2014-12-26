package cn.edu.neu.cloudlab.haolap.util;

import cn.edu.neu.cloudlab.haolap.configuration.CubeConfiguration;

public class PathConf {
    private static final String cubeElementPathPrefix = "/CubeElement";
    private static final String schemaXmlFilePathPrefix = "/SchemaServer/schema.xml";
    private static final String dimensionXmlFilePathPrefix = "/SchemaServer/dimension.xml";
    private static final String cubeXmlFilePathPrefix = "/SchemaServer/cube.xml";


    public static String getHDFSBasePath() {
        return CubeConfiguration.getConfiguration().get("path.base", "/HaoLap");
    }

    public static String getCubeElementPath() {

        return getHDFSBasePath() + cubeElementPathPrefix;
    }

    public static String getSchemaXmlFilePath() {
        return getHDFSBasePath() + schemaXmlFilePathPrefix;
    }

    public static String getDimensionXmlFilePath() {
        return getHDFSBasePath() + dimensionXmlFilePathPrefix;
    }

    public static String getCubeXmlFilePath() {
        return getHDFSBasePath() + cubeXmlFilePathPrefix;
    }
}
