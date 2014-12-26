package cn.edu.neu.cloudlab.experiment;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class TestCase {
    protected Log log;
    private String name;

    public TestCase() {
        this.name = this.getClass().getSimpleName() + "_1";
    }

    public void start() {
        log = LogFactory.getLog(getClass());
        try {
            doProcess();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
        }
    }

    protected abstract void doProcess() throws Exception;

    public String getTestCaseName() {
        return this.name;
    }

    public void setTestCaseName(String name) {
        this.name = name;
    }
}
