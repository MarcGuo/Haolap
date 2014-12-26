package cn.edu.neu.cloudlab.haolap.requestDealer;

import cn.edu.neu.cloudlab.haolap.RPC.SchemaClient;
import cn.edu.neu.cloudlab.haolap.RPC.SchemaClientInterface;
import cn.edu.neu.cloudlab.haolap.cube.Dimension;
import cn.edu.neu.cloudlab.haolap.cube.DimensionRange;
import cn.edu.neu.cloudlab.haolap.cube.Level;
import cn.edu.neu.cloudlab.haolap.cube.Schema;
import cn.edu.neu.cloudlab.haolap.exception.*;
import cn.edu.neu.cloudlab.haolap.nodeMeaningMapper.*;
import cn.edu.neu.cloudlab.haolap.util.PageHelper;
import cn.edu.neu.cloudlab.haolap.util.PathConf;
import cn.edu.neu.cloudlab.haolap.util.SystemConf;
import cn.edu.neu.cloudlab.haolap.xmlDriver.AxisInfoXmlDriver;
import cn.edu.neu.cloudlab.haolap.xmlDriver.CubeMetaInfoXmlDriver;
import cn.edu.neu.cloudlab.haolap.xmlDriver.PairXmlDriver;
import cn.edu.neu.cloudlab.haolap.xmlDriver.beans.NodeMeaningBean;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.*;

public class MetaDataNodeRequestDealer {
    private static final String axisDelimiter = SystemConf.getAxisDelimiter();
    private final String xmlRequest;
    private TimeNodeMeaningMapper timeNodeMeaningMapper = new TimeNodeMeaningMapper();
    private AreaNodeMeaningMapper areaNodeMeaningMapper = new AreaNodeMeaningMapper();
    private DepthNodeMeaningMapper depthNodeMeaningMapper = new DepthNodeMeaningMapper();
    private TimeAxisMapper timeAxisMapper = new TimeAxisMapper();
    private AreaAxisMapper areaAxisMapper = new AreaAxisMapper();
    private DepthAxisMapper depthAxisMapper = new DepthAxisMapper();
    private String returnXmlStr;

    public MetaDataNodeRequestDealer(String xmlRequest) {
        super();
        this.xmlRequest = xmlRequest;
    }

    public String getXmlRequest() {
        return xmlRequest;
    }

    public void deal() throws XmlTypeErrorException, DocumentException,
            NumberFormatException, InvalidXmlException {
        if (AxisInfoXmlDriver.isAxisInfoXml(xmlRequest)) {
            axisInfoAction();
        } else if (CubeMetaInfoXmlDriver.isCubeMetaInfoXml(xmlRequest)) {
            cubeMetaInfoAction();
        } else if (PairXmlDriver.isPairXml(xmlRequest)) {
            pairInfoAction();
        } else {
            throw new InvalidXmlException();
        }
    }

    public String getReturnXmlStr() {
        return returnXmlStr;
    }

