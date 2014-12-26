package cn.edu.neu.cloudlab.haolap.xml;

import java.util.ArrayList;
import java.util.List;

public class XmlNodeGeneratorBase implements XmlNodeGenerator {
    private List<XmlNodeGenerator> subNodes = new ArrayList<XmlNodeGenerator>();
    private String xmlNodeName;
    private String xmlNodeStr;
    private boolean isStringValue;

    public XmlNodeGeneratorBase(String xmlNodeName, boolean isStringValue) {
        super();
        this.isStringValue = isStringValue;
        this.xmlNodeName = xmlNodeName;
    }

    @Override
    public String generate() {
        this.initXmlNodeStr();
        if (this.isStringValue) {
            this.xmlNodeStr += this.xmlNodeName + "\n";
        } else {
            this.generatorHeader();
            for (XmlNodeGenerator node : subNodes) {
                this.xmlNodeStr += node.generate();
            }
            this.generatorTail();
        }
        return this.xmlNodeStr;
    }

    @Override
    public XmlNodeGenerator addSubNodeGenerator(XmlNodeGenerator subNode) {
        this.subNodes.add(subNode);
        return this;
    }

    @Override
    public XmlNodeGenerator removeSubNodeByIndex(int index) {
        XmlNodeGenerator subNode = this.subNodes.get(index);
        this.subNodes.remove(index);
        return subNode;
    }

    @Override
    public void clearSubNodes() {
        this.subNodes.clear();
    }

    private void initXmlNodeStr() {
        this.xmlNodeStr = "";
    }

    private void generatorHeader() {
        this.xmlNodeStr += "<" + this.xmlNodeName + ">\n";
    }

    private void generatorTail() {
        this.xmlNodeStr += "</" + this.xmlNodeName + ">\n";
    }
}
