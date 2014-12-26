package cn.edu.neu.cloudlab.haolap.cube;

import cn.edu.neu.cloudlab.haolap.exception.InvalidObjectStringException;
import cn.edu.neu.cloudlab.haolap.util.SystemConf;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Dimension limitation: we consider that there's only one Dimension Hierarchy
 * in a dimension. consequently, there's not a attribute to describe the
 * hierarchy
 * <p/>
 * and for avoiding the redundancy we override the method hashcode() and the
 * method equals()
 *
 * @author Neoh
 */
public class Dimension implements Comparable<Dimension> {
    private final static String regex;

    static {
        String levelRegex = Level.getRegex();
        levelRegex = levelRegex.split("\\^")[1];
        levelRegex = levelRegex.split("\\$")[0];
        String dimensionRangeRegex = DimensionRange.getRegex();
        dimensionRangeRegex = dimensionRangeRegex.split("\\^")[1];
        dimensionRangeRegex = dimensionRangeRegex.split("\\$")[0];
        regex = "^Dimension *\\[name=[a-zA-Z_0-9]+, orderNumber=[a-zA-Z_0-9]+, "
                + "range=\\{"
                + "("
                + dimensionRangeRegex
                + "){1}"
                + "\\}"
                + ", *levels=\\{"
                + "("
                + levelRegex
                + ";"
                + "){0,}"
                + "\\}\\]$";
    }

    private String name;
    private SortedSet<Level> levels;
    private int orderNumber;
    private DimensionRange range;

    protected Dimension(String name, SortedSet<Level> levels, int orderNumber,
                        DimensionRange range) {
        super();
        this.name = name;
        this.levels = levels;
        this.orderNumber = orderNumber;
        this.range = range;
    }

    public static String getRegex() {
        return regex;
    }

    public static Dimension getDimension(String name, SortedSet<Level> levels,
                                         int orderNumber, DimensionRange range) {
        DimensionPool dimensionPool = DimensionPool.getDimensionPool();
        return dimensionPool.getDimension(name, levels, orderNumber, range);
    }

    public static Dimension constructFromString(String dimensionStr)
            throws InvalidObjectStringException {
        String name;
        int orderNumber;
        DimensionRange range;
        SortedSet<Level> levels = new TreeSet<Level>();
        if (!dimensionStr.matches(regex)) {
            throw new InvalidObjectStringException("object string("
                    + dimensionStr + ") not matches: " + regex);
        }
        String tmp[] = dimensionStr.split("^Dimension *\\[");
        tmp = tmp[1].split("\\]$");
        String objectFields[] = tmp[0].split("(, *range=)|(, *levels=)");
        String rangeFields[] = objectFields[1].split("^\\{");
        rangeFields = rangeFields[1].split("\\}$");
        String dimensionRangeStr = rangeFields[0];
        range = DimensionRange.constructFromString(dimensionRangeStr);
        String levelsFields[] = objectFields[2].split("^\\{");
        levelsFields = levelsFields[1].split("\\}$");
        levelsFields = levelsFields[0].split("Level ");
        for (int i = 1; i < levelsFields.length; i += 1) {
            levelsFields[i] = "Level " + levelsFields[i].split(";$")[0];
        }
        for (String levelStr : levelsFields) {
            if ("".equals(levelStr)) {
                continue;
            }
            levels.add(Level.constructFromString(levelStr));
        }
        String otherFields[] = tmp[0].split(",");
        name = otherFields[0].split("=")[1];
        orderNumber = Integer.valueOf(otherFields[1].split("=")[1]);
        return Dimension.getDimension(name, levels, orderNumber, range);
    }

    public long getDimensionLength() {
        long length = 1L;
        for (Level level : levels) {
            length *= level.getLength();
        }
        return length;
    }

    public String getName() {
        return name;
    }

    public SortedSet<Level> getLevels() {
        return levels;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public DimensionRange getRange() {
        return range;
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
        Dimension other = (Dimension) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        String resultStr = "Dimension [name=" + name + ", orderNumber="
                + orderNumber + ", range=";
        resultStr += "{";
        resultStr += range.toString();
        resultStr += "}";
        resultStr += ", levels=";
        resultStr += "{";
        for (Level level : levels) {
            resultStr += level.toString();
            resultStr += ";";
        }
        resultStr += "}";
        resultStr += "]";
        return resultStr;
    }

    @Override
    public int compareTo(Dimension dimension) {
        return this.orderNumber - dimension.orderNumber;
    }

    public boolean isCorrespondingTo(Dimension dimension) {
        String regex = ".*" + SystemConf.getDimensionNameDelimiter();
        String tmp[] = this.name.split(regex);
        String thisFilteredName = tmp[tmp.length - 1];
        tmp = dimension.name.split(regex);
        String dimensionFilteredName = tmp[tmp.length - 1];
        return thisFilteredName.equals(dimensionFilteredName);
    }
}
