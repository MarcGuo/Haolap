package cn.edu.neu.cloudlab.haolap.mapreduce;

import cn.edu.neu.cloudlab.haolap.util.PathConf;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.List;

public class HFJobCreator {
    public Job createAggregationJob(Configuration conf, long numberOfPages,
                                    List<Long> usedPageNos, String fromCubeIdentifier,
                                    String newCubeIdentifier) throws IOException {
        Job aggregationJob = new Job(conf, "HFAggregation");

        aggregationJob.setMapOutputKeyClass(Text.class);
        aggregationJob.setMapOutputValueClass(DoubleWritable.class);
        aggregationJob.setOutputKeyClass(Text.class);
        aggregationJob.setOutputValueClass(DoubleWritable.class);

        aggregationJob.setMapperClass(HFMapper.class);
        aggregationJob.setPartitionerClass(HFPartitioner.class);
        aggregationJob.setReducerClass(HFReducerImpl.class);

        aggregationJob.setInputFormatClass(HFInputFormat.class);
        aggregationJob.setOutputFormatClass(HFOutputFormat.class);

        // TODO the numberOfReduceTasks can only be int
        aggregationJob.setNumReduceTasks((int) numberOfPages);

        String inputPathStr = PathConf.getCubeElementPath()
                + fromCubeIdentifier + "/";
        String outputPathStr = PathConf.getCubeElementPath()
                + newCubeIdentifier + "/";

        for (long l : usedPageNos) {
            FileInputFormat.addInputPath(aggregationJob, new Path(inputPathStr
                    + l + "/data"));
        }
        FileOutputFormat.setOutputPath(aggregationJob, new Path(outputPathStr));

        // if (!aggregationJob.isSuccessful()) {
        // System.err.println("aggregation job not completed successfully");
        // }
        //
        // Job pageCutingJob = new Job(conf, "HFPageCuting");
        //
        // pageCutingJob.setOutputKeyClass(LongWritable.class);
        // pageCutingJob.setOutputValueClass(DoubleWritable.class);
        return aggregationJob;
    }
}
