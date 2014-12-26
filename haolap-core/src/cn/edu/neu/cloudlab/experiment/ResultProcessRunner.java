package cn.edu.neu.cloudlab.experiment;

import cn.edu.neu.cloudlab.ToolsRunner;
import cn.edu.neu.cloudlab.experiment.translator.ResultProcess;

/**
 * Created by marc on 6/12/14.
 */
public class ResultProcessRunner implements ToolsRunner {
    @Override
    public void run(String[] args) throws Exception {
        ResultProcess.generateResults(ExperimentConfiguration.OUTPUT_BASE_PATH, ExperimentConfiguration.OUTPUT_BASE_PATH + "/");
    }

    @Override
    public String getName() {
        return "Sar Result Processor";
    }
}
