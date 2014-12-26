package cn.edu.neu.cloudlab.experiment.common;

import cn.edu.neu.cloudlab.experiment.ExperimentConfiguration;

/**
 * Created by marc on 6/12/14.
 */
public class MakeBaseOutputDirCommand extends LinuxCommandRunner {
    @Override
    protected String executedCmd() {
        return "ssh " + getServerName() + " mkdir -p " + ExperimentConfiguration.OUTPUT_BASE_PATH;
    }
}
