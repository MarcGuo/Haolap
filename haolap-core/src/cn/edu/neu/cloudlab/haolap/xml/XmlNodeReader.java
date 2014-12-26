package cn.edu.neu.cloudlab.haolap.xml;

import org.w3c.dom.Node;

import java.util.Iterator;

public interface XmlNodeReader extends Iterator<Node>, Iterable<Node> {
    public String getFileName();

    public String getNodeInfo(Node node);
}
