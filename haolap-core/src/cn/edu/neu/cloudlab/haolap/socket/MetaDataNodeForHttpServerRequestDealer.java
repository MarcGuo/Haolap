package cn.edu.neu.cloudlab.haolap.socket;

import cn.edu.neu.cloudlab.haolap.exception.InvalidXmlException;
import cn.edu.neu.cloudlab.haolap.exception.XmlTypeErrorException;
import cn.edu.neu.cloudlab.haolap.requestDealer.MetaDataNodeRequestDealer;
import cn.edu.neu.cloudlab.haolap.util.SystemConf;
import org.dom4j.DocumentException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MetaDataNodeForHttpServerRequestDealer implements Runnable {
    private static final String socketMessageEndMark = SystemConf
            .getSocketMessageEndMark();
    private Socket connection = null;
    private PrintWriter out;
    private BufferedReader in;
    private MetaDataNodeRequestDealer metaDataNodeRequestDealer;

    public MetaDataNodeForHttpServerRequestDealer(Socket connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(connection.getOutputStream());
            out.flush();
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String message = "";
            String line = "";
            do {
                line = in.readLine();
                if (!("\3".equals(line))) {
                    message += line;
                }
            } while (null != line && !(socketMessageEndMark.equals(line)));
            System.out.println(message);
            metaDataNodeRequestDealer = new MetaDataNodeRequestDealer(message);
            metaDataNodeRequestDealer.deal();
            System.out.println(metaDataNodeRequestDealer.getReturnXmlStr());
            sendMessage(out, metaDataNodeRequestDealer.getReturnXmlStr());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (XmlTypeErrorException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (InvalidXmlException e) {
            // TODO this is an exception that not handled
            e.printStackTrace();
        }
    }

    private void sendMessage(PrintWriter out, String message)
            throws IOException {
        out.println(message);
        out.println(socketMessageEndMark);
        out.flush();
    }
}
