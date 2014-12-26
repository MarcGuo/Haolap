package cn.edu.neu.cloudlab.haolap.xmlDriver;

import cn.edu.neu.cloudlab.haolap.exception.XmlTypeErrorException;
import cn.edu.neu.cloudlab.haolap.util.SystemConf;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

public class CubeMetaInfoXmlDriver {
    private final static String cubeInformationXmlKey = SystemConf
            .getCubeInformationXmlKey();
    private Document document;
    private String cubeIdentifier;

    public CubeMetaInfoXmlDriver(String xmlStr) throws DocumentException,
            XmlTypeErrorException {
        super();
        if (!isCubeMetaInfoXml(xmlStr)) {
            throw new XmlTypeErrorException("not a valid cubeMetaInfo Xml");
        }
        document = DocumentHelper.parseText(xmlStr);
        cubeIdentifier = document.selectSingleNode("//properties/cube")
                .getText();
    }

    public static boolean isCubeMetaInfoXml(String xmlStr) {
        xmlStr = xmlStr.toLowerCase().replace("\n", "");
        String regex = "<type>" + cubeInformationXmlKey + "</type>";
        regex = regex.toLowerCase();
        if (xmlStr.contains(regex)) {
            return true;
        }
        return false;
    }

    public String getCubeIdentifier() {
        return cubeIdentifier;
    }
}