    private void axisInfoAction() throws DocumentException,
            XmlTypeErrorException {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("properties");
        AxisInfoXmlDriver axisInfoXmlDriver = new AxisInfoXmlDriver(xmlRequest);
        String cubeIdentifier = axisInfoXmlDriver.getCubeIdentifier();
        root.addElement("cube").addText(cubeIdentifier);
        try {
            SchemaClientInterface schemaClient = SchemaClient.getSchemaClient();
            Schema schema = schemaClient.getSchema(cubeIdentifier);
            SortedSet<Dimension> dimensions = schema.getDimensions();
            Element dimensionsNode = root.addElement("dimensions");
            List<String> dimensionNames = axisInfoXmlDriver.getDimensionNames();
            for (String dimensionName : dimensionNames) {
                Element dimensionNode = dimensionsNode.addElement("dimension");
                dimensionNode.addElement("name").addText(dimensionName);
                Dimension correspondingDimension = findCorrespondingDimension(
                        dimensionName, dimensions);
                SortedSet<Level> levels = correspondingDimension.getLevels();
                AxisIterator axisIterator = new AxisIterator(levels);
                String axisesStr = "";
                long axisWithoutMeaning[];
                while (axisIterator.hasNext()) {
                    axisWithoutMeaning = axisIterator.next();
                    if ("Time".equals(dimensionName)) {
                        axisesStr += timeAxisMapper
                                .getAxisWithMeaning(axisWithoutMeaning)
                                + axisDelimiter;
                    } else if ("Area".equals(dimensionName)) {
                        axisesStr += areaAxisMapper
                                .getAxisWithMeaning(axisWithoutMeaning)
                                + axisDelimiter;
                    } else if ("Depth".equals(dimensionName)) {
                        axisesStr += depthAxisMapper
                                .getAxisWithMeaning(axisWithoutMeaning)
                                + axisDelimiter;
                    }
                }
                dimensionNode.addElement("axis").addText(axisesStr);
            }
            root.addElement("success").addText("true");
        } catch (CubeNotExistsException e) {
            root.addElement("success").addText("false");
            Element errorsNode = root.addElement("errors");
            errorsNode.addElement("error").addText("CubeNotExists error");
        } catch (SchemaNotExistsException e) {
            root.addElement("success").addText("false");
            Element errorsNode = root.addElement("errors");
            errorsNode.addElement("error").addText("SchemaNotExists error");
        } catch (CorrespondingDimensionNotExistsException e) {
            root.addElement("success").addText("false");
            Element errorsNode = root.addElement("errors");
            errorsNode.addElement("error").addText(
                    "CorrespondingDimensionNotExists error");
        } catch (AxisWithoutMeaningErrorException e) {
            root.addElement("success").addText("false");
            Element errorsNode = root.addElement("errors");
            errorsNode.addElement("error").addText("AxisWithoutMeaning error");
        }
        this.returnXmlStr = document.asXML();
    }

    private Dimension findCorrespondingDimension(String dimensionName,
                                                 SortedSet<Dimension> dimensions)
            throws CorrespondingDimensionNotExistsException {
        if (null == dimensionName) {
            throw new CorrespondingDimensionNotExistsException();
        }
        for (Dimension correspondingDimension : dimensions) {
            if (dimensionName.equals(correspondingDimension.getName())) {
                return correspondingDimension;
            }
        }
        throw new CorrespondingDimensionNotExistsException();
    }

