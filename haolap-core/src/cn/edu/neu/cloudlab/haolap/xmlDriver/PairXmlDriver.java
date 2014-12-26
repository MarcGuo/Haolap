package cn.edu.neu.cloudlab.haolap.xmlDriver;

import cn.edu.neu.cloudlab.haolap.exception.XmlTypeErrorException;
import cn.edu.neu.cloudlab.haolap.util.SystemConf;
import cn.edu.neu.cloudlab.haolap.xmlDriver.beans.NodeMeaningBean;
import org.dom4j.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PairXmlDriver {
    private final static String pairInformationXmlKey = SystemConf
            .getPairInformationXmlKey();
    List<NodeMeaningBean> nodeMeaningBeans = new ArrayList<NodeMeaningBean>();
    private Document document;
    private String cubeIdentifier;
    private String timestamp;

    public PairXmlDriver(String xmlStr) throws DocumentException,
            XmlTypeErrorException {
        super();
        if (!isPairXml(xmlStr)) {
            throw new XmlTypeErrorException("not a valid pair Xml");
        }
        document = DocumentHelper.parseText(xmlStr);
        cubeIdentifier = document.selectSingleNode("//properties/cube")
                .getText();
        timestamp = document.selectSingleNode("//properties/timestamp")
                .getText();
        // construct nodeMeaningBeans
        String dimensionName;
        String type;
        String value;
        Element root = this.document.getRootElement();
        List<?> list = root.selectNodes("//properties/parameters/parameter");
        for (Iterator<?> iter = list.iterator(); iter.hasNext(); ) {
            Node node = (Node) iter.next();
            dimensionName = node.selectSingleNode("dimensionName").getText();
            type = node.selectSingleNode("type").getText();
            value = node.selectSingleNode("value").getText();
            nodeMeaningBeans.add(new NodeMeaningBean(cubeIdentifier,
                    dimensionName, type, value));
        }
    }

    public static boolean isPairXml(String xmlStr) {
        xmlStr = xmlStr.toLowerCase().replace("\n", "");
        String regex = "<type>" + pairInformationXmlKey + "</type>";
        regex = regex.toLowerCase();
        if (xmlStr.contains(regex)) {
            return true;
        }
        return false;
    }

    public String getTimeStampValue() {
        return timestamp;
    }

    public String getCubeIdentirier() {
        return cubeIdentifier;
    }

    public List<NodeMeaningBean> getNodeMeaningBeans() {
        return nodeMeaningBeans;
    }
}
