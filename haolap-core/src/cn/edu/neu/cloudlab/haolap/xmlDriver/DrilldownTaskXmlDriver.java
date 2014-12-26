package cn.edu.neu.cloudlab.haolap.xmlDriver;

import cn.edu.neu.cloudlab.haolap.exception.XmlTypeErrorException;
import cn.edu.neu.cloudlab.haolap.util.SystemConf;
import cn.edu.neu.cloudlab.haolap.xmlDriver.beans.ConditionBean;
import org.dom4j.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DrilldownTaskXmlDriver implements OLAPTaskXmlDriver {
    private final static String drilldownXmlKey = SystemConf
            .getDrilldownTaskXmlKey();
    private final String aggregation = "";
    private Document document;
    private String fromCubeIdentifier;
    private String timestamp;
    private List<ConditionBean> conditionBeans = new ArrayList<ConditionBean>();

    public DrilldownTaskXmlDriver(String xmlStr) throws DocumentException,
            XmlTypeErrorException {
        super();
        if (!isDrilldownTaskXml(xmlStr)) {
            throw new XmlTypeErrorException("not a valid drilldownTask Xml");
        }
        document = DocumentHelper.parseText(xmlStr);
        timestamp = document.selectSingleNode("//properties/timestamp")
                .getText();
        fromCubeIdentifier = document.selectSingleNode(
                "//properties/operation/from").getText();

        Element root = this.document.getRootElement();
        List<?> list = root
                .selectNodes("//properties/operation/conditions/condition");
        String dimensionName;
        String levelName;
        final String start = "";
        final String end = "";
        ConditionBean conditionBean;
        for (Iterator<?> iter = list.iterator(); iter.hasNext(); ) {
            Node node = (Node) iter.next();
            dimensionName = node.selectSingleNode("dimensionName").getText();
            levelName = node.selectSingleNode("levelName").getText();
            conditionBean = new ConditionBean(dimensionName, levelName, start,
                    end);
            conditionBeans.add(conditionBean);
        }
    }

    public static boolean isDrilldownTaskXml(String xmlStr) {
        xmlStr = xmlStr.toLowerCase().replace("\n", "");
        String regex = "<type>" + drilldownXmlKey + "</type>";
        regex = regex.toLowerCase();
        if (xmlStr.contains(regex)) {
            return true;
        }
        return false;
    }

    @Override
    public String getKey() {
        return drilldownXmlKey;
    }

    @Override
    public String getAggregation() {
        return aggregation;
    }

    @Override
    public String getFromCubeIdentifier() {
        return fromCubeIdentifier;
    }

    @Override
    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public List<ConditionBean> getConditionBeans() {
        return conditionBeans;
    }

}
