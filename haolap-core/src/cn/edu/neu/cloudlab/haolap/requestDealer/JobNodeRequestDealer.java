package cn.edu.neu.cloudlab.haolap.requestDealer;

import cn.edu.neu.cloudlab.haolap.RPC.SchemaClient;
import cn.edu.neu.cloudlab.haolap.RPC.SchemaClientInterface;
import cn.edu.neu.cloudlab.haolap.condition.*;
import cn.edu.neu.cloudlab.haolap.configuration.CubeConfiguration;
import cn.edu.neu.cloudlab.haolap.cube.*;
import cn.edu.neu.cloudlab.haolap.exception.*;
import cn.edu.neu.cloudlab.haolap.mapreduce.HFJobCreator;
import cn.edu.neu.cloudlab.haolap.util.*;
import cn.edu.neu.cloudlab.haolap.validation.PointValidation;
import cn.edu.neu.cloudlab.haolap.xmlDriver.*;
import cn.edu.neu.cloudlab.haolap.xmlDriver.beans.ConditionBean;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.dom4j.DocumentException;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

public class JobNodeRequestDealer {
    private static final String schemaNameAndDimensionNameDelimiter = SystemConf
            .getSchemaNameAndDimensionNameDelimiter();
    // xml request
    private final String xmlRequest;
    // Global utils
    private SchemaClientInterface schemaClient = SchemaClient.getSchemaClient();
    private HFJobCreator hfJobCreator = new HFJobCreator();

    // properties read from xml request
    private String fromCubeIdentifier;
    private Schema fromSchema;
    private AggregationCondition.Type aggregationType;
    private String timestamp;
    private List<ConditionBean> conditionBeans;

    // newCubeIdentifier & newSchema
    private String newCubeIdentifier;
    private Schema newSchema;

    // four condition
    private CubeCondition cubeCondition;
    private SetCondition setCondition;
    private TargetCondition targetCondition;
    private AggregationCondition aggregationCondition;

    // conf
    private Configuration conf;

    // jobs
    private Job aggregationJob;

    public JobNodeRequestDealer(String xmlRequest) {
        super();
        this.xmlRequest = xmlRequest;
    }

