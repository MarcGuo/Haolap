package cn.edu.neu.cloudlab.haolap.runner;

import cn.edu.neu.cloudlab.ToolsRunner;
import cn.edu.neu.cloudlab.util.GetJavaProperty;

/**
 * Created by marc on 14/12/15.
 */
public class PropertiesRunner implements ToolsRunner {
    @Override
    public void run(String[] args) throws Exception {
        System.out.println(GetJavaProperty.getProperties(args));
    }

    @Override
    public String getName() {
        return "Properties Inspector";
    }
}
