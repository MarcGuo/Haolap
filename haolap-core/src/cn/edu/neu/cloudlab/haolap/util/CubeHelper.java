package cn.edu.neu.cloudlab.haolap.util;

import cn.edu.neu.cloudlab.haolap.RPC.SchemaClient;
import cn.edu.neu.cloudlab.haolap.RPC.SchemaClientInterface;
import cn.edu.neu.cloudlab.haolap.cube.Dimension;
import cn.edu.neu.cloudlab.haolap.cube.Schema;
import cn.edu.neu.cloudlab.haolap.exception.CubeNotExistsException;
import cn.edu.neu.cloudlab.haolap.exception.SchemaNotExistsException;

import java.util.Set;

public class CubeHelper {
    public static long getNumberOfElements(String cubeIdentifier)
            throws CubeNotExistsException, SchemaNotExistsException {
        SchemaClientInterface schemaClient = SchemaClient.getSchemaClient();
        Schema schema = schemaClient.getSchema(cubeIdentifier);
        Set<Dimension> dimensions = schema.getDimensions();
        long numberOfElements = 1;
        for (Dimension dimension : dimensions) {
            numberOfElements *= dimension.getDimensionLength();
        }
        return numberOfElements;
    }

    public static long getNumberOfElements(Schema schema) {
        Set<Dimension> dimensions = schema.getDimensions();
        long numberOfElements = 1;
        for (Dimension dimension : dimensions) {
            numberOfElements *= dimension.getDimensionLength();
        }
        return numberOfElements;
    }
}
