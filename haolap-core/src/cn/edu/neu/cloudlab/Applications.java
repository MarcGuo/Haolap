package cn.edu.neu.cloudlab;

import cn.edu.neu.cloudlab.experiment.ResultProcessRunner;
import cn.edu.neu.cloudlab.haolap.runner.JobServerRunner;
import cn.edu.neu.cloudlab.haolap.runner.MetaDataServerRunner;
import cn.edu.neu.cloudlab.haolap.runner.PropertiesRunner;
import cn.edu.neu.cloudlab.haolap.runner.SchemaServerRunner;
import com.google.common.base.Preconditions;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Applications {
    private static Log log = LogFactory.getLog(Applications.class);

    public static void main(String[] args) throws Exception {
        //System.err.println("ARGS:"+Arrays.toString(args));
        for (String key : System.getenv().keySet()) {
            System.out.println(key + " " + System.getenv(key));
        }
        Map<String, List<String>> argsMap = prepareArgs(args);
        if (argsMap.containsKey("help")) {
            printUsage();
            System.exit(0);
        }
        //printAllParameter(argsMap);

        // System.out.println(argsMap.get("app").size());

        ToolsRunner runningTool = getRunnerTool(argsMap.get("app") == null
                || argsMap.get("app").size() == 0 ? null : argsMap.get("app")
                .get(0));
        log.info("Starting " + runningTool.getName());

        String[] appArgs = new String[argsMap.get("args").size()];
        for (int i = 0; i < appArgs.length; i++) {
            appArgs[i] = argsMap.get("args").get(i).trim();
        }
        runningTool.run(appArgs);
    }

    public static ToolsRunner getRunnerTool(String actionName) {

        Preconditions.checkNotNull(actionName, "No Action is set. Please see usage:\n" + getUsageStr());
        Preconditions.checkArgument(
                actionName.equals("sar") || actionName.equals("jobserver") || actionName.equals("schemaserver") || actionName.equals("metadataserver") || actionName.equals("property"),
                "app:{jobserver/schemaserver/metadataserver/sar/property}");
        if ("sar".equals(actionName)) {
            return new ResultProcessRunner();
        } else if ("jobserver".equals(actionName)) {
            return new JobServerRunner();
        } else if ("metadataserver".equals(actionName)) {
            return new MetaDataServerRunner();
        } else if ("schemaserver".equals(actionName)) {
            return new SchemaServerRunner();
        } else if ("property".equals(actionName)) {
            return new PropertiesRunner();
        }
        return null;
    }


    public static Map<String, List<String>> prepareArgs(String[] args) {
        Map<String, List<String>> preparedArgs = new HashMap<String, List<String>>();
        List<String> values = null;
        String key = null;
        for (String arg : args) {
            if (arg.startsWith("--")) {
                key = arg.substring(2);
                values = new ArrayList<String>();
                if (key != null) {
                    preparedArgs.put(key, values);
                }
            } else {
                if (values != null) {
                    values.add(arg);
                }
            }
        }
        if (!preparedArgs.containsKey("args")) {
            preparedArgs.put("args", new ArrayList<String>());
        }
        return preparedArgs;
    }

    public static void printUsage() {
        System.err.println(getUsageStr());

    }

    public static String getUsageStr() {
        StringBuilder usageBuilder = new StringBuilder();
        usageBuilder.append("\n").append("Usage : ").append('\n');
        usageBuilder.append("--app {sar} ").append('\n');
        usageBuilder.append("--args arg1...argn ").append('\n');
        usageBuilder.append('\n');
        usageBuilder
                .append("args 4 pagerankgen: <output> <nodesAmount> [mapper-amount,default:12] ")
                .append('\n');

        return usageBuilder.toString();
    }

    public static void printAllParameter(Map<String, List<String>> parameters) {
        for (String key : parameters.keySet()) {
            System.out.println(key);
            for (String val : parameters.get(key)) {
                System.out.print(" " + val);
            }
        }
    }

}