    public String getXmlRequest() {
        return xmlRequest;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public void deal() throws XmlTypeErrorException, CubeNotExistsException,
            SchemaNotExistsException, AggregationTypeInvalidException,
            CorrespondingDimensionNotExistsException,
            CorrespondingLevelNotExistsException, SizeNotEqualException,
            DocumentException, IOException, InvalidXmlException {
        if (DeleteXmlDriver.isDeleteXml(xmlRequest)) {
            // TODO not yet implemented
        } else if (SelectTaskXmlDriver.isSelectTaskXml(xmlRequest)) {
            selectTaskAction();
        } else if (RollupTaskXmlDriver.isRollupTaskXml(xmlRequest)) {
            // TODO not yet implemented
        } else if (DrilldownTaskXmlDriver.isDrilldownTaskXml(xmlRequest)) {
            // TODO not yet implemented
        } else if (SliceTaskXmlDriver.isSliceTaskXml(xmlRequest)) {
            // TODO not yet implemented
        } else if (DiceTaskXmlDriver.isDiceTaskXml(xmlRequest)) {
            // TODO not yet implemented
        } else {
            throw new InvalidXmlException();
        }
    }

    public void runJob() throws IOException, InterruptedException,
            ClassNotFoundException {
        aggregationJob.setJarByClass(getClass());
        aggregationJob.waitForCompletion(true);
    }

    public boolean isJobSuccess() throws IOException {
        if (null == aggregationJob) {
            return false;
        }
        return aggregationJob.isSuccessful();
    }

    public void cleanCubeDirectory() throws IOException {
        FileSystem hdfs = FileSystem.get(CubeConfiguration.getConfiguration());
        String cubePathStr = PathConf.getCubeElementPath() + newCubeIdentifier
                + "/";
        Path cubePath = new Path(cubePathStr);
        // Path logs = new Path(cubePathStr + "_logs");
        // hdfs.delete(logs, true);
        FileStatus files[] = hdfs.listStatus(cubePath);
        ArrayList<FileStatus> needDeleteFile = new ArrayList<FileStatus>();
        for (FileStatus file : files) {
            try {
                String fileNo = file.getPath().toString().split("-")[2];
                Path newFilePath = new Path(cubePathStr
                        + Integer.valueOf(fileNo));
                hdfs.rename(file.getPath(), newFilePath);
            } catch (Exception e) {
                needDeleteFile.add(file);
            }
        }
        for (FileStatus fileStatus : needDeleteFile) {
            Path path = fileStatus.getPath();
            System.out.println("Delete " + path.getName());
            hdfs.delete(fileStatus.getPath(), true);

        }
    }

    public String getNewCubeIdentifier() {
        return this.newCubeIdentifier;
    }

    public void updateToSchemaServer() throws SchemaAlreadyExistsException,
            CubeAlreadyExistsException, CubeNotExistsException {
        SchemaClientInterface schemaClient = SchemaClient.getSchemaClient();
        schemaClient.addSchema(newSchema);
        Cube fromCube = schemaClient.getCube(fromCubeIdentifier);
        schemaClient.addCube(new Cube(newCubeIdentifier,
                fromCube.getDataType(), newSchema.getName()));
    }

    private void selectTaskAction() throws XmlTypeErrorException,
            CubeNotExistsException, SchemaNotExistsException,
            AggregationTypeInvalidException,
            CorrespondingDimensionNotExistsException, DocumentException,
            CorrespondingLevelNotExistsException, SizeNotEqualException,
            IOException {
        this.readPropertiesFromXmlRequest();
        this.initConditions();
        this.initNewCubeIdentifier();
        this.initNewSchema();
        System.out.println(this.fromSchema);
        System.out.println(this.newSchema);
        this.initConf();
        this.aggregationJob = this.generateAggregationJob();
        // TODO add newCube and newSchema to SchemaServer
        // TODO response in another thread
    }

    private Job generateAggregationJob() throws SizeNotEqualException,
            IOException {
        List<Long> usedPageNos = new ArrayList<Long>();
        long numberOfPagesForNewSchema = PageHelper.getNumberOfPages(newSchema);
        System.out.println(newSchema.toString());
        List<Long> pageStartPoint = PageHelper
                .translateElementPointToPagePoint(setCondition.getStartPoint(),
                        fromSchema);
        List<Long> pageEndPoint = PageHelper.translateElementPointToPagePoint(
                setCondition.getEndPoint(), fromSchema);
        List<Long> pagePoint = new ArrayList<Long>();
        long numberOfPageSegmentsInDimensions[] = PageHelper
                .getNumberOfPageSegmentsInDimensions(fromSchema);
        long[] tmp;
        long numberOfPagesForFromSchema = PageHelper
                .getNumberOfPages(fromSchema);
        for (long pageNo = 0; pageNo < numberOfPagesForFromSchema; pageNo += 1) {
            tmp = Serializer.deserialize(numberOfPageSegmentsInDimensions,
                    new BigInteger(String.valueOf(pageNo)));
            pagePoint.clear();
            System.out.print("from page position: ");
            for (long l : tmp) {
                pagePoint.add(l);
                System.out.print(l);
                System.out.print(",");
            }
            System.out.print("\t");
            try {
                PointValidation.validate(pagePoint, pageStartPoint,
                        pageEndPoint);
                System.out.println("selected");
            } catch (InvalidPointException e) {
                System.out.println();
                continue;
            }
            usedPageNos.add(pageNo);
        }
        Job aggregationJob = hfJobCreator.createAggregationJob(conf,
                numberOfPagesForNewSchema, usedPageNos, fromCubeIdentifier,
                newCubeIdentifier);
        return aggregationJob;
    }

    private void initConf() {
        conf = CubeConfiguration.getCopyOfConfiguration(CubeConfiguration
                .getConfiguration());
        // set fromCubeIdentifier & newCubeIdentifier
        conf.set(SystemConf.getFromCubeIdentifierForConfSetString(),
                cubeCondition.getCubeIdentifier());
        conf.set(SystemConf.getNewCubeIdentifierForConfSetString(),
                newCubeIdentifier);
        // set fromSchema & newSchema
        conf.set(SystemConf.getFromSchemaForConfSetString(),
                fromSchema.toString());
        conf.set(SystemConf.getNewSchemaForConfSetString(),
                newSchema.toString());
        // set fromDimensionLengths & newDimensionLengths
        conf.set(SystemConf.getFromDimensionLengthsForConfSetString(),
                this.generateFromDimensionLengthsStr());
        conf.set(SystemConf.getNewDimensionLengthsForConfSetString(),
                this.generateNewDimensionLengthsStr());
        // set aggregationType
        conf.set(SystemConf.getAggregationTypeForConfSetString(),
                aggregationCondition.getAggregationType().toString());
        // set startPoint & endPoint
        conf.set(SystemConf.getStartPointForConfSetString(),
                this.generateStartPointStr());
        conf.set(SystemConf.getEndPointForConfSetString(),
                this.generateEndPointStr());
        // set numberOfElementsInFromCubePerPage
        conf.setLong(
                SystemConf
                        .getNumberOfElementsInFromCubePerPageForConfSetString(),
                CubeHelper.getNumberOfElements(fromSchema)
                        / PageHelper.getNumberOfPages(fromSchema));
    }

    private String generateStartPointStr() {
        String startPointStr = "";
        List<Long> startPoint = setCondition.getStartPoint();
        for (long l : startPoint) {
            startPointStr += l;
            startPointStr += SystemConf.getHadoopConfDelimiter();
        }
        return startPointStr;
    }

    private String generateEndPointStr() {
        String endPointStr = "";
        List<Long> endPoint = setCondition.getEndPoint();
        for (long l : endPoint) {
            endPointStr += l;
            endPointStr += SystemConf.getHadoopConfDelimiter();
        }
        return endPointStr;
    }

    private String generateNewDimensionLengthsStr() {
        long newDimensionLengths[] = DimensionHelper
                .getDimensionLengths(newSchema);
        String newDimensionLengthsStr = "";
        for (long l : newDimensionLengths) {
            newDimensionLengthsStr += l;
            newDimensionLengthsStr += SystemConf.getHadoopConfDelimiter();
        }
        return newDimensionLengthsStr;
    }

    private String generateFromDimensionLengthsStr() {
        long fromDimensionLengths[] = DimensionHelper
                .getDimensionLengths(fromSchema);
        String fromDimensionLengthsStr = "";
        for (long l : fromDimensionLengths) {
            fromDimensionLengthsStr += l;
            fromDimensionLengthsStr += SystemConf.getHadoopConfDelimiter();
        }
        return fromDimensionLengthsStr;
    }

    private void initNewSchema()
            throws CorrespondingDimensionNotExistsException {
        SortedSet<DimensionLevelPair> dimensionLevelPairs = targetCondition
                .getPairs();
        SortedSet<Dimension> fromDimensions = fromSchema.getDimensions();
        SortedSet<Dimension> newDimensions = new TreeSet<Dimension>();
        SortedSet<Level> fromLevels;
        SortedSet<Level> newLevels;
        List<Long> startPoint = setCondition.getStartPoint();
        List<Long> endPoint = setCondition.getEndPoint();
        System.err.println("startPoint:" + startPoint + " endPoint:" + endPoint);
        int newDimensionOrderNo = 0;
        Dimension correspondingDimension;
        for (DimensionLevelPair dimensionLevelPair : dimensionLevelPairs) {
            // dimensionRange
            DimensionRange newDimensionRange = new DimensionRange(
                    startPoint.get(newDimensionOrderNo),
                    endPoint.get(newDimensionOrderNo));
            // levels
            int dimensionNo = dimensionLevelPair.getDimensionOrderNo();
            int levelNo = dimensionLevelPair.getLevelOrderNo();
            correspondingDimension = findCorrespondingDimension(dimensionNo,
                    fromDimensions);
            long[] levelLengths = getLevelLengths(correspondingDimension);
            long dimensionStart[] = Serializer.deserialize(levelLengths,
                    new BigInteger(String.valueOf(newDimensionRange.getStart())));
            long dimensionEnd[] = Serializer.deserialize(levelLengths,
                    new BigInteger(String.valueOf(newDimensionRange.getEnd())));
            newLevels = new TreeSet<Level>();
            fromLevels = correspondingDimension.getLevels();
            Iterator<Level> iterator = fromLevels.iterator();
            long preLevelLength = 1;
            for (int newOrderNumber = 0; newOrderNumber <= levelNo
                    && iterator.hasNext(); newOrderNumber += 1) {
                Level tmpLevel = iterator.next();
                long length = (preLevelLength == 1 ? (dimensionEnd[newOrderNumber]
                        - dimensionStart[newOrderNumber] + 1)
                        : tmpLevel.getLength());
                preLevelLength = length;
                long begin = dimensionStart[newOrderNumber];
                newLevels.add(new Level(tmpLevel.getName(), length, begin,
                        newOrderNumber));
            }
            newDimensions.add(Dimension.getDimension(
                    correspondingDimension.getName(), newLevels,
                    newDimensionOrderNo, newDimensionRange));
            newDimensionOrderNo += 1;
            System.out.print("lengths: \t");
            for (Dimension dimension : newDimensions) {
                System.out.println(dimension.toString());
            }
            System.out.println();
        }
        newSchema = Schema.getSchema(newCubeIdentifier
                        + schemaNameAndDimensionNameDelimiter + fromSchema.getName(),
                newDimensions);
    }

    private void initNewCubeIdentifier() {
        do {
            newCubeIdentifier = RandomMd5Generator.generate();
        } while (schemaClient.isCubeExist(newCubeIdentifier));
    }

    private void readPropertiesFromXmlRequest() throws DocumentException,
            XmlTypeErrorException, CubeNotExistsException,
            SchemaNotExistsException, AggregationTypeInvalidException,
            CorrespondingDimensionNotExistsException {
        OLAPTaskXmlDriver olapTaskXmlDriver = new SelectTaskXmlDriver(
                xmlRequest);
        fromCubeIdentifier = olapTaskXmlDriver.getFromCubeIdentifier();
        fromSchema = schemaClient.getSchema(fromCubeIdentifier);
        String aggregationTypeStr = olapTaskXmlDriver.getAggregation();
        aggregationType = translateAggregationType(aggregationTypeStr);
        timestamp = olapTaskXmlDriver.getTimestamp();
        conditionBeans = olapTaskXmlDriver.getConditionBeans();
        fullFillConditionBeans();
    }

    private void initConditions()
            throws CorrespondingDimensionNotExistsException,
            CorrespondingLevelNotExistsException {
        cubeCondition = new CubeCondition(fromCubeIdentifier);
        setCondition = new SetCondition();
        targetCondition = new TargetCondition();
        SortedSet<Dimension> dimensions = fromSchema.getDimensions();
        Dimension correspondingDimension;
        Level level;
        for (ConditionBean conditionBean : conditionBeans) {
            correspondingDimension = findDimension(
                    conditionBean.getDimensionName(), dimensions);
            setCondition.addStartEndPair(correspondingDimension,
                    Long.valueOf(conditionBean.getStart()),
                    Long.valueOf(conditionBean.getEnd()));
            level = findCorrespondingLevel(conditionBean.getLevelName(),
                    correspondingDimension.getLevels());
            targetCondition.addDimensionLevel(correspondingDimension, level);
        }
        aggregationCondition = new AggregationCondition(aggregationType);
    }

    private AggregationCondition.Type translateAggregationType(
            String aggregationStr) throws AggregationTypeInvalidException {
        if (aggregationStr.toLowerCase().equals("avg")) {
            return AggregationCondition.Type.average;
        } else if (aggregationStr.toLowerCase().equals("sum")) {
            return AggregationCondition.Type.sum;
        } else if (aggregationStr.toLowerCase().equals("max")) {
            return AggregationCondition.Type.max;
        } else if (aggregationStr.toLowerCase().equals("min")) {
            return AggregationCondition.Type.min;
        } else {
            throw new AggregationTypeInvalidException();
        }
    }

    private void fullFillConditionBeans()
            throws CorrespondingDimensionNotExistsException {
        for (ConditionBean conditionBean : conditionBeans) {
            String dimensionName = conditionBean.getDimensionName();
            SortedSet<Dimension> dimensions = fromSchema.getDimensions();
            Dimension correspondingDimension = findDimension(dimensionName,
                    dimensions);
            // fullFill levelName
            String levelName = conditionBean.getLevelName();
            if (null == levelName || "".equals(levelName)) {
                SortedSet<Level> levels = correspondingDimension.getLevels();
                Level lastLevel = levels.last();
                conditionBean.setLevelName(lastLevel.getName());
            }
            // fullFill start
            String start = conditionBean.getStart();
            if (null == start || "".equals(start)) {
                conditionBean.setStart(String.valueOf(correspondingDimension
                        .getRange().getStart()));
            }
            // fullFill end
            String end = conditionBean.getEnd();
            if (null == end || "".equals(end)) {
                conditionBean.setEnd(String.valueOf(correspondingDimension
                        .getRange().getEnd()));
            }
        }
    }

    private Dimension findDimension(String dimensionName,
                                    SortedSet<Dimension> dimensions)
            throws CorrespondingDimensionNotExistsException {
        if (null == dimensionName || "".equals(dimensionName)) {
            throw new CorrespondingDimensionNotExistsException();
        }
        for (Dimension dimension : dimensions) {
            if (dimensionName.equals(dimension.getName())) {
                return dimension;
            }
        }
        throw new CorrespondingDimensionNotExistsException();
    }

    private Level findCorrespondingLevel(String levelName,
                                         SortedSet<Level> levels)
            throws CorrespondingLevelNotExistsException {
        if (null == levelName || "".equals(levelName)) {
            throw new CorrespondingLevelNotExistsException();
        }
        for (Level level : levels) {
            if (levelName.equals(level.getName())) {
                return level;
            }
        }
        throw new CorrespondingLevelNotExistsException();
    }

    private Dimension findCorrespondingDimension(int dimensionNo,
                                                 SortedSet<Dimension> dimensions)
            throws CorrespondingDimensionNotExistsException {
        for (Dimension correspondingDimension : dimensions) {
            if (dimensionNo == correspondingDimension.getOrderNumber()) {
                return correspondingDimension;
            }
        }
        throw new CorrespondingDimensionNotExistsException();
    }

    private long[] getLevelLengths(Dimension dimension) {
        SortedSet<Level> levels = dimension.getLevels();
        int numberOfLevels = levels.size();
        long levelLengths[] = new long[numberOfLevels];
        int i = 0;
        for (Level level : levels) {
            levelLengths[i] = level.getLength();
            i += 1;
        }
        return levelLengths;
    }
}
