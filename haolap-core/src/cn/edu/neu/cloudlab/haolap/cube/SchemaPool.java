package cn.edu.neu.cloudlab.haolap.cube;

import cn.edu.neu.cloudlab.haolap.exception.SchemaNotExistsException;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;

public class SchemaPool {
    private static SchemaPool uniqueInstance = new SchemaPool();
    private static Set<Schema> schemas = new HashSet<Schema>();

    public static SchemaPool getSchemaPool() {
        return uniqueInstance;
    }

    /**
     * it's not recommend to use this method to modify the SchemaPool if not
     * need
     *
     * @param originalSchema
     * @param replaceTo
     * @throws SchemaNotExistsException
     */
    @Deprecated
    public static void replace(Schema originalSchema, Schema replaceTo)
            throws SchemaNotExistsException {
        if (!schemas.contains(originalSchema)) {
            throw new SchemaNotExistsException("original schema not exist");
        }
        schemas.remove(originalSchema);
        schemas.add(replaceTo);
    }

    public Schema getSchema(String name, SortedSet<Dimension> dimensions) {
        for (Schema schema : schemas) {
            if (name.equals(schema.getName())) {
                return schema;
            }
        }
        Schema newSchema = new Schema(name, dimensions);
        schemas.add(newSchema);
        return newSchema;
    }

    public boolean isExist(String schemaName) {
        for (Schema schema : schemas) {
            if (schemaName.equals(schema.getName())) {
                return true;
            }
        }
        return false;
    }

    public void clear() {
        schemas.clear();
    }
}
