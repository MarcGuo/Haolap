package cn.edu.neu.cloudlab.experiment.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.InputStreamReader;
import java.io.LineNumberReader;

public abstract class LinuxCommandRunner implements Runnable {
    protected Log log = LogFactory.getLog(getClass());
    protected String lineSeparator = System.getProperty("line.separator", "\n");
    protected String serverName;

    protected String outputPath;

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    @Override
    public void run() {
        try {
            String cmd = executedCmd();
//            System.out.println(cmd);
            String rt = runCommand(cmd, false);
//            System.out.println(rt);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    protected abstract String executedCmd();

    private String runCommand(String cmd, boolean result) {
        StringBuilder buf = new StringBuilder();
        String rt = "-1";
        try {
            Process pos = Runtime.getRuntime().exec(cmd);
            pos.waitFor();
            if (!result) {
                rt = String.valueOf(pos.exitValue());
            } else {
                InputStreamReader ir = new InputStreamReader(
                        pos.getInputStream());
                LineNumberReader input = new LineNumberReader(ir);
                String ln = null;
                while ((ln = input.readLine()) != null) {
                    buf.append(ln + lineSeparator);
                }
                rt = buf.toString();

                InputStreamReader eir = new InputStreamReader(
                        pos.getErrorStream());
                LineNumberReader eInput = new LineNumberReader(eir);
                ln = null;
                buf.delete(0, buf.length());
                while ((ln = eInput.readLine()) != null) {
                    buf.append(ln + lineSeparator);
                }
                rt += buf.toString();

                input.close();
                eInput.close();
                eir.close();
                ir.close();
                pos.destroy();
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return rt;
    }
}