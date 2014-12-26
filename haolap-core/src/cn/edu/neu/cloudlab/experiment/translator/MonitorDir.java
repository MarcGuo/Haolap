package cn.edu.neu.cloudlab.experiment.translator;

import java.io.File;
import java.util.Iterator;

public class MonitorDir implements Iterable<MonitorFile> {

    private File[] configFiles;

    public MonitorDir(String dirPath) {
        File dir = new File(dirPath);
        this.configFiles = dir.listFiles();
    }

    @Override
    public Iterator<MonitorFile> iterator() {
        return new MonitorFileIterator();
    }

    private class MonitorFileIterator implements Iterator<MonitorFile> {
        int index = 0;

        @Override
        public boolean hasNext() {
            if (index < configFiles.length) {
                return true;
            }
            return false;
        }

        @Override
        public MonitorFile next() {
            return new MonitorFile(configFiles[index++]);
        }

        @Override
        public void remove() {
            throw new RuntimeException("this method is unimplemented");
        }

    }

}
