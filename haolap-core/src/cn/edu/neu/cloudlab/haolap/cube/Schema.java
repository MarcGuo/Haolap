package cn.edu.neu.cloudlab.haolap.cube;

import cn.edu.neu.cloudlab.haolap.exception.InvalidObjectStringException;
import cn.edu.neu.cloudlab.haolap.exception.SchemaNotExistsException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * a Schema is consist of one or more dimensions
 * <p/>
 * and for avoiding the redundancy we override the method hashcode() and the
 * method equals()
 *
 * @author Neoh
 */
public class Schema implements Writable {
    private final static String regex;

    static {
        String dimensionRegex = Dimension.getRegex();
        dimensionRegex = dimensionRegex.split("\\^")[1];
        dimensionRegex = dimensionRegex.split("\\$")[0];
        regex = "^Schema *\\[name=[a-zA-Z_0-9]+, *dimensions=\\{" + "("
                + dimensionRegex + ";" + "){0,}" + "\\}\\]$";
    }

    private String name;
    private SortedSet<Dimension> dimensions;

    /**
     * this constructor exists for the RPC only it's not recommend to use this
     * constructor if not needed, please use
     * {@code public static Schema getSchema(String name, Set<Dimension> dimensions)}
     * instead
     */
    @Deprecated
    public Schema() {

    }

    // we consider the Schema as a sharing flyweight object
    protected Schema(String name, SortedSet<Dimension> dimensions) {
        super();
        this.name = name;
        this.dimensions = dimensions;
    }

    public static Schema getSchema(String name, SortedSet<Dimension> dimensions) {
        SchemaPool schemaPool = SchemaPool.getSchemaPool();
        return schemaPool.getSchema(name, dimensions);
    }

    public static String getRegex() {
        return regex;
    }

    public static Schema constructedFromString(String schemaStr)
            throws InvalidObjectStringException {
        String name;
        SortedSet<Dimension> dimensions = new TreeSet<Dimension>();
        if (!schemaStr.matches(regex)) {
            throw new InvalidObjectStringException("object string(" + schemaStr
                    + ") not matches: " + regex);
        }
        String tmp[] = schemaStr.split("^Schema *\\[");
        tmp = tmp[1].split("\\]$");
        tmp = tmp[0].split(", *dimensions=");
        name = tmp[0].split("=")[1];
        tmp = tmp[1].split("^\\{");
        tmp = tmp[1].split("\\}$");
        tmp = tmp[0].split("Dimension ");
        for (int i = 1; i < tmp.length; i += 1) {
            tmp[i] = "Dimension " + tmp[i].split(";$")[0];
        }
        for (String dimensionStr : tmp) {
            if ("".equals(dimensionStr)) {
                continue;
            }
            dimensions.add(Dimension.constructFromString(dimensionStr));
        }
        return Schema.getSchema(name, dimensions);
    }

    /**
     * get the dimensions of the schema
     *
     * @return
     */
    public SortedSet<Dimension> getDimensions() {
        return dimensions;
    }

    /**
     * get the name of the Schema
     *
     * @return
     */
    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Schema other = (Schema) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        String resultStr = "Schema [name=" + name + ", dimensions=";
        resultStr += "{";
        for (Dimension dimension : dimensions) {
            resultStr += dimension.toString();
            resultStr += ";";
        }
        resultStr += "}";
        resultStr += "]";
        return resultStr;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void readFields(DataInput in) throws IOException {
        String schemaStr = Text.readString(in);
        try {
            Schema schema = Schema.constructedFromString(schemaStr);
            this.name = schema.name;
            this.dimensions = schema.dimensions;
            // replace schema with this in the schemaPool
            SchemaPool.replace(schema, this);
        } catch (InvalidObjectStringException e) {
            e.printStackTrace();
            System.exit(-1);
        } catch (SchemaNotExistsException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    @Override
    public void write(DataOutput out) throws IOException {
        Text.writeString(out, this.toString());
    }

}
