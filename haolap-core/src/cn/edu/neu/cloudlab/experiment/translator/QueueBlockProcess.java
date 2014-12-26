package cn.edu.neu.cloudlab.experiment.translator;

public class QueueBlockProcess extends BlockProcess {

    private String keyword = "runq-sz";
    private String logName = "Queue";
    private String[] titles = {"runq-sz", "blocked"};

    public QueueBlockProcess(BlockProcess next) {
        super(next);
    }

    @Override
    protected String keyWord() {
        return this.keyword;
    }

    @Override
    protected String logName() {
        return this.logName;
    }

    @Override
    protected String[] titles() {
        return this.titles;
    }

}
