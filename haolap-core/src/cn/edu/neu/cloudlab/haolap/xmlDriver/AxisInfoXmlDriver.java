package cn.edu.neu.cloudlab.haolap.xmlDriver;

import cn.edu.neu.cloudlab.haolap.exception.XmlTypeErrorException;
import cn.edu.neu.cloudlab.haolap.util.SystemConf;
import org.dom4j.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AxisInfoXmlDriver {
    private final static String axisInformationXmlKey = SystemConf
            .getAxisInformationXmlKey();
    private Document document;
    private String cubeIdentifier;
    private List<String> dimensionNames = new ArrayList<String>();

    public AxisInfoXmlDriver(String xmlStr) throws DocumentException,
            XmlTypeErrorException {
        super();
        if (!isAxisInfoXml(xmlStr)) {
            throw new XmlTypeErrorException("not a valid axisInfo Xml");
        }
        document = DocumentHelper.parseText(xmlStr);
        cubeIdentifier = document.selectSingleNode("//properties/cube")
                .getText();

        Element root = this.document.getRootElement();
        List<?> list = root.selectNodes("//properties/dimensions/name");
        String dimensionName;
        for (Iterator<?> iter = list.iterator(); iter.hasNext(); ) {
            Node node = (Node) iter.next();
            dimensionName = node.getText();
            dimensionNames.add(dimensionName);
        }
    }

    public static boolean isAxisInfoXml(String xmlStr) {
        xmlStr = xmlStr.toLowerCase().replace("\n", "");
        String regex = "<type>" + axisInformationXmlKey + "</type>";
        regex = regex.toLowerCase();
        if (xmlStr.contains(regex)) {
            return true;
        }
        return false;
    }

    public String getCubeIdentifier() {
        return cubeIdentifier;
    }

    public List<String> getDimensionNames() {
        return dimensionNames;
    }
}
