package cn.edu.neu.cloudlab.experiment.translator;

import java.io.*;
import java.util.Iterator;

public class MonitorFile implements Iterator<Block>, Iterable<Block> {

    boolean flag = true;
    private FileReader fr;
    private BufferedReader br;
    private String caseName;

    private StringBuffer block = new StringBuffer();

    public MonitorFile(File file) {
        initCaseName(file);
        initReader(file);
    }

    public static BlockProcess create() {
        return new CPUBlockProcess(new DiskBlockProcess(new QueueBlockProcess(
                new NetworkBlockProcess(new MemoryBlockProcess(null)))));
    }

    public String caseName() {
        return this.caseName;
    }

    private void initCaseName(File file) {
        caseName = file.getName();// substring
    }

    private void initReader(File file) {
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean hasNext() {
        try {
            String tmp;
            this.block.delete(0, this.block.length());
            do {
                tmp = br.readLine();
                if (null == tmp)
                    return false;
                if (tmp.contains(" lo ") || tmp.contains(" sit0 ")) {
                    continue;
                }
                block.append(" ");
                block.append(tmp);
            } while (!"".equals(tmp.trim()));

            if ("".equals(block.toString().trim())) {
                if (br != null) {
                    br.close();
                }
                if (fr != null) {
                    fr.close();
                }
                return false;
            } else {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Block next() {
        return new Block(block.toString());
    }

    @Override
    public Iterator<Block> iterator() {
        return this;
    }

    public String getCaseName() {
        return caseName;
    }

}
