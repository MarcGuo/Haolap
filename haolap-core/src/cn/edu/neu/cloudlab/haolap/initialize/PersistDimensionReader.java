package cn.edu.neu.cloudlab.haolap.initialize;

import cn.edu.neu.cloudlab.haolap.configuration.CubeConfiguration;
import cn.edu.neu.cloudlab.haolap.cube.Dimension;
import cn.edu.neu.cloudlab.haolap.cube.DimensionRange;
import cn.edu.neu.cloudlab.haolap.cube.Level;
import cn.edu.neu.cloudlab.haolap.exception.CubeXmlFileNotExistsException;
import cn.edu.neu.cloudlab.haolap.util.PathConf;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class PersistDimensionReader {
    SortedSet<Dimension> read() throws CubeXmlFileNotExistsException,
            IOException, DocumentException {
        Configuration conf = CubeConfiguration.getConfiguration();
        FileSystem hdfs = FileSystem.get(conf);
        Path dimensionXmlFile = new Path(PathConf.getDimensionXmlFilePath());
        if (!hdfs.exists(dimensionXmlFile)) {
            throw new CubeXmlFileNotExistsException();
        }
        FSDataInputStream in = hdfs.open(dimensionXmlFile);
        SAXReader reader = new SAXReader();
        Document document = reader.read(in);
        SortedSet<Dimension> dimensions = new TreeSet<Dimension>();
        List<?> dimensionNodeList = document
                .selectNodes("//dimensions/dimension");
        Node dimensionNode;
        String dimensionName;
        int dimensionOrderNumber;
        SortedSet<Level> levels;
        List<?> levelNodeList;
        Node levelNode;
        String levelName;
        long levelLength;
        long levelBegin;
        int levelOrderNumber;
        DimensionRange dimensionRange;
        Node dimensionRangeNode;
        long dimensionRangeStart;
        long dimensionRangeEnd;
        for (Iterator<?> dimensionNodeIter = dimensionNodeList.iterator(); dimensionNodeIter
                .hasNext(); ) {
            dimensionNode = (Node) dimensionNodeIter.next();
            dimensionName = dimensionNode.selectSingleNode("name").getText();
            dimensionOrderNumber = Integer.valueOf(dimensionNode
                    .selectSingleNode("orderNumber").getText());
            levelNodeList = dimensionNode.selectNodes("level");
            levels = new TreeSet<Level>();
            for (Iterator<?> levelNodeIter = levelNodeList.iterator(); levelNodeIter
                    .hasNext(); ) {
                levelNode = (Node) levelNodeIter.next();
                levelName = levelNode.selectSingleNode("name").getText();
                levelLength = Long.valueOf(levelNode.selectSingleNode("length")
                        .getText());
                levelBegin = Long.valueOf(levelNode.selectSingleNode("begin")
                        .getText());
                levelOrderNumber = Integer.valueOf(levelNode.selectSingleNode(
                        "orderNumber").getText());
                levels.add(new Level(levelName, levelLength, levelBegin,
                        levelOrderNumber));
            }
            dimensionRangeNode = dimensionNode.selectSingleNode("range");
            dimensionRangeStart = Long.valueOf(dimensionRangeNode
                    .selectSingleNode("start").getText());
            dimensionRangeEnd = Long.valueOf(dimensionRangeNode
                    .selectSingleNode("end").getText());
            dimensionRange = new DimensionRange(dimensionRangeStart,
                    dimensionRangeEnd);
            dimensions.add(Dimension.getDimension(dimensionName, levels,
                    dimensionOrderNumber, dimensionRange));
        }
        return dimensions;
    }
}
