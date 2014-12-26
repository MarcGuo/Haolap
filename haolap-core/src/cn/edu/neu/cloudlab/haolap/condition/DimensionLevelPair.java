package cn.edu.neu.cloudlab.haolap.condition;

public class DimensionLevelPair implements Comparable<DimensionLevelPair> {
    private final int dimensionOrderNo;
    private final int levelOrderNo;

    public DimensionLevelPair(int dimensionOrderNo, int levelOrderNo) {
        super();
        this.dimensionOrderNo = dimensionOrderNo;
        this.levelOrderNo = levelOrderNo;
    }

    public int getDimensionOrderNo() {
        return dimensionOrderNo;
    }

    public int getLevelOrderNo() {
        return levelOrderNo;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + dimensionOrderNo;
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
        DimensionLevelPair other = (DimensionLevelPair) obj;
        if (dimensionOrderNo != other.dimensionOrderNo)
            return false;
        return true;
    }

    @Override
    public int compareTo(DimensionLevelPair dimensionLevelPair) {
        return this.dimensionOrderNo - dimensionLevelPair.dimensionOrderNo;
    }

}
