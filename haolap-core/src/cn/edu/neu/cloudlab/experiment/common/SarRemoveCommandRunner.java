package cn.edu.neu.cloudlab.experiment.common;

import cn.edu.neu.cloudlab.experiment.ExperimentConfiguration;

public class SarRemoveCommandRunner extends LinuxCommandRunner {

    @Override
    protected String executedCmd() {
        if (getServerName().equals(ExperimentConfiguration.SERVER_NAMES[0])) {
            return "ls";
        }
        String cmd = "ssh " + getServerName() + " rm " + getOutputPath();
        return cmd;
    }

}