    private void cubeMetaInfoAction() throws DocumentException,
            XmlTypeErrorException {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("properties");
        CubeMetaInfoXmlDriver cubeMetaInfoXmlDriver = new CubeMetaInfoXmlDriver(
                xmlRequest);
        String cubeIdentifier = cubeMetaInfoXmlDriver.getCubeIdentifier();
        root.addElement("cube").addText(cubeIdentifier);
        try {
            SchemaClientInterface schemaClient = SchemaClient.getSchemaClient();
            Schema schema = schemaClient.getSchema(cubeIdentifier);
            // schema
            Element schemaNode = root.addElement("schema");
            schemaNode.addElement("name").addText(schema.getName());
            Element dimensionsNode = schemaNode.addElement("dimensions");
            SortedSet<Dimension> dimensions = schema.getDimensions();
            for (Dimension dimension : dimensions) {
                Element dimensionNode = dimensionsNode.addElement("dimension");
                dimensionNode.addElement("name").addText(dimension.getName());
                Element levelsNode = dimensionNode.addElement("levels");
                SortedSet<Level> levels = dimension.getLevels();
                for (Level level : levels) {
                    Element levelNode = levelsNode.addElement("level");
                    levelNode.addElement("name").addText(level.getName());
                    levelNode.addElement("length").addText(
                            String.valueOf(level.getLength()));
                    levelNode.addElement("begin").addText(
                            String.valueOf(level.getBegin()));
                    levelNode.addElement("orderNumber").addText(
                            String.valueOf(level.getOrderNumber()));
                }
                dimensionNode.addElement("orderNumber").addText(
                        String.valueOf(dimension.getOrderNumber()));
                Element dimensionRangeNode = dimensionNode.addElement("range");
                DimensionRange dimensionRange = dimension.getRange();
                dimensionRangeNode.addElement("start").addText(
                        String.valueOf(dimensionRange.getStart()));
                dimensionRangeNode.addElement("end").addText(
                        String.valueOf(dimensionRange.getEnd()));
            }
            // pages
            Element pagesNode = root.addElement("pages");
            long numberOfPages = PageHelper.getNumberOfPages(schema);
            for (long pageNo = 0; pageNo < numberOfPages; pageNo += 1) {
                Element pageNode = pagesNode.addElement("page");
                pageNode.addElement("id").addText(String.valueOf(pageNo));
                String address = PathConf.getCubeElementPath() + cubeIdentifier
                        + "/" + pageNo;
                pageNode.addElement("address").addText(address);
            }
            // pageSegment
            Element pageSegmentNode = root.addElement("pageSegment");
            long numberOfPageSegmentsInDimension[] = PageHelper
                    .getNumberOfPageSegmentsInDimensions(schema);
            int i = 0;
            for (Dimension dimension : dimensions) {
                Element infoNode = pageSegmentNode.addElement("info");
                infoNode.addElement("dimensionName").addText(
                        dimension.getName());
                infoNode.addElement("amount").addText(
                        String.valueOf(numberOfPageSegmentsInDimension[i]));
                i += 1;
            }
            root.addElement("success").addText("true");
        } catch (CubeNotExistsException e) {
            root.addElement("success").addText("false");
            Element errorsNode = root.addElement("errors");
            errorsNode.addElement("error").addText(
                    "invalid key: range or format");
        } catch (SchemaNotExistsException e) {
            root.addElement("success").addText("false");
            Element errorsNode = root.addElement("errors");
            errorsNode.addElement("error").addText(
                    "invalid key: range or format");
        }
        this.returnXmlStr = document.asXML();
    }

