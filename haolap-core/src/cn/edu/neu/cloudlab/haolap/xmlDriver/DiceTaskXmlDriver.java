package cn.edu.neu.cloudlab.haolap.xmlDriver;

import cn.edu.neu.cloudlab.haolap.exception.XmlTypeErrorException;
import cn.edu.neu.cloudlab.haolap.util.SystemConf;
import cn.edu.neu.cloudlab.haolap.xmlDriver.beans.ConditionBean;
import org.dom4j.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DiceTaskXmlDriver implements OLAPTaskXmlDriver {
    private final static String diceXmlKey = SystemConf.getDiceTaskXmlKey();
    private final String aggregation = "";
    private Document document;
    private String fromCubeIdentifier;
    private String timestamp;
    private List<ConditionBean> conditionBeans = new ArrayList<ConditionBean>();

    public DiceTaskXmlDriver(String xmlStr) throws DocumentException,
            XmlTypeErrorException {
        super();
        if (!isDiceTaskXml(xmlStr)) {
            throw new XmlTypeErrorException("not a valid diceTask Xml");
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
        final String levelName = "";
        String start;
        String end;
        ConditionBean conditionBean;
        Node tmpNode;
        for (Iterator<?> iter = list.iterator(); iter.hasNext(); ) {
            Node node = (Node) iter.next();
            dimensionName = node.selectSingleNode("dimensionName").getText();
            if (null == (tmpNode = node.selectSingleNode("start"))) {
                start = "";
            } else {
                start = tmpNode.getText();
            }
            if (null == (tmpNode = node.selectSingleNode("end"))) {
                end = "";
            } else {
                end = tmpNode.getText();
            }
            conditionBean = new ConditionBean(dimensionName, levelName, start,
                    end);
            conditionBeans.add(conditionBean);
        }
    }

    public static boolean isDiceTaskXml(String xmlStr) {
        xmlStr = xmlStr.toLowerCase().replace("\n", "");
        String regex = "<type>" + diceXmlKey + "</type>";
        regex = regex.toLowerCase();
        if (xmlStr.contains(regex)) {
            return true;
        }
        return false;
    }

    @Override
    public String getKey() {
        return diceXmlKey;
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
