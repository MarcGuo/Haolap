package cn.edu.neu.cloudlab.haolap.socket;

import cn.edu.neu.cloudlab.haolap.exception.*;
import cn.edu.neu.cloudlab.haolap.requestDealer.JobNodeRequestDealer;
import cn.edu.neu.cloudlab.haolap.util.SystemConf;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class JobServerRequestDealer implements Runnable {
    private static final String socketMessageEndMark = SystemConf
            .getSocketMessageEndMark();
    private Socket connection = null;
    private JobNodeRequestDealer jobNodeRequestDealer;
    private PrintWriter out;
    private BufferedReader in;

    public JobServerRequestDealer(Socket connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        try {
            // get input and output streams
            out = new PrintWriter(connection.getOutputStream());
            out.flush();
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String message = "";
            String line = "";
            do {
                System.out.println("readLine() arrived.");
                line = in.readLine();
                System.out.println("line: " + line);
                if (!("\3".equals(line))) {
                    message += line;
                }
            } while (null != line && !(socketMessageEndMark.equals(line)));
            System.out.println(message);
            jobNodeRequestDealer = new JobNodeRequestDealer(message);
            jobNodeRequestDealer.deal();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                sendMessage(out,
                        generateCommandInvalidReturnXml("socket error"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (XmlTypeErrorException e) {
            e.printStackTrace();
            try {
                sendMessage(out,
                        generateCommandInvalidReturnXml("XmlType error"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (CubeNotExistsException e) {
            e.printStackTrace();
            try {
                sendMessage(out,
                        generateCommandInvalidReturnXml("CubeNotExists error"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (SchemaNotExistsException e) {
            e.printStackTrace();
            try {
                sendMessage(out,
                        generateCommandInvalidReturnXml("SchemaNotExist error"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (AggregationTypeInvalidException e) {
            e.printStackTrace();
            try {
                sendMessage(
                        out,
                        generateCommandInvalidReturnXml("AggregationTypeInvalid error"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (CorrespondingDimensionNotExistsException e) {
            e.printStackTrace();
            try {
                sendMessage(
                        out,
                        generateCommandInvalidReturnXml("CorrespondingDimensionNotExists error"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (CorrespondingLevelNotExistsException e) {
            e.printStackTrace();
            try {
                sendMessage(
                        out,
                        generateCommandInvalidReturnXml("CorrespondingLevelNotExists error"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (SizeNotEqualException e) {
            e.printStackTrace();
            try {
                sendMessage(out,
                        generateCommandInvalidReturnXml("SizeNotEqual error"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (DocumentException e) {
            e.printStackTrace();
            try {
                sendMessage(out,
                        generateCommandInvalidReturnXml("XmlGeneration error"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (InvalidXmlException e) {
            // TODO this is an exception that not handled
            e.printStackTrace();
        }

        // send back first success message
        try {
            sendMessage(out, generateCommandValidReturnXml());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // run job
        try {
            jobNodeRequestDealer.runJob();
            jobNodeRequestDealer.cleanCubeDirectory();
            jobNodeRequestDealer.updateToSchemaServer();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                sendMessage(out, generateJobFailedReturnXml("mapreduce error"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            try {
                sendMessage(out, generateJobFailedReturnXml("mapreduce error"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            try {
                sendMessage(out, generateJobFailedReturnXml("mapreduce error"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (SchemaAlreadyExistsException e) {
            e.printStackTrace();
            try {
                sendMessage(
                        out,
                        generateJobFailedReturnXml("SchemaAlreadyExists in update period error"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (CubeAlreadyExistsException e) {
            e.printStackTrace();
            try {
                sendMessage(
                        out,
                        generateJobFailedReturnXml("CubeAlreadyExists in update period error"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (CubeNotExistsException e) {
            e.printStackTrace();
            e.printStackTrace();
            try {
                sendMessage(
                        out,
                        generateJobFailedReturnXml("FromCubeNotExists in update period error"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        // send back second success message
        try {
            if (jobNodeRequestDealer.isJobSuccess()) {
                sendMessage(out, generateJobSucceedReturnXml());
            } else {
                sendMessage(out, generateJobSucceedReturnXml());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendMessage(PrintWriter out, String message)
            throws IOException {
        out.println(message);
        out.println(socketMessageEndMark);
        out.flush();
    }

    private String generateCommandValidReturnXml() {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("properties");
        root.addElement("success").addText("true");
        root.addElement("timestamp").addText(
                jobNodeRequestDealer.getTimestamp());
        return document.asXML();
    }

    private String generateCommandInvalidReturnXml(String errorMessage) {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("properties");
        root.addElement("success").addText("false");
        root.addElement("timestamp").addText(
                jobNodeRequestDealer.getTimestamp());
        Element errorsNode = root.addElement("errors");
        errorsNode.addElement("error").addText(errorMessage);
        return document.asXML();
    }

    private String generateJobSucceedReturnXml() {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("properties");
        root.addElement("success").addText("true");
        root.addElement("timestamp").addText(
                jobNodeRequestDealer.getTimestamp());
        root.addElement("cube").addText(
                jobNodeRequestDealer.getNewCubeIdentifier());
        return document.asXML();
    }

    private String generateJobFailedReturnXml(String errorMessage) {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("properties");
        root.addElement("success").addText("false");
        root.addElement("timestamp").addText(
                jobNodeRequestDealer.getTimestamp());
        Element errorsNode = root.addElement("errors");
        errorsNode.addElement("error").addText(errorMessage);
        return document.asXML();
    }
}