    private void pairInfoAction() throws DocumentException,
            XmlTypeErrorException {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("properties");
        PairXmlDriver pairXmlDriver = new PairXmlDriver(xmlRequest);
        String timestamp = pairXmlDriver.getTimeStampValue();
        root.addElement("timestamp").addText(timestamp);
        String cubeIdentifier = pairXmlDriver.getCubeIdentirier();
        System.err.println(cubeIdentifier);
        try {
            Schema schema = SchemaClient.getSchemaClient().getSchema(
                    cubeIdentifier);
            int minYear = 0, minMonth = 0, minDay = 0;
            int maxYear = 0, maxMonth = 0, maxDay = 0;
            Set<Dimension> dimensions = schema.getDimensions();
            Dimension timeDimension = null;
            for (Dimension dimension : dimensions) {
                if ("Time".equals(dimension.getName())) {
                    timeDimension = dimension;
                    break;
                }
            }
            Set<Level> timeLevels = timeDimension.getLevels();
            for (Level level : timeLevels) {
                if ("year".equals(level.getName())) {
                    minYear = (int) level.getBegin();
                    maxYear = (int) (level.getBegin() + level.getLength() - 1);
                } else if ("month".equals(level.getName())) {
                    minMonth = (int) level.getBegin();
                    maxMonth = (int) (level.getBegin() + level.getLength() - 1);
                } else if ("day".equals(level.getName())) {
                    minDay = (int) level.getBegin();
                    maxDay = (int) (level.getBegin() + level.getLength() - 1);
                }
            }
            System.err.println("year: " + minYear + "-" + maxYear);
            System.err.println("month: " + minMonth + "-" + maxMonth);
            System.err.println("day: " + minDay + "-" + maxDay);
            timeNodeMeaningMapper = new TimeNodeMeaningMapper(minYear, maxYear,
                    minMonth, maxMonth, minDay, maxDay);
        } catch (CubeNotExistsException e) {
            e.printStackTrace();
        } catch (SchemaNotExistsException e) {
            e.printStackTrace();
        }
        try {
            List<NodeMeaningBean> nodeMeaningBeans = pairXmlDriver
                    .getNodeMeaningBeans();
            for (NodeMeaningBean nodeMeaningBean : nodeMeaningBeans) {
                System.err.println(nodeMeaningBean.getType() + ": "
                        + nodeMeaningBean.getValue());
            }
            Element parametersNode = root.addElement("parameters");
            for (NodeMeaningBean nodeMeaningBean : nodeMeaningBeans) {
                String value = null;
                Element parameterNode = parametersNode.addElement("parameter");
                if ("Time".equals(nodeMeaningBean.getDimensionName())) {
                    value = timeNodeMeaningMapper.getMeaning(nodeMeaningBean
                            .getValue());
                    parameterNode.addElement("dimensionName").addText(
                            nodeMeaningBean.getDimensionName());
                    parameterNode.addElement("type").addText(
                            nodeMeaningBean.getType());
                    parameterNode.addElement("value").addText(value);
                } else if ("Area".equals(nodeMeaningBean.getDimensionName())) {
                    value = areaNodeMeaningMapper.getMeaning(nodeMeaningBean
                            .getValue());
                    parameterNode.addElement("dimensionName").addText(
                            nodeMeaningBean.getDimensionName());
                    parameterNode.addElement("type").addText(
                            nodeMeaningBean.getType());
                    parameterNode.addElement("value").addText(value);
                } else if ("Depth".equals(nodeMeaningBean.getDimensionName())) {
                    value = depthNodeMeaningMapper.getMeaning(nodeMeaningBean
                            .getValue());
                    parameterNode.addElement("dimensionName").addText(
                            nodeMeaningBean.getDimensionName());
                    parameterNode.addElement("type").addText(
                            nodeMeaningBean.getType());
                    parameterNode.addElement("value").addText(value);
                } else {
                    throw new DimensionNotExistsException();
                }
            }

            root.addElement("cube").addText(cubeIdentifier);
            root.addElement("success").addText("true");
        } catch (NodeMeaningKeyErrorException e) {
            root.addElement("success").addText("false");
            Element errorsNode = root.addElement("errors");
            errorsNode.addElement("error").addText(
                    "invalid key: range or format");
        } catch (NumberFormatException e) {
            root.addElement("success").addText("false");
            Element errorsNode = root.addElement("errors");
            errorsNode.addElement("error").addText("key should be number");
        } catch (DimensionNotExistsException e) {
            root.addElement("success").addText("false");
            Element errorsNode = root.addElement("errors");
            errorsNode.addElement("error").addText("DimensionNotExists error");
        }
        this.returnXmlStr = document.asXML();
    }

    private static class AxisIterator {
        private long axisWithoutMeaning[];
        private SortedSet<Level> levels;
        private int numberOfLevels;

        public AxisIterator(SortedSet<Level> levels) {
            this.levels = levels;
            numberOfLevels = levels.size();
            axisWithoutMeaning = new long[numberOfLevels];
            int i = 0;
            for (Level level : levels) {
                axisWithoutMeaning[i] = level.getBegin();
                i += 1;
            }
        }

        public boolean hasNext() {
            int i = 0;
            for (Level level : levels) {
                if (axisWithoutMeaning[i] > level.getBegin()
                        + level.getLength() - 1) {
                    return false;
                }
                i += 1;
            }
            return true;
        }

        public long[] next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            // save current
            long current[] = new long[numberOfLevels];
            for (int i = 0; i < numberOfLevels; i += 1) {
                current[i] = axisWithoutMeaning[i];
            }
            // iterate next
            Stack<Level> levelsStack = new Stack<Level>();
            for (Level level : levels) {
                levelsStack.push(level);
            }
            for (int i = numberOfLevels - 1; i >= 0; i -= 1) {
                Level level = levelsStack.pop();
                if (axisWithoutMeaning[i] < level.getBegin()
                        + level.getLength() - 1) {
                    axisWithoutMeaning[i] += 1;
                    break;
                } else {
                    if (i != 0) {
                        axisWithoutMeaning[i] = 0;
                    } else {
                        axisWithoutMeaning[i] += 1;
                    }
                }
            }
            return current;
        }
    }
}
