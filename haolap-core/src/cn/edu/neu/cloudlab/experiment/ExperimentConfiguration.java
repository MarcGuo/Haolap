package cn.edu.neu.cloudlab.experiment;

/**
 * Created by marc on 5/29/14.
 */
public class ExperimentConfiguration {
    public static int[] THREAD_NUMS = {1};
    public static String[] SERVER_NAMES = new String[]{"cloud00",
            "cloud01", "cloud02", "cloud03", "cloud04", "cloud05", "cloud06",
            "cloud07", "cloud08", "cloud09", "cloud10", "cloud11", "cloud12"};
    public static int SERVER_SIZE = SERVER_NAMES.length;
    public static String OUTPUT_BASE_PATH = "~/output";
    public static String OUTPUT_RESULT_FILE_NAME = "result.csv";

    static {
        //Configuration conf = CubeConfiguration.getConfiguration();

    }

}
