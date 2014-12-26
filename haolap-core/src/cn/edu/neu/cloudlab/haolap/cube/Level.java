package cn.edu.neu.cloudlab.haolap.cube;

import cn.edu.neu.cloudlab.haolap.exception.InvalidObjectStringException;

/**
 * this is the a Level for a dimension (hierarchy)
 * <p/>
 * and for avoiding the redundancy we override the method hashcode() and the
 * method equals()
 *
 * @author Neoh
 */
public class Level implements Comparable<Level> {
    private final static String regex = "^Level *\\[name=[a-zA-Z_0-9]+, *length=[0-9]+, *begin=[0-9]+, *orderNumber=[a-zA-Z_0-9]+\\]$";
    private String name;
    private long length;
    private long begin;
    // start with 0
    private int orderNumber;

    public Level(String name, long length, long begin, int orderNumber) {
        super();
        this.name = name;
        this.length = length;
        this.begin = begin;
        this.orderNumber = orderNumber;
    }

    public static String getRegex() {
        return regex;
    }

    public static Level constructFromString(String levelStr)
            throws InvalidObjectStringException {
        String name;
        long length;
        long begin;
        int orderNumber;

        if (!levelStr.matches(regex)) {
            throw new InvalidObjectStringException("object string(" + levelStr
                    + ") not matches: " + regex);
        }
        String tmp[] = levelStr.split("\\[");
        tmp = tmp[1].split("\\]");
        tmp = tmp[0].split(",");
        name = tmp[0].split("=")[1];
        length = Long.valueOf(tmp[1].split("=")[1]);
        begin = Long.valueOf(tmp[2].split("=")[1]);
        orderNumber = Integer.valueOf(tmp[3].split("=")[1]);
        return new Level(name, length, begin, orderNumber);
    }

    /**
     * get the name of the level the name is different from other levels in a
     * dimension
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    public long getLength() {
        return length;
    }

    public long getBegin() {
        return begin;
    }

    public int getOrderNumber() {
        return orderNumber;
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
        Level other = (Level) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Level [name=" + name + ", length=" + length + ", begin="
                + begin + ", orderNumber=" + orderNumber + "]";
    }

    @Override
    public int compareTo(Level level) {
        return this.orderNumber - level.orderNumber;
    }
}
