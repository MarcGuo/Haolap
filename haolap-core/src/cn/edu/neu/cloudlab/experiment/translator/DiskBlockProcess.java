package cn.edu.neu.cloudlab.experiment.translator;

public class DiskBlockProcess extends BlockProcess {

    private String keyword = "DEV";
    private String logName = "Disk";
    private String[] titles = {"rd_sec/s", "wr_sec/s", "%util"};

    public DiskBlockProcess(BlockProcess next) {
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
