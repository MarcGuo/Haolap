package cn.edu.neu.cloudlab.experiment.translator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public abstract class BlockProcess {

    private BlockProcess next;

    public BlockProcess(BlockProcess next) {
        this.next = next;
    }

    public void process(Block block, String outputPath) {
        // System.out.println(this.getClass().getName());
        if (block.match(this.keyWord())) {
            System.out.println(this.getClass().getName());
            this.output(this.retrieveValues(block), outputPath);
        } else {
            if (this.next != null) {
                this.next.process(block, outputPath);
            }

        }
    }

    protected abstract String keyWord();

    protected abstract String[] titles();

    protected abstract String logName();

    private void output(String outputString, String outputPath) {
        System.out.println(this.logName() + ":\t" + outputString);
        FileWriter fileWriter;
        String delimiter = "\n";
        try {
            fileWriter = new FileWriter(outputPath + "/" + this.logName(), true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(outputString + delimiter);
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String retrieveValues(Block block) {
        String[] titles = titles();
        StringBuffer result = new StringBuffer();
        for (String title : titles) {
            result.append(block.scan(title));
            result.append(",");
        }
        return result.toString();
    }

}
