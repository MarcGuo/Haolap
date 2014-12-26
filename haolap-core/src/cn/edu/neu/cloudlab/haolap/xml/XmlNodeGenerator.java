package cn.edu.neu.cloudlab.haolap.xml;

public interface XmlNodeGenerator {
    public String generate();

    public XmlNodeGenerator addSubNodeGenerator(XmlNodeGenerator subNode);

    public XmlNodeGenerator removeSubNodeByIndex(int index);

    public void clearSubNodes();

}
