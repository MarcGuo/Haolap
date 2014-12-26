package cn.edu.neu.cloudlab.experiment.common;

public class SarStopCommandRunner extends LinuxCommandRunner {

    @Override
    protected String executedCmd() {
        return "ssh " + serverName + " pkill sar";
    }

}
