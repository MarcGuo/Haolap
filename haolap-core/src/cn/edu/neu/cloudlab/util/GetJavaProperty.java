package cn.edu.neu.cloudlab.util;

/**
 * Created by marc on 14/12/15.
 */
public class GetJavaProperty {
    public static String getProperties(String args[]) {
        StringBuilder propertiesStr = new StringBuilder();
        if (args.length == 0) {
            for (Object prop : System.getProperties().keySet()) {
                propertiesStr.append(prop + "=" + System.getProperty((String) prop, "")).append("\n");
            }
        } else {
            for (String prop : args) {
                propertiesStr.append(System.getProperty(prop, "")).append("\n");
            }
        }
        return propertiesStr.toString();
    }
}
