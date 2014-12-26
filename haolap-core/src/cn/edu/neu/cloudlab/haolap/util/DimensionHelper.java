package cn.edu.neu.cloudlab.haolap.util;

import cn.edu.neu.cloudlab.haolap.RPC.SchemaClient;
import cn.edu.neu.cloudlab.haolap.RPC.SchemaClientInterface;
import cn.edu.neu.cloudlab.haolap.cube.Dimension;
import cn.edu.neu.cloudlab.haolap.cube.Schema;
import cn.edu.neu.cloudlab.haolap.exception.CubeNotExistsException;
import cn.edu.neu.cloudlab.haolap.exception.SchemaNotExistsException;

import java.util.Set;

public class DimensionHelper {
    public static long[] getDimensionLengths(String cubeIdentifier)
            throws CubeNotExistsException, SchemaNotExistsException {
        SchemaClientInterface schemaClient = SchemaClient.getSchemaClient();
        Schema schema = schemaClient.getSchema(cubeIdentifier);
        Set<Dimension> dimensions = schema.getDimensions();
        int numberOfDimensions = dimensions.size();
        long dimensionLengths[] = new long[numberOfDimensions];
        int i = 0;
        for (Dimension dimension : dimensions) {
            dimensionLengths[i++] = dimension.getDimensionLength();
        }
        return dimensionLengths;
    }

    public static long[] getDimensionLengths(Schema schema) {
        Set<Dimension> dimensions = schema.getDimensions();
        int numberOfDimensions = dimensions.size();
        long dimensionLengths[] = new long[numberOfDimensions];
        int i = 0;
        for (Dimension dimension : dimensions) {
            dimensionLengths[i++] = dimension.getDimensionLength();
        }
        return dimensionLengths;
    }

    // TODO not yet tested
    public static String fileterCubeIdentifier(
            String dimensionNameWithCubeIdentifier, String cubeIdentifier) {
        return dimensionNameWithCubeIdentifier.split(cubeIdentifier
                + SystemConf.getSchemaNameAndDimensionNameDelimiter())[0];
    }

    // TODO not yet tested
    public static String addCubeIdentifierToDimensionName(
            String dimensionNameWithoutCubeIdentifier, String cubeIdentifier) {
        return cubeIdentifier
                + SystemConf.getSchemaNameAndDimensionNameDelimiter()
                + dimensionNameWithoutCubeIdentifier;
    }
}
