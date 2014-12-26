package cn.edu.neu.cloudlab.experiment.util;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import java.io.File;
import java.io.IOException;

/**
 * Created by Marc Guo on 2014/10/8.
 */
public class PathHelper {
    public static void prepareTheOutputPath(String fileOutputPath) throws Exception {
        Preconditions.checkNotNull(Strings.isNullOrEmpty(fileOutputPath));
        File outputDir = new File(fileOutputPath);
        if (!outputDir.exists() && !outputDir.mkdirs()) {
            throw new IOException(
                    "The output dir does not exist or cannot created");
        }
        if (!outputDir.isDirectory()) {
            throw new IOException("The output path cannot be a file");
        }
    }
}

