package cn.edu.neu.cloudlab.haolap.socket;

import cn.edu.neu.cloudlab.haolap.util.SystemConf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.Date;

public class MetaDataNodeForHttpServerTestClient {
    private int port = 5700;
    private Socket requestSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void run() throws UnknownHostException, IOException,
            ClassNotFoundException {

        testPairInfoRequest();
        testCubeMetaInfoRequest();
        testAxisInfoRequest();
    }

    private void testPairInfoRequest() throws IOException {
        // create a socket to connect to the server
        requestSocket = new Socket("localhost", port);
        // get input an output streams
        out = new PrintWriter(requestSocket.getOutputStream());
        out.flush();
        in = new BufferedReader(new InputStreamReader(
                requestSocket.getInputStream()));
        String message = getPairInfoRequestXml();
        sendMessage(out, message);
        String line = "";
        System.out.print("message: ");
        do {
            line = in.readLine();
            System.out.println(line);
        } while (null != line
                && !(SystemConf.getSocketMessageEndMark().equals(line)));
        in.close();
        out.close();
        requestSocket.close();
    }

    private void testCubeMetaInfoRequest() throws IOException {
        requestSocket = new Socket("localhost", port);
        // get input an output streams
        out = new PrintWriter(requestSocket.getOutputStream());
        out.flush();
        in = new BufferedReader(new InputStreamReader(
                requestSocket.getInputStream()));
        String message = getCubeMetaInfoRequestXml();
        sendMessage(out, message);
        String line = "";
        System.out.print("message: ");
        do {
            line = in.readLine();
            System.out.println(line);
        } while (null != line
                && !(SystemConf.getSocketMessageEndMark().equals(line)));
        in.close();
        out.close();
        requestSocket.close();
    }

    private void testAxisInfoRequest() throws IOException {
        requestSocket = new Socket("localhost", port);
        // get input an output streams
        out = new PrintWriter(requestSocket.getOutputStream());
        out.flush();
        in = new BufferedReader(new InputStreamReader(
                requestSocket.getInputStream()));
        String message = getAxisInfoRequestXml();
        sendMessage(out, message);
        String line = "";
        System.out.print("message: ");
        do {
            line = in.readLine();
            System.out.println(line);
        } while (null != line
                && !(SystemConf.getSocketMessageEndMark().equals(line)));
        in.close();
        out.close();
        requestSocket.close();
    }

    private String getPairInfoRequestXml() {
        String timestampStr;
        String cubeIdentifier;
        Date now = new Date();
        Timestamp timestamp = new Timestamp(now.getTime());
        timestampStr = timestamp.toString();
        cubeIdentifier = "baseCube";
        String pairInfoXmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<properties>" + "<timestamp>"
                + timestampStr
                + "</timestamp>"
                + "<type>pairinformation</type>"
                + "<cube>"
                + cubeIdentifier
                + "</cube>"
                + "<parameters>"
                + "<parameter>"
                + "<dimensionName>Time</dimensionName>"
                + "<type>start</type>"
                + "<value>1850-6</value>"
                + "</parameter>"
                + "<parameter>"
                + "<parameter>"
                + "<dimensionName>Time</dimensionName>"
                + "<type>start</type>"
                + "<value>1850-06</value>"
                + "</parameter>"
                + "<dimensionName>Time</dimensionName>"
                + "<type>end</type>"
                + "<value>1850-6-10</value>"
                + "</parameter>"
                + "<parameter>"
                + "<dimensionName>Area</dimensionName>"
                + "<type>start</type>"
                + "<value>0-10-11</value>"
                + "</parameter>"
                + "<parameter>"
                + "<dimensionName>Depth</dimensionName>"
                + "<type>end</type>"
                + "<value>0-4-2</value>"
                + "</parameter>"
                + "</parameters>"
                + "</properties>";
        return pairInfoXmlStr;
    }

    private String getAxisInfoRequestXml() {
        String timestampStr;
        String cubeIdentifier;
        Date now = new Date();
        Timestamp timestamp = new Timestamp(now.getTime());
        timestampStr = timestamp.toString();
        cubeIdentifier = "baseCube";
        String pairInfoXmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<properties>" + "<timestamp>" + timestampStr
                + "</timestamp>" + "<type>axisinformation</type>" + "<cube>"
                + cubeIdentifier + "</cube>" + "<dimensions>"
                + "<name>Time</name>" + "<name>Area</name>"
                + "<name>Depth</name>" + "</dimensions>" + "</properties>";
        return pairInfoXmlStr;
    }

    private String getCubeMetaInfoRequestXml() {
        String timestampStr;
        String cubeIdentifier;
        Date now = new Date();
        Timestamp timestamp = new Timestamp(now.getTime());
        timestampStr = timestamp.toString();
        cubeIdentifier = "baseCube";
        String pairInfoXmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<properties>" + "<timestamp>" + timestampStr
                + "</timestamp>" + "<type>cubeinformation</type>" + "<cube>"
                + cubeIdentifier + "</cube>" + "</properties>";
        return pairInfoXmlStr;
    }

    private void sendMessage(PrintWriter out, String message)
            throws IOException {
        out.println(message);
        out.println(SystemConf.getSocketMessageEndMark());
        out.flush();
    }
}
