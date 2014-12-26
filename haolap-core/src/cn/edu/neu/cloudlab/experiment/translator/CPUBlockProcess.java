package cn.edu.neu.cloudlab.experiment.translator;

public class CPUBlockProcess extends BlockProcess {

    private String keyword = "CPU";
    private String logName = "CPU";
    private String[] titles = {"%iowait", "%idle"};

    public CPUBlockProcess(BlockProcess next) {
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
