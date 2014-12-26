package cn.edu.neu.cloudlab.experiment.util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class PathFinder {
    private static Pattern pattern = Pattern.compile("^-?\\d+$");

    public static Path findlastIterationResult(FileSystem fs,
                                               Path iterationOutputPath) throws IOException {
        FileStatus[] status = fs.listStatus(iterationOutputPath);
        List<Integer> fileNumList = new ArrayList<Integer>();
        for (FileStatus fileStatus : status) {
            String fileName = fileStatus.getPath().getName();
            fileName = fileName.substring(1);
            if (pattern.matcher(fileName).matches())
                fileNumList.add(Integer.parseInt(fileName));
        }
        Collections.sort(fileNumList);
        int lastNum = fileNumList.get(fileNumList.size() - 1);
        return new Path(status[0].getPath().getParent().toString() + "/i"
                + lastNum);
    }

    public static Path findlastIterationResult(Path iterationOutputPath)
            throws IOException {
        FileSystem fs = null;
        try {
            fs = FileSystem.get(new Configuration());
            return findlastIterationResult(fs, iterationOutputPath);
        } catch (IOException e) {
            throw e;
        } finally {
            if (fs != null) {
                fs.close();
            }
        }
    }
}
