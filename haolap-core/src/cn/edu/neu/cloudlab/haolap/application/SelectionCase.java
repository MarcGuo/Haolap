package cn.edu.neu.cloudlab.haolap.application;

import cn.edu.neu.cloudlab.haolap.exception.*;
import cn.edu.neu.cloudlab.haolap.requestDealer.JobNodeRequestDealer;
import org.dom4j.DocumentException;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

public class SelectionCase {

    public static void main(String args[]) {
        String selectTaskXmlStr;
        String timestampStr;
        String fromCubeIdentifier;
        String aggregationTypeStr = "sum";
        Date now = new Date();
        Timestamp timestamp = new Timestamp(now.getTime());
        timestampStr = timestamp.toString();
        fromCubeIdentifier = "baseCube";
        selectTaskXmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<properties>" + "<timestamp>"
                + timestampStr
                + "</timestamp>"
                + "<operation>"
                + "<type>select</type>"
                + "<conditions>"
                + "<condition>"
                + "<dimensionName>Time</dimensionName>"
                + "<levelName>month</levelName>"
                + "<start>0</start>"
                + "<end>61</end>"
                + "</condition>"
                + "<condition>"
                + "<dimensionName>Area</dimensionName>"
                + "<levelName>oneSixteenth</levelName>"
                + "<start>0</start>"
                + "<end>127</end>"
                + "</condition>"
                + "<condition>"
                + "<dimensionName>Depth</dimensionName>"
                + "<levelName>10m</levelName>"
                + "</condition>"
                + "</conditions>"
                + "<aggregation>"
                + aggregationTypeStr
                + "</aggregation>"
                + "<from>"
                + fromCubeIdentifier
                + "</from>"
                + "</operation></properties>";
        JobNodeRequestDealer jobNodeRequestDealer = new JobNodeRequestDealer(
                selectTaskXmlStr);
        try {
            jobNodeRequestDealer.deal();
            jobNodeRequestDealer.runJob();
            jobNodeRequestDealer.cleanCubeDirectory();
            jobNodeRequestDealer.updateToSchemaServer();
        } catch (XmlTypeErrorException e) {
            e.printStackTrace();
        } catch (CubeNotExistsException e) {
            e.printStackTrace();
        } catch (SchemaNotExistsException e) {
            e.printStackTrace();
        } catch (AggregationTypeInvalidException e) {
            e.printStackTrace();
        } catch (CorrespondingDimensionNotExistsException e) {
            e.printStackTrace();
        } catch (CorrespondingLevelNotExistsException e) {
            e.printStackTrace();
        } catch (SizeNotEqualException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidXmlException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SchemaAlreadyExistsException e) {
            e.printStackTrace();
        } catch (CubeAlreadyExistsException e) {
            e.printStackTrace();
        }
    }
}
