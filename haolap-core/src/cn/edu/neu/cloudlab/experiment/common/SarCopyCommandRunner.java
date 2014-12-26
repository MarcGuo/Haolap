package cn.edu.neu.cloudlab.experiment.common;

import cn.edu.neu.cloudlab.experiment.ExperimentConfiguration;

/**
 * Created by marc on 6/12/14.
 */
public class SarCopyCommandRunner extends LinuxCommandRunner {

    @Override
    protected String executedCmd() {
        if (this.serverName.equals(ExperimentConfiguration.SERVER_NAMES[0])) {
            return "ls";
        }

        String cmd = "scp " + this.getServerName() + ":" + this.getOutputPath() + " " + ExperimentConfiguration.OUTPUT_BASE_PATH;
        return cmd;
    }

}
