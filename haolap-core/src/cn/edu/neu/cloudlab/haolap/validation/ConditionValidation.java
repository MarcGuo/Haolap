package cn.edu.neu.cloudlab.haolap.validation;

import cn.edu.neu.cloudlab.haolap.RPC.SchemaClient;
import cn.edu.neu.cloudlab.haolap.RPC.SchemaClientInterface;
import cn.edu.neu.cloudlab.haolap.condition.*;
import cn.edu.neu.cloudlab.haolap.cube.Dimension;
import cn.edu.neu.cloudlab.haolap.cube.Level;
import cn.edu.neu.cloudlab.haolap.cube.Schema;
import cn.edu.neu.cloudlab.haolap.exception.CubeNotExistsException;
import cn.edu.neu.cloudlab.haolap.exception.InvalidConditionException;
import cn.edu.neu.cloudlab.haolap.exception.SchemaNotExistsException;

import java.util.List;
import java.util.SortedSet;

public class ConditionValidation {
    public static void validate(FullCondition fullCondition)
            throws CubeNotExistsException, SchemaNotExistsException,
            InvalidConditionException {
        CubeCondition cubeCondition = fullCondition.getCubeCondition();
        SetCondition setCondition = fullCondition.getSetCondition();
        TargetCondition targetCondition = fullCondition.getTargetCondition();
        AggregationCondition aggregationCondition = fullCondition
                .getAggregationCondition();

        SchemaClientInterface schemaClient = SchemaClient.getSchemaClient();
        Schema schema = schemaClient.getSchema(cubeCondition
                .getCubeIdentifier());
        SortedSet<Dimension> dimensions = schema.getDimensions();

        // CubeCondition: cubeIdentifier and corresponding schema exists
        schemaClient.getSchema(cubeCondition.getCubeIdentifier());

        // SetCondition: dimensions exists in the schema
        List<Integer> orders = setCondition.getDimensionOrders();
        for (int order : orders) {
            getCorrespondingDimension(order, dimensions);
        }

        // SetCondition: start&end in DimesnionRange
        orders = setCondition.getDimensionOrders();
        List<Long> startPoint = setCondition.getStartPoint();
        int i = 0;
        for (int order : orders) {
            Dimension dimension = getCorrespondingDimension(order, dimensions);
            if (!(dimension.getRange().getStart() <= startPoint.get(i))) {
                throw new InvalidConditionException(
                        "the start in startPoint is not bigger than the start in the dimension range");
            }
            i += 1;
        }
        List<Long> endPoint = setCondition.getEndPoint();
        i = 0;
        for (int order : orders) {
            Dimension dimension = getCorrespondingDimension(order, dimensions);
            if (!(endPoint.get(i) <= dimension.getRange().getEnd())) {
                throw new InvalidConditionException(
                        "the end in endPoint is not smaller than the end in the dimension range");
            }
            i += 1;
        }

        // TargetCondition: dimensions exists in the schema
        SortedSet<DimensionLevelPair> pairs = targetCondition.getPairs();
        for (DimensionLevelPair pair : pairs) {
            getCorrespondingDimension(pair.getDimensionOrderNo(), dimensions);
        }

        // TargetCondition: all levels exists in corresponding dimension
        pairs = targetCondition.getPairs();
        for (DimensionLevelPair pair : pairs) {
            Dimension dimension = getCorrespondingDimension(
                    pair.getDimensionOrderNo(), dimensions);
            SortedSet<Level> levels = dimension.getLevels();
            getCorrespondingLevel(pair.getLevelOrderNo(), levels);
        }

        // AggregationCondition: type validate
        if (!(aggregationCondition.getAggregationType() instanceof AggregationCondition.Type)) {
            throw new InvalidConditionException("aggregation type is invalid");
        }
    }

    private static Dimension getCorrespondingDimension(
            int dimensionOrderNumber, SortedSet<Dimension> dimensions)
            throws InvalidConditionException {
        for (Dimension dimension : dimensions) {
            if (dimensionOrderNumber == dimension.getOrderNumber()) {
                return dimension;
            }
        }
        throw new InvalidConditionException(
                "dimension not exists in the schema");
    }

    private static Level getCorrespondingLevel(int levelNo,
                                               SortedSet<Level> levels) throws InvalidConditionException {
        for (Level level : levels) {
            if (levelNo == level.getOrderNumber()) {
                return level;
            }
        }
        throw new InvalidConditionException(
                "level not exists in the corresponding dimension");
    }
}
