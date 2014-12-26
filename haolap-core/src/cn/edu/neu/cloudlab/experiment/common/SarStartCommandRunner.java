package cn.edu.neu.cloudlab.experiment.common;

public class SarStartCommandRunner extends LinuxCommandRunner {
    @Override
    protected String executedCmd() {
        return "ssh " + serverName + " sar -u -v -q -d -b -m -w -r -n ALL 1 > "
                + outputPath + "&";
    }

}
