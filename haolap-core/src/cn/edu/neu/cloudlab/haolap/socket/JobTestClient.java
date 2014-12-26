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

public class JobTestClient {
    private int port = 5800;
    private Socket requestSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void run() throws UnknownHostException, IOException,
            ClassNotFoundException {
        // create a socket to connect to the server
        requestSocket = new Socket("localhost", port);
        // get input an output streams
        out = new PrintWriter(requestSocket.getOutputStream());
        out.flush();
        in = new BufferedReader(new InputStreamReader(
                requestSocket.getInputStream()));
        String message = getRequestXml();
        sendMessage(out, message);
        String line = "";
        System.out.print("message: ");
        do {
            line = in.readLine();
            System.out.println(line);
        } while (null != line
                && !(SystemConf.getSocketMessageEndMark().equals(line)));
        do {
            line = in.readLine();
            System.out.println(line);
        } while (null != line
                && !(SystemConf.getSocketMessageEndMark().equals(line)));
        in.close();
        out.close();
        requestSocket.close();
    }

    private String getRequestXml() {
        String timestampStr;
        String fromCubeIdentifier;
        String aggregationTypeStr = "sum";
        Date now = new Date();
        Timestamp timestamp = new Timestamp(now.getTime());
        timestampStr = timestamp.toString();
        fromCubeIdentifier = "baseCube";
        String selectTaskXmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
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
        return selectTaskXmlStr;
    }

    private void sendMessage(PrintWriter out, String message)
            throws IOException {
        out.println(message);
        out.println(SystemConf.getSocketMessageEndMark());
        out.flush();
    }
}
