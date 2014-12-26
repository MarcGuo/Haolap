package cn.edu.neu.cloudlab.haolap.validation;

import cn.edu.neu.cloudlab.haolap.exception.InvalidPointException;
import cn.edu.neu.cloudlab.haolap.exception.SizeNotEqualException;

import java.util.List;

public class PointValidation {
    public static void validate(List<Long> pointToValidate,
                                List<Long> startPoint, List<Long> endPoint)
            throws SizeNotEqualException, InvalidPointException {
        int size = pointToValidate.size();
        if (size != startPoint.size()) {
            throw new SizeNotEqualException(
                    "the sizes of pointToValidate and startPoint should be equal");
        }
        if (size != endPoint.size()) {
            throw new SizeNotEqualException(
                    "the sizes of pointToValidate and endPoint should be equal");
        }

        for (int i = 0; i < size; i += 1) {
            if (!(startPoint.get(i) <= pointToValidate.get(i) && pointToValidate
                    .get(i) <= endPoint.get(i))) {
                throw new InvalidPointException("not valid point: "
                        + startPoint.get(i) + " <= " + pointToValidate.get(i)
                        + " <= " + endPoint.get(i));
            }
        }
    }
}
