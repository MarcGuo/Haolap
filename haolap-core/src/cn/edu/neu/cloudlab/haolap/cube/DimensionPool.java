package cn.edu.neu.cloudlab.haolap.cube;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;

public class DimensionPool {
    private static DimensionPool uniqueInstance = new DimensionPool();
    private Set<Dimension> dimensions = new HashSet<Dimension>();

    private DimensionPool() {

    }

    public static DimensionPool getDimensionPool() {
        return uniqueInstance;
    }

    public Dimension getDimension(String name, SortedSet<Level> levels,
                                  int orderNumber, DimensionRange range) {
//		for (Dimension dimension : dimensions) {
//			if (name.equals(dimension)) {
//				return dimension;
//			}
//		}
        Dimension newDimension = new Dimension(name, levels, orderNumber, range);
        dimensions.add(newDimension);
        return newDimension;
    }

    public void clear() {
        this.dimensions.clear();
    }
}
