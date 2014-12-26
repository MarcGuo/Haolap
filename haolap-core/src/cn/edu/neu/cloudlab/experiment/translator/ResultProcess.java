package cn.edu.neu.cloudlab.experiment.translator;

import java.io.File;

public final class ResultProcess {

    // private static String dirPath = "D:/Datasource";
    // private static String outputPath = "D:/Datasource/";

    public static void generateResults(String dirPath, String outputDir) {
        MonitorDir dir = new MonitorDir(dirPath);
        for (MonitorFile file : dir) {
            // one file is a case
            String caseName = file.caseName();
            File outputDirectory = new File(outputDir + caseName + ".output");
            outputDirectory.mkdirs();
            for (Block block : file) {
                create().process(block, outputDirectory.getPath());
            }
        }
    }

    private static BlockProcess create() {
        return new CPUBlockProcess(new DiskBlockProcess(new QueueBlockProcess(
                new NetworkBlockProcess(new MemoryBlockProcess(null)))));
    }

}
