package cn.edu.neu.cloudlab.haolap.condition;

public class StartEndPair implements Comparable<StartEndPair> {
    private final int dimensionOrderNumber;
    private final long start;
    private final long end;

    public StartEndPair(int dimensionOrderNumber, long start, long end) {
        super();
        this.dimensionOrderNumber = dimensionOrderNumber;
        this.start = start;
        this.end = end;
    }

    public int getDimensionOrderNumber() {
        return dimensionOrderNumber;
    }

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + dimensionOrderNumber;
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
        StartEndPair other = (StartEndPair) obj;
        if (dimensionOrderNumber != other.dimensionOrderNumber)
            return false;
        return true;
    }

    @Override
    public int compareTo(StartEndPair startEndPair) {
        return this.dimensionOrderNumber - startEndPair.dimensionOrderNumber;
    }
}
