package cn.edu.neu.cloudlab.experiment.common;

import cn.edu.neu.cloudlab.experiment.ExperimentConfiguration;
import cn.edu.neu.cloudlab.experiment.NodeCommandConfiguration;
import cn.edu.neu.cloudlab.experiment.util.NodeMonitor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NodeLinuxCommandOperator implements NodeMonitorOperation {
    List<LinuxCommandRunner> linuxCommandRunners;

    public NodeLinuxCommandOperator() {
        this.linuxCommandRunners = new ArrayList<LinuxCommandRunner>();
    }

    public List<LinuxCommandRunner> getLinuxCommandRunner() {
        return linuxCommandRunners;
    }

    public NodeLinuxCommandOperator addLinuxCommandRunner(LinuxCommandRunner linuxCommandRunner) {
        this.linuxCommandRunners.add(linuxCommandRunner);
        return this;
    }

    @Override
    public void doOperation(NodeMonitor monitor) {
        for (LinuxCommandRunner runner : this.linuxCommandRunners) {
            ExecutorService exeService = Executors
                    .newFixedThreadPool(ExperimentConfiguration.SERVER_SIZE);
            for (String serverName : ExperimentConfiguration.SERVER_NAMES) {
                String monitorOutputPath = monitor.getOutputBasePath() + serverName + "." + monitor.getTestCaseName() + "." + NodeCommandConfiguration.SAR_MONITOR_FILE_NAME;
                runner.setOutputPath(monitorOutputPath);
                runner.setServerName(serverName);
                runner.run();
                exeService.execute(runner);
            }
            exeService.shutdown();
        }

    }
}
