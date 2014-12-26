package cn.edu.neu.cloudlab.experiment.translator;

public class NetworkBlockProcess extends BlockProcess {

    private String keyword = "IFACE";
    private String logName = "IFace";
    private String[] titles = {"rxkB/s", "txkB/s"};

    public NetworkBlockProcess(BlockProcess next) {
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
