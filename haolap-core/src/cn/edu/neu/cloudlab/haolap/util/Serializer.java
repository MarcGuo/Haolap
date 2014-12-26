package cn.edu.neu.cloudlab.haolap.util;

import java.math.BigInteger;
import java.util.List;

public final class Serializer {
    public static BigInteger serialize(long lengths[], long position[]) {
        BigInteger serialNumber = new BigInteger("0");
        int numberOfDimensions = lengths.length;
        for (int i = 0; i < numberOfDimensions; i += 1) {
            serialNumber = serialNumber.add(new BigInteger(String.valueOf(position[i])));
            serialNumber = serialNumber.multiply(new BigInteger(String
                    .valueOf(i + 1 < numberOfDimensions ? lengths[i + 1] : 1)));
        }
        return serialNumber;
    }

    public static long[] deserialize(long lengths[], BigInteger serialNumber) {
        int numberOfDimensions = lengths.length;
        long position[] = new long[numberOfDimensions];
        BigInteger temp = serialNumber;
        for (int i = numberOfDimensions - 1; i >= 0; i -= 1) {
            position[i] = temp.mod(new BigInteger(String.valueOf(lengths[i])))
                    .longValue();
            temp = temp.divide(new BigInteger(String.valueOf(lengths[i])));
        }
        return position;
    }

    public static BigInteger serialize(long[] lengths, List<Long> position) {
        long positionArray[] = new long[position.size()];
        int i = 0;
        for (long l : position) {
            positionArray[i] = l;
            i += 1;
        }
        return serialize(lengths, positionArray);
    }
}
