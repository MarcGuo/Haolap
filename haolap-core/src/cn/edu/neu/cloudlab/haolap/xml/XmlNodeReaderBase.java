package cn.edu.neu.cloudlab.haolap.xml;

import cn.edu.neu.cloudlab.haolap.configuration.CubeConfiguration;
import cn.edu.neu.cloudlab.haolap.exception.XmlPathNotFoundException;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class XmlNodeReaderBase implements XmlNodeReader {

    private final String pathDelimiter = ",";
    private String fileName;
    private List<Node> requiredNodes = new ArrayList<Node>();
    private List<String> path;
    private Iterator<Node> current;

    /**
     * construct the Reader with a xml file
     *
     * @param fileName
     * @param pathStr  the path of the node that you want to find
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     * @throws XmlPathNotFoundException
     */
    public XmlNodeReaderBase(String fileName, String pathStr)
            throws ParserConfigurationException, SAXException, IOException,
            XmlPathNotFoundException {
        super();
        this.fileName = fileName;
        pathStr = pathStr.replace(" ", "");
        pathStr = pathStr.replace(".", this.pathDelimiter);
        this.path = Arrays.asList(pathStr.split(this.pathDelimiter));
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
                .newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory
                .newDocumentBuilder();
        Path hdfsFile = new Path(fileName);
        FileSystem hdfs = FileSystem.get(CubeConfiguration
                .getConfiguration());
        FSDataInputStream fsDataInputStream = hdfs.open(hdfsFile);
        Document doc = documentBuilder.parse(fsDataInputStream);
        getNode(doc.getChildNodes(), this.path, 0);
        this.current = this.requiredNodes.iterator();
    }

    /**
     * construct the Reader with a xmlStr instead of file the parameters are not
     * well enough. because the flag is useless now, but we need it to distinct
     * the constructor
     *
     * @param xmlStr
     * @param pathStr the path of the node that you want to find
     * @param flag    I am sorry for the flag is useless now
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     * @throws XmlPathNotFoundException
     */
    // TODO try to solve the flag problem
    public XmlNodeReaderBase(String xmlStr, String pathStr, boolean flag)
            throws ParserConfigurationException, SAXException, IOException,
            XmlPathNotFoundException {
        super();
        this.fileName = "";
        pathStr = pathStr.replace(" ", "");
        pathStr = pathStr.replace(".", this.pathDelimiter);
        this.path = Arrays.asList(pathStr.split(this.pathDelimiter));
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
                .newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory
                .newDocumentBuilder();

        Document doc = documentBuilder.parse(new InputSource(new StringReader(
                xmlStr)));
        getNode(doc.getChildNodes(), this.path, 0);
        this.current = this.requiredNodes.iterator();
    }

    @Override
    public String getFileName() {
        return this.fileName;
    }

    @Override
    public boolean hasNext() {
        return this.current.hasNext();
    }

    @Override
    public Node next() {
        return this.current.next();
    }

    @Override
    public void remove() {
        this.current.remove();
    }

    @Override
    public Iterator<Node> iterator() {
        return this.requiredNodes.iterator();
    }

    @Override
    public String getNodeInfo(Node node) {
        return node.getFirstChild().getNodeValue();
    }

    private void getNode(NodeList nodeList, List<String> path, int pathDepth)
            throws XmlPathNotFoundException {
        for (int i = 0; i < nodeList.getLength(); i += 1) {
            Node node = nodeList.item(i);
            // get rid of {enter}, the empty node
            if (node.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            if (path.get(pathDepth).equals(node.getNodeName())) {
                if (pathDepth == path.size() - 1) {
                    this.requiredNodes.add(node);
                } else {
                    getNode(node.getChildNodes(), path, pathDepth + 1);
                }
            }
        }
        if (this.requiredNodes.size() == 0) {
            throw new XmlPathNotFoundException();
        }
    }

}
