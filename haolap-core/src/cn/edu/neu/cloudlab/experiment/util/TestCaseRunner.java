package cn.edu.neu.cloudlab.experiment.util;

import cn.edu.neu.cloudlab.experiment.MultiThreadTestCase;
import cn.edu.neu.cloudlab.experiment.TestCase;
import cn.edu.neu.cloudlab.experiment.common.EmptyOperator;
import cn.edu.neu.cloudlab.experiment.common.NodeMonitorOperation;
import com.google.common.base.Preconditions;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.concurrent.CountDownLatch;

/**
 * Created by MarcGuo on 6/1/14.
 */
public class TestCaseRunner {
    private static Log log = LogFactory.getLog(TestCaseRunner.class);

    public static void runTestCases(Class<? extends TestCase>[] clzs, NodeMonitorOperation startOperator, NodeMonitorOperation stopOperator) {
        Preconditions.checkArgument(clzs != null && clzs.length != 0, "There is no tests need to be executed");
        for (Class<? extends TestCase> clz : clzs) {
            try {
                String taskname = clz.getSimpleName() + "_" + 1;
                log.debug("Running Task " + taskname);
                runTestCase(clz, taskname, startOperator, stopOperator);
            } catch (Exception e) {
                log.error(e);
            }
        }
    }

    public static void runTestCases(TestCase[] cases, NodeMonitorOperation startOperator, NodeMonitorOperation stopOperator) {
        Preconditions.checkArgument(cases != null && cases.length != 0, "There is no tests need to be executed");
        for (TestCase caze : cases) {
            try {
                String taskname = caze.getClass().getSimpleName() + "_" + 1;
                log.debug("Running Task " + taskname);
                runTestCase(caze, taskname, startOperator, stopOperator);
            } catch (Exception e) {
                log.error(e);
            }
        }
    }

    public static void runTestCase(Class<? extends TestCase> cls, String name, NodeMonitorOperation startOperator, NodeMonitorOperation stopOperator) throws Exception {
        TestCase cases = cls.newInstance();
        NodeMonitor monitor = new NodeMonitor(name, startOperator, stopOperator);
        monitor.strat();
        cases.start();
        monitor.stop();
    }

    public static void runTestCase(TestCase testCase, String name, NodeMonitorOperation startOperator, NodeMonitorOperation stopOperator) throws Exception {
        NodeMonitor monitor = new NodeMonitor(name, startOperator, stopOperator);
        monitor.strat();
        testCase.start();
        monitor.stop();
    }

    public static void runMultiThreadTestCases(Class<? extends MultiThreadTestCase>[] clzs, int[] threadNumbers, NodeMonitorOperation startOperator, NodeMonitorOperation stopOperator) {
        Preconditions.checkArgument(clzs != null && clzs.length != 0, "There is no tests need to be executed");
        for (Class<? extends MultiThreadTestCase> clz : clzs) {
            for (int thread : threadNumbers) {
                try {
                    String taskname = clz.getSimpleName() + "_" + thread;
                    log.debug("Running Task " + taskname);
                    runMultiThreadTestCase(clz, thread, taskname, startOperator, stopOperator);
                } catch (Exception e) {
                    log.error(e);
                }
            }
        }
    }

    public static void runMultiThreadTestCases(MultiThreadTestCase[] multiThreadTestCases, int[] threadNumbers, NodeMonitorOperation startOperator, NodeMonitorOperation stopOperator) {
        Preconditions.checkArgument(multiThreadTestCases != null && multiThreadTestCases.length != 0, "There is no tests need to be executed");
        for (MultiThreadTestCase multiThreadTestCase : multiThreadTestCases) {
            for (int thread : threadNumbers) {
                try {
                    String taskname = multiThreadTestCase.getClass().getSimpleName() + "_" + thread;
                    log.debug("Running Task " + taskname);
                    runMultiThreadTestCase(multiThreadTestCase, thread, taskname, startOperator, stopOperator);
                } catch (Exception e) {
                    log.error(e);
                }
            }
        }
    }

    public static void runMultiThreadTestCase(Class<? extends MultiThreadTestCase> cls,
                                              int threadSize, String name, NodeMonitorOperation startOperator, NodeMonitorOperation stopOperator) throws Exception {
        CountDownLatch end = new CountDownLatch(threadSize);
        MultiThreadTestCase cases = cls.newInstance();
        cases.setEnd(end);
        cases.setThreadSize(threadSize);
        NodeMonitor monitor = new NodeMonitor(name, new EmptyOperator(), new EmptyOperator());
        monitor.strat();
        cases.start();
        end.await();
        monitor.stop();
    }

    public static void runMultiThreadTestCase(MultiThreadTestCase multiTestCase,
                                              int threadSize, String name, NodeMonitorOperation startOperator, NodeMonitorOperation stopOperator) throws Exception {
        CountDownLatch end = new CountDownLatch(threadSize);
        multiTestCase.setEnd(end);
        multiTestCase.setThreadSize(threadSize);
        NodeMonitor monitor = new NodeMonitor(name, new EmptyOperator(), new EmptyOperator());
        monitor.strat();
        multiTestCase.start();
        end.await();
        monitor.stop();
    }

}
