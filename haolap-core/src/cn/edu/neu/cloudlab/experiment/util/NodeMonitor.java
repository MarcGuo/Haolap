package cn.edu.neu.cloudlab.experiment.util;

import cn.edu.neu.cloudlab.experiment.ExperimentConfiguration;
import cn.edu.neu.cloudlab.experiment.common.NodeMonitorOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NodeMonitor {
    protected Log log = LogFactory.getLog(getClass());
    private String resultOutputFile;
    private String testCaseName;
    private long tStart = 0L;
    private NodeMonitorOperation startOperation;
    private NodeMonitorOperation stopOperation;

    public NodeMonitor(String testCaseName, NodeMonitorOperation start, NodeMonitorOperation stop) {
        this.resultOutputFile = getOutputBasePath() + ExperimentConfiguration.OUTPUT_RESULT_FILE_NAME;
        this.testCaseName = testCaseName;
        this.startOperation = start;
        this.stopOperation = stop;
    }

    public void strat() {
        if (startOperation != null) {
            startOperation.doOperation(this);
        }
        tStart = System.currentTimeMillis();
    }

    public void stop() {
        this.logRuningTime();
        if (stopOperation != null) {
            stopOperation.doOperation(this);
        }
    }

    public void logRuningTime() {
        double tEnd = (System.currentTimeMillis() - tStart) / 1000.0;
        try {
            File logFile = new File(resultOutputFile);
            if (!logFile.exists()) {
                logFile.createNewFile();
            }
            FileWriter fw = new FileWriter(logFile, true);
            String runningTime = String.format("%s,%s,%f\r\n",
                    new SimpleDateFormat("yy-MM-dd:hh:mm:ss")
                            .format(new Date()), testCaseName, tEnd);
            log.info(runningTime);
            fw.write(runningTime);
            fw.flush();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getOutputBasePath() {
        if (ExperimentConfiguration.OUTPUT_BASE_PATH.endsWith("/")) {
            return ExperimentConfiguration.OUTPUT_BASE_PATH;
        } else {
            return ExperimentConfiguration.OUTPUT_BASE_PATH + "/";
        }
    }

    public String getResultOutputFile() {
        return this.resultOutputFile;
    }

    public String getTestCaseName() {
        return this.testCaseName;
    }
}
