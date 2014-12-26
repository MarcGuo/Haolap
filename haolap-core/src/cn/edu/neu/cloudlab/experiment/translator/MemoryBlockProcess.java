package cn.edu.neu.cloudlab.experiment.translator;

public class MemoryBlockProcess extends BlockProcess {

    private String keyword = "kbmemfree";
    private String logName = "Memory";
    private String[] titles = {"%memused"};

    public MemoryBlockProcess(BlockProcess next) {
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
