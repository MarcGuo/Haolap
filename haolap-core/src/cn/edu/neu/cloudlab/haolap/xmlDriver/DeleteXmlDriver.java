package cn.edu.neu.cloudlab.haolap.xmlDriver;

import cn.edu.neu.cloudlab.haolap.exception.XmlTypeErrorException;
import cn.edu.neu.cloudlab.haolap.util.SystemConf;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

public class DeleteXmlDriver {
    private final static String deleteXmlKey = SystemConf.getDeleteTaskXmlKey();
    private Document document;
    private String cubeIdentifier;
    private String timestamp;

    public DeleteXmlDriver(String xmlStr) throws DocumentException,
            XmlTypeErrorException {
        super();
        if (!isDeleteXml(xmlStr)) {
            throw new XmlTypeErrorException("not a valid delete Xml");
        }
        document = DocumentHelper.parseText(xmlStr);
        timestamp = document.selectSingleNode("//properties/timestamp")
                .getText();
        cubeIdentifier = document.selectSingleNode(
                "//properties/operation/from").getText();
    }

    public static boolean isDeleteXml(String xmlStr) {
        xmlStr = xmlStr.toLowerCase().replace("\n", "");
        String regex = "<type>" + deleteXmlKey + "</type>";
        regex = regex.toLowerCase();
        if (xmlStr.contains(regex)) {
            return true;
        }
        return false;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getCubeIdentifier() {
        return cubeIdentifier;
    }
}
