package cn.edu.neu.cloudlab.haolap.initialize;

import cn.edu.neu.cloudlab.haolap.configuration.CubeConfiguration;
import cn.edu.neu.cloudlab.haolap.cube.Cube;
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

public class PersistCubeReader {
    public SortedSet<Cube> read() throws CubeXmlFileNotExistsException, IOException,
            DocumentException {
        Configuration conf = CubeConfiguration.getConfiguration();
        FileSystem hdfs = FileSystem.get(conf);
        Path cubeXmlFile = new Path(PathConf.getCubeXmlFilePath());
        if (!hdfs.exists(cubeXmlFile)) {
            throw new CubeXmlFileNotExistsException();
        }
        FSDataInputStream in = hdfs.open(cubeXmlFile);
        SAXReader reader = new SAXReader();
        Document document = reader.read(in);
        List<?> list = document.selectNodes("//cubes/cube");
        Node node;
        String identifier;
        String dataType;
        String schemaName;
        SortedSet<Cube> cubes = new TreeSet<Cube>();
        for (Iterator<?> iter = list.iterator(); iter.hasNext(); ) {
            node = (Node) iter.next();
            identifier = node.selectSingleNode("identifier").getText();
            dataType = node.selectSingleNode("dataType").getText();
            schemaName = node.selectSingleNode("schemaName").getText();
            cubes.add(new Cube(identifier, dataType, schemaName));
        }
        return cubes;
    }
}
