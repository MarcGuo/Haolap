package cn.edu.neu.cloudlab.experiment;

import org.apache.commons.logging.LogFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class MultiThreadTestCase extends TestCase {
    protected CountDownLatch end;
    private int threadSize;

    public MultiThreadTestCase(CountDownLatch end, int threadSize) {
        super();
        this.end = end;
        this.threadSize = threadSize;
        this.setTestCaseName(this.getClass().getSimpleName() + "_" + this.threadSize);
    }

    public MultiThreadTestCase() {
        super();
        this.setTestCaseName(this.getClass().getSimpleName() + "_multi");
    }

    public void start() {
        log = LogFactory.getLog(getClass());
        ExecutorService exeService = Executors.newFixedThreadPool(this.threadSize);
        for (int i = 0; i < threadSize; i++) {
            exeService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        process();
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.error(e);
                    }
                }
            });
        }
        exeService.shutdown();
    }

    protected void process() throws Exception {
        try {
            doProcess();
        } catch (Exception e) {
            throw e;
        } finally {
            end.countDown();
        }
    }

    protected abstract void doProcess() throws Exception;

    public void setEnd(CountDownLatch end) {
        this.end = end;
    }

    public void setThreadSizeAndChangeTheNameOfTheCase(int threadSize) {
        setThreadSize(threadSize);
        this.setTestCaseName(this.getClass().getSimpleName() + "_" + this.threadSize);
    }

    public void setThreadSize(int threadSize) {
        this.threadSize = threadSize;
        this.setTestCaseName(this.getClass().getSimpleName() + "_" + this.threadSize);
    }
}
