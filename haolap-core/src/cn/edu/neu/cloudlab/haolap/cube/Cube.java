package cn.edu.neu.cloudlab.haolap.cube;

import cn.edu.neu.cloudlab.haolap.exception.InvalidObjectStringException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Cube implements Writable {
    private final static String regex = "^Cube *\\[identifier=[a-zA-Z_0-9]+, dataType=[a-zA-Z_0-9]+, schemaName=[a-zA-Z_0-9]+\\]$";
    private String identifier;
    private String dataType;
    private String schemaName;

    /**
     * this constructor exists for the RPC only
     * it's not recommend to use this constructor if not needed, please use
     * {@code public Cube(String identifier, String dataType, String schemaName)}
     * instead
     */
    @Deprecated
    public Cube() {

    }

    public Cube(String identifier, String dataType, String schemaName) {
        super();
        this.identifier = identifier;
        this.dataType = dataType;
        this.schemaName = schemaName;
    }

    public static Cube constructFromString(String cubeStr)
            throws InvalidObjectStringException {
        String identifier;
        String dataType;
        String schemaName;
        if (!cubeStr.matches(regex)) {
            throw new InvalidObjectStringException("object string(" + cubeStr
                    + ") not matches: " + regex);
        }
        String tmp[] = cubeStr.split("\\[");
        tmp = tmp[1].split("\\]");
        tmp = tmp[0].split(",");
        identifier = tmp[0].split("=")[1];
        dataType = tmp[1].split("=")[1];
        schemaName = tmp[2].split("=")[1];
        return new Cube(identifier, dataType, schemaName);
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getDataType() {
        return dataType;
    }

    public String getSchemaName() {
        return schemaName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((identifier == null) ? 0 : identifier.hashCode());
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
        Cube other = (Cube) obj;
        if (identifier == null) {
            if (other.identifier != null)
                return false;
        } else if (!identifier.equals(other.identifier))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Cube [identifier=" + identifier + ", dataType=" + dataType
                + ", schemaName=" + schemaName + "]";
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        String cubeStr = Text.readString(in);
        try {
            Cube cube = Cube.constructFromString(cubeStr);
            this.identifier = cube.identifier;
            this.dataType = cube.dataType;
            this.schemaName = cube.schemaName;
        } catch (InvalidObjectStringException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    @Override
    public void write(DataOutput out) throws IOException {
        Text.writeString(out, this.toString());
    }
}
